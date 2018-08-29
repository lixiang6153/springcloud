package com.easystudy.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.easystudy.dao.Dao;
import com.easystudy.util.JsonUtil;

@Repository
public class DaoSupport<T> implements Dao<T>{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public void add(Class<T> entityClass, T o) {
		hibernateTemplate.save(entityClass.getName(), o);
	}
	@Override
	public void add(Object o) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(o);
	}
	@Override
	public void add(Object... objects) {
		int i = 0;
		for (Object object : objects) {
			hibernateTemplate.save(object);
			i++;
			if(i % 20 == 0){
				hibernateTemplate.flush();
			}
		}
		
	}
	@Override
	public void saveOrUpdate(T o) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(o);
	}
	@Override
	public void delete(Class<T> entityClass, Serializable... ids) {
		// TODO Auto-generated method stub
		int i=0;
		for (Serializable id : ids) {
			Object entity=find(entityClass, id);
			hibernateTemplate.delete(entity);
			i++;
			if(i%10==0){
				hibernateTemplate.flush();
			}
		}

	}
	@Override
	public void update(Class<T> entityClass, T o) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(entityClass.getName(), o);
	}
	@SuppressWarnings("unchecked")
	@Override
	public T find(Class<T> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return (T) hibernateTemplate.get(entityClass.getName(), id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> entityClass, Criterion... criterions) {
		// TODO Auto-generated method stub	
		DetachedCriteria criteria=DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		List<T> list = (List<T>) hibernateTemplate.findByCriteria(criteria);
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> entityClass, Integer pageIndex,
			Integer pageSize, Criterion... criterions) {
		DetachedCriteria criteria=DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		Integer firstResult=(pageIndex-1)*pageSize;
		return (List<T>) hibernateTemplate.findByCriteria(criteria, firstResult, pageSize);
	}
	@Override
	public int getMaxPage(List<T> list,int pageSize) {
		if(list.size()%pageSize==0){
			return list.size()/pageSize;
		}
		return list.size()/pageSize+1;
	}
	@Override
	public Long getRowCount(Class<T> entityClass,Criterion... criterions) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		criteria.setProjection(Projections.rowCount());

		List<?> list = hibernateTemplate.findByCriteria(criteria);
		return (list != null && list.size() > 0) ? (Long) list.get(0) : 0;
	}
	@Override
	public void delete(T o) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(o);
	}

	public List<T> findByHql(String hql,Class<T> c, Object... objects) {
		// TODO Auto-generated method stubhibernateTemplate.find(hql, objects);	
		Query<T> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql,c);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(DetachedCriteria criteria) {
		return (List<T>) hibernateTemplate.findByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Integer pageIndex, Integer pageSize, DetachedCriteria criteria) {
		Integer firstResult=(pageIndex-1)*pageSize;
		return (List<T>) hibernateTemplate.findByCriteria(criteria, firstResult, pageSize);
	}
	@SuppressWarnings("deprecation")
	@Override
	public BigInteger  findMaxBySql(String sql) {
		BigInteger  i = (BigInteger ) hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql).list().get(0);	
		return i;
	}
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	@Override
	public List<T> findBySql(String sql,Class<T> entityClass ,Object... objects) {
		// TODO Auto-generated method stub
		NativeQuery nq = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(entityClass);
		for (int i = 0; i < objects.length; i++) {
			nq.setParameter(i, objects[i]);
		}
		return nq.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(int pageIndex, int pageSize, Order order, DetachedCriteria criteria) {
		criteria.addOrder(order);
		Integer firstResult=(pageIndex-1)*pageSize;
		List<?> list = hibernateTemplate.findByCriteria(criteria, firstResult, pageSize);
		return (List<T>) list;
	}
	@Override
	public List<T> findAll(int pageIndex, int pageSize,Class<T> entityClass,Order order ,Criterion[] criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return findAll(pageIndex, pageSize, order, criteria);
	}
	@Override
	public void flush() {
		hibernateTemplate.flush();
	}
	@Override
	public List<Object[]> findByHql(String hql, Object[] objects) {
		Query<Object[]> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql,Object[].class);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.getResultList();
	}
	@Override
	public List<Object[]> findBySqlObjects(String sql, Object... objects) {
		NativeQuery<Object[]> nq = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			nq.setParameter(i, objects[i]);
		}
		return nq.getResultList();
	}
	@Override
	public int addOrUpdateOrDeleteBySql(String sql, Object... objects) {
		Query<Object[]> nq = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			nq.setParameter(i, objects[i]);
		}
		return  nq.executeUpdate();
	}
	@Override
	public List<T> findByExample(int page,int size,Class<T> entityClass,T o) {
		Integer firstResult=(page-1)*size;
		List<T> list = hibernateTemplate.findByExample(entityClass.getName(), o, firstResult, size);
		return list;
	}
	@Override
	public List<T> findByAllProperties(int page, int size, Class<T> entityClass, Object value) {
		Integer firstResult=(page-1)*size;
		List<Field> fields = JsonUtil.getAllFields(entityClass);
		if(fields != null && fields.size() > 0){
			StringBuilder sb  = new StringBuilder("select ");
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				field.setAccessible(true);
				if(!field.getName().equals("serialVersionUID")){
					sb.append(field.getName());
					if(i != fields.size()-1){
						sb.append(",");
					}else{
						sb.append(" from ").append(entityClass.getSimpleName()).append(" where '1' = '1'");
					}
				}
			}
			int position = 0;
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				if(!field.getName().equals("serialVersionUID")){
					if(position == 0){
						sb.append(" and ").append(field.getName()).append(" like '%").append(value).append("%'");
					}else{
						sb.append(" or ").append(field.getName()).append(" like '%").append(value).append("%'");
					}
					position++;
				}
			}
			sb.append(" limit ").append(firstResult).append(",").append(size);
			List<T> list = findBySql(sb.toString(), entityClass);
			return list;
		}
		
		return null;
	}

	@Override
	public Long getRowCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		List<?> list = hibernateTemplate.findByCriteria(criteria);
		System.out.println(list);
		return (list != null && list.size() > 0) ? (Long) list.get(0) : 0;
	}
	
	
}