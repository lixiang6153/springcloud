package com.easystudy.handlers;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class Student {
	private String id;
	private String name;
	private Classes cls;
	
	// 日期类型转化--配置mvc:annotation-driver
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthDay;
	@NumberFormat(pattern="#,###,###.#") //1,124,567.8
	private Float salary;
	
	public Student(String id, String name, String cls_id, String cls_name) {
		super();
		this.id = id;
		this.name = name;
		this.cls = new Classes(cls_id, cls_name);
	}
	
	public Student(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Classes getCls() {
		return cls;
	}
	public void setCls(Classes cls) {
		this.cls = cls;
	}
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", cls=" + cls + ", birthDay=" + birthDay + ", salary=" + salary
				+ "]";
	}
}
