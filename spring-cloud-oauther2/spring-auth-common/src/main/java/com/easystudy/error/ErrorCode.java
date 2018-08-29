package com.easystudy.error;

public enum ErrorCode { // 数字值保持与c++后台一致
	ERROR_SUCCESS(0, "成功"),						
	ERROR_NOT_FOUND(-29, "未找到或不存在"),								
	ERROR_SERVER_ERROR(-500, "服务内部错误"),		
	ERROR_HANDLE_TIMEOUT(-10, "超时"),	
	ERROR_INVALID_PARAM(-1, "无效参数"),			
	ERROR_MEMORY_OUT(-6, "内存溢出"),		
	ERROR_CHECK_CODE(-16, "验证码错误"),
	ERROR_OBJECT_EXIST(-17, "对象已存在"),
	ERROR_USER_PASSWORD(-1, "用户名或密码不正确"),
	ERROR_DELETE_FAIL(-9, "删除对象失败"),
	ERROR_INVALID_ARRAY(-1, "无效列表"),
	ERROR_EXIST_TIMESPAN(-1, "该时间段包含已有时间"),
	ERROR_NOT_LOGIN(-12, "未登录或会话过期"),
	ERROR_BAD_ORIGINAL(-1, "原始密码错误"),
	ERROR_NO_RIGHT(-27, "权限不足"),
	ERROR_NOT_SUPPORT(-4, "接口不支持"),
	ERROR_BUSY(-499, "忙碌中"),
	
	;		
	
	private Integer error;	
	private String description;	
	
	private ErrorCode(Integer error, String description){
		this.error = error;
		this.description = description;
	}
	
	public Integer getError(){
		return this.error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setError(Integer error) {
		this.error = error;
	}
}