package com.easystudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.easystudy.service.impl.UserDetailsServiceImpl;

/**
 * 安全服务配置（Spring Security http URL拦截保护）：：
 * URL强制拦截保护服务，可以配置哪些路径不需要保护，哪些需要保护。默认全都保护
 * 继承了WebSecurityConfigurerAdapter之后，再加上几行代码，我们就能实现以下的功能：
 * 1、要求用户在进入你的应用的任何URL之前都进行验证
 * 2、创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
 * 3、启用HTTP Basic和基于表单的验证
 * 4、Spring Security将会自动生成一个登陆页面和登出成功页面
 * 
 * @EnableWebSecurity注解以及WebSecurityConfigurerAdapter一起配合提供基于web的security。
 * 继承了WebSecurityConfigurerAdapter之后，再加上几行代码，我们就能实现以下的功能：
 * 1、要求用户在进入你的应用的任何URL之前都进行验证
 * 2、创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
 * 3、启用HTTP Basic和基于表单的验证
 * 4、Spring Security将会自动生成一个登陆页面和登出成功页面
 * 默认页面：
 * 登录页面：/login 
 * 注销页面：/login?logout
 * 错误页面：/login?error
 * 
 * 与ResourceServerConfigurerAdapter区别
 * 1、ResourceServerConfigurerAdapter被配置为不同的端点（参见antMatchers），而WebSecurityConfigurerAdapter不是。
 *   这两个适配器之间的区别在于，RealServServer配置适配器使用一个特殊的过滤器来检查请求中的承载令牌，以便通过OAuth2对请求进行认证。
 *   WebSecurityConfigurerAdapter适配器用于通过会话对用户进行身份验证（如表单登录）
 * 2、WebSecurityConfigurerAdapter是默认情况下spring security的http配置，
 *   ResourceServerConfigurerAdapter是默认情况下spring security oauth2的http配置
 *   在ResourceServerProperties中，定义了它的order默认值为SecurityProperties.ACCESS_OVERRIDE_ORDER - 1;是大于1的,
 *   即WebSecurityConfigurerAdapter的配置的拦截要优先于ResourceServerConfigurerAdapter，优先级高的http配置是可以覆盖优先级低的配置的。
 *   某些情况下如果需要ResourceServerConfigurerAdapter的拦截优先于WebSecurityConfigurerAdapter需要在配置文件中添加
 *   security.oauth2.resource.filter-order=99
 *   
 *   
重点：
@@如果使用jdbc存储客户信息，必须做如下粗啊哦做：
（1）建立如下数据表oauth_client_details，auth2会从oauth_client_details查询授权秘钥信息（在哪一个数据库中建立，就看你配置的数据源是哪一个） 
（2）往对应表中插入记录（一般是第三方app注册之后返回给第三方app的clientid和秘钥信息
          这个是默认的类的表,一般用它默认的即可,我们这边就需要根据以上的字段配置相关的内容,如下:
insert into oauth_client_details(client_id,resource_ids,client_secret,scope,authorized_grant_types,
								 web_server_redirect_uri,authorities,access_token_validity,
								 refresh_token_validity,autoapprove)
						values('test_client_id', 'test_resource_id', 'test_client_secret',
							   'user_info','authorization_code', 'http://localhost:8082/ui/login',
							   'ROLE_ADMIN', 3600, 7200, 'true');
注意：scope客户受限的范围。如果范围未定义或为空（默认值），客户端不受范围限制。read write all
	authorities授予客户的授权机构（普通的Spring Security权威机构）。
	authorized_grant_types：为授权用户可以授权码的操作类型，有：authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: "authorization_code,refresh_token". 
	additional_information默认为NULL，否则必须为json格式Map<String,Object>形式的字符串，例如：{"systemInfo":"Atlas System"}
 */
// 配置示例：
//// 无需访问权限
//http.authorizeRequests()
//		.antMatchers("/**/test.*", "/**/*.css","/**/*.js","/**/images/*").permitAll()
//		// admin角色访问权限 ADMIN
//		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")	//“/admin/”开头的URL必须要是管理员用户，譬如”admin”用户	
//		// user角色访问权限
//		.antMatchers("/**/operator/*.*").hasAuthority("ROLE_USER")
//		// 公共页面
//		.antMatchers("/signup","/about").permitAll()			// 任何人(包括没有经过验证的)都可以访问”/signup”和”/about”
//		// 其他请求授权
//		.anyRequest().authenticated()							// 其余所有请求全部需要鉴权认证
//	.and()
//	// login页面自定义配置都可以访问
//	.formLogin()												// 使用Java配置默认值设置了基于表单的验证。使用POST提交到”/login”时，需要用”username”和”password”进行验证
//		.loginPage("/login")									// 注明了登陆页面，意味着用GET访问”/login”时，显示登陆页面
//		.permitAll()											// 任何人(包括没有经过验证的)都可以访问”/login”和”/login?error”
//	.and()
//		.logout().permitAll();									// 任何人都可以登出页面都可以访问	

@Configuration
@EnableWebSecurity  		// 创建了一个WebSecurityConfigurerAdapter，且自带了硬编码的order=3,使用spring security而不是auth
//@Order(1) 				// 定义拦截器配置拦截次序,高于ResourceServerConfigurerAdapter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	// 自定义用户服务-校验用户是否合法，实现改接口，spring即可获取对应用户的角色、权限等信息，然后可以拦截URL判断是否具有对应权限
	// 具体是否可以访问对应URL配置可以在HttpSecurity中配置
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	/**
	 * 密码加密器:将用户密码进行加密
	 * @return
	 */
	@Bean
    public PasswordEncoder passwordEncoder() {
		// 使用BCrypt进行密码的hash
        return new BCryptPasswordEncoder();
    }

	/**
	 * 不定义没有password grant_type即密码授权模式（总共四种授权模式：授权码、implicat精简模式、密码、client credentials）
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * 如果有要忽略拦截校验的静态资源，在此处添加
	 * 忽略任何以”/resources/”开头的请求，这和在XML配置http@security=none的效果一样
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web
		.ignoring()
		.antMatchers("/resources/**");	
	}

	/**
	 * 允许对特定的http请求基于安全考虑进行配置,默认情况下，适用于所有的请求，
	 * 但可以使用requestMatcher(RequestMatcher)或者其它相似的方法进行限制
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 如：将所有的url访问权限设定为角色名称为"ROLE_USER"
		// http.authorizeRequests().antMatchers("/").hasRole("USER").and().formLogin();
		
		// 启用HTTP Basic和基于表单的验证
		http.requestMatchers()
				.antMatchers("/login", "/oauth/authorize", "/oauth/accessToken", "/oauth/token")						// 这些请求不需要授权
				.and() 
				.authorizeRequests()																					// 定义权限配置
					.anyRequest().authenticated()				                                                        // 任何请求都必须经过认证才能访问--登录后可以访问任意页面
				.and()
					.formLogin()								                                                        // 定制登录表单
						//.loginPage("/login")					                                                        // 设置登录url
						//.failureUrl("/login?error")                                                                   
						//.defaultSuccessUrl("/home")			                                                        // 设置登录成功默认跳转url
						.permitAll()							                                                        //允许任何人访问登录url
				.and()                                                                                                  
					.logout().permitAll()                                                                           
				.and()                                                                                                  
					.csrf().disable()							                                                        // 禁止跨域请求
					.httpBasic();								                                                        // 进行http Basic认证					
	}
	
	/**
	 * 系统安全用户验证模式：
	 * 1、使用内存模式创建验证
	 * 2、使用数据库创建验证，实现userDetailsService接口即可
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 将验证过程交给自定义
		// 这两个会默认在providerlist中增加一个DaoAuthenticationProvider
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
		
		// 内存创建用户：写死不利于项目实际应用
		// 验证的时候就是通过创建的用户名、密码、角色进行验证的
		// 创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
		// 创建一个用户名是“admin”，密码是“123456”，角色是“ROLE_ADMIN以及ROLE_USER”的用户
		//auth
		//	.inMemoryAuthentication()
		//		.withUser("user").password("password").roles("USER")  			// 在内存中的验证(memory authentication)叫作”user”的用户
		//	.and()
		//		.withUser("admin").password("123456").roles("ADMIN", "USER");	// 在内存中的验证(memory authentication)叫作”admin”的管理员用户
	}
}
