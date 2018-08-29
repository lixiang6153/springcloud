package com.easystudy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(){
		System.out.println("设置服务提供者的hello被调用了...");
		return "Hello World!";
	}
}
