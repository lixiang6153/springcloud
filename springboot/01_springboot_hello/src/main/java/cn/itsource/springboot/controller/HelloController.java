package cn.itsource.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//RestController他就是一个Controller
public class HelloController {

	@RequestMapping("/hello1")
	public String hello1() {

		return "hello v1";
	}
	@RequestMapping("/hello2")
	public String hello2() {
		
		return "hello v2";
	}
}
