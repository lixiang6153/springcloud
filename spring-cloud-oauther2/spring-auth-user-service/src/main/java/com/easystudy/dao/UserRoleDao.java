package com.easystudy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easystudy.model.UserRole;

/**
 * 用户角色自定义接口
 * @author Administrator
 */
@Repository
public interface UserRoleDao extends /*CrudRepository<UserRole, Long>, */PagingAndSortingRepository<UserRole, Long>{
	/**
	 * 通过用户id和角色id获取用户角色
	 * @param username
	 * @return
	 */
	@Query(value="select * from userrole ur where ur.user_id=:userId and ur.role_id=:roleId",nativeQuery=true)
    public UserRole findByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);
	
	/**
	 * 通过用户id获取用户角色
	 * @param username
	 * @return
	 */
	@Query(value="select * from userrole ur where ur.user_id=:userId",nativeQuery=true)
    public List<UserRole> findByUserId(@Param("userId") Long userId);
	
	/**
	 * 通过用户id清空用户角色
	 */
	@Modifying
	@Query(value="delete from userrole ur where ur.user_id=:userId", nativeQuery=true)
	public void deleteByUserId(@Param("userId") Long id);
}
