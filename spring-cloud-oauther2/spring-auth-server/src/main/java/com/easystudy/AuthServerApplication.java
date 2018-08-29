package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 简单理解一下OAuth2，你要登录一个XX网站下片，但是下片需要注册登录成为这个网站的会员，你不想注册，
 * 还好网站提供了qq登录，你选择qq登录。那么问题来了，你选择qq登录时，会跳转到qq的登录页面，输入qq
 * 账号密码，注意，这时登录qq与这个XX网站是没关系的，这是qq做的登录页，登录时qq会问你是否允许该XXX
 * 网站获取你的个人信息如头像、昵称等，你勾选同意，登录成功后就又回到了XX网站的某个页面了。这是一个什么
 * 流程呢，其实是一个XX网站试图获取你个人信息、然后问你是否同意的一个流程。你同意了，qq才会允许第三方
 * 网站获取你的个人信息
 * 使用：
 * 1、通过client_id请求授权码
 * 请求：
 * http://localhost:8762/auth/oauth/authorize?response_type=code&client_id=test_client_id&redirect_uri=http://localhost:8082/ui/login
 * 响应：
 * http://localhost:8082/ui/login?code=uB0dHT
 * 2、通过授权码换取token
 * http://localhost:8762/auth/oauth/token?client_id=test_client_id&client_secret=test_client_secret&scope=user_info&grant_type=authorization_code&code=uB0dHT&redirect_uri=http://localhost:8082/ui/login
 * 返回token：2bccded3-1dd2-4f44-bb82-aea659c514ee
 * 3、通过token获取用户信息
 * http://localhost:8762/auth/user?access_token=2bccded3-1dd2-4f44-bb82-aea659c514ee
 * 返回用户信息
 * 4、刷新token
 * http://localhost:8762/auth/oauth/token?client_id=test_client_id&grant_type=refresh_token&refresh_token=.....
 */
@SpringBootApplication		// SpringBoot应用
@EnableDiscoveryClient		// 开启服务发现功能
@EnableFeignClients			// 开启Feign
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}
