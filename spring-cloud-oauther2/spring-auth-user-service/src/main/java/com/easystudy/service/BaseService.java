package com.easystudy.service;

import java.util.List;

/**
 * 自定义查询nativeQuery=true说明为原生sql语句
 * 1代表的是方法参数里面的顺序：此处为用户名
 * 例如：
 * nativeQuery表示原生的sql语句（根据数据库的不同，在sql的语法或结构方面可能有所区别）进行查询数据库的操作
 * @Query(value="select * from user u where u.username=:username",nativeQuery=true)
 * @Query("update Task t set t.taskName = ?1 where t.id = ?2")
 * @Query(value = "select name,author,price from Book b where b.name like %:name%")
 * SPEL表达式
 * @Query(value = "select * from #{#entityName} b where b.name=?1", nativeQuery = true)
 * @param <T>
 */
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
	public void add(List<T> o);
	/**
	 * 根据id往数据库删除一条记录
	 * @param id 删除对象的id
	 */
	public void delete(Long id);
	/**
	 * 往数据库删除一条记录
	 * @param o
	 */
	public void delete(T o);
	/**
	 * 往数据库删除列表中的所有记录
	 * @param o
	 */
	public void delete(List<T> o);
	/**
	 * 根据id查找
	 * @param id 查对对象的id
	 * @return 返回得到的结果
	 */
	T find(Long id);
	/**
	 * 根据id列表查找所有对象
	 * @param ids 查对对象的id列表
	 * @return 返回得到的结果
	 */
	List<T> findAll(List<Long> ids);
	/**
	 * 获取表中记录条数
	 */
	Long count();
	/**
	 * 查找表中所有对象
	 * @param entityClass 表对应的实体类
	 * @return 返回得到的结果
	 */
	List<T> findAll();
	
	/**
	 * 根据条件分页查询
	 * @param entityClass 表对应的实体类
	 * @param pageIndex 初始页-从1开始
	 * @param pageSize 每页数量
	 * @return 返回得到的结果集
	 */
	public List<T> findAll(Integer pageIndex, Integer pageSize);
	
	/**
	 * 根据条件分页查询
	 * @param entityClass 表对应的实体类
	 * @param pageIndex 初始页-从1开始
	 * @param pageSize 每页数量
	 * @param sortColum 排序的列
	 * @param asc 是否升序排列
	 * @return 返回得到的结果集
	 */
	public List<T> findAll(Integer pageIndex, Integer pageSize, String sortColum, boolean asc);
}
