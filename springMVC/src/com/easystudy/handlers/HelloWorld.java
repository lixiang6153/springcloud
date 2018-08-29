package com.easystudy.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	/**
	 * 通过InternalResourceViewResolver视图解析器解析：
	 * prefix+returnValue+后缀得到实际物理视图，然后转发到页面
	 * eg:
	 * /WEB-INF/views/success.jsp
	 */
	@RequestMapping("/helloworld")
	public String hello(){
		System.out.println("Hello World!");
		return "success";
	}

}
