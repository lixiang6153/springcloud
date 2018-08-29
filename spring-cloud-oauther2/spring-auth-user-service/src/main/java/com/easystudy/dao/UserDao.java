package com.easystudy.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easystudy.model.User;

/**
 * 用户自定义接口
 * @author Administrator
 */
@Repository
public interface UserDao extends /*CrudRepository<User, Long>, */PagingAndSortingRepository<User, Long>{
	/**
	 * 自定义查询nativeQuery=true说明为原生sql语句
	 * 1代表的是方法参数里面的顺序：此处为用户名
	 * @param username
	 * @return
	 */
	@Query(value="select * from user u where u.username=:username",nativeQuery=true)
    public User findByUsername(@Param("username") String username);
	
	/**
	 * 根据用户id更新用户名称
	 */
	@Modifying
	@Query(value="update user u set u.name=:name where id=:id", nativeQuery=true)
	public void updateNameById(@Param("id") Long id, @Param("name") String name);
}
