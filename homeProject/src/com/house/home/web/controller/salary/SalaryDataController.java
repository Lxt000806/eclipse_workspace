package com.house.home.web.controller.salary;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.salary.SalaryData;
import com.house.home.service.salary.SalaryDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/salaryData")
public class SalaryDataController extends BaseController{
	@Autowired
	private  SalaryDataService salaryDataService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryDataService.findPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		return new ModelAndView("admin/salary/salaryData/salaryData_list")
		.addObject("salaryData", salaryData);
	}
}
