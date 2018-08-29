package com.easystudy.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public interface BaseService<T> {
	/**
	 * 往数据库插入一条记录，如果已存在则抛异常
	 * @param o
	 */
	public void add(T o);
	/**
	 * 往数据库插入n条记录，如果已存在则抛异常
	 * @param o
	 */
	public void add(Object... o);
	/**
	 * 往数据库插入一条记录，如果已存在则更新
	 * @param o
	 */
	void saveOrUpdate(T o);
/**
 * 根据id往数据库删除一条记录,支持批量删除
 * 
 * @param id 删除对象的id
 */
	public void delete(Serializable... ids);
	/**
	 * 往数据库删除一条记录
	 * @param o
	 */
	public void delete(T o);
	/**
	 * 往数据库更新一条记录
	 * @param o
	 */
	public void update(T o);
	/**
	 * 根据id查找
	 * 
	 * @param id 查对对象的id
	 * @return 返回得到的结果
	 */
	public T find(Serializable id);
	/**
	 * 根据条件查找
	 * 
	 * @param criterions 条件,通过Restrictions的静态方法添加
	 * @return 返回得到的结果集
	 */
	public List<T> findAll(Criterion... criterions);
	/**
	 * 根据条件查找
	 * 
	 * @param criterions 条件,通过Restrictions的静态方法添加
	 * @return 返回得到的结果集
	 */
	public List<T> findAll(Order order,Criterion...criterions);
	/**
	 * 根据条件查找
	 * 
	 * @param criterions 条件,通过Restrictions的静态方法添加
	 * @return 返回得到的结果集
	 */
	public List<T> findAll(int pageIndex,int pageSize,Order order,Criterion...criterions);
	/**
	 * 根据条件分页查询
	 * 
	 * @param pageIndex 初始页
	 * @param pageSize 每页数量
	 * @param criterions 条件,通过Restrictions的静态方法添加
	 * @return 返回得到的结果集
	 */
	public List<T> findAll(Integer pageIndex, Integer pageSize, Criterion... criterions);
	
	/**
	 * 根据某个集体作分页时,得到最大页码
	 * @param list 要分页的集合
	 * @param pageSize 每页数量
	 * @return 返回最大页码
	 */
	public int getMaxPage(List<T> list, int pageSize);
	/**
	 * 根据条件查询符合条件的数据条数
	 *
	 * @param criterions 条件
	 * @return
	 */
	public Long getRowCount(Criterion... criterions);
	/**
	 * 根据条件查询符合条件的数据条数
	 *
	 * @param criterions 条件
	 * @return
	 */
	public Long getRowCount(DetachedCriteria criteria);
	/**
	 * 根据条件查询
	 * @param criteria 条件
	 * @return
	 */
	public List<T> findAll(DetachedCriteria criteria);
	/**
	 * 根据条件查询
	 * @param criteria 条件
	 * @return
	 */
	public List<T> findAll(Order order,DetachedCriteria criteria);
	/**
	 * 根据条件查询
	 * @param criteria 条件
	 * @return
	 */
	public List<T> findAll(int page,int pageSize,Order order,DetachedCriteria criteria);
	/**
	 * 根据条件分页查询
	 * @param pageIndex 开始页
	 * @param pageSize 每页数量 
	 * @param criteria 条件
	 * @return
	 */
	public List<T> findAll(Integer pageIndex,Integer pageSize,DetachedCriteria criteria);

	/**
	 * 根据hql查询
	 * List<User> list = userService.findByHql("select new com.donwait.model.User(u.userName,u.password) from User u");
	 * 部分字段查询需要实体类构造方法的支持
	 * @param hql hql语句
	 * @param objects hql参数
	 * @return
	 */
	public List<T> findByHql(String hql,Object...objects);
	/**
	 * 查找最大值
	 * @param shobjects
	 * @return
	 */
	public BigInteger findMaxBySql(String sql);
	/**
	 * 根据sql语句查询
	 */
	public List<T> findBysql(String sql,Object...objects);
	/**
	 * 取得实体类的类型
	 * @return
	 */
	public Class<T> getEntityClass();
	/**
	 * 刷新数据库
	 */
	public void flush();
	/**
	 * 根据hsql查询
	 * @param hql select u.userName,u.password from User u
	 * @param objects
	 * @return 结果集
	 */
	public List<Object[]> findByHqlObjects(String hql,Object...objects);
	/**
	 * 根据hql查询
	 * @param hql
	 * @param objects
	 * @return
	 */
	List<Object[]> findBySqlObjects(String sql,Object... objects);
	/**
	 * sql添加更新删除
	 * @param sql
	 * @return 受影响的数据条数
	 */
	int addOrUpdateOrDeleteBySql(String sql,Object...objects);	
	
	/**
	 * 根据条件查询
	 * @param o
	 * @return
	 */
	List<T> findByExample(int page,int size,T o);
	/**
	 * 全字段模糊匹配搜索
	 * @param page
	 * @param size
	 * @param entityClass
	 * @param value 模糊值
	 * @return
	 */
	List<T> findByAllProperties(int page, int size, Object value);
	
}
