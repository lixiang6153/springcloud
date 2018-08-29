package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * RequestRateLimiter属于GatewayFilter Factories一种，所以需要在RouteLocator中加入为具体Route项加入或者在配置文件application.yml中配置
 * @author Administrator
	spring:
	      cloud:
	        gateway:
	          routes:
	          - id: requestratelimiter_route
	            uri: http://example.org
	            filters:
	            - name: RequestRateLimiter
	              args:
	                redis-rate-limiter.replenishRate: 10
	                redis-rate-limiter.burstCapacity: 20
	                
如果自定义RequestRateLimiter的application.yml配置如下
spring:
      cloud:
        gateway:
          routes:
          - id: requestratelimiter_route
            uri: http://example.org
            filters:
            - name: RequestRateLimiter
              args:
                rate-limiter: "#{@myRateLimiter}"
                key-resolver: "#{@userKeyResolver}"
 */
@SpringBootApplication
@EnableDiscoveryClient 	// 该注解会根据配置文件中的地址，将服务自身注册到服务注册中
public class SpringCloudGatewayApp {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApp.class, args);
	}

}
