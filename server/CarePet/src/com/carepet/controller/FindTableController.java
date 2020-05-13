package com.carepet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carepet.entity.FindTable;
import com.carepet.utils.service.FindTableService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/findtable")
public class FindTableController {
	@Resource
	private FindTableService findTableService;
	
	@RequestMapping("/listall")
	public String list(Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
//		List<String> strings=new ArrayList<String>();
//		strings.add("https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg");
//		strings.add("https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg");
//		System.out.println(gson.toJson(strings));
		List<FindTable> findTables=findTableService.list();
		String findTablestring=gson.toJson(findTables);
		model.addAttribute("findTables", findTablestring);
		System.out.println(findTablestring);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(findTablestring);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//	"https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg++https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg" imgjson不同图片之间用++拼接

//	请求参数 findtable样板
//	{"id":4,"userid":4,"longitude":4,"latitude":4,"imgjson":"4","title":"4","content":"4","time":"4","city":"4","pettype":"4"}
	@RequestMapping("/insertfindtable")
	public void insertfindtable(@RequestParam String findtable) {
		Gson gson=new Gson();
		FindTable findTable2=gson.fromJson(findtable, FindTable.class);
		findTableService.saveFindTable(findTable2);
	}
	@RequestMapping("/listsame")
	public String listSame(@RequestParam String city,Model model) {
		Gson gson=new Gson();
		List<FindTable> findTables=findTableService.listSameCity(city);
		String findTablestring=gson.toJson(findTables);
		model.addAttribute("findTables", findTablestring);
		return "list";
	}
	@RequestMapping("/liststrf")
	public String list(@RequestParam String sousuo,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		List<FindTable> findTables=findTableService.liststrf(sousuo);
		String findTablestring=gson.toJson(findTables);
		System.out.println(findTablestring);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(findTablestring);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return findTablestring;
	}
}
