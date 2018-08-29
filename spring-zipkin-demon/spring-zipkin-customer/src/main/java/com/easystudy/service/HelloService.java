package com.easystudy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import feign.hystrix.FallbackFactory;

/**
 * 访问eureka中的user-service远程服务
 * @author Administrator
 *
 */
@FeignClient(name = "hello",/*fallback = HelloServiceImpl.class,*/ fallbackFactory=HelloServiceFallbackFactory.class)
public interface HelloService {
    @GetMapping("hello")
    public String hello();
}

/**
 * 错误处理
 * @author Administrator
 */
@Component class HelloServiceFallbackFactory implements FallbackFactory<HelloService>{

	@Override
	public HelloService create(Throwable cause) {
		System.out.println("请求错误：" + cause.getMessage());
		
		// 降级处理
		return new HelloService() {

			@Override
			public String hello() {
				return "调用失败，请确定服务是否启动或稍后重试！";
			}			
		};
	}
	
}