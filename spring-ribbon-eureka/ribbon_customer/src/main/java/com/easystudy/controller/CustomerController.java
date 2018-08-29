package com.easystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easystudy.service.HelloService;

@RestController
public class CustomerController {
	
	@Autowired
	HelloService service;
	
	@RequestMapping("/hi")
	public String customer(){
		return service.helloService();
	}
}
