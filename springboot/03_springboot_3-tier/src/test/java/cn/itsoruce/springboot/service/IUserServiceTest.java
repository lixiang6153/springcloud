package cn.itsoruce.springboot.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import cn.itsoruce.springboot.Yhptest;
import cn.itsoruce.springboot.domian.User;

//原来：
//@RunWith(springjunit....)
//@ContextConfigra....(classpath:applciationContext.xml)
//新的：
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Yhptest.class)//创建一个Spring容器，并且会扫描Yhptest的包及其子子孙孙包的bean
public class IUserServiceTest {

	@Autowired
	private IUserService userService;
	
	@Test
	public void testAdd() {
		userService.add(new User(1L, "zs"));
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

}
