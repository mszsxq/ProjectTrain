package com.carepet.utils.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carepet.entity.User;

@Repository
public class ChangePhotoDao {
	@Resource
	private SessionFactory sessionFactory;
	public void updateUser(String head, int id) {
		Session session=this.sessionFactory.getCurrentSession();
		User user=session.get(User.class, id);
		user.setTouxiang(head);
		session.save(user);
	}
}
