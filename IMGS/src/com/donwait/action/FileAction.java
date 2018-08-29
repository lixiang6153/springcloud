package com.donwait.action;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.donwait.error.ErrorCode;
import com.donwait.file.FastDFSClient;
import com.donwait.log.ImgRecordHandler;
import com.donwait.model.Image;
import com.donwait.util.ReturnValue;

/**
 * 计算文件上传进度及上传文件，配合MyJakartaMultiPartRequest.java使用
 * @author Administrator
 *
 */
@Controller
@Scope(value = "prototype")
public class FileAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String msg;									// 文件进度获取
	private String fileRelativePath;					// 删除文件文件相对路径
	//@Autowired
	//private ImageActionHandler imageActionHandler;		// 图片服务
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public String getFileRelativePath() {
		return fileRelativePath;
	}
	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}
	
	/**
	 * 功能：从FDFS中删除文件
	 * @param group1/M00/00/00/wKgMGVpS_IKAb8H5AAAAHjGinYM407.txt
	 * @return ReturnValue
	 * @throws IOException,JSONException
	*/
	public String delete() throws IOException, JSONException{
		try {
			if(null != fileRelativePath && (fileRelativePath.startsWith("/group") || fileRelativePath.startsWith("group"))){
				if(fileRelativePath.startsWith("/group")){
					fileRelativePath = fileRelativePath.substring(1);
				}
				
				int pos = fileRelativePath.indexOf("/");
				String groupName = fileRelativePath.substring(0, pos);
				String subPath = fileRelativePath.substring(pos+1);
				
				// 从FastDFS中删除对应文件
				if(FastDFSClient.deleteFile(groupName, subPath)){
					setAjaxResponse(new ReturnValue());
				}else{
					setAjaxResponse(new ReturnValue(ErrorCode.ERROR_DELETE_FAIL));
				}
			}else{
				setAjaxResponse(new ReturnValue(ErrorCode.ERROR_INVALID_PARAM));
			}
							    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	/**
	 * 保存文件--临时文件
	 * @param
	 * @return
	 * @throws JSONException 
	 */
	public String upload() throws IOException, JSONException {
		// 获取文件上传的URL和大小
		HttpServletRequest request = ServletActionContext.getRequest();
		String strUrl = (String)request.getAttribute("url");
		Long size = (long)request.getAttribute("size");
		
		// 上传文件成功
		if(!strUrl.isEmpty()){
			Image image = new Image(0, strUrl, size != null ? size.intValue() : 0);
			
			// 异步添加记录防止卡死
			ImgRecordHandler.addRecord(image);
			setAjaxResponse(new ReturnValue(strUrl));
			
//			if(imageActionHandler.add(image)){
//				setAjaxResponse(new ReturnValue(strUrl));
//			}else{
//				setAjaxResponse(new ReturnValue(ErrorCode.ERROR_ADD_FAIL));
//			}
		}else{
			setAjaxResponse(new ReturnValue(ErrorCode.ERROR_SERVER_ERROR));
		}		
		return SUCCESS;				
	}
	
	/**
	 * 保存文件--永久文件（暂时不记录在库）
	 * @param
	 * @return
	 * @throws JSONException 
	 */
	public String upload2() throws IOException, JSONException {
		// 获取文件上传的URL和大小
		HttpServletRequest request = ServletActionContext.getRequest();
		String strUrl = (String)request.getAttribute("url");
		/*Long size = (long)request.getAttribute("size");*/
		String exception = (String) request.getAttribute("exception");
		if(exception != null){
			setAjaxResponse(new ReturnValue(ErrorCode.ERROR_SERVER_ERROR,exception));
		}else{
			// 上传文件成功
			if(strUrl != null && !strUrl.trim().equals("")){
				setAjaxResponse(new ReturnValue(strUrl));
			}else{
				setAjaxResponse(new ReturnValue("the param url of img is null"));
			}		
		}
		return SUCCESS;
	}
	
	/**
	 * 方法  listenPresent 监听文件上传的进度
	 * @return perStr String 上传进度百分比
	 * @throws JSONException 
	 */
	public String listenPresent() throws JSONException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String perStr = (String) request.getSession().getAttribute("percent");
		msg = perStr;
		setAjaxResponse(new ReturnValue(msg));
		return SUCCESS;
	}
	/**
	 * 方法 clearlistenPresent 清空进度缓存数据
	 * @return
	 * @throws JSONException 
	 */
	public String clearlistenPresent() throws JSONException {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("percent", "0%");
		msg = "true";
		setAjaxResponse(new ReturnValue(msg));
		return SUCCESS;
	}
	
}
