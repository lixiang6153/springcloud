package com.donwait.serviceImpl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import com.donwait.dao.Dao;
import com.donwait.service.BaseService;
import com.donwait.util.GenericsUtils;

public class BaseServiceImpl<T> implements BaseService<T>{
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
		dao.add(entityClass, o);
	}
	@Override
	public void delete(Serializable... ids) {
		dao.delete(entityClass, ids);
	}
	@Override
	public void delete(T o) {
		dao.delete(o);
	}
	@Override
	public void update(T o) {
		dao.update(entityClass, o);
	}
	
	@Override
	public T find(Serializable id) {
		return dao.find(entityClass, id);
	}
	@Override
	public List<T> findAll(Criterion... criterions) {
		return dao.findAll(entityClass, criterions);
	}
	@Override
	public List<T> findAll(Integer pageIndex, Integer pageSize, Criterion... criterions) {
		DetachedCriteria criteria=DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		List<T> list=dao.findAll(pageIndex, pageSize, criteria);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
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
	public List<T> findAll(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		List<T> list=dao.findAll(criteria);
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
	public Integer findMaxBySql(String sql) {
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
	
	
}
