package com.easystudy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.easystudy.dao.RoleRightsDao;
import com.easystudy.model.RoleRights;
import com.easystudy.service.RoleRightsService;

@Service
public class RoleRightsServiceImpl implements RoleRightsService {

	@Autowired
	private RoleRightsDao dao;
	
	/**
	 * 往数据库插入一条记录，如果已存在则抛异常
	 * @param o
	 */
	@Override
	public void add(RoleRights o) {
		dao.save(o);
	}

	/**
	 * 往数据库插入n条记录，如果已存在则抛异常
	 * @param o
	 */
	@Override
	public void add(List<RoleRights> o) {
		dao.saveAll(o);
	}

	/**
	 * 根据id往数据库删除一条记录
	 * @param id 删除对象的id
	 */
	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}

	/**
	 * 根据id往数据库删除一条记录
	 * @param id 删除对象的id
	 */
	@Override
	public void delete(RoleRights o) {
		dao.delete(o);
	}

	/**
	 * 往数据库删除列表中的所有记录
	 * @param o
	 */
	@Override
	public void delete(List<RoleRights> o) {
		dao.deleteAll(o);
	}

	/**
	 * 根据id查找
	 * @param id 查对对象的id
	 * @return 返回得到的结果
	 */
	@Override
	public RoleRights find(Long id) {
		Optional<RoleRights> op = dao.findById(id);
		if(op != null && op.isPresent()){
			return op.get();
		}
		return null;
	}
	
	/**
	 * 根据id列表查找所有对象
	 * @param ids 查对对象的id列表
	 * @return 返回得到的结果
	 */
	@Override
	public List<RoleRights> findAll(List<Long> ids) {
		Iterable<RoleRights> iterable = dao.findAllById(ids);
		if(iterable != null){
			List<RoleRights> list = new ArrayList<RoleRights>();
			iterable.forEach(single -> {list.add(single);});
			if(list.size() > 0)
				return list;
		}
		return null;
	}

	/**
	 * 获取表中记录条数
	 */
	@Override
	public Long count() {
		return dao.count();
	}

	/**
	 * 查找表中所有对象
	 * @param entityClass 表对应的实体类
	 * @return 返回得到的结果
	 */
	@Override
	public List<RoleRights> findAll() {
		Iterable<RoleRights> iterable = dao.findAll();
		if(iterable != null){
			List<RoleRights> list = new ArrayList<RoleRights>();
			iterable.forEach(single -> {list.add(single);});
			if(list.size() > 0)
				return list;
		}
		return null;
	}

	/**
	 * 根据条件分页查询
	 * @param entityClass 表对应的实体类
	 * @param pageIndex 初始页-从1开始
	 * @param pageSize 每页数量
	 * @return 返回得到的结果集
	 */
	@Override
	public List<RoleRights> findAll(Integer pageIndex, Integer pageSize) {
		if(pageIndex <= 0) pageIndex = 1;
		Pageable page = PageRequest.of(pageIndex, pageSize);
		
		Iterable<RoleRights> iterable = dao.findAll(page);
		if(iterable != null){
			List<RoleRights> list = new ArrayList<RoleRights>();
			iterable.forEach(single -> {list.add(single);});
			if(list.size() > 0)
				return list;
		}
		return null;
	}

	/**
	 * 根据条件分页查询
	 * @param entityClass 表对应的实体类
	 * @param pageIndex 初始页-从1开始
	 * @param pageSize 每页数量
	 * @param sortColum 排序的列
	 * @param asc 是否升序排列
	 * @return 返回得到的结果集
	 */
	@Override
	public List<RoleRights> findAll(Integer pageIndex, Integer pageSize, String sortColum, boolean asc) {
		if(pageIndex <= 0) pageIndex = 1;
		// 分页
		Pageable page = null;
		if(sortColum != null && !sortColum.isEmpty()){
			// 排序
			Sort sort = null;
			if(asc){
				sort = Sort.by(Sort.Direction.ASC, sortColum);
			}else{
				sort = Sort.by(Sort.Direction.DESC, sortColum);
			}
			page = PageRequest.of(pageIndex, pageSize, sort);
		}else{
			page = PageRequest.of(pageIndex, pageSize);
		}
		
		// 检索转化
		Iterable<RoleRights> iterable = dao.findAll(page);
		if(iterable != null){
			List<RoleRights> list = new ArrayList<RoleRights>();
			iterable.forEach(single -> {list.add(single);});
			if(list.size() > 0)
				return list;
		}
		return null;
	}

	/**
	 * 通过角色id查询角色所有权限
	 * @param username
	 * @return
	 */
	@Override
	public List<RoleRights> findByRoleId(Long roleId) {
		return dao.findByRoleId(roleId);
	}

	/**
	 * 通过用户id清空角色权限
	 * @param userId
	 * @return
	 */
	@Override
	public void deleteByRoleId(Long roleId) {
		dao.deleteByRoleId(roleId);
	}

	/**
	 * 通过角色id和权限id查询某角色权限信息
	 * @param username
	 * @return
	 */
	@Override
	public RoleRights findByRoleIdAndRightId(Long roleId, Long rightId) {
		return dao.findByRoleIdAndRightId(roleId, rightId);
	}

}