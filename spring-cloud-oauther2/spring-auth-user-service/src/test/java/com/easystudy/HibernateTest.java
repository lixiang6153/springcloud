package com.easystudy;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easystudy.model.User;
import com.easystudy.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)				// 必须要Junit4以上
@SpringBootTest(classes = UserServiceApp.class)		// 指定启动的应用，使用启动后创建的spring上下文环境
public class HibernateTest {
	@Autowired
	private UserService userService;
	
	@Test
	public void findUser(){
		User user = userService.findByUsername("lixx");
		System.out.println("搜索结果：" + user);
	}
	
	@Test
	public void testUser() {
		User user = new User();
		user.setUsername("lixx");
		user.setPassword("123456");
		user.setName("李强");
		user.setBirthday(new Date());
		user.setEmail("lixiang6153@126.com");
		user.setPhone("18689250776");
		user.setSex(0L);
		userService.add(user);
	}
}
