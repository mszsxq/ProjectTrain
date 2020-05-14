package com.carepet.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carepet.entity.Community;
import com.carepet.utils.dao.UserDao;
import com.carepet.utils.service.ChangePhotoService;
import com.carepet.utils.service.ExperienceService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/changephoto")
public class ChangePhotoController {
	@Resource
	private UserDao changephoto;
	@RequestMapping("/change")
	public void updateUserPhoto(@RequestParam String headname,@RequestParam int userId) {
		System.out.println("photo");
		String head=headname;
		changephoto.updateUserTouxiang(head,userId);
	}
}
