package cn.itsoruce.springboot.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/***
 * 持久层基类接口
 * 
 * @author zhaoyi
 *@NoRepositoryBean：让SpringDataJpa不要为它创建bean
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  //根据传过来的jpql拿到相应的数据  (jpal中?对应的参数)
  List findByJpql(String jpql, Object... values);
  //根据传过来的jpql拿到相应的数据 -支持缓存  (jpal中?对应的参数)
  List findCacheByJpql(String cacheJpql, Object... values);
}