package com.donwait.enums;

public enum DataType {
	DATA_TYPE_DOOR(0x01),			// 获取设备列表【动态接入设备】
	DATA_TYPE_DOOR_EMPLOYEE(0x02),	// 获取具有开门权限的人
	DATA_TYPE_NEW_FACE(0x04),		// 获取新人脸【动态人脸布控】
	DATA_TYPE_DOOR_TIME(0x08);		// 获取开门段【动态布控】
	private Integer value;
	
	private DataType(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}
}
