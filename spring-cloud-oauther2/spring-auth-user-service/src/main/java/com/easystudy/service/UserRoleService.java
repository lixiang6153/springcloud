package com.easystudy.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import com.easystudy.model.UserRole;

public interface UserRoleService extends BaseService<UserRole>{
	/**
	 * 通过用户id和角色id获取用户角色
	 * @param username
	 * @return
	 */
    public UserRole findByUserIdAndRoleId(Long userId, Long roleId);
    
	/**
	 * 通过用户id获取用户角色
	 * @param username
	 * @return
	 */
    public List<UserRole> findByUserId(Long userId);
	
	/**
	 * 通过用户id清空用户角色
	 */
	@Modifying
	public void deleteByUserId(Long id);
}
