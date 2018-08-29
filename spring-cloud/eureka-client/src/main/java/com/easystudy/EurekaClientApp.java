package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient            //通过该注解，实现服务发现，注册
@SpringBootApplication
public class EurekaClientApp {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApp.class, args);
	}

}
