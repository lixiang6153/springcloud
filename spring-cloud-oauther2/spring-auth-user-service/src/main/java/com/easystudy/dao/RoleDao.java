package com.easystudy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easystudy.model.Role;

/**
 * 用户自定义接口
 * @author Administrator
 */
@Repository
public interface RoleDao extends /*CrudRepository<Role, Long>, */PagingAndSortingRepository<Role, Long>{
	/**
	 * 通过角色名查询角色信息
	 * @param username
	 * @return
	 */
	@Query(value="select * from role r where r.name=:rolename", nativeQuery=true)
    public Role findByRoleName(@Param("rolename") String rolename);
	
	/**
	 * 通过用户id获取用户角色列表
	 * @param userId
	 * @return
	 */
	@Query(value="select r.* from user u join userrole ur join role r where u.id=ur.user_id and ur.role_id=r.id and u.id=:userId", nativeQuery=true)
	public List<Role> findRolesByUserId(@Param("userId") Long userId);
	
	/**
	 * 通过用户名获取用户角色列表
	 * @param userName
	 * @return
	 */
	@Query(value="select r.* from user u join userrole ur join role r where u.id=ur.user_id and ur.role_id=r.id and u.username=:username", nativeQuery=true)
	public List<Role> findRolesByUserName(@Param("username") String userName);
	
}
