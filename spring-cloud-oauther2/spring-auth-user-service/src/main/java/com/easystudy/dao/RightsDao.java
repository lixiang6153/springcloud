package com.easystudy.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easystudy.model.Rights;

/**
 * 用户权限操作接口
 * @author Administrator
 */
@Repository
public interface RightsDao extends /*CrudRepository<Rights, Long>, */PagingAndSortingRepository<Rights, Long>{
	/**
	 * 通过权限值获取权限详情
	 * @param rightValue
	 * @return
	 */
	@Query(value="select * from rights where value=:rightValue", nativeQuery=true)
	public Rights findRightsByValue(@Param("rightValue") String rightValue);
}
