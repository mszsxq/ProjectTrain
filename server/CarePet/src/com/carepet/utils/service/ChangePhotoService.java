package com.carepet.utils.service;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.ram.model.v20150501.CreateUserResponse.User;
import com.carepet.utils.dao.ChangePhotoDao;
@Service
public class ChangePhotoService {
	@Resource
	private ChangePhotoDao changeDao;
	@Transactional(readOnly = false)
	public void updateUser(String head, int id) {
		changeDao.updateUser(head, id);
	}
	

}
