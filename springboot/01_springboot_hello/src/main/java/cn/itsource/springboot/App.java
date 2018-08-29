package cn.itsource.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 原来Spring web项目启动原理
 *   1）在外部启动Tomcat时
 *   2）会根据web.xml里面配置spring相关的配置初始化一个Spring
 *       要通过特定配置文件-applcationContext.xml bean,aop
 *   3) 还会扫描特定注解，并且把这些bean纳入Spring管理
 *     controller(用户请求) service（业务）  dao（数据操作）
 *   
 * @author Administrator
 *
 */
@SpringBootApplication//标识该项目为springboot的应用
public class App {

	public static void main(String[] args) {
		//启动springboot应用
		//1)拉起一个内置Tomcat
		//2)也会初始化一个Spring-原来手动配置web.xml,applicationContext-*.xml通通都自动配置
		    //里面有一些默认的配置，比如springmvc mybatis 事务配置，等
		//3)会把加了@SpringBootApplication的主键的类当前包，及其子子孙孙包，进行相关注解的扫描，
		//把相关的bean纳入Spring管理
		SpringApplication.run(App.class, args);
	}
}
