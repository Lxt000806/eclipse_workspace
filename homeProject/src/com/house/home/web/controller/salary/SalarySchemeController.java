package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.salary.SalaryScheme;
import com.house.home.service.salary.SalarySchemeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/salaryScheme")
public class SalarySchemeController extends BaseController{
	
	@Autowired
	private  SalarySchemeService salarySchemeService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salarySchemeService.findPageBySql(page, salaryScheme);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSalaryItemJqgrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goSalaryItemJqgrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salarySchemeService.findSalaryItemBySql(page, salaryScheme);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goEmpListJqgrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goEmpListJqgrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salarySchemeService.findEmpListBySql(page, salaryScheme);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPaymentListJqgrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goPaymentListJqgrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salarySchemeService.findPaymentListBySql(page, salaryScheme);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSchemeItemJqgrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goSchemeItemJqgrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salarySchemeService.findSchemeItemBySql(page, salaryScheme);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_list")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_save")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		if(salaryScheme.getPk()!= null){
			salaryScheme = salarySchemeService.get(SalaryScheme.class, salaryScheme.getPk());
		}
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_update")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		if(salaryScheme.getPk()!= null){
			salaryScheme = salarySchemeService.get(SalaryScheme.class, salaryScheme.getPk());
		}
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_view")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goEmpList")
	public ModelAndView goEmpList(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		if(salaryScheme.getPk()!= null){
			salaryScheme = salarySchemeService.get(SalaryScheme.class, salaryScheme.getPk());
		}
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_empList")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goPaymentList")
	public ModelAndView goPaymentList(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		if(salaryScheme.getPk()!= null){
			salaryScheme = salarySchemeService.get(SalaryScheme.class, salaryScheme.getPk());
		}
		salaryScheme.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_paymentList")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goAddPayment")
	public ModelAndView goAddPayment(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		if(salaryScheme.getPk()!= null){
			salaryScheme = salarySchemeService.get(SalaryScheme.class, salaryScheme.getPk());
		}
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_addPayment")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goUpdatePayment")
	public ModelAndView goUpdatePayment(HttpServletRequest request ,
			HttpServletResponse response, Map<String, Object> map){
		
		System.out.println(request.getParameter("map"));
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_updatePayment")
		.addObject("map", jsonObject);
	}
	
	@RequestMapping("/goViewPayment")
	public ModelAndView goViewPayment(HttpServletRequest request ,
			HttpServletResponse response, Map<String, Object> map){
		
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_viewPayment")
		.addObject("map", jsonObject);
	}
	
	
	
	@RequestMapping("/goSchemeItemSet")
	public ModelAndView goSchemeItemSet(HttpServletRequest request ,
			HttpServletResponse response, SalaryScheme salaryScheme){
		
		return new ModelAndView("admin/salary/salaryScheme/salaryScheme_schemeItemSet")
		.addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, SalaryScheme salaryScheme){
		logger.debug("薪酬方案新增");
		try {
			
			String detailJson = request.getParameter("detailJson");
			salaryScheme.setDetailJson(detailJson);
			salaryScheme.setM_umState("A");
			salaryScheme.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryScheme.setExpired("F");
			
			Result result =this.salarySchemeService.doSave(salaryScheme);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增薪酬方案失败");
		}
	}
	
	@RequestMapping("/doSavePayment")
	public void doSavePayment(HttpServletRequest request,HttpServletResponse response, SalaryScheme salaryScheme){
		logger.debug("薪酬方案分账定义新增");
		try {
			
			String detailJson = request.getParameter("detailJson");
			salaryScheme.setDetailJson(detailJson);
			salaryScheme.setM_umState("M");
			salaryScheme.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryScheme.setExpired("F");
			
			Result result =this.salarySchemeService.doSavePayment(salaryScheme);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增薪酬方案失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, SalaryScheme salaryScheme){
		logger.debug("部分到货开始");
		try {
			
			String detailJson = request.getParameter("detailJson");
			salaryScheme.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryScheme.setDetailJson(detailJson);
			salaryScheme.setM_umState("M");
			salaryScheme.setExpired("F");
			
			Result result =this.salarySchemeService.doSave(salaryScheme);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增薪酬方案失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryScheme salaryScheme){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salarySchemeService.findPageBySql(page, salaryScheme);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬方案_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
