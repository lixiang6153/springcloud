package com.easystudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * URL拦截配置
 * 实现功能（作用）：
 * 1、要求用户在进入你的应用的任何URL之前都进行验证
 * 2、创建一个用户名是“john”，密码是“123”，角色是“ROLE_USER”的用户【自动添加了“ROLE_”前缀】
 * 3、启用HTTP Basic和基于表单的验证
 * 4、Spring Security将会自动生成一个登陆页面和登出成功页面
 */
@Order(1)			// 使用注解方式使bean的加载顺序得到控制，配置改类被加载的顺序，优先级为1
@EnableWebSecurity	// 包含@Configuration，@EnableWebSecurity注解以及WebSecurityConfigurerAdapter一起配合提供基于web的security
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * Override this method to configure the HttpSecurity. Typically subclasses should not invoke this method by calling super as it may override their configuration. The default configuration is:
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 */
	 @Override
    protected void configure(HttpSecurity http) throws Exception {
		 http.requestMatchers()
	         	//.antMatchers("/login", "/oauth/authorize", "/oauth/accessToken", "/auth/oauth/token")		// 这些页面不需要授权
	         	.antMatchers("/login")																		// 这些页面不需要授权
	         .and()
	         .authorizeRequests()
	         	.anyRequest().authenticated()																// 其他所有页面必须授权
	         .and()
	         	.formLogin()																				//定制登录表单
		         	//.loginPage("/login")																	//设置登录url
					//.defaultSuccessUrl("/home")																//设置登录成功默认跳转url
					.permitAll();
    } 

	/**
	 * 1、创建用户
	 * 2、验证用户
	 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {    	
    	// 内存中创建[验证]一个用户名为john，密码为123的用户，第三方应用登录的时候必须指定改用户名和密码通过认证后才发放code授权码
    	// 第三方应用凭借用户授权（使用用户名和密码）获取的授权码换取access_token然后通过access_token获取用户信息
    	// 角色名会默认被添加上"ROLE_"前缀，如下USER角色，实际的名称为ROLE_USER
    	auth.inMemoryAuthentication()	
	        .withUser("john")								// 创建的用户名
	        .password(passwordEncoder().encode("123"))		// 验证的用户密码
	        .roles("USER")									// 改用户的角色
	        .and()											// 级联
	        .withUser("lixx")								// 创建用户lixx
	        .password(passwordEncoder().encode("dw123456"))	// 用户密码
	        .roles("ADMIN", "USER");						// 用户角色为ROLE_ADMIN和ROLE_USER有两种角色
    } 
    
    /**
     * 拦截URL，设置忽略安全校验的静态资源拦截
     * Override this method to configure WebSecurity. For example, if you wish to ignore certain requests.
     */
    @Override
	public void configure(WebSecurity web) throws Exception {
    	web
    		.ignoring()										// 忽略如下请求
    		.antMatchers("/resources/**"); 					// 忽略以/resources/打头的请求
	}

	/**
     * 创建加密对象，对密码进行加密
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
