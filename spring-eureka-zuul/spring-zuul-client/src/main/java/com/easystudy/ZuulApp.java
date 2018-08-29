package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.easystudy.filter.AccessFilter;

@SpringBootApplication		// SpringBoot应用
@EnableEurekaClient			// 支持eureka，EnableEurekaClient也使用EnableDiscoveryClient注解	
//@EnableDiscoveryClient  	// 通过该注解，实现服务发现，注册-spring-cloud-commons，支持consul、zookeeper，eureka
@EnableZuulProxy			// 启用zuul过滤功能，为@EnableZuulServer的增强版.当Zuul与Eureka、Ribbon等组件配合使用时，
							// 我们使用@EnableZuulProxy,提供了pre、route、post、error、SimpleHostRoutingFilter过滤功能
public class ZuulApp {
	@Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulApp.class, args);
	}

}
