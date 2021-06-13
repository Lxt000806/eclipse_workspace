package com.house.home.web.controller.salary;

import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.salary.SalaryData;
import com.house.home.service.salary.SalaryCalcReportService;
import com.house.home.service.salary.SalaryMonService;

@Controller
@RequestMapping("/admin/salaryCalcReport")
public class SalaryCalcReportController extends BaseController{

	@Autowired
	private SalaryCalcReportService salaryCalcReportService;
	@Autowired
	private SalaryMonService salaryMonService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryCalcReportService.findPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		Integer checkedMon = 0;
		List<Map<String,Object>> monList = salaryMonService.getCheckedMon();
		if(monList != null && monList.size()>0){
			checkedMon = Integer.parseInt(monList.get(0).get("SalaryMon").toString());
		}
		
		salaryData.setSalaryMon(checkedMon);
		salaryData.setSalaryMonTo(checkedMon);
		
		return new ModelAndView("admin/salary/salaryCalcReport/salaryCalcReport_list")
		.addObject("salaryData", salaryData);
	}
}
