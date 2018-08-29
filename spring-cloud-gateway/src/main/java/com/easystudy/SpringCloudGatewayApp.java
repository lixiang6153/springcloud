package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayApp {

	/**
	 * spinggateway配置方式两种（这里为代码）
	 * 1、代码配置方式配置网关路由
	 * 2、application.yml配置文件配置
	 * http://localhost:8888/baidu
	 * http://localhost:8888/a
	 * http://localhost:8888/b/user/hello
	 * @param builder
	 * @return
	 */
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //basic proxy
                .route(r -> r.path("/baidu")
                       .uri("http://baidu.com:80/")
                	  ).build();
//        builder.routes()
//        			.route("host_route", r -> r.path("/a/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8081"))
//        			.route("host_route", r -> r.path("/b/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8081"))
//        			.build();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApp.class, args);
	}

}
