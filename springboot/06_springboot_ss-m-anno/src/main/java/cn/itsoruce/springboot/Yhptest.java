package cn.itsoruce.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.itsoruce.springboot.mapper")
public class Yhptest {

	public static void main(String[] args) {
		SpringApplication.run(Yhptest.class, args);
	}
}
