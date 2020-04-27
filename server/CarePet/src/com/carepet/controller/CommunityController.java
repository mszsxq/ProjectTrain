package com.carepet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carepet.entity.Community;
import com.carepet.entity.FindTable;
import com.carepet.utils.service.CommunityService;
import com.google.gson.Gson;
import com.carepet.entity.Community;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@Resource
	private CommunityService communityService;
	
	@RequestMapping("listall")
	public String list(Model model) {
		Gson gson=new Gson();
		List<Community> communities=communityService.list();
		String communityString=gson.toJson(communities);
		model.addAttribute("findTables", communityString);
		return "list";
	}
	
	@RequestMapping("/insertcommunity")
	public void insertcommunity(@RequestParam String community) {
		Gson gson=new Gson();
		Community community2=gson.fromJson(community, Community.class);
		communityService.saveCommunity(community2);
	}
	@RequestMapping("/listsame")
	public String listSame(@RequestParam String city,Model model) {
		Gson gson=new Gson();
		List<Community> communities=communityService.listSameCity(city);
		String findTablestring=gson.toJson(communities);
		model.addAttribute("findTables", findTablestring);
		return "list";
	}
}
