package com.carepet.utils.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carepet.entity.FindTable;

import javassist.tools.framedump;

@Repository
public class FindTableDao {
	
	@Resource
	private SessionFactory sessionFactory;
	
	public void saveFindTable(FindTable findTable) {
		Session session=this.sessionFactory.getCurrentSession();
		session.save(findTable);
	}
	public void updateModel(String head, int id) {
		ArrayList<FindTable> findTables=(ArrayList<FindTable>) findAllFindTable();
		for (FindTable findTable : findTables) {
			if (findTable.getUserid()==id) {
				String[] strings=findTable.getImgjson().split("--");
				strings[1]=head;
				String img=new String();
				for (int i = 0; i < strings.length; i++) {
					img+=strings[i]+"--";
				}
				findTable.setImgjson(img);
			}
			
		}
	}
	public void uploadFindTable(FindTable findTable) {
		Session session=this.sessionFactory.getCurrentSession();
		session.update(findTable);
	}
	
	public void deleteFindTable(int id) {
		Session session=this.sessionFactory.getCurrentSession();
		FindTable findTable=new FindTable();
		findTable.setId(id);
		session.delete(findTable);
	}
	
	public List<FindTable> findAllFindTable(){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from FindTable");
		return query.list();
	}
	public List<FindTable> findSameCity(String city){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from FindTable");
		List<FindTable> findTables=query.list();
		List<FindTable> findTables2=new ArrayList<FindTable>();
		for (FindTable findTable : findTables) {
			if (findTable.getCity().contains(city)) {
				findTables2.add(findTable);
			}
		}
		return findTables2;
	}
//搜索
	public List<FindTable> liststrf(String str){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from FindTable where concat(city,pettype) like ?");
		query.setParameter(0, "%"+str+"%");
		List<FindTable> findtables=query.list();
		return findtables;
		
	}
	
	public List<FindTable> listrecent(){
		Session session=this.sessionFactory.getCurrentSession();
	
		while(true) {
			Query query=session.createQuery("from FindTable order by rand()  ");
		    query.setMaxResults(5);
			List<FindTable> findtables=query.list();
			return findtables;
		}
		 
		
	}
	public List<FindTable> ListRandom(int num) {
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from FindTable");
		query.setFirstResult(num*8);
		query.setMaxResults(8);
		return query.list();
	}
}
