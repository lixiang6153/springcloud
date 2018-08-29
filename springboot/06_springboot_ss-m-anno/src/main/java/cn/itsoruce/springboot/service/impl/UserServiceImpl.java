package cn.itsoruce.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.itsoruce.springboot.domian.User;
import cn.itsoruce.springboot.mapper.UserMapper;
import cn.itsoruce.springboot.service.IUserService;

@Service
//在一个类中读的远远多于写的方法，所有要在类上面配置读事务，在具体的写方法上面配置写事务。
//SUPPORTS 有就用，没有就算了 REQUIRED有就用，没有就自己开启事务
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void add(User user) {
		userMapper.save(user);
	}

	@Transactional
	@Override
	public void delete(Long id) {
	}

	@Transactional
	@Override
	public void update(User user) {

	}

	@Override
	public User getById(Long id) {
		return null;
	}

	@Override
	public List<User> getAll() {
		PageHelper.startPage(5, 5);
		return userMapper.loadAll();
	}

}
