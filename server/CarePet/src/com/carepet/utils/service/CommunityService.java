package com.carepet.utils.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carepet.ai.animal.AnimalClassify;
import com.carepet.entity.Community;
import com.carepet.entity.FindTable;
import com.carepet.utils.dao.CommunityDao;

@Service
public class CommunityService {

	@Resource
	private CommunityDao communityDao;
	@Transactional(readOnly = false)
	public void saveCommunity(Community community) {
		String imgjson=community.getImgjson();
		String urlpath="";
		if (imgjson.contains("++")) {
			urlpath=imgjson.split("++")[0];
		}else {
			urlpath=imgjson;
		}
		String type=new AnimalClassify().animal(urlpath);
		community.setContent("#"+type+community.getContent());
		this.communityDao.saveCommunity(community);
	}
	
	@Transactional(readOnly = false)
	public void updateCommunity(Community community) {
		this.communityDao.uploadCommunity(community);
	}
	
	@Transactional(readOnly = false)
	public void insertCommunity(Community community) {
		this.communityDao.saveCommunity(community);
	}
	
	@Transactional(readOnly = false)
	public void deleteCommunity(Community community) {
		this.communityDao.deleteCommunity(community.getId());
	}
	
	@Transactional(readOnly = false)
	public void deleteCommunity(int id) {
		this.communityDao.deleteCommunity(id);
	}
	
	@Transactional(readOnly = false)
	public List<Community> list() {
		return this.communityDao.findAllCommunity();
	}
	
	@Transactional(readOnly = false)
	public List<Community> listSameCity(String city) {
		return this.communityDao.findSameCity(city);
	}
}