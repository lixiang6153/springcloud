package com.easystudy.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.User;
import com.easystudy.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	protected UserService userService;
	
	/**
	 * 通过用户名获取用户信息
	 * @param username
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "/findByUserName/{username}", method = RequestMethod.GET)
	public ReturnValue<User> findByUserName(@PathVariable("username") String username) throws JsonProcessingException{
		User user = userService.findByUsername(username);
		
		ReturnValue<User> ret = new ReturnValue<User>(user);
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(ret);
		System.out.println(str);
		
		return ret;
	}
	
	/**
	 * 获取所有用户信息
	 * @return
	 */
	@RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
	public ReturnValue<List<User>> findAllUser(){
		List<User> users = userService.findAll();
		System.out.println("查找所有用户：" + users);
		return new ReturnValue<List<User>>(users);
	}
	
	/**
	 * 根据用户id删除用户
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
	public ReturnValue<String> deleteUser(@PathVariable("id") Long id){
		userService.delete(id);
		return new ReturnValue<String>();
	}
	
	/**
	 * 添加用户
	 * @param username
	 * @param password
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ReturnValue<User> addUser(@RequestParam(name="username", required=true) String username, @RequestParam(name="password", required=true) String password, 
						  	   		 @RequestParam(name="name", required=true) String name, @RequestParam(name="sex", required=false, defaultValue="0") Long sex){
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setSex(sex);
		
		userService.add(user);
		
		return new ReturnValue<User>(user);
	}
	
	public static void main(String[] args) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		User user = new User();
		user.setUsername("lixx");
		user.setPassword("123456");
		user.setName("李强");
		user.setBirthday(new Date());
		user.setEmail("lixiang6153@126.com");
		user.setPhone("18689250776");
		user.setSex(0L);
		
		String str = mapper.writeValueAsString(user);
		System.out.println(str);
		
		User user2 = mapper.readValue(str, User.class);
		System.out.println("用户名：" + user2.getName());
		
		ReturnValue<User> ret = new ReturnValue<User>(user);
		String response = mapper.writeValueAsString(ret);
		System.out.println(response);
	}
}
