package com.easystudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.RoleRights;
import com.easystudy.service.RoleRightsService;

@RestController
@RequestMapping("/roleRights")
public class RoleController {
	
	@Autowired
	protected RoleRightsService roleRightsService;
	
	/**
	 * 通过角色id获取角色权限列表
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/findByRoleId/{roleId}", method = RequestMethod.GET)
	public ReturnValue<List<RoleRights>> findRolesByUserId(@PathVariable("roleId") Long roleId){
		List<RoleRights> roles = roleRightsService.findByRoleId(roleId);
		return new ReturnValue<List<RoleRights>>(roles);
	}
}
