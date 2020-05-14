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
	
	public List<Communitys> findAllExperience(){
		Session session=this.sessionFactory.getCurrentSession();
		System.out.println("flag=====2");
		Query query=session.createQuery("from Community where flag=2");
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
}
