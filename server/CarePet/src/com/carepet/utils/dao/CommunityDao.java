package com.carepet.utils.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carepet.entity.Community;
import com.carepet.entity.Communitys;
import com.carepet.entity.User;
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
	
	public List<Communitys> findAllCommunity(){
		UserDao userDao = new UserDao();
		Session session=this.sessionFactory.getCurrentSession();
		System.out.println("flag=====1");
		Query query=session.createQuery("from Community order by id desc");
		query.setMaxResults(4);
		List<Community> list=query.list();
		List<Communitys> cs=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			String title=list.get(i).getTitle();
			String imgjson=list.get(i).getImgjson();
			String content=list.get(i).getContent();
			int userId=list.get(i).getUserId();
			String time=list.get(i).getTime();
			Query query1=session.createQuery("from User where id="+userId);
			User user=(User) query1.uniqueResult();
			String name=user.getUsername();
			String headName=user.getTouxiang();
			Communitys communitys=new Communitys(title,imgjson, content,userId,name,headName,time);
			System.out.println(communitys.toString());
			cs.add(communitys);
		}
		return cs;
	}
	public List<Communitys> findSomeCommunity(int flag){
		UserDao userDao = new UserDao();
		Session session=this.sessionFactory.getCurrentSession();
		System.out.println("flag=====2");
		Query query=session.createQuery("from Community order by id desc");
		query.setMaxResults(4);
		query.setFirstResult(flag-1);
		List<Community> list=query.list();
		List<Communitys> cs=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			String title=list.get(i).getTitle();
			String imgjson=list.get(i).getImgjson();
			String content=list.get(i).getContent();
			int userId=list.get(i).getUserId();
			String time=list.get(i).getTime();
			Query query1=session.createQuery("from User where id="+userId);
			User user=(User) query1.uniqueResult();
			String name=user.getUsername();
			String headName=user.getTouxiang();
			Communitys communitys=new Communitys(title,imgjson, content,userId,name,headName,time);
			System.out.println(communitys.toString());
			cs.add(communitys);
		}
		return cs;
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
	//鎼滅储鍒嗕韩甯�
	public List<Communitys> findCommunityWithStr(String str){
		Session session2=this.sessionFactory.getCurrentSession();
		Query query=session2.createQuery("from Community where tag= ? and title like ?");
		query.setParameter(0, "puppy");
		query.setParameter(1, "%"+str+"%");
		List<Community> list=query.list();
		List<Communitys> cs=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			String title=list.get(i).getTitle();
			String imgjson=list.get(i).getImgjson();
			String content=list.get(i).getContent();
			int userId=list.get(i).getUserId();
			String time=list.get(i).getTime();
			Query query1=session2.createQuery("from User where id="+userId);
			User user=(User) query1.uniqueResult();
			String name=user.getUsername();
			String headName=user.getTouxiang();
			Communitys communitys=new Communitys(title,imgjson, content,userId,name,headName,time);
			System.out.println(communitys.toString());
			cs.add(communitys);
		}
		return cs;
	}
	
	//鎼滅储缁忛獙璐�
	public List<Communitys> findCommunityWithStrE(String str){
		Session session3=this.sessionFactory.getCurrentSession();
		Query query=session3.createQuery("from Community where tag= ? and title like ?");
		query.setParameter(0, "experience");
		query.setParameter(1, "%"+str+"%");
		List<Community> list=query.list();
		List<Communitys> cs=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			String title=list.get(i).getTitle();
			String imgjson=list.get(i).getImgjson();
			String content=list.get(i).getContent();
			int userId=list.get(i).getUserId();
			String time=list.get(i).getTime();
			Query query1=session3.createQuery("from User where id="+userId);
			User user=(User) query1.uniqueResult();
			String name=user.getUsername();
			String headName=user.getTouxiang();
			Communitys communitys=new Communitys(title,imgjson, content,userId,name,headName,time);
			System.out.println(communitys.toString());
			cs.add(communitys);
		}
		return cs;
	}

}
