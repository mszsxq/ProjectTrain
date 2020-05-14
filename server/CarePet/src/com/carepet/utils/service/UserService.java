package com.carepet.utils.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carepet.ai.animal.AnimalClassify;
import com.carepet.entity.User;
import com.carepet.entity.FindTable;
import com.carepet.utils.dao.UserDao;
import com.carepet.utils.dao.UserDao;

@Service
public class UserService {

	@Resource
	private UserDao userDao;
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		this.userDao.saveUser(user);
	}
	
	@Transactional(readOnly = false)
	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}
	
	@Transactional(readOnly = false)
	public void insertUser(User user) {
		this.userDao.saveUser(user);
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		this.userDao.deleteUser(user.getId());
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(int id) {
		this.userDao.deleteUser(id);
	}
	
	@Transactional(readOnly = false)
	public List<User> list() {
		return this.userDao.findAllUser();
	}
	
	@Transactional(readOnly = false)
	public User getUser(int id) {
		return this.userDao.findUser(id);
	}
}
