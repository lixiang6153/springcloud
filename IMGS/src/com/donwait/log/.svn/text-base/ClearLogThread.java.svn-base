package com.donwait.log;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.donwait.actionHandler.ImageActionHandler;
import com.donwait.file.FastDFSClient;
import com.donwait.model.Image;
import com.donwait.util.DateUtil;

/**
 * 日誌清理線程
 * @author Administrator
 *
 */

public class ClearLogThread extends TimerTask{
	// 默认保存3个月图片
	private int saveDays = 90;
	private ImageActionHandler imageActionservice;
	private Logger logger = Logger.getLogger(ClearLogThread.class);

	public ClearLogThread(ImageActionHandler service,int days){
		this.imageActionservice = service;
		this.saveDays = days;
	}
	
	public ImageActionHandler getImageActionservice() {
		return imageActionservice;
	}


	public void setImageActionservice(ImageActionHandler imageActionservice) {
		this.imageActionservice = imageActionservice;
	}

	public int getSaveDays() {
		return saveDays;
	}

	public void setSaveDays(int saveDays) {
		this.saveDays = saveDays;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// 获取删除的日期时间点
		Date date = DateUtil.addDay(new Date(), - this.saveDays);
		String date_string = DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS);
		
		try{
			// 上传至fastDFS
			String paramsDescription = "uploadTime::<" + date_string + "::Date";
			long count = imageActionservice.getMax("ImageService", paramsDescription);
			if(count > 0){
				int size = 200;
				int page = (int) (count > size ? count/size + 1 : 1);
				// 分页查询
				for(int i = 1; i <= page; ++i){
					List<Image> imgs = imageActionservice.query("ImageService", i, size, paramsDescription, "asc", "uploadTime");
					if(imgs != null && imgs.size() > 0){
						for(Image image: imgs){
							if(image.getUrl() != null && !image.getUrl().isEmpty()){								
							    String fileRelativePath = image.getUrl();
							    if(fileRelativePath.startsWith("/group")){
									fileRelativePath = fileRelativePath.substring(1);
								}
								
								int pos = fileRelativePath.indexOf("/");
								if(-1 == pos)
									continue;
								
								String groupName = fileRelativePath.substring(0, pos);
								String subPath = fileRelativePath.substring(pos+1);
								
								// 从FastDFS中删除对应文件
								if(FastDFSClient.deleteFile(groupName, subPath)){
									logger.info(new Date() + " 删除文件【"+fileRelativePath+"】成功!");
								}else{
									logger.error(new Date() + " 删除文件【"+fileRelativePath+"】失败！");
								}
							}
						}
					}
				}
			}
			
			// 删除所有记录
			String sql = "delete from image where uploadtime <'" +date_string+"'";
			imageActionservice.getImageService().addOrUpdateOrDeleteBySql(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
