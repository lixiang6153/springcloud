package com.donwait.enums;

public enum SwipeType {
	WORK("上班打卡"),
	OFFWORK("下班打卡");
	
	private String description;
	private SwipeType(String description){
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}
