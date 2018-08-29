package cn.itsoruce.springboot.mapper;

import java.util.List;

import cn.itsoruce.springboot.domian.User;

public interface UserMapper {

	//保存一个对象
	void save(User user);

	//移除一个对象
	void remove(Long id);

	//更新对象
	void update(User user);

	//通过Id加载一个对象
	User loadById(Long id);

	//加载所有的对象
	List<User> loadAll();
}
