package com.house.home.web.controller.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.salary.SalaryData;
import com.house.home.service.salary.PersonalSalaryQueryService;
import com.house.home.service.salary.SalaryCalcService;

@Controller
@RequestMapping("admin/personalSalaryQuery")
public class PersonalSalaryQueryController extends BaseController{

	@Autowired
	private PersonalSalaryQueryService personalSalaryQueryService;
	@Autowired
	private SalaryCalcService salaryCalcService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public List<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		
		salaryData.setEmpName(this.getUserContext(request).getCzybh());
		List<Map<String, Object>> salaryDatas = personalSalaryQueryService.getMainPageData(salaryData);
		if (salaryDatas == null || salaryDatas.size()>1) {
			return null;
		}
		return salaryDatas;
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		Integer salaryMon = salaryCalcService.getMaxCalcMon();
		
		return new ModelAndView("admin/salary/personalSalaryQuery/personalSalaryQuery_list")
		.addObject("salaryData", salaryData).addObject("salaryMon", salaryMon);
	}
	
	@RequestMapping("/getSalaryScheme")
	@ResponseBody
	public List<Map<String, Object>> getSalaryScheme(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		
		salaryData.setEmpName(this.getUserContext(request).getCzybh());
		List<Map<String, Object>> salaryScheme = personalSalaryQueryService.getSalaryScheme(salaryData);
		if (salaryScheme == null) {
			return new ArrayList<Map<String,Object>>();
		}
		return salaryScheme;
	}
}
