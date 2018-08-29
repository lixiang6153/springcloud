package com.easystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.easystudy.service.HelloService;

@RestController
public class TestController {
	@Autowired
	HelloService helloService;
	
	@Autowired
	RestTemplate restTemplate;
	
    @GetMapping("/hello")
    public String test(){
    	System.out.println("这是消费者接收到的hello请求");
        return helloService.hello();
    }
    @GetMapping("/hello2")
    public String test2(){
    	System.out.println("这是消费者接收到的hello请求2222");
    	return restTemplate.getForObject("http://HELLO/hello", String.class);
    }
}
