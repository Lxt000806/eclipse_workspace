package com.house.home.web.controller.salary;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.salary.SalaryData;
import com.house.home.service.salary.SalaryCalcService;
import com.house.home.service.salary.SalaryCompareAnalyseService;
import com.house.home.service.salary.SalaryMonService;

import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;

@Controller
@RequestMapping("/admin/salaryCompareAnalyse")
public class SalaryCompareAnalyseController extends BaseController{
	@Autowired
	private  SalaryCompareAnalyseService salaryCompareAnalyseService;
	@Autowired
	private SalaryMonService salaryMonService;
	@Autowired
	private SalaryCalcService salaryCalcService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  Map<String , Object> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{

		List<Map<String, Object>> list = salaryCompareAnalyseService.findPageBySql(salaryData);
		if (list.size() > 0 && list != null) {
			return list.get(0);
		}
		return null;
	}
	
	@RequestMapping("/goJoinEmpJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goJoinEmpJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		salaryCompareAnalyseService.findJoinEmpPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goLeaveEmpJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goLeaveEmpJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		salaryCompareAnalyseService.findLeaveEmpPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goBaseSalaryChgEmpJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goBaseSalaryChgEmpJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		salaryCompareAnalyseService.findBaseSalaryCHgEmpPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goRealPayJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goRealPayJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		salaryCompareAnalyseService.findRealPayPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goUnPaidEmpJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goUnPaidEmpJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		salaryCompareAnalyseService.findUnPaidEmpPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,SalaryData salaryData) throws Exception {
		Integer lastMon = 0;

		salaryData.setSalaryMon(Integer.parseInt(DateUtil.format(DateUtil.addMonth(new Date(), -1), "yyyyMM")));
		List<Map<String, Object>> list = salaryMonService.getLastMon(salaryData);
		if(list != null && list.size() > 0){
			if (list.get(0).get("SalaryMon") != null){
				lastMon = Integer.parseInt(salaryMonService.getLastMon(salaryData).get(0).get("SalaryMon").toString());
				salaryData.setCompareMon(lastMon);
			}
		}
		lastMon = Integer.parseInt(salaryMonService.getLastMon(salaryData).get(0).get("SalaryMon").toString());
		salaryData.setCompareMon(lastMon);
		Integer scheme = salaryCalcService.getSalaryScheme();
		salaryData.setSalaryScheme(scheme);
		return new ModelAndView("admin/salary/salaryCompareAnalyse/salaryCompareAnalyse_list")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/getLastMon")
	@ResponseBody
	public  Map<String, Object> getLastMon(HttpServletRequest request, 
			HttpServletResponse response,SalaryData salaryData) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Integer lastMon = 0;
		List<Map<String, Object>> list = salaryMonService.getLastMon(salaryData);
		Integer scheme = salaryCalcService.getSalaryScheme();
		map.put("salaryScheme", scheme);
		map.put("lastMon", lastMon);
		if(list != null && list.size() > 0){
			if (list.get(0).get("SalaryMon") != null){
				lastMon = Integer.parseInt(salaryMonService.getLastMon(salaryData).get(0).get("SalaryMon").toString());
				map.put("lastMon", lastMon);
			}
		}
		return map;
	}
}
