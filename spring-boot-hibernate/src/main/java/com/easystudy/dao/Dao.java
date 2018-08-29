package com.easystudy.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
/**
 * 通用dao
 * @author Administrator
 *
 */
public interface Dao<T> {
	/**
	 * 往数据库插入一条记录
	 * @param entityClass 表对应的实体类
	 * @param o 表对应的实体类实例对象
	 */
	void add(Class<T> entityClass, T o);
	/**
	 * 往数据库中插入一条记录，如果已存在则抛异常
	 * @param o
	 */
	void add(Object o);
	/**
	 * 往数据库中插入一条记录，如果已存在则抛异常
	 * @param o
	 */
	void add(Object... o);
	/**
	 * 往数据库插入一条记录，如果已存在则更新
	 * @param o
	 */
	void saveOrUpdate(T o);
	/**
	 * 根据id往数据库删除一条记录
	 * @param entityClass 表对应的实体类
	 * @param id 删除对象的id
	 */
	void delete(Class<T> entityClass, Serializable... ids);
	/**
	 * 往数据库删除一条记录
	 */
	void delete(T o);
	/**
	 * 往数据库更新一条记录
	 * @param entityClass 表对应的实体类
	 * @param o 表对应的实体类实例对象
	 */
	void update(Class<T> entityClass, T o);
	/**
	 * 根据id查找
	 * @param entityClass 表对应的实体类
	 * @param id 查对对象的id
	 * @return 返回得到的结果
	 */
	T find(Class<T> entityClass, Serializable id);
	/**
	 * 根据条件查找
	 * @param entityClass  实体体
	 * @param criterions 条件,通过Restrictions的静态方法添加
	 * @return 返回得到的结果集
	 */
	List<T> findAll(Class<T> entityClass, Criterion... criterions);
	/**
	 * 根据条件分页查询
	 * @param entityClass 表对应的实体类
	 * @param pageIndex 初始页
	 * @param pageSize 每页数量
	 * @param criterions 条件,通过Restrictions的静态方法添加
	 * @return 返回得到的结果集
	 */
	List<T> findAll(Class<T> entityClass, Integer pageIndex, Integer pageSize, Criterion... criterions);
	
	/**
	 * 根据某个集体作分页时,得到最大页码
	 * @param list 要分页的集合
	 * @param pageSize 每页数量
	 * @return 返回最大页码
	 */
	int getMaxPage(List<T> list, int pageSize);
	/**
	 * 根据条件查询符合条件的数据条数
	 * @param entityClass 表对应的实体类
	 * @param criterions 条件
	 * @return
	 */
	Long getRowCount(Class<T> entityClass, Criterion... criterions);
	
	
	/**
	 * 根据hql查询
	 * @param hql hql语句
	 * @param objects hql参数
	 * @return
	 */
	List<T> findByHql(String hql,Class<T> entityClass,Object...objects);
	/**
	 * 根据条件查询
	 * @param criteria 条件
	 * @return
	 */
	List<T> findAll(DetachedCriteria criteria);
	/**
	 * 根据条件分页查询
	 * @param pageIndex 开始页
	 * @param pageSize 每页数量 
	 * @param criteria 条件
	 * @return
	 */
	List<T> findAll(Integer pageIndex,Integer pageSize,DetachedCriteria criteria);
	/**
	 * 查找最大值
	 * @param sql
	 * @param objects
	 * @return
	 */
	BigInteger  findMaxBySql(String sql);
	/**
	 * 根据sql语句查询对象
	 * @param sql sql语句必须是全字段
	 * @param entityClass
	 * @param objects
	 * @return
	 */
	List<T> findBySql(String sql, Class<T> entityClass, Object[] objects);
	/**
	 * 根据sql语句查询对象
	 * @param sql
	 * @param objects
	 * @return
	 */
	List<Object[]> findBySqlObjects(String sql,Object...objects);
	/**
	 * 分页按序查询
	 * @param pageIndex
	 * @param pageSize
	 * @param order
	 * @param criteria
	 * @return
	 */
	List<T> findAll(int pageIndex, int pageSize, Order order, DetachedCriteria criteria);
	/**
	 * 分页按序查询
	 * @param pageIndex
	 * @param pageSize
	 * @param criterions
	 * @return
	 */
	List<T> findAll(int pageIndex, int pageSize, Class<T> entityClass, Order order, Criterion[] criterions);
	/**
	 * 刷新数据库
	 */
	void flush();
	/**
	 * 根据hql查询
	 * @param hql
	 * @param objects
	 * @return
	 */
	List<Object[]> findByHql(String hql, Object... objects);
	/**
	 * sql添加或更新
	 * @param sql
	 * @param objects
	 */
	int addOrUpdateOrDeleteBySql(String sql,Object...objects);
	/**
	 * 根据条件查询
	 * @param o
	 * @return
	 */
	List<T> findByExample(int page,int size,Class<T> entityClass,T o);
	/**
	 * 全字段模糊匹配查询
	 * @param page
	 * @param size
	 * @param entityClass
	 * @param o
	 * @return
	 */
	List<T> findByAllProperties(int page,int size,Class<T> entityClass,Object value);
	/**
	 * 根据条件取得数量
	 * @param criteria
	 * @return
	 */
	Long getRowCount(DetachedCriteria criteria);
	
}