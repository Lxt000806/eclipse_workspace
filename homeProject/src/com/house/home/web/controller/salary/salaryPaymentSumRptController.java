package com.house.home.web.controller.salary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.salary.SalaryPayment;
import com.house.home.service.salary.SalaryCalcService;
import com.house.home.service.salary.SalaryPaymentSumRptService;

@Controller
@RequestMapping("/admin/salaryPaymentSumRpt")
public class salaryPaymentSumRptController extends BaseController{
	
	@Autowired
	private SalaryPaymentSumRptService salaryPaymentSumRptService;
	
	@Autowired
	private SalaryCalcService salaryCalcService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryPayment salaryPayment) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryPaymentSumRptService.findPageBySql(page, salaryPayment);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getMainPageData")
	@ResponseBody
	public List<Map<String, Object>> getMainPageData(HttpServletRequest request ,
			HttpServletResponse response,SalaryPayment salaryPayment){
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryPaymentSumRptService.getMainPageData(salaryPayment);
		
		return list;
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryPayment salaryPayment){
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();		
		
		Integer calcMon = Integer.parseInt(DateUtil.format(DateUtil.addMonth(new Date(), -1), "yyyyMM"));
		salaryPayment.setSalaryMon(calcMon);
		
		list = salaryPaymentSumRptService.getPaymentDefList(salaryPayment);

		String json = "[]";
		if(list != null){
			json = JSON.toJSONString(list);
		}
		
		return new ModelAndView("admin/salary/salaryPaymentSumRpt/salaryPaymentSumRpt_list")
		.addObject("salaryPayment", salaryPayment).addObject("object",json);
	}
	
	@ResponseBody
	@RequestMapping("/getPaymentDefList")
	public List<Map<String, Object>> getPaymentDefList(SalaryPayment salaryPayment){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryPaymentSumRptService.getPaymentDefList(salaryPayment);
		if(list == null){
	
			return new ArrayList<Map<String,Object>>();
		}
		
		return list;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryPayment salaryPayment){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		List<Map<String, Object>> list = salaryPaymentSumRptService.getMainPageData(salaryPayment);
		page.setResult(list);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬汇总报表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	
	
	
}
