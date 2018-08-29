package com.easystudy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {
	
	@RequestMapping("/hello")
	public String testB(){
		return "Hello world BBBBBBBBBB!";
	}
}
