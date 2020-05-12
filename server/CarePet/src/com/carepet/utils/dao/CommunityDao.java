package com.carepet.utils.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carepet.entity.Community;
import com.carepet.entity.Community;

@Repository
public class CommunityDao {

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
	
	public List<Community> findAllCommunity(){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Community");
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
	//搜索分享帖
	public List<Community> findCommunityWithStr(String str){
		Session session2=this.sessionFactory.getCurrentSession();
		Query query=session2.createQuery("from Community where tag= ? and title like ?");
		query.setParameter(0, "puppy");
		query.setParameter(1, "%"+str+"%");
		List<Community> communitys=query.list();
		return communitys;
	}
	
	//搜索经验贴
	public List<Community> findCommunityWithStrE(String str){
		Session session3=this.sessionFactory.getCurrentSession();
		Query query=session3.createQuery("from Community where tag= ? and title like ?");
		query.setParameter(0, "experience");
		query.setParameter(1, "%"+str+"%");
		List<Community> communitys=query.list();
		return communitys;
		
	}

}
