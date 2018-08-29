package cn.itsoruce.springboot.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itsoruce.springboot.dao.IUserDao;
import cn.itsoruce.springboot.domian.User;

@Repository
public class UserDaoImpl implements IUserDao {

	@Override
	public void save(User user) {

		System.out.println("save user .....");
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User loadById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
