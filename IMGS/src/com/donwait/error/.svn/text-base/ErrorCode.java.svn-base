package com.donwait.error;

public enum ErrorCode {
	ERROR_SUCCESS(0, "成功"),						
	ERROR_NOT_FOUND(-1, "未找到"),								
	ERROR_SERVER_ERROR(-2, "服务内部错误"),		
	ERROR_HANDLE_TIMEOUT(-3, "超时"),	
	ERROR_INVALID_PARAM(-4, "无效参数"),			
	ERROR_MEMORY_OUT(-5, "内存溢出"),		
	ERROR_CHECK_CODE(-6, "验证码错误"),
	ERROR_OBJECT_EXIST(-7, "对象已存在"),
	ERROR_USER_PASSWORD(-8, "用户名或密码不正确"),
	ERROR_ADD_FAIL(-9, "增加记录失败"),
	ERROR_DELETE_FAIL(-10, "删除记录失败"),
	ERROR_INVALID_ARRAY(-11, "无效列表"),
	ERROR_EXIST_TIMESPAN(-12, "该时间段包含已有时间"),
	ERROR_NOT_LOGIN(-13, "未登录或会话过期"),
	ERROR_BAD_ORIGINAL(-14, "原始密码错误"),
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