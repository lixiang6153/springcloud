package com.donwait.enums;

public enum SubscribeType {
	SUB_TYPE_SWIPE_MESSAGE(0X01, "刷脸消息"),
	SUB_TYPE_ADD_PERSON(0X02, "增加人员"),
	SUB_TYPE_UPDATE_PERSON(0X04, "更新人员"),
	SUB_TYPE_RMV_PERSON(0X08, "删除人员"),
	SUB_TYPE_ADD_DOOR(0X10, "添加门禁"),
	SUB_TYPE_UPDATE_DOOR(0X20, "更新门禁"),
	SUB_TYPE_RMV_DOOR(0X40, "删除门禁"),
	SUB_TYPE_DEVICE_STATE(0X80, "设备状态"),
	SUB_TYPE_UPDATE_DOORTIME(0X100, "增加门禁时间"),
	SUB_TYPE_ADD_PERSON_RIGHT(0X200, "增加人员权限"),
	SUB_TYPE_UPDATE_PERSON_RIGHT(0X400, "更新人员权限"),
	SUB_TYPE_UPDATE_FEATURE(0X800, "更新图片"),
	SUB_TYPE_ADD_FEATURE(0X1000, "增加图片"),
	SUB_TYPE_FACE_REAL_POSITION(0X2000, "人脸实时位置"),
	SUB_TYPE_RMV_COMPANY(0X4000, "公司信息删除");
	
	private Integer value;
	private String description;
	
	private SubscribeType(Integer v, String desc){
		this.value = v;
		this.description = desc;
	}
	
	public Integer getValue(){
		return this.value;
	}
	
	public String getDescription(){
		return this.description;
	}
}
