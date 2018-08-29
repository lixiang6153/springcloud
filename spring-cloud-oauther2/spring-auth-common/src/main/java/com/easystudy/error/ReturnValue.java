package com.easystudy.error;

public class ReturnValue<T> {
	private Integer error;					// 错误
	private String description;				// 错误描述
	private T value;						// 返回值【当error为ERROR_NO_SUCCESS才有可能返回值-判断值是否为空】
	
	// 成功不带返回值
	public ReturnValue(){
		this.error = ErrorCode.ERROR_SUCCESS.getError();
		this.description = "success";
	}
	
	// 成功带返回值
	public ReturnValue(T value){
		if(null == value){
			this.error = ErrorCode.ERROR_SERVER_ERROR.getError();
			this.description = "the result is null";
		} else {
			this.error = ErrorCode.ERROR_SUCCESS.getError();
			this.description = "success";
			this.value = value;
		}		
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
