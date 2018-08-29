package com.easystudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.Role;
import com.easystudy.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleRightsController {
	
	@Autowired
	protected RoleService roleService;
	
	/**
	 * 通过用户id获取角色列表
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/findRolesByUserId/{userId}", method = RequestMethod.GET)
	public ReturnValue<List<Role>> findRolesByUserId(@PathVariable("userId") Long userId){
		List<Role> roles = roleService.findRolesByUserId(userId);
		return new ReturnValue<List<Role>>(roles);
	}
	
	/**
	 * 通过用户名获取角色列表
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/findRolesByUserName/{userName}", method = RequestMethod.GET)
	public ReturnValue<List<Role>> findRolesByUserName(@PathVariable("userName") String userName){
		List<Role> roles = roleService.findRolesByUserName(userName);
		return new ReturnValue<List<Role>>(roles);
	}
}
