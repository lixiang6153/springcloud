package com.easystudy.druid;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @SpringBootConfiguration继承自@Configuration，二者功能也一致，标注当前类是配置类
 * 并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到spring容器中，并且实例名就是方法名
 * 
 * 此处配置druid拦截的页面以及druid初始化参数，同web.xml配置方式-filter-mapping和初始化参数context-param
 */
@Slf4j
@SpringBootConfiguration 
public class DruidMonitorConfig {
	
	/**
	 * 配置拦截过滤器过滤地址、访问源、初始化参数等
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		log.info("初始化 Druid 监控 Servlet.");
		// sevlet中配置 druid拦截的url_patttrn
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
		// IP白名单
		servletRegistrationBean.addInitParameter("allow", "192.168.12.8,127.0.0.1");
		// IP黑名单(共同存在时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", "192.168.12.9");
		// 控制台管理用户名和密码
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "admin");
		// 是否能够重置数据，是否禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", "true");
		return servletRegistrationBean;
	}

	/**
	 * 有很多请求要直接请求进来，不需要鉴权登陆druid页面的
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

}