package com.easystudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @MapperScan这个对应了项目中mapper（dao）所对应的包路径，很多同学就是这里忘了加导致异常的
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.easystudy.mapper")		//将项目中对应的mapper类的路径加进来就可以了
public class Springboot2MybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2MybatisDemoApplication.class, args);
	}

}
