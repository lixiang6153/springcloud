package com.easystudy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/user", method=RequestMethod.GET)
public class HelloController {

	@RequestMapping("/hello")
	public String hello(){
		return "Hello World!";
	}
	
//	@RequestMapping("/save")
//	public String hello(@RequestParam(value="name", required=false, defaultValue="") String name){
//		return "add user:" + name + " success!";
//	}
//	
//	@RequestMapping("/get")
//	public String get(@RequestParam(value="id", required=true, defaultValue="001") Long id){
//		return new User(id, "").toString();
//	}
}
