package com.carepet.utils.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carepet.ai.animal.AnimalClassify;
import com.carepet.entity.FindTable;
import com.carepet.entity.User;
import com.carepet.utils.dao.FindTableDao;
import com.carepet.utils.dao.UserDao;

@Service
public class FindTableService {
	@Resource
	private FindTableDao findTableDao;
	@Resource
	private UserDao userDao;
	@Transactional(readOnly = false)
	public void saveFindTable(FindTable findTable) {
		String imgjson=findTable.getImgjson();
//		System.out.println(findTable);
		String urlpath="";
		if (imgjson==null) {
			imgjson="https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg";
		}
		if (imgjson.contains("--")) {
			urlpath=imgjson.split("--")[0];
		}else {
			urlpath=imgjson;
		}
		User user=userDao.findUser(findTable.getUserid());
		imgjson=user.getUsername()+"--"+user.getTouxiang()+"--"+imgjson;
		findTable.setImgjson(imgjson);
		findTable.setPettype(new AnimalClassify().animal(urlpath));
		this.findTableDao.saveFindTable(findTable);
	}
	
	@Transactional(readOnly = false)
	public void updateFindTable(FindTable findTable) {
		this.findTableDao.uploadFindTable(findTable);
	}
	
	@Transactional(readOnly = false)
	public void insertFindTable(FindTable findTable) {
		this.findTableDao.saveFindTable(findTable);
	}
	
	@Transactional(readOnly = false)
	public void deleteFindTable(FindTable findTable) {
		this.findTableDao.deleteFindTable(findTable.getId());
	}
	
	@Transactional(readOnly = false)
	public void deleteFindTable(int id) {
		this.findTableDao.deleteFindTable(id);
	}
	
	@Transactional(readOnly = false)
	public List<FindTable> list() {
		return this.findTableDao.findAllFindTable();
	}
	
	@Transactional(readOnly = false)
	public List<FindTable> listSameCity(String city) {
		return this.findTableDao.findSameCity(city);
	}
//搜索
	@Transactional(readOnly = false)
	public List<FindTable> liststrf(String str) {
		return this.findTableDao.liststrf(str);
	}
	@Transactional(readOnly = false)
	public List<FindTable> listrencent() {
		return this.findTableDao.listrecent();
	}
	@Transactional(readOnly = false)
	public List<FindTable> listRandom(int num) {
		return this.findTableDao.ListRandom(num);
	}
}
