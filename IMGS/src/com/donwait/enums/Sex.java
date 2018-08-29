package com.donwait.enums;

public enum Sex {
	MAN("男"),WOMAN("女");
	private String text;

	private Sex(String text) {
		this.text = text;
	}
	public static Integer parse(String text) {
		Sex[] values = Sex.values();
		for (Sex station : values) {
			if (station.text.equals(text)){
				return station.ordinal();
			}		
		}
		return null;
	};
}
