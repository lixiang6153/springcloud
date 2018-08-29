package cn.itsoruce.springboot.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.Page;

import cn.itsoruce.springboot.Yhptest;
import cn.itsoruce.springboot.user.domian.User;
import cn.itsoruce.springboot.user.service.IUserService;

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
		User user = new User( "yts");
		System.out.println(user);
		userService.add(user);
		System.out.println(user);
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
		Page<User> all = (Page<User>) userService.getAll();
		//com.github.pagehelper.Page---->相当于是原来的PageList
		System.out.println(all.getClass());
		
		//当前页
		System.out.println("当前页:"+all.getPageNum());
		//总页数
		System.out.println("总页数:"+all.getPages());
		//总条数
		System.out.println("总条数:"+all.getTotal());
		//每页显示多少条
		System.out.println("每页显示多少条:"+all.getPageSize());
		//当前页数据-它本身（本身就是list）
		for (User user : all) {
			System.out.println(user);
			
		}
		
	}

}
