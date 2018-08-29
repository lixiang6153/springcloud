package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @SpringBootApplication相当于 @Configuration、@EnableAutoConfiguration 、 @ComponentScan 三个的作用
 * @Configuration 表示这个类是一个spring 配置类，一般这里面会定义Bean，会把这个类中bean加载到spring容器中
 * @EnableAutoConfiguration springboot的注解 会在你开启某些功能的时候自动配置 ，这个注解告诉Spring Boot根据添加的jar依赖猜测你想如何配置Spring
 */
@SpringBootApplication
public class App {
	
    public static void main( String[] args ){
    	SpringApplication.run(App.class, args);
    	
    	/*ApplicationContext applicationContext = SpringApplication.run(App.class, args);
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println("datasource is :" + dataSource);

		// 检查数据库是否是hikar数据库连接池
		if (!(dataSource instanceof HikariDataSource)) {
			System.err.println(" Wrong datasource type :" + dataSource.getClass().getCanonicalName());
			System.exit(-1);
		}

		try {
			Connection connection = dataSource.getConnection();
			ResultSet rs = connection.createStatement().executeQuery("SELECT 1");
			if (rs.first()) {
				System.out.println("Connection OK!");
			} else {
				System.out.println("Something is wrong");
			}
			// connection.close();
		} catch (SQLException e) {
			System.out.println("connect DB failed.");
			e.printStackTrace();
			System.exit(-2);
		}*/
    }
    
}
