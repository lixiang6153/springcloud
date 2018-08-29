package com.easystudy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloController {
	@Value("${server.application.name}")
	private String applicationName;
	
	@RequestMapping("/hello")
	public String hello(){
		System.out.println("当前服务名：" + applicationName);
		return applicationName;
	}
}
