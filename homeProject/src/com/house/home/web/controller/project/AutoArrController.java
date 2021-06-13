package com.house.home.web.controller.project;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.AutoArr;
import com.house.home.service.project.AutoArrService;

@Controller
@RequestMapping("/admin/autoArr")
public class AutoArrController extends BaseController{

	@Autowired
	private AutoArrService autoArrService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,AutoArr autoArr) throws Exception{
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		autoArrService.findPageBySql(page, autoArr);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,AutoArr autoArr){
		
		return new ModelAndView("admin/project/autoArr/autoArr_code").addObject("autoArr",autoArr);
	}
	
	/**
	 * 根据id查询Purchase详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAutoArr")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		AutoArr autoArr= autoArrService.get(AutoArr.class, id);
		if(autoArr == null){
			return this.out("系统中不存在PK="+id+"工人安排表", false);
		}
		return this.out(autoArr, true);
	}
	
	
	
	
	
	
	
	
	
	
	
}
