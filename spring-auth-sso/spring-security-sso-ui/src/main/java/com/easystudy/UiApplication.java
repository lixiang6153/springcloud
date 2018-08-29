package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**使用：
 * 1、打开浏览器：访问 http://localhost:8082/ui/index UiWebConfig转到index.html
 * 2、点击登录：login跳转到统一授权服务中心http://localhost:8081/auth/login
 * 3、输入用户名密码（john，123），授权中心验证成功后又重定向到（原来页面）：http://localhost:8082/ui/index或http://localhost:8083/ui2/index
 * 4、再次在原来页面2中点击登录即可，此时页面放回到http://localhost:8083/ui2/securedPage即获取到资源服务的资源
 * 原因：index.html页面登录需要跳转到securedPage页面，但是需要验证授权，所以先到授权中心鉴权后才能返回原来页面跳转至录securedPage页面
 */
@SpringBootApplication
public class UiApplication  {

	/**
	 * 请求监听器：
	 * 在介绍webApplicationContext初始化时，我们已经通过ContextLoaderListener将web容器与
	 * spring容器整合，为什么这里又要引入一个额外的RequestContextListener以支持Bean的另外3个作用域呢?
	 * 在整合spring容器时使用ContextLoaderListener，它实现了ServletContextListener监听器接口
	 * ServletContextListener只负责监听web容器启动和关闭的事件,而RequestContextListener实现
	 * ServletRequestListener监听器接口，该监听器监听HTTP请求事件，web服务器接收的每一次请求都会通知该监听器。
	 * @return
	 */
//    @Bean
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}