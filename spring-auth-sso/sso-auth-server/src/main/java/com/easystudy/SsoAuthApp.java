package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 使用步骤：
 * 1、第三方应用向授权中心申请接入，授权中心会赋予第三方应用client_id与secret_key
 * 2、户进入第三方应用，第三方应用要求用户给予用户资料访问的授权。此时会302跳转到认证授权服务器登录页面，即3步骤开始登录（测试从第三部开始即可）
 *   授权模式：
 *   a.authorization code：授权码模式，是功能最完整、流程最严密的授权模式。它的特点就是通过客户端的后台服务器，与"服务提供商"的认证服务器进行互动。授权的令牌与应用接入key对于普通用户来说是隔离的
 *   b.Implicit：简化模式，直接在浏览器中向认证服务器申请令牌，url中会直接暴露用户的AccessToken。
 *   c.密码模式（resource owner password credentials）：用户向第三方应用提供自己的用户名和密码。第三方应用使用这些信息，向"服务商提供商"索要授权
 *   d.客户端模式（client credentials）：用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务，其实不存在授权问题
 * 3、浏览器跳转访问授权页面申请授权：(验证可以不用client_secret===>&client_secret=secret,可以不用&scope=user_info)
 * http://localhost:8081/auth/oauth/authorize?response_type=code&client_id=SampleClientId&redirect_uri=http://localhost:8082/ui/login
 * 输入用户名john,密码123
 * 4、用户同意给予第三方应用应用用户资料的授权，此时从认证服务器302跳转回以前的第三方应用，同时附带上授权码。第三方应用得到了认证服务器的授权码：
 * http://localhost:8082/ui/login?code=yQ8L0d
 * 5、第三方应用根据授权码与自身的secret_key向认证服务器（以POST方式）申请资源令牌。此处是在后台进行的，用户是不可见的 (这里必须用到secret获取accessToken：http://localhost:8081/auth/oauth/accessToken)
 * http://localhost:8081/auth/oauth/token?client_id=SampleClientId&client_secret=secret&scope=user_info&grant_type=authorization_code&code=yQ8L0d&redirect_uri=http://localhost:8082/ui/login
 * 6、认证服务器对第三方应用进行认证，确认所有信息正确以后向第三方应用发放资源令牌。此处是在后台进行的，用户是不可见的
 * 7、第三方应用根据资源令牌从服务器获取用户的信息（信息中是不包含类似密码这样的敏感信息的）。此处是在后台进行的，用户是不可见的
 * 请求：http://localhost:8081/auth/user/me?access_token=111f2618-c627-408f-8650-38949749153e
 * 返回：{"authorities":[{"authority":"ROLE_USER"}],"details":{"remoteAddress":"0:0:0:0:0:0:0:1","sessionId":"FD0AD84B15731EAD357F5D8795E224B3","tokenValue":"111f2618-c627-408f-8650-38949749153e","tokenType":"Bearer","decodedDetails":null},"authenticated":true,"userAuthentication":{"authorities":[{"authority":"ROLE_USER"}],"details":{"remoteAddress":"0:0:0:0:0:0:0:1","sessionId":"DD3AB1F0DF034789B554F8603AEC0C2E"},"authenticated":true,"principal":{"password":null,"username":"john","authorities":[{"authority":"ROLE_USER"}],"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true},"credentials":null,"name":"john"},"credentials":"","clientOnly":false,"principal":{"password":null,"username":"john","authorities":[{"authority":"ROLE_USER"}],"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true},"oauth2Request":{"clientId":"SampleClientId","scope":["user_info"],"requestParameters":{"code":"5heUsD","grant_type":"authorization_code","scope":"user_info","response_type":"code","redirect_uri":"http://localhost:8082/ui/login","client_secret":"secret","client_id":"SampleClientId"},"resourceIds":[],"authorities":[],"approved":true,"refresh":false,"redirectUri":"http://localhost:8082/ui/login","responseTypes":["code"],"extensions":{},"grantType":"authorization_code","refreshTokenRequest":null},"name":"john"}
 * 8、重定向到第三方平台：http://localhost:8082/ui/login?access_token=372c3458-4067-4b0b-8b77-7930f660d990&token_type=bearer&expires_in=37026
 * 
 * #授权认证：
 * #1.浏览器向UI服务器点击触发要求安全认证 
 * #2.跳转到授权服务器获取授权许可码 
 * #3.从授权服务器带授权许可码跳回来 
 * #4.UI服务器向授权服务器获取AccessToken 
 * #5.返回AccessToken到UI服务器 
 * #6.发出/resource请求到UI服务器 
 * #7.UI服务器将/resource请求转发到Resource服务器 
 * #8.Resource服务器要求安全验证,于是直接从授权服务器获取认证授权信息进行判断后(最后会响应给UI服务器,UI服务器再响应给浏览中器
 * 访问：http://localhost:8081/auth/user/me
 * 
 * 2、继承SpringBootServletInitializer容器启动时会调用其onStartup，可以覆盖方法做自己的事情
 */
@SpringBootApplication
@EnableResourceServer			// 资源服务器
public class SsoAuthApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SsoAuthApp.class, args);
		
//		new SpringApplicationBuilder(SsoAuthApp.class)
//		.properties("spring.config.name=client").run(args);
	}
}


