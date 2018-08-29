package com.easystudy.error;

public class ReturnValue {
	private Integer error;					// 错误
	private String description;				// 错误描述
	private Object value;					// 返回值【当error为ERROR_NO_SUCCESS才有可能返回值-判断值是否为空】
	
	// 成功不带返回值
	public ReturnValue(){
		this.error = ErrorCode.ERROR_SUCCESS.getError();
		this.description = "success";
	}
	
	// 成功带返回值
	public ReturnValue(Object value){
		this.error = ErrorCode.ERROR_SUCCESS.getError();
		this.description = "success";
		this.value = value;
	}
	
	// 成功带返回条数-count
	public ReturnValue(Integer count){
		this.error = ErrorCode.ERROR_SUCCESS.getError();
		this.description = "success";
		this.value = count;
	}
	
	// 成功带返回条数-count
	public ReturnValue(Long count){
		this.error = ErrorCode.ERROR_SUCCESS.getError();
		this.description = "success";
		this.value = count;
	}
	
	// 成功带返回字符串
	public ReturnValue(String str){
		this.error = ErrorCode.ERROR_SUCCESS.getError();
		this.description = "success";
		this.value = str;
	}
	
	// 返回错误
	public ReturnValue(ErrorCode error){
		this.error = error.getError();
		this.description = error.getDescription();
	}
	
	// 返回错误--对错误描述进行更改
	public ReturnValue(ErrorCode error, String description){
		this.error = error.getError();
		this.description = description;
	}
	
	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
