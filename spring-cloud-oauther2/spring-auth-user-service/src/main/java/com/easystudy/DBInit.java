package com.easystudy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.easystudy.model.Rights;
import com.easystudy.model.Role;
import com.easystudy.model.RoleRights;
import com.easystudy.model.User;
import com.easystudy.model.UserRole;
import com.easystudy.service.RightService;
import com.easystudy.service.RoleRightsService;
import com.easystudy.service.RoleService;
import com.easystudy.service.UserRoleService;
import com.easystudy.service.UserService;

/**
 * 启动-数据库加载
 * @author Administrator
 * 启动参数加载释放方式有如下几种方式：
 * 1、实现CommandLineRunner命令行运行接口
 * 2、使用过滤器，在实现类中标注注解：
 * 	 @WebFilter(filterName="myFilter",urlPatterns="/*")，属性filterName声明过滤器的名称,可选；属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明，必选
 * 3、实现ServletContext监听器，在实现类中标注注解：
 *   @WebListener，实现ServletContextListener接口(即标注@WebListener并且实现implements ServletContextListener)
 *   在启动类中添加@ServletComponentScan扫描自定义servlet
 * 4、实现HttpSessionListener监听器，在实现类中标注注解：
 *   @WebListener，实现HttpSessionListener接口(即标注@WebListener并且实现implements HttpSessionListener)
 * 5、在主容器的main函数实现自己的初始化(即SpringApplication.run(UserServiceApp.class, args)之后添加)
 */
@Component
@Order(1)
public class DBInit implements CommandLineRunner{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RightService rightService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleRightsService roleRightsService;
	
	@Override
	public void run(String... args) throws Exception {
		
		// 秘钥加密存储到数据库--此处手动填写到数据库
		String text = new BCryptPasswordEncoder().encode("test_client_secret");
		System.out.println(text);
		
		// 创建用户
		List<User> users = initUser();
		
		// 创建权限
		List<Rights> rights = initRights();
		
		// 创建角色
		List<Role> roles = initRoles();
		
		// 用户角色
		// 超级管理员
		initUserRole(users.get(0), roles);
		
		// 管理员
		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(roles.get(1));
		roles2.add(roles.get(2));
		initUserRole(users.get(1), roles2);
		
		// 操作员
		List<Role> roles3 = new ArrayList<Role>();
		roles3.add(roles.get(2));
		initUserRole(users.get(2), roles3);
		
		// 角色权限
		// 超级管理员权限
		initRoleRights(roles.get(0), rights);
		
		// 管理员权限
		List<Rights> right2 = new ArrayList<Rights>();
		right2.add(rights.get(1));
		right2.add(rights.get(2));
		initRoleRights(roles.get(1), right2);
		
		// 操作员权限
		List<Rights> right3 = new ArrayList<Rights>();
		right3.add(rights.get(2));
		initRoleRights(roles.get(2), right3);
	}
	
	private void initRoleRights(Role role, List<Rights> rights) {
		for (Rights right : rights) {
			RoleRights rr = roleRightsService.findByRoleIdAndRightId(role.getId().longValue(), right.getId().longValue());
			if(null == rr){
				rr = new RoleRights();
				rr.setRole_id(role.getId());
				rr.setRight_id(right.getId());
				roleRightsService.add(rr);
			}
		}
	}

	private void initUserRole(User user, List<Role> roles) {
		for (Role r : roles) {
			UserRole ur = userRoleService.findByUserIdAndRoleId(user.getId().longValue(), r.getId().longValue());
			if(null == ur){
				ur = new UserRole();
				ur.setUser_id(user.getId());
				ur.setRole_id(r.getId());
				userRoleService.add(ur);
			}
		}
		
	}

	List<User> initUser(){
		User user = userService.findByUsername("lixx");
		if(null == user){
			// 超级管理员权限
			user = new User();
			user.setUsername("lixx");
			//user.setPassword("dw123456");
			user.setPassword(new BCryptPasswordEncoder().encode("dw123456"));
			user.setName("李强");
			user.setBirthday(new Date());
			user.setEmail("lixiang6153@126.com");
			user.setPhone("18689250776");
			user.setSex(0L);
			userService.add(user);
		}
		
		User user2 = userService.findByUsername("admin");
		if(null == user2){
			// 超级管理员权限
			user2 = new User();
			user2.setUsername("admin");
			user2.setPassword(new BCryptPasswordEncoder().encode("dw123456"));
			//user2.setPassword("dw123456");
			user2.setName("管理员");
			user2.setBirthday(new Date());
			user2.setSex(0L);
			userService.add(user2);
		}
		
		User user3 = userService.findByUsername("operator");
		if(null == user3){
			// 超级管理员权限
			user3 = new User();
			user3.setUsername("operator");
			user3.setPassword(new BCryptPasswordEncoder().encode("dw123456"));
			//user3.setPassword("dw123456");
			user3.setName("操作员");
			user3.setBirthday(new Date());
			user3.setSex(1L);
			userService.add(user3);
		}
		
		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user2);
		users.add(user3);
		return users;
	}
	
	List<Role> initRoles(){
		Role role1 = roleService.findByRoleName("ROLE_SUPER");
		if(null == role1){
			role1 = new Role();
			role1.setName("ROLE_SUPER");
			role1.setStatus(1L);
			role1.setValue("001");
			role1.setTips("超级管理员");
			roleService.add(role1);
		}
		
		Role role2 = roleService.findByRoleName("ROLE_ADMIN");
		if(null == role2){ 
			role2 = new Role();
			role2.setName("ROLE_ADMIN");
			role2.setStatus(1L);
			role2.setValue("002");
			role2.setTips("管理员");
			roleService.add(role2);
		}
		
		Role role3 = roleService.findByRoleName("ROLE_OPERATOR");
		if(null == role3){
			role3 = new Role();
			role3.setName("ROLE_OPERATOR");
			role3.setStatus(1L);
			role3.setValue("003");
			role3.setTips("操作员");
			roleService.add(role3);
		}
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		roles.add(role3);
		return roles;
	}
	
	List<Rights> initRights(){
		Rights right1 = rightService.findRightsByValue("001");
		if(null == right1){
			right1 = new Rights();
			right1.setName("ROLE_HOME");
			right1.setValue("001");
			rightService.add(right1);
		}
		
		Rights right2 = rightService.findRightsByValue("002");
		if(null == right2){
			right2 = new Rights();
			right2.setName("ROLE_DEVICE");
			right2.setValue("002");
			rightService.add(right2);
		}
		
		Rights right3 = rightService.findRightsByValue("003");
		if(null == right3){
			right3 = new Rights();
			right3.setName("ROLE_VIDEO");
			right3.setValue("003");
			rightService.add(right3);
		}
		
		List<Rights> rights = new ArrayList<Rights>();
		rights.add(right1);
		rights.add(right2);
		rights.add(right3);
		return rights;
	}
}
