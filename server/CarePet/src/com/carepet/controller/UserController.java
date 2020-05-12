package com.carepet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carepet.entity.Community;
import com.carepet.entity.FindTable;
import com.carepet.entity.User;
import com.carepet.utils.service.CommunityService;
import com.carepet.utils.service.UserService;
import com.google.gson.Gson;
import com.carepet.entity.Community;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
//	@ResponseBody
//	@RequestMapping("listall")
//	public String list(Model model,HttpServletRequest req,HttpServletResponse rep) throws IOException {
//		Gson gson=new Gson();
//		List<Community> communities=communityService.list();
//		String communityString=gson.toJson(communities);
//		model.addAttribute("findTables", communityString);
//		PrintWriter writer = rep.getWriter();
//		writer.println(communityString+"\n");
//	    writer.flush();
//	    writer.close();
//	    System.out.println(communityString+"");
//		return communityString;
//	}
//	
	@RequestMapping("/insertuser")
	public void insertcommunity(@RequestParam String user) {
		Gson gson=new Gson();
		User user2=gson.fromJson(user, User.class);
		userService.saveUser(user2);
	}
//	@RequestMapping("/listsame")
//	public String listSame(@RequestParam String city,Model model) {
//		Gson gson=new Gson();
//		List<Community> communities=communityService.listSameCity(city);
//		String findTablestring=gson.toJson(communities);
//		model.addAttribute("findTables", findTablestring);
//		return "list";
//	}
	@RequestMapping("/getuser")
	public String list(@RequestParam String id,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		System.out.println(id);
		int userId = Integer.parseInt(id);
		User user=userService.getUser(userId);
		String usering=gson.toJson(user);
		model.addAttribute("user",usering);
		System.out.println(usering);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(usering);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
