package cn.itsoruce.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cn.itsoruce.springboot.repository.BaseRepositoryFactoryBean;

@EnableJpaRepositories(
		basePackages={"cn.itsoruce.springboot.repository"},
		repositoryFactoryBeanClass=BaseRepositoryFactoryBean.class
)
@SpringBootApplication
public class Yhptest {

	public static void main(String[] args) {
		SpringApplication.run(Yhptest.class, args);
	}
}
