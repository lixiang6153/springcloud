package com.easystudy.config;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.easystudy.service.impl.UserDetailsServiceImpl;

/**
 * 创建授权配置信息:认证服务器，进行认证和授权,声明一个认证服务器，当用此注解后，应用启动后将自动生成几个Endpoint
 * AuthorizationServerConfigurer包含三种配置：
 * ClientDetailsServiceConfigurer：client客户端的信息配置，
 * client信息包括：clientId、secret、scope、authorizedGrantTypes、authorities
 * （1）scope：表示权限范围，可选项，用户授权页面时进行选择
 * （2）authorizedGrantTypes：有四种授权方式 
 * 		Authorization Code：用验证获取code，再用code去获取token（用的最多的方式，也是最安全的方式）
 * 		Implicit: 隐式授权模式
 * 		Client Credentials (用來取得 App Access Token)
 * 		Resource Owner Password Credentials
 * 		authorized_grant_types： 多个用逗号隔开：authorization_code,password,refresh_token,implicit,client_credentials
 * （3）authorities：授予client的权限
 */
@Configuration
@EnableAuthorizationServer			// oauth2分为资源服务和认证授权服务，可以分开，可以统一进程
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	// 验证管理器，是一个AuthenticationProvider验证集合，本身不做验证
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    // 使用redis存储的时候使用
    //@Autowired
    //private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private DataSource dataSource;
    
    /*
     * 
      redis 存储部分
    // 声明TokenStore实现：使用redis存储token信息---此处会报错
//    @Bean
//    RedisTokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
    @Bean
    MyRedisTokenStore  redisTokenStore(){
    	return new MyRedisTokenStore(redisConnectionFactory);
    }
    // 配合redis使用，spring5.0之后password被添加上{}，所以比较的必须使用自定义的
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyBCryptPasswordEncoder();
    }
    */
    
    // token存储数据库-使用jdbc存储客户token信息
    @Bean
    public JdbcTokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    // 声明 ClientDetails实现:定义授权的请求的路径的Bean--JDBC存储客户信息
    @Bean
    public ClientDetailsService JdbcClientDetails() {
    	// 使用JdbcClientDetailsService客户端详情服务
    	// Jdbc实现客户端详情服务，数据源dataSource不做叙述，使用框架默认的表
        return new JdbcClientDetailsService(dataSource);
    }
    
    /**
     * 授权认证端点拦截验证：
     * 声明授权和token的端点以及token的服务的一些配置信息，比如采用什么存储方式、token的有效期等：
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * 包含以下的端点（以上这些endpoint都在源码里的endpoint包里面）:
     * 1、AuthorizationEndpoint 根据用户认证获得授权码，有下面两个方法
     *   -/oauth/authorize - GET
     *   -/oauth/authorize - POST
     * 2、TokenEndpoint 客户端根据授权码获取 token 
     *   -/oauth/token - GET
     *   -/oauth/token - POST
     * 3、CheckTokenEndpoint 资源服务器用来校验token
     *   -/oauth/check_token
     * 4、WhitelabelApprovalEndpoint 显示授权服务器的确认页
     * 	 -/oauth/confirm_access
     * 5、WhitelabelErrorEndpoint 显示授权服务器的错误页 
     *   -/oauth/error
     * 6、/oauth/token_key：如果jwt模式则可以用此来从认证服务器获取公钥
     * 这些端点有个特点，如果你自己实现了上面的方法，他会优先使用你提供的方法，利用这个特点，通常都会根据自己的
     * 需要来设计自己的授权确认页面，例如使用 QQ 登录微博的认证页面
     * 在官方的示例中，通过下面代码直接指定了视图:
     * registry.addViewController("/oauth/confirm_access").setViewName("authorize");
     * 
     * 授权类型：
     * 通过AuthorizationServerEndpointsConfigurer来进行配置，默认情况下，支持除了密码外的所有授权类型，相关授权类型的一些类：
     * （1）authenticationManager：直接注入一个AuthenticationManager，自动开启密码授权类型
     * （2）userDetailsService：如果注入UserDetailsService，那么将会启动刷新token授权类型，会判断用户是否还是存活的
     * （3）authorizationCodeServices：AuthorizationCodeServices的实例，auth code 授权类型的服务
     * （4）implicitGrantService：imlpicit grant
     * （5）tokenGranter：
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        		 // 使用的认证管理器-默认包含登录认证、用户名密码认证
        		 .authenticationManager(authenticationManager)
        		 // 从内存中查请求的token
        		 //.tokenStore(redisTokenStore())
        		 // token存储在数据库中-生产环境使用以免服务器崩溃
        		 .tokenStore(jdbcTokenStore())
        		 // 若无，refresh_token会有UserDetailsService is required错误
        		 // 从数据查用户授权信息
                 .userDetailsService(userDetailsService);
                 
        // 配置TokenServices参数
        // Spring Cloud Security OAuth2通过DefaultTokenServices类来完成token生成、过期等 OAuth2 标准规定的业务逻辑
        // 而DefaultTokenServices又是通过TokenStore接口完成对生成数据的持久化
        
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        // tokenServices.setTokenStore(redisTokenStore());								// token存储在redis中-如果存储在jdbc中就需要建立token存储表
//        tokenServices.setTokenStore(endpoints.getTokenStore());							// token存储在redis中-如果存储在jdbc中就需要建立token存储表
//        tokenServices.setSupportRefreshToken(true);										// 支持更换token
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());		// jdbc具体的秘钥认证服务-如果存储在jdbc中就需要建立oauth_client_details表
//        tokenServices.setAccessTokenValiditySeconds(60*60*12); 			// token有效期自定义设置，默认12小时
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);	// 默认30天，这里修改
//        endpoints.tokenServices(tokenServices);
        
        endpoints.tokenServices(defaultTokenServices());
    }
    
    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * 声明安全约束，哪些允许访问，哪些不允许访问
     * /oauth/token
     * 1、这个如果配置支持allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走ClientCredentialsTokenEndpointFilter来保护
     * 2、如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	// 允许获取token
        security.tokenKeyAccess("permitAll()");
        // isAuthenticated()检查access_token需要进行授权
        security.checkTokenAccess("isAuthenticated()");
        // 允许以form形式提交表单信息，否则向/oauth/token或/oauth/accessToken端点获取access_token的时候报错401
        security.allowFormAuthenticationForClients();
    }
    
    /**
     * 为秘钥分配权限限定：
     * 通客户端通过分配的client_id和私钥secret以及私钥的权限等向认证服务器获取授权码，然后换取token，也可以定期更新token
     * 通过手动创建client_id和client_secret，验证用户访问有效性：（client_id，返回授权码）
     * 如，具体步骤：
     * 1、用户访问授权页面
     * localhost:8080/oauth/authorize?client_id=android&response_type=code&redirect_uri=http://www.baidu.com
     * 2、此时浏览器会让你输入用户名密码，这是因为 Spring Security 在默认情况下会对所有URL添加Basic Auth认证。默认的用户名为user, 密码是随机生成的，在控制台日志中可以看到
     * 3、输入用户名密码，拿到code
     * 4、调用POST/GET http://android:secret@localhost:8080/oauth/token来换区access_token
     *   参数：grant_type=authorization_code&code=Li4NZo&redirect_uri=http://www.baidu.com
     *   由于authorization_code的授权方式不需要 client_secret, 因此secret可以填写任意值
     * 5、用返回的access_token访问对应页面，到此我们最最基本的授权服务就搭建完成了
     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()																	// 使用in-memory存储--redis
//                    .withClient("android")													// 分配的client_id=app
//                    .scopes("app")															// 允许的授权范围，scope代表授权范围，可以客户端自己设定不同的值代表不同的范围
//                    .secret("android")														// client_secret
//                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")	// 该client允许的授权类型:grant_type=authorization_code或password.
//                .and()
//                    .withClient("webapp")
//                    .scopes("xx")
//                    .authorizedGrantTypes("implicit")											// 授权模式4种：授权码模式（authorization code）即response_type为code、简化模式（implicit）、密码模式（resource owner password credentials）、客户端模式（client credentials）
//                .and()
//                    .withClient("browser")
//                    .authorizedGrantTypes("refresh_token", "password")
//                    .scopes("ui");
//    }
    
    /**
     * AuthorizationServerConfigurer 的一个回调配置项:
     * client的信息的读取：在ClientDetailsServiceConfigurer类里面进行配置，
     * 可以有in-memory、jdbc等多种读取方式，jdbc需要调用JdbcClientDetailsService类，
     * 此类需要传入相应的DataSource.
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.withClientDetails(clientDetails());
    	// 这个地方指的是从jdbc查出数据来[存储]
        clients.withClientDetails(JdbcClientDetails());
    }

    /**
     * Spring Cloud Security OAuth2通过DefaultTokenServices类来完成token生成、过期等 OAuth2 标准规定的业务逻辑，
     * 而DefaultTokenServices又是通过TokenStore接口完成对生成数据的持久化。在上面的demo中，TokenStore的默认实现为
     * InMemoryTokenStore，即内存存储。 对于Client信息，ClientDetailsService接口负责从存储仓库中读取数据，在上面的
     * demo中默认使用的也是InMemoryClientDetialsService实现类。说到这里就能看出，要想使用数据库存储，只需要提供这些接口的
     * 实现类即可。庆幸的是，框架已经为我们写好JDBC实现了，即JdbcTokenStore和JdbcClientDetailsService
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * 
     * token存储方式共有三种分别是：
     * （1）InMemoryTokenStore：存放内存中，不会持久化
     * （2）JdbcTokenStore：存放数据库中
     * （3）Jwt: json web token
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //tokenServices.setTokenStore(redisTokenStore());									// token存储在redis中-如果存储在jdbc中就需要建立token存储表
        tokenServices.setTokenStore(jdbcTokenStore());									// token存储在redis中-如果存储在jdbc中就需要建立token存储表
        tokenServices.setSupportRefreshToken(true);										// 支持更换token
        tokenServices.setClientDetailsService(JdbcClientDetails());						// jdbc具体的秘钥认证服务-如果存储在jdbc中就需要建立oauth_client_details表
        //tokenServices.setClientDetailsService(InMemClientDetails());					// 内存秘钥认证服务
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(15)); // token有效期自定义设置，30天
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));// 默认30天
        return tokenServices;
    }
}
