package com.easystudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired    
    private BCryptPasswordEncoder passwordEncoder;
    
    /**
     * 用postman使用post方式向/oauth/token或/oauth/accessToken这个接口获取access_token的时候报错401
     * 允许以form形式提交表单信息
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
        	.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients();
    }

    /**
     * 授权码换取（第一步校验）: 
     * 1、第三方应用通过client_id,response_typey以及redirect_uri获取对应的授权码，
     * 2、然后通过授权码在换取access_token（此时就需要用到client_secret、scope以及grant_type（authorization_code）、redirect_uri
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    	// 注意：我们直接把账号密码写到内存里了，真正使用的时候这里要换成自定义的userDetailsService
    	// secret密码配置从 Spring Security 5.0开始必须以 {bcrypt}+加密后的密码 这种格式填写
        clients.inMemory()															// 内存模式认证
	        .withClient("SampleClientId")											// client_id
	        .secret(passwordEncoder.encode("secret"))								// client_secret
	        .authorizedGrantTypes("authorization_code","refresh_token")				// grant_type，可以填写多个授权类型,可以通过refresh_token更换过期token
	        .scopes("user_info")													// 申请的权限范围scope-用于获取用户信息：客户受限的范围。如果范围未定义或为空（默认值），客户端不受范围限制。read write all
	        //.resourceIds(resourceIds)
	        .autoApprove(true) 														// 必选参数，用户授权完成后的回调地址，应用需要通过此回调地址获得用户的授权结果。此地址必须与在应用注册时填写的回调地址一致，注意：该地址是第三方应用注册的地址，此处写死
	        .redirectUris("http://localhost:8082/ui/login","http://localhost:8083/ui2/login"); 
	     // .accessTokenValiditySeconds(3600)		// 1 hour
    }
}