package com.easystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easystudy.service.HelloService;

@RestController
public class CustomerController {
	
	@Autowired
	HelloService service;
	
	/**
	 * 这里可以像调用本地普通方法一样调用远程过程
	 * Feign给你的就是不一般的体验
	 * @return
	 */
	@RequestMapping("/hi")
	public String customer(){
		return service.hello();
	}
}
