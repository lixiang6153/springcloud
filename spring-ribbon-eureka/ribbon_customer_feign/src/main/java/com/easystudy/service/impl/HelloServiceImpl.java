package com.easystudy.service.impl;

import com.easystudy.service.HelloService;

/**
 * Feign容错降级处理类，它并不是Spring的Service类，
 * Feign会扫描标有@FeignClient注解的接口，生成代理
 * HelloServiceImpl仅仅是被指定的降级处理类
 * @author Administrator
 *
 */
public class HelloServiceImpl implements HelloService{

	@Override
	public String hello() {		
		return "请求错误";
	}

	/**
	 * 发现这里的入参里我故意去掉了@RequestParam、@RequestBody、@RequestHeader注解，
	 * 因为这几个注解本质上的意义就在于Feign在做微服务调用的时候对http传递参数用的，
	 * 但服务降级根本不会做http请求了，所以此处可以省略
	 */
	@Override
	public String hello(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
