package com.donwait.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

public class HibernateUtil {
	protected static SessionFactory sessionFactory = null;
	
	static {
		getSessionFactory();
	}
	
	public static SessionFactory getSessionFactory(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory;
	}
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	public static Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 根据条件生成DetacheCriteria
	 * @param c
	 * @param criterions
	 * @return
	 */
	public static DetachedCriteria createDetacheCriteriaByCriterions(Class<?> c,Criterion...criterions){
		DetachedCriteria criteria = DetachedCriteria.forClass(c);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria;
	}
}
