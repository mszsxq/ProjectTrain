package com.carepet.utils.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carepet.entity.Community;
@Repository
public class ExperienceDao {
	@Resource
	private SessionFactory sessionFactory;
	public void saveCommunity(Community community) {
		Session session=this.sessionFactory.getCurrentSession();
		session.save(community);
	}
	
	public void uploadCommunity(Community community) {
		Session session=this.sessionFactory.getCurrentSession();
		session.update(community);
	}
	
	public void deleteCommunity(int id) {
		Session session=this.sessionFactory.getCurrentSession();
		Community community=new Community();
		community.setId(id);
		session.delete(community);
	}
	
	public List<Community> findAllExperience(){
		Session session=this.sessionFactory.getCurrentSession();
		System.out.println("flag=====2");
		Query query=session.createQuery("from Community where flag = 2");
		return query.list();
	}
	public List<Community> findSameCity(String city){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Community");
		List<Community> communitys=query.list();
		List<Community> communitys2=new ArrayList<Community>();
		for (Community Community : communitys) {
			if (Community.getCity().contains(city)) {
				communitys2.add(Community);
			}
		}
		return communitys2;
	}
}
