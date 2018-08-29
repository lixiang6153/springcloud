package com.easystudy.druid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * druid数据源状态监控.
 * 也可以通过配置文件配置：
 *  #druid StatViewServlet监控配置
	spring.datasource.druid.stat-view-servlet.enabled= true
	spring.datasource.druid.stat-view-servlet.url-pattern= /druid/*
	spring.datasource.druid.stat-view-servlet.reset-enable=false
	spring.datasource.druid.stat-view-servlet.login-username=admin
	spring.datasource.druid.stat-view-servlet.login-password=admin
	spring.datasource.druid.stat-view-servlet.allow=192.168.1.110,127.0.0.1
	spring.datasource.druid.stat-view-servlet.deny=192.168.16.111
 */
@WebServlet(urlPatterns = "/druid/*", initParams = {
		// IP白名单 (没有配置或者为空，则允许所有访问)
		@WebInitParam(name = "allow", value = "192.168.12.8,127.0.0.1"),
		// IP黑名单 (存在共同时，deny优先于allow)
		@WebInitParam(name = "deny", value = "192.168.12.9"),
		// 用户名
		@WebInitParam(name = "loginUsername", value = "admin"),
		// 密码
		@WebInitParam(name = "loginPassword", value = "admin"),
		// 禁用HTML页面上的“Reset All”功能
		@WebInitParam(name = "resetEnable", value = "false") })
@SuppressWarnings("serial")
public class DruidStatViewServlet extends StatViewServlet {

}