package com.ssh.action;

import java.io.ByteArrayInputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope(value="prototype")
public class TestAction extends ActionSupport{
	private String userName;
	private ByteArrayInputStream inputStream;		// 返回流
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public String testGet(){
		System.out.println(userName);
		System.out.println("testGet");
		return "success";
	}
	public String testPost(){
		System.out.println(userName);
		System.out.println("testPost");
		return "testPost";
	}
	public String testAjax(){
		System.out.println(userName);
		System.out.println("testAjax");
		setInputStream(new ByteArrayInputStream("java...".getBytes()));
		return "testAjax";
	}
}
