package com.easystudy.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easystudy.dao.Dao;
import com.easystudy.service.BaseService;
import com.easystudy.util.GenericsUtils;
import com.easystudy.util.JsonUtil;

@Service
public class BaseServiceImpl<T> implements BaseService<T>{
	public static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private Dao<T> dao;
	private Class<T> entityClass;
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		/**
		 * 取得泛型的类型
		 */
		entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	}
	public void setDao(Dao<T>dao) {
		this.dao = dao;
	}
	public Dao<T> getDao() {
		return dao;
	}
	@Override
	public void add(T o) {
		try {
			dao.add(entityClass, o);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加失败:" + e.getMessage() +"\n 操作元素:" + JsonUtil.getJsonObject(o));
		}
	}
	@Override
	public void add(Object... o) {
		// TODO Auto-generated method stub
		dao.add(o);
	}
	@Override
	public void saveOrUpdate(T o) {
		dao.saveOrUpdate(o);
		
	}
	@Override
	public void delete(Serializable... ids) {
		// TODO Auto-generated method stub
		dao.delete(entityClass, ids);
	}
	@Override
	public void delete(T o) {
		// TODO Auto-generated method stub
		dao.delete(o);
	}
	@Override
	public void update(T o) {
		// TODO Auto-generated method stub
		dao.update(entityClass, o);
	}
	
	@Override
	public T find(Serializable id) {
		// TODO Auto-generated method stub
		return dao.find(entityClass, id);
	}
	@Override
	public List<T> findAll(Criterion... criterions) {
		// TODO Auto-generated method stub
		return dao.findAll(entityClass, criterions);
	}
	@Override
	public List<T> findAll(Integer pageIndex, Integer pageSize, Criterion... criterions) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return dao.findAll(pageIndex, pageSize, criteria);
	}
	@Override
	public int getMaxPage(List<T> list, int pageSize) {
		// TODO Auto-generated method stub
		return dao.getMaxPage(list, pageSize);
	}
	@Override
	public Long getRowCount(Criterion... criterions) {
		// TODO Auto-generated method stub
		return dao.getRowCount(entityClass, criterions);
	}
	@Override
	public Long getRowCount(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		return dao.getRowCount(criteria);
	}
	@Override
	public List<T> findAll(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		List<T> list=null;
		try{
			list = dao.findAll(criteria);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<T> findAll(Integer pageIndex, Integer pageSize, DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		List<T> list= dao.findAll(pageIndex, pageSize, criteria);
		return list;
	}
	@Override
	public List<T> findByHql(String hql,Object... objects) {
		// TODO Auto-generated method stub
		return dao.findByHql(hql,entityClass,objects);
	}
	@Override
	public BigInteger  findMaxBySql(String sql) {
		return dao.findMaxBySql(sql);
	}
	@Override
	public List<T> findBysql(String sql,Object...objects) {
		// TODO Auto-generated method stub
		return dao.findBySql(sql, entityClass, objects);
	}

	@Override
	public List<T> findAll(Order order, Criterion... criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		criteria.addOrder(order);	
		return findAll(criteria);
	}
	@Override 
	public List<T> findAll(Order order, DetachedCriteria criteria) {
		criteria.addOrder(order);
		return findAll(criteria);
	}
	@Override
	public List<T> findAll(int pageIndex, int pageSize, Order order, Criterion... criterions) {
		// TODO Auto-generated method stub
		return dao.findAll(pageIndex, pageSize, entityClass, order, criterions);
	}
	@Override
	public List<T> findAll(int pageIndex, int pageSize, Order order, DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		return dao.findAll(pageIndex, pageSize, order, criteria);
	}
	public Class<T> getEntityClass() {
		return entityClass;
	}
	@Override
	public void flush() {
		dao.flush();
		
	}
	@Override
	public List<Object[]> findByHqlObjects(String hql, Object... objects) {
		List<Object[]> list=dao.findByHql(hql,objects);
		return list;
	}
	@Override
	public List<Object[]> findBySqlObjects(String sql, Object... objects) {
		return dao.findBySqlObjects(sql,objects);
	}
	@Override
	public int addOrUpdateOrDeleteBySql(String sql, Object... objects) {
		return dao.addOrUpdateOrDeleteBySql(sql, objects);
	}
	@Override
	public List<T> findByExample(int page,int size,T o) {
		return dao.findByExample(page, size, entityClass, o);
	}
	@Override
	public List<T> findByAllProperties(int page, int size,Object value) {
		// TODO Auto-generated method stub
		return dao.findByAllProperties(page, size, entityClass, value);
	}
	
}
