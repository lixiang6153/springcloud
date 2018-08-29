package com.easystudy.service;

import java.util.List;

import com.easystudy.model.Role;

/**
 * 角色专门的服务接口
 * @author Administrator
 *
 */
public interface RoleService  extends BaseService<Role>{
	/**
	 * 通过角色名称获取角色信息
	 * @param name
	 * @return
	 */
	public Role findByRoleName(String name);
	
	/**
	 * 通过用户id获取用户角色列表
	 * @param userName
	 * @return
	 */
	public List<Role> findRolesByUserId(Long userId);
	
	/**
	 * 通过用户id获取用户角色列表
	 * @param userName
	 * @return
	 */
	public List<Role> findRolesByUserName(String userName);
}
