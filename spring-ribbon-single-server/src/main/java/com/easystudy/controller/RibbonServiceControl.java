package com.easystudy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonServiceControl {
	
	@RequestMapping("hello")
    public String helloWorld(HttpServletRequest request){
          return "请求路径： "+request.getRequestURL();
    }
	
}
