package com.donwait.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;

import com.donwait.action.BaseAction;
import com.donwait.listener.FileListener;
import com.donwait.util.FileOperatorUtil;
import com.opensymphony.xwork2.inject.Inject;


public class MyJakartaMultiPartRequest implements MultiPartRequest {
	public static Logger logger = Logger.getLogger(BaseAction.class);
	private long lastfileLen = 0L;
	// maps parameter name -> List of FileItem objects
	protected Map<String, List<FileItem>> files = new HashMap<String, List<FileItem>>();
	// maps parameter name -> List of param values
	protected Map<String, List<String>> params = new HashMap<String, List<String>>();
	// any errors while processing this request
	protected List<String> errors = new ArrayList<String>();
	protected long maxSize = 1000 * 1024 * 1024l; //in bytes  = 1000M

	@Inject(StrutsConstants.STRUTS_MULTIPART_MAXSIZE)
	public void setMaxSize(String maxSize) {
		this.maxSize = Long.parseLong(maxSize);
	}

	/***
	 * parseRequest List<FileItem> 重写JakartaMultiPartRequest类，加入文件上传进度监听
	 * @author 
	 * @return
	 * @throws FileUploadException
	 */
	protected List<FileItem> parseRequest(HttpServletRequest request,
			String saveDir) throws FileUploadException {
		
		// 记录起始时间
		long st = System.currentTimeMillis();
		
		/*createLocalDir(request);*/
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// param sizeMax The maximum allowed size, in bytes. The default value of -1 indicates, that there is no limit.
		upload.setFileSizeMax(-1L);
		// Sets the maximum allowed size of a single uploaded file, in bytes
		upload.setSizeMax(maxSize);
		upload.setHeaderEncoding("utf-8");
		// 设置进度监听器
		upload.setProgressListener(new FileListener(request));
		
		try {
			
			List<?> items = upload.parseRequest(request);
			FileItem item = null;
			
			for (int i = 0; i < items.size(); i++) {
				// 表单项
				item = (FileItem) items.get(i);
				String picPath = item.getName();

				if( null != picPath && (!"".equals(picPath)) ){
					String[] picPaths = picPath.split("\\\\");
					if(picPaths.length > 0){
						picPath = picPaths[picPaths.length - 1];
					}	
					// Byte
					lastfileLen = item.getSize();
					/*fileName = saveToLocal(filePath, item, picPath);*/
				}
				
				// 更新上传文件的发布路径
				if(item.getFieldName().equals("file") && picPath != null){
					try {
						// 上传至fastDFS
						/*String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
						String uploadFile = client.uploadFile(fileName, prefix, null);*/
						
						String prefix = picPath.substring(picPath.lastIndexOf(".")+1);
						String uploadFile = FastDFSClient.uploadFile(item.get(), prefix, null);
						request.setAttribute("url", uploadFile);						// 文件路径
						request.setAttribute("size", (long)(lastfileLen/1024));			// 单位K				
						
						// 清理文件
						/*new File(fileName).delete();*/
						logger.info(new Date() + " 上传文件:" + uploadFile);				    
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
		}
		
		// 打印文件上传速度
		long et = System.currentTimeMillis();
		String txt = " 文件上传速度为：" + (Double.valueOf(lastfileLen)*1000)/(Double.valueOf(String.valueOf(et - st))*1024*1024) + "兆/秒,即mb/s";
		System.out.println(txt);
		logger.debug(new Date() + txt);
		return upload.parseRequest(createRequestContext(request));
	}
	/**
	 * 创建本地目录
	 * @param request
	 */
	@SuppressWarnings("unused")
	private void createLocalDir(HttpServletRequest request) {
		String curProPath = request.getServletContext().getRealPath("");
		String filePath = FileOperatorUtil.createScrrenManagerFolder(curProPath);
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
	}
	/**
	 * 保存文件至本地
	 * @param filePath
	 * @param item
	 * @param picPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private String saveToLocal(String filePath, FileItem item, String picPath) throws Exception {
		String fileName;
		String saveFileName;
		// 保存文件名
		saveFileName = FileOperatorUtil.isDoubleFileNameAndNew(filePath,picPath.trim());
		fileName = filePath + saveFileName;
		// 判断FileItem对象封装的数据内容是存储在内存中，还是存储在临时文件中
		if (item.isInMemory()) {
			// 处理小文件，数据直接放入内存中
			item.write(new File(fileName));
		} else {
			// FileItem类对象封装的数据是一个普通文本表单字段，还是一个文件表单字段
			if (!item.isFormField() && picPath.length() > 0) {
				((DiskFileItem) item).getStoreLocation().renameTo(new File(fileName));
			}
		}
		return fileName;
	}

	public void parse(HttpServletRequest request, String saveDir)
			throws IOException {
		try {
			processUpload(request, saveDir);
		} catch (FileUploadException e) {
			errors.add(e.getMessage());
		}
	}

	private void processUpload(HttpServletRequest request, String saveDir)
			throws FileUploadException, UnsupportedEncodingException {
		for (FileItem item : parseRequest(request, saveDir)) {
			if (item.isFormField()) {
				processNormalFormField(item, request.getCharacterEncoding());
			} else {
				processFileField(item);
			}
		}
	}

	private void processFileField(FileItem item) {

		// Skip file uploads that don't have a file name - meaning that no file
		// was selected.
		if (item.getName() == null || item.getName().trim().length() < 1) {
			return;
		}

		List<FileItem> values;
		if (files.get(item.getFieldName()) != null) {
			values = files.get(item.getFieldName());
		} else {
			values = new ArrayList<FileItem>();
		}

		values.add(item);
		files.put(item.getFieldName(), values);
	}

	private void processNormalFormField(FileItem item, String charset)
			throws UnsupportedEncodingException {
		List<String> values;
		if (params.get(item.getFieldName()) != null) {
			values = params.get(item.getFieldName());
		} else {
			values = new ArrayList<String>();
		}

		// note: see http://jira.opensymphony.com/browse/WW-633
		// basically, in some cases the charset may be null, so
		// we're just going to try to "other" method (no idea if this
		// will work)
		if (charset != null) {
			values.add(item.getString(charset));
		} else {
			values.add(item.getString());
		}
		params.put(item.getFieldName(), values);
	}

	/*
	 * 重写此方法，加入上传进度的监听 private List<FileItem> parseRequest(HttpServletRequest
	 * servletRequest, String saveDir) throws FileUploadException {
	 * DiskFileItemFactory fac = createDiskFileItemFactory(saveDir);
	 * ServletFileUpload upload = new ServletFileUpload(fac);
	 * upload.setSizeMax(maxSize); return
	 * upload.parseRequest(createRequestContext(servletRequest)); }
	 */
	@SuppressWarnings("unused")
	private DiskFileItemFactory createDiskFileItemFactory(String saveDir) {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		// Make sure that the data is written to file
		fac.setSizeThreshold(0);
		if (saveDir != null) {
			fac.setRepository(new File(saveDir));
		}
		return fac;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFileParameterNames()
	 */
	public Enumeration<String> getFileParameterNames() {
		return Collections.enumeration(files.keySet());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getContentType(java.lang.String)
	 */
	public String[] getContentType(String fieldName) {
		List<FileItem> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<String> contentTypes = new ArrayList<String>(items.size());
		for (FileItem fileItem : items) {
			contentTypes.add(fileItem.getContentType());
		}

		return contentTypes.toArray(new String[contentTypes.size()]);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFile(java.lang.String)
	 */
	public File[] getFile(String fieldName) {
		List<FileItem> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<File> fileList = new ArrayList<File>(items.size());
		for (FileItem fileItem : items) {
			fileList.add(((DiskFileItem) fileItem).getStoreLocation());
		}

		return fileList.toArray(new File[fileList.size()]);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFileNames(java.lang.String)
	 */
	public String[] getFileNames(String fieldName) {
		List<FileItem> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<String> fileNames = new ArrayList<String>(items.size());
		for (FileItem fileItem : items) {
			fileNames.add(getCanonicalName(fileItem.getName()));
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFilesystemName(java.lang.String)
	 */
	public String[] getFilesystemName(String fieldName) {
		List<FileItem> items = files.get(fieldName);

		if (items == null) {
			return null;
		}

		List<String> fileNames = new ArrayList<String>(items.size());
		for (FileItem fileItem : items) {
			fileNames.add(((DiskFileItem) fileItem).getStoreLocation()
					.getName());
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameter(java.lang.String)
	 */
	public String getParameter(String name) {
		List<String> v = params.get(name);
		if (v != null && v.size() > 0) {
			return v.get(0);
		}

		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameterNames()
	 */
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(params.keySet());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String name) {
		List<String> v = params.get(name);
		if (v != null && v.size() > 0) {
			return v.toArray(new String[v.size()]);
		}

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getErrors() {
		return errors;
	}

	/***
	 * Returns the canonical name of the given file.
	 * 
	 * @param filename
	 *            the given file
	 * @return the canonical name of the given file
	 */
	private String getCanonicalName(String filename) {
		int forwardSlash = filename.lastIndexOf("/");
		int backwardSlash = filename.lastIndexOf("\\");
		if (forwardSlash != -1 && forwardSlash > backwardSlash) {
			filename = filename.substring(forwardSlash + 1, filename.length());
		} else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
			filename = filename.substring(backwardSlash + 1, filename.length());
		}

		return filename;
	}

	/***
	 * Creates a RequestContext needed by Jakarta Commons Upload.
	 * 
	 * @param req
	 *            the request.
	 * @return a new request context.
	 */
	private RequestContext createRequestContext(final HttpServletRequest req) {
		return new RequestContext() {
			public String getCharacterEncoding() {
				return req.getCharacterEncoding();
			}

			public String getContentType() {
				return req.getContentType();
			}

			public int getContentLength() {
				return req.getContentLength();
			}

			public InputStream getInputStream() throws IOException {
				InputStream in = req.getInputStream();
				if (in == null) {
					throw new IOException("Missing content in the request");
				}
				return req.getInputStream();
			}
		};
	}

	@Override
	public void cleanUp() {
		
	} 

}
