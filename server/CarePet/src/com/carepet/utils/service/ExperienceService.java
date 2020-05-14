package com.carepet.utils.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carepet.ai.animal.AnimalClassify;
import com.carepet.entity.Community;
import com.carepet.entity.Communitys;
import com.carepet.utils.dao.CommunityDao;
import com.carepet.utils.dao.ExperienceDao;
@Service
public class ExperienceService {
	@Resource
	private ExperienceDao experienceDao;
	@Transactional(readOnly = false)
	public void saveCommunity(Community community) {
		String imgjson=community.getImgjson();
		String urlpath="";
		if (imgjson.contains("--")) {
			urlpath=imgjson.split("--")[0];
		}else {
			urlpath=imgjson;
		}
		String type=new AnimalClassify().animal(urlpath);
		community.setContent("#"+type+community.getContent());
		this.experienceDao.saveCommunity(community);
	}
	
	@Transactional(readOnly = false)
	public void updateCommunity(Community community) {
		this.experienceDao.uploadCommunity(community);
	}
	
	@Transactional(readOnly = false)
	public void insertCommunity(Community community) {
		this.experienceDao.saveCommunity(community);
	}
	
	@Transactional(readOnly = false)
	public void deleteCommunity(Community community) {
		this.experienceDao.deleteCommunity(community.getId());
	}
	
	@Transactional(readOnly = false)
	public void deleteCommunity(int id) {
		this.experienceDao.deleteCommunity(id);
	}
	
	@Transactional(readOnly = false)
	public List<Communitys> list() {
		return this.experienceDao.findAllExperience();
	}
	
	@Transactional(readOnly = false)
	public List<Community> listSameCity(String city) {
		return this.experienceDao.findSameCity(city);
	}
}
