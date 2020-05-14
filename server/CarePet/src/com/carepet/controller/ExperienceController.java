package com.carepet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carepet.entity.Community;
import com.carepet.entity.Communitys;
import com.carepet.utils.service.CommunityService;
import com.carepet.utils.service.ExperienceService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/experience")
public class ExperienceController {
	@Resource
	private ExperienceService experienceService;
	@RequestMapping("/insertcommunity")
	public void insertcommunity(@RequestParam String community) {
		System.out.println("sssss1111");
		Gson gson=new Gson();
		Community community2=gson.fromJson(community, Community.class);
		System.out.println(community2.getTime());
		experienceService.saveCommunity(community2);
	}
	@ResponseBody
	@RequestMapping("/listall")
	public String list(Model model,HttpServletRequest req,HttpServletResponse rep) throws IOException {
		rep.setCharacterEncoding("UTF-8");
		Gson gson=new Gson();
		List<Communitys> communities=experienceService.list();
		String communityString=gson.toJson(communities);
		model.addAttribute("findTables", communityString);
		PrintWriter writer = rep.getWriter();
		writer.println(communityString+"\n");
	    writer.flush();
	    writer.close();
	    System.out.println(communityString+"");
		return communityString;
	}
}
