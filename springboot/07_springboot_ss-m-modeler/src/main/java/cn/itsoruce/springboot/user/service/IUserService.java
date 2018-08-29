package cn.itsoruce.springboot.user.service;

import java.util.List;

import cn.itsoruce.springboot.user.domian.User;

public interface IUserService {

	//添加一个对象
	void add(User user);

	//删除一个对象
	void delete(Long id);

	//更新对象
	void update(User user);

	//通过Id获取一个对象
	User getById(Long id);

	//加获取所有的对象
	List<User> getAll();
}
