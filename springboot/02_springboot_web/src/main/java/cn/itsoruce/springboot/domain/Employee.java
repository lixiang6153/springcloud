package cn.itsoruce.springboot.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Employee {

	private Long id;
	private String name;
	private Date birthDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//前台格式化展示
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
	public Date getBirthDay() {
		return birthDay;
	}

	//前台传入特定格式的字符串，需要将它转换为日期
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Employee(Long id, String name, Date birthDay) {
		super();
		this.id = id;
		this.name = name;
		this.birthDay = birthDay;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", birthDay=" + birthDay + "]";
	}

}
