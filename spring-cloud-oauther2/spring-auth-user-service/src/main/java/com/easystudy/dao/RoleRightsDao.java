package com.easystudy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easystudy.model.RoleRights;

/**
 * 用户自定义接口
 * @author Administrator
 */
@Repository
public interface RoleRightsDao extends /*CrudRepository<RoleRights, Long>, */PagingAndSortingRepository<RoleRights, Long>{
	/**
	 * 通过角色id和权限id查询某角色权限信息
	 * @param username
	 * @return
	 */
	@Query(value="select * from rolerights rr where rr.role_id=:roleId and rr.right_id=:rightId", nativeQuery=true)
    public RoleRights findByRoleIdAndRightId(@Param("roleId") Long roleId, @Param("rightId") Long rightId);
	
	/**
	 * 通过角色id查询角色所有权限
	 * @param username
	 * @return
	 */
	@Query(value="select * from rolerights rr where rr.role_id=:roleId", nativeQuery=true)
    public List<RoleRights> findByRoleId(@Param("roleId") Long roleId);
	
	/**
	 * 通过用户id清空角色权限
	 * @param userId
	 * @return
	 */
	@Modifying
	@Query(value="delete from rolerights rr where rr.role_id=:roleId", nativeQuery=true)
	public void deleteByRoleId(@Param("roleId") Long roleId);	
}
