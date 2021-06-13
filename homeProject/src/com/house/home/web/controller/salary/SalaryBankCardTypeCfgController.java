package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.salary.SalaryBankCardTypeCfg;
import com.house.home.entity.salary.SalaryInd;
import com.house.home.service.salary.SalaryBankCardTypeCfgService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/salaryBankCardTypeCfg")
public class SalaryBankCardTypeCfgController extends BaseController{
	@Autowired
	private  SalaryBankCardTypeCfgService salaryBankCardTypeCfgService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryBankCardTypeCfg salaryBankCardTypeCfg) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salaryBankCardTypeCfgService.findPageBySql(page, salaryBankCardTypeCfg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		
		return new ModelAndView("admin/salary/salaryBankCardTypeCfg/salaryBankCardTypeCfg_list")
		.addObject("salaryBankCardTypeCfg", salaryBankCardTypeCfg);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response, SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		
		return new ModelAndView("admin/salary/salaryBankCardTypeCfg/salaryBankCardTypeCfg_save")
		.addObject("salaryBankCardTypeCfg", salaryBankCardTypeCfg);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response, SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		
		if(salaryBankCardTypeCfg.getPk() != null){
			salaryBankCardTypeCfg = salaryBankCardTypeCfgService.get(SalaryBankCardTypeCfg.class, salaryBankCardTypeCfg.getPk());
		}
		
		return new ModelAndView("admin/salary/salaryBankCardTypeCfg/salaryBankCardTypeCfg_update")
		.addObject("salaryBankCardTypeCfg", salaryBankCardTypeCfg);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response, SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		
		if(salaryBankCardTypeCfg.getPk() != null){
			salaryBankCardTypeCfg = salaryBankCardTypeCfgService.get(SalaryBankCardTypeCfg.class, salaryBankCardTypeCfg.getPk());
		}
		
		return new ModelAndView("admin/salary/salaryBankCardTypeCfg/salaryBankCardTypeCfg_view")
		.addObject("salaryBankCardTypeCfg", salaryBankCardTypeCfg);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		logger.debug("新增开始");
		try{

			boolean checkDescr = salaryBankCardTypeCfgService.checkSalaryBankCardTypeCfg(salaryBankCardTypeCfg, "A");
			if(!checkDescr){
				ServletUtils.outPrintFail(request, response, "新增失败，系统中存在完全相同的数据");
				return;
			}
			
			if(StringUtils.isBlank(salaryBankCardTypeCfg.getSalaryPayCmp())){
				salaryBankCardTypeCfg.setSalaryPayCmp("");
			}
			salaryBankCardTypeCfg.setLastUpdate(new Date());
			salaryBankCardTypeCfg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryBankCardTypeCfg.setExpired("F");
			salaryBankCardTypeCfg.setActionLog("ADD");
			
			salaryBankCardTypeCfgService.save(salaryBankCardTypeCfg);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增系统指标失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		logger.debug("编辑开始");
		try{

			boolean checkDescr = salaryBankCardTypeCfgService.checkSalaryBankCardTypeCfg(salaryBankCardTypeCfg, "M");
			if(!checkDescr){
				ServletUtils.outPrintFail(request, response, "编辑失败，编辑后系统中存在完全相同的数据");
				return;
			}
			
			if(StringUtils.isBlank(salaryBankCardTypeCfg.getSalaryPayCmp())){
				salaryBankCardTypeCfg.setSalaryPayCmp("");
			}
			salaryBankCardTypeCfg.setLastUpdate(new Date());
			salaryBankCardTypeCfg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryBankCardTypeCfg.setActionLog("EDIT");
			
			salaryBankCardTypeCfgService.update(salaryBankCardTypeCfg);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryBankCardTypeCfg salaryBankCardTypeCfg){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salaryBankCardTypeCfgService.findPageBySql(page, salaryBankCardTypeCfg);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬发放银行卡类型配置_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
