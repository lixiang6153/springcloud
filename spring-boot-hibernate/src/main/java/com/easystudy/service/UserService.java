package com.easystudy.service;

import java.util.List;

import com.easystudy.model.User;

public interface UserService extends BaseService<User>{
	/**
	 * 根据索引查询
	 * @param personIdType 证件类型
	 * @param personId 证件号码,可为空
	 * @return
	 */
	List<User> findByPersionIdType_PersonId(Integer personIdType,String personId);
	
	/**
	 * 根据名称查询
	 * @param personIdType 证件类型
	 * @param personId 证件号码,可为空
	 * @return
	 */
	List<User> findByPersionName(String name);
}
