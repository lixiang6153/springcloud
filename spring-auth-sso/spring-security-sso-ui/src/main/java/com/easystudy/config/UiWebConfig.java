package com.easystudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * url订制:
 * @EnableWebMvc是使用Java 注解快捷配置Spring Webmvc的一个注解。在使用该注解后配置一个继承于
 * WebMvcConfigurerAdapter的配置类即可配置好Spring Webmvc,启动Spring MVC特性
 * 也就是写Spring MVC时的时候会用到
 * EnableWebMvc注解的作用： 就是把WebMvcConfigurationSupport中的配置加载过来，
 * 					         你继承WebMvcConfigurerAdapter时自定义的一些配置会覆盖
 * 					   WebMvcConfigurationSupport里的默认配置
 * 
 * pring Boot 默认为我们提供了静态资源处理，使用 WebMvcAutoConfiguration 中的配置各种属性。
 * 建议大家使用Spring Boot的默认配置方式，如果需要特殊处理的再通过配置进行修改，如果想要自己完全控制WebMVC，
 * 就需要在@Configuration注解的配置类上增加@EnableWebMvc，增加该注解以后WebMvcAutoConfiguration
 * 中配置就不会生效，你需要自己来配置需要的每一项
 * 
 * 1、@EnableWebMvc+extends WebMvcConfigurationAdapter在扩展的类中重写父类的方法即可，这种方式会屏蔽Spring Boot的@EnableAutoConfiguration中的设置。
 * 2、extends WebMvcConfigurationSupport在扩展的类中重写父类的方法即可，这种方式会屏蔽Spring Boot的@EnableAutoConfiguration中的设置
 * 3、extends WebMvcConfigurationAdapter在扩展的类中重写父类的方法即可，这种方式依旧使用Spring Boot的@EnableAutoConfiguration中的设置
 * 	    而我们继承的WebMvcConfigurerAdapter覆盖的方法又会覆盖WebMvcConfigurationSupport 里的默认配
 * 
 * extends WebMvcConfigurerAdapter过期，使用extends WebMvcConfigurationSupport会屏蔽SpringBoot配置，
 * 解决办法：实现接口WebMvcConfigurer实现自己想实现的接口即可！
 * 
 * 注意：thymeleaf渲染模板的使用
 * spring.thymeleaf.cache是否开启模板缓存，默认true
 * spring.thymeleaf.content-type指定Content-Type，默认为: text/html
 * spring.thymeleaf.enabled是否允许MVC使用Thymeleaf，默认为: true
 * spring.thymeleaf.encoding指定模板的编码，默认为: UTF-8
 * spring.thymeleaf.excluded-view-names指定不使用模板的视图名称，多个以逗号分隔
 * spring.thymeleaf.prefix指定模板的前缀，默认为:classpath:/templates/
 * spring.thymeleaf.suffix指定模板的后缀，默认为:.html
 * spring.thymeleaf.template-resolver-order指定模板的解析顺序，默认为第一个
 */
@Configuration
@EnableWebMvc
public class UiWebConfig implements WebMvcConfigurer {

	/**
	 * 提供配置解析功能
	 * @Value("${spring_only}")
	 * private String springOnly;
	 * 或xml配置：<property name="url" value="${jdbc.url}"/>
	 * 可以很方便的通过配置XML来实现对Classpath下的配置文件的注入
	 * @return
	 */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
	 * 默认的sevlet处理：所有请求都先经过默认sevlet，如果是静态文件，则进行处理，不是则放行交由DispatcherServlet控制器处理
	 */
    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 配置静态资源处理
     * 在项目开发过程中，经常会涉及页面跳转问题，而且这个页面跳转没有任何业务逻辑过程，只是单纯的路由过程 ( 点击一个按钮跳转到一个页面 ),如
     *  @RequestMapping("/toview")
		 public String view(){
		    return "view";
		 }
		 访问/toview控制器跳转到view.jsp,如果项目中有很多类似的无业务逻辑跳转过程，那样会有很多类似的代码,如何可以简单编写，这种代码
		 registry.addViewController("/toview").setViewName("/view"); 效果等同于上面
     */
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
        
        // 根路径控制器跳转到index页面
        registry.addViewController("/")
            	.setViewName("forward:/index");
        
        registry.addViewController("/index");
        registry.addViewController("/securedPage");
    }
    
//	/**
//	 * 视图解析器
//	 */
//	@Override
//	protected void configureViewResolvers(ViewResolverRegistry registry) {
//		// TODO Auto-generated method stub
//		super.configureViewResolvers(registry);
    	// 修改默认的视图类型： prefix前缀/WEB-INF/views/目录，以及后缀为.jsp视图
//		//registry.jsp("/WEB-INF/views/", ".jsp");
    	// model转成json
//		//registry.enableContentNegotiation(new MappingJackson2JsonView());
//	}

    /**
	 * Spring Boot 的默认资源映射：
	 * 其中默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources）
	 * 其中默认配置的 /webjars/** 映射到 classpath:/META-INF/resources/webjars/ 
	 * 上面的 static、public、resources 等目录都在 classpath: 下面（如 src/main/resources/static）
	 * 如果上述路径都有，那么优先级顺序为：META/resources > resources > static > public，不够用或修改的情况则用到自定义目录
	 * 自定义目录:
	 * 以增加 /resources/* 映射到 classpath:/resources/* 
	 */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
            	.addResourceLocations("/resources/");
    }

}