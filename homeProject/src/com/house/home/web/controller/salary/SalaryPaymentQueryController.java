package com.house.home.web.controller.salary;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.service.salary.SalaryCalcService;
import com.house.home.service.salary.SalaryMonService;
import com.house.home.service.salary.SalaryPaymentQueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.entity.salary.SalaryPayment;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;

@Controller
@RequestMapping("/admin/salaryPaymentQuery")
public class SalaryPaymentQueryController extends BaseController{
	@Autowired
	private  SalaryPaymentQueryService salaryPaymentQueryService;
	@Autowired
	private SalaryMonService salaryMonService;
	@Autowired
	private SalaryCalcService salaryCalcService;
	
	@RequestMapping("/getMainPageData")
	@ResponseBody
	public List<Map<String, Object>> getMainPageData(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryCalcService.getPaymentDetailPageData(salaryData);
		
		return list;
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryPayment salaryPayment){
		Integer maxCheckedMon = 0;
		List<Map<String, Object>> list = salaryMonService.getCheckedMon();
		if(list != null && list.size() > 0){
			maxCheckedMon = Integer.parseInt(list.get(0).get("SalaryMon").toString());
		}
		salaryPayment.setSalaryMon(maxCheckedMon);
		
		return new ModelAndView("admin/salary/salaryPaymentQuery/salaryPaymentQuery_list")
		.addObject("salaryPayment", salaryPayment);
	}
	
	@ResponseBody
	@RequestMapping("/getSchemeList")
	public List<Map<String, Object>> getSchemeList(SalaryPayment salaryPayment){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryPaymentQueryService.getSchemeList(salaryPayment);
		if(list == null){
	
			return new ArrayList<Map<String,Object>>();
		}
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/getPaymentDefList")
	public List<Map<String, Object>> getPaymentDefList(SalaryPayment salaryPayment){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryPaymentQueryService.getPaymentDefList(salaryPayment);
		if(list == null){
	
			return new ArrayList<Map<String,Object>>();
		}
		
		return list;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryData salaryData){

		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		List<Map<String, Object>> list = salaryCalcService.getPaymentDetailPageData(salaryData);
		page.setResult(list);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬分账_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
