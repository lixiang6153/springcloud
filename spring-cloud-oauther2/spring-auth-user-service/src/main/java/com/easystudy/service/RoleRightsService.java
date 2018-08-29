package com.easystudy.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import com.easystudy.model.RoleRights;

public interface RoleRightsService extends BaseService<RoleRights>{
	/**
	 * 通过角色id和权限id查询某角色权限信息
	 * @param username
	 * @return
	 */
    public RoleRights findByRoleIdAndRightId(Long roleId, Long rightId);
    
	/**
	 * 通过角色id查询角色所有权限
	 * @param username
	 * @return
	 */
    public List<RoleRights> findByRoleId(Long roleId);
	
	/**
	 * 通过用户id清空角色权限
	 * @param userId
	 * @return
	 */
	@Modifying
	public void deleteByRoleId(Long roleId);
}
