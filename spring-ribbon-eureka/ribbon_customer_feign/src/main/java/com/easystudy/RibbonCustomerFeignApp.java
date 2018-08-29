package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient  // 通过该注解，实现服务发现，注册-spring-cloud-commons，支持consul、zookeeper，eureka
//@EnableEurekaClient   // 支持eureka，EnableEurekaClient也使用EnableDiscoveryClient注解
@EnableFeignClients 	// 开启spring cloud feign的支持,启动器一定要加@EnableFeignClients，代表进行Feign调用，Feign会到Eureka拉取服务列表，供调用的。
//@EnableHystrix
public class RibbonCustomerFeignApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(RibbonCustomerFeignApp.class, args);
	}

}
