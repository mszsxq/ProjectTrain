package com.carepet.utils.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carepet.entity.User;
import com.carepet.entity.User;
import com.carepet.entity.User;

@Repository
public class UserDao {

	@Resource
	private SessionFactory sessionFactory;
	public void saveUser(User user) {
		Session session=this.sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	public void updateUserTouxiang(String headName,int id) {
		Session session=this.sessionFactory.getCurrentSession();
		User user=session.get(User.class, id);
		user.setTouxiang(headName);
		session.save(user);
	}
	public void updateUser(User user) {
		Session session=this.sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	
	public void deleteUser(int id) {
		Session session=this.sessionFactory.getCurrentSession();
		User user=new User();
		user.setId(id);
		session.delete(user);
	}
	public User findUser(int id) {
		Session session=this.sessionFactory.getCurrentSession();
		User user=new User();
		user=session.get(User.class, id);
		return user;
	}
	public List<User> findAllUser(){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User");
		return query.list();
	}

}
