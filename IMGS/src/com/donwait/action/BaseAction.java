package com.donwait.action;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.donwait.error.ErrorCode;
import com.donwait.util.ReturnValue;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 基本Action类
 * @author Administrator
 *
 */
public class BaseAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private ByteArrayInputStream inputStream;// 返回流
	public static Logger logger = Logger.getLogger(BaseAction.class);
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		BaseAction.logger = logger;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	
	public void setAjaxResponse(ReturnValue result) {
		try {
			JSONObject jsonObject = new JSONObject();
			if(ErrorCode.ERROR_SUCCESS.getError() == result.getError() && !isNull(result.getValue())){
				jsonObject.put("result", result.getValue());	
			}else{
				logger.info("错误:" + result.getDescription() + "[" + result.getDescription() + "]");	
			}
			jsonObject.put("description", result.getDescription());	
			jsonObject.put("error", result.getError());
			setInputStream(new ByteArrayInputStream(jsonObject.toString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Boolean isNull(Object value){
		if(value !=null ){
			return false;
		}
		return true;
	}
	
	public Boolean isNull(String str){
		if(str!=null&&!str.trim().equals("")){
			return false;
		}
		return true;
	}

	public Boolean isNull(List<?> list){
		if(list!=null&&list.size()>0){
			return false;
		}
		return true;
	}	
}
