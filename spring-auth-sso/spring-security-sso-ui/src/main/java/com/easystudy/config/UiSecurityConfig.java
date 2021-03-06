package com.easystudy.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @EnableOAuth2Sso单点认证服务器上去认证：在UI服务器主类加上@EnableOAuth2Sso,
 * 这个注解会帮我们完成跳转到授权服务器,当然要些配置application.yml
 * 
 * 
 * 安全服务配置：
 * URL强制拦截保护服务，可以配置哪些路径不需要保护，哪些需要保护。默认全都保护
 * 继承了WebSecurityConfigurerAdapter之后，再加上几行代码，我们就能实现以下的功能：
 * 1、要求用户在进入你的应用的任何URL之前都进行验证
 * 2、创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
 * 3、启用HTTP Basic和基于表单的验证
 * 4、Spring Security将会自动生成一个登陆页面和登出成功页面
 */

@EnableOAuth2Sso
@Configuration
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")										// 所有匹配请求
            .authorizeRequests()									// 后续每个子匹配器将会按照声明的顺序起作用
            .antMatchers("/", "/login**").permitAll()				// 匹配login打头的页面都可以访问
            .anyRequest().authenticated();							// 其他请求都必须授权登录后才能访问
        
//		 http
//		 .authorizeRequests()										// 每个子匹配器将会按照声明的顺序起作用
//	 	 .antMatchers("/signup", "/about").permitAll()				// 匹配的路径任何人都可以访问
//	 	 .antMatchers("admin/**").hasRole("ADMIN")					// 匹配的路径只有ROLE_ADMIN角色才可以访问
//	 	 .anyRequest().authenticated()								// 其他匹配的[剩下的]任何请求都需要授权
//	 	 .and()
//	 	 .formLogin().loginProcessingUrl("/login").permitAll();		// form登录都可以访问
    }
}
