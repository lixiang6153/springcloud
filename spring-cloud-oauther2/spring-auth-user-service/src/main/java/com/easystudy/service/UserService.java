package com.easystudy.service;

import com.easystudy.model.User;

/**
 * 提供用户专职的服务接口
 * @author Administrator
 */
public interface UserService extends BaseService<User>{
	/**
	 * 用户用户名获取用户信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * 根据用户id更新用户名称
	 * @param id
	 * @param name
	 */
	public void updateNameById(Long id, String name);
}