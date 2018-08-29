package cn.itsource.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//RestController他就是一个Controller
public class HelloController2 {

	@RequestMapping("/hello3")
	public String hello3() {

		return "hello v3";
	}
	@RequestMapping("/hello4")
	public String hello4() {
		
		return "hello v4";
	}
}
