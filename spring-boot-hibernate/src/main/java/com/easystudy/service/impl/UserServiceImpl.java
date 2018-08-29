package com.easystudy.service.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easystudy.model.User;
import com.easystudy.service.UserService;
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	// 使用value具体指定使用哪个事务管理器
    @Transactional(value="txManager1")
	@Override
	public List<User> findByPersionIdType_PersonId(Integer personIdType, String personId) {
		Criterion c1 = Restrictions.eq("personIdType", personIdType);
		Criterion c2= Restrictions.eq("personId", personId);;
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(c1);
		if(personId!=null){
			criteria.add(c2);
		}
		return findAll(criteria);
	}

    // 在存在多个事务管理器的情况下，如果使用value具体指定
    // 则默认使用方法 annotationDrivenTransactionManager() 返回的事务管理器
    @Transactional
	@Override
	public List<User> findByPersionName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
