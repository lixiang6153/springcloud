package cn.itsoruce.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.itsoruce.springboot.domian.User;

public interface UserMapper {

	@Insert("insert into t_user(name) values(#{name})")
	@Options(useGeneratedKeys=true,keyColumn="id",keyProperty="id")
	//保存一个对象
	void save(User user);

	@Delete("delete from t_user where id = #{id}")
	//移除一个对象
	void remove(Long id);

	//更新对象
	@Update("update t_user set name = #{name} where id = #{id}")
	void update(User user);

	@Select("select * from t_user where id = #{id}")
	//通过Id加载一个对象
	User loadById(Long id);

	@Select("select * from t_user")
	//加载所有的对象
	List<User> loadAll();
}
