package com.easystudy.druid;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid我们都很熟悉，是一款非常好用的数据连接池，但是很多开发者不知， druid除了数据连接池，
 * 还集成了对站点的URL进行统计的功能，下面就详细的为大家讲解一下如何配置druid的url统计。
 * 比如统计哪些sql语句执行慢，需要优化，就是通过过滤器来实现sql统计的
 * WebStatFilter用于采集web-jdbc关联监控的数据，此处配置的是druid监控的数据过滤器. 
 * exlusions配置：经常需要排除一些不必要的url，比如.js,/jslib/等等。配置在init-param中
 * 
 * 通过配置文件配置也可以：
 * 
	#druid WebStatFilter监控配置
	spring.datasource.druid.web-stat-filter.enabled= true
	spring.datasource.druid.web-stat-filter.url-pattern=/*
	spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*
	spring.datasource.druid.web-stat-filter.session-stat-enable=true
	spring.datasource.druid.web-stat-filter.session-stat-max-count=10
	spring.datasource.druid.web-stat-filter.principal-session-name=
	spring.datasource.druid.web-stat-filter.principal-cookie-name=
	spring.datasource.druid.web-stat-filter.profile-enable=
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", 
		  initParams = {
				  		// 忽略资源
				  		@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") 
				  	   }
		   )
public class DruidStatFilter extends WebStatFilter {
}