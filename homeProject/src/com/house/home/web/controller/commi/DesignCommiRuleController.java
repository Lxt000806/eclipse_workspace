package com.house.home.web.controller.commi;

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

import com.house.home.entity.basic.Employee;
import com.house.home.entity.commi.DesignCommiRule;
import com.house.home.entity.workflow.Department;
import com.house.home.service.commi.DesignCommiRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/designCommiRule")
public class DesignCommiRuleController extends BaseController{
	@Autowired
	private  DesignCommiRuleService designCommiRuleService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,DesignCommiRule designCommiRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		designCommiRuleService.findPageBySql(page, designCommiRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, DesignCommiRule designCommiRule) throws Exception {
		
		return new ModelAndView("admin/commi/designCommiRule/designCommiRule_list")
				.addObject("designCommiRule", designCommiRule);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, DesignCommiRule designCommiRule) throws Exception {
		
		return new ModelAndView("admin/commi/designCommiRule/designCommiRule_save")
				.addObject("designCommiRule", designCommiRule);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, DesignCommiRule designCommiRule) throws Exception {
		Department department = new Department();
		Employee employee = new Employee();
		if(designCommiRule.getPk() != null){
			designCommiRule = designCommiRuleService.get(DesignCommiRule.class, designCommiRule.getPk());
			if(designCommiRule != null && StringUtils.isNotBlank(designCommiRule.getDepartment())){
				department = designCommiRuleService.get(Department.class, designCommiRule.getDepartment());
			}
			if(designCommiRule != null && StringUtils.isNotBlank(designCommiRule.getEmpCode())){
				employee = designCommiRuleService.get(Employee.class, designCommiRule.getEmpCode());
			}
		}
		
		return new ModelAndView("admin/commi/designCommiRule/designCommiRule_update")
		.addObject("designCommiRule", designCommiRule)
		.addObject("department", department).addObject("employee", employee);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, DesignCommiRule designCommiRule) throws Exception {
		Department department = new Department();
		Employee employee = new Employee();
		if(designCommiRule.getPk() != null){
			designCommiRule = designCommiRuleService.get(DesignCommiRule.class, designCommiRule.getPk());
			if(designCommiRule != null && StringUtils.isNotBlank(designCommiRule.getDepartment())){
				department = designCommiRuleService.get(Department.class, designCommiRule.getDepartment());
			}
			if(designCommiRule != null && StringUtils.isNotBlank(designCommiRule.getEmpCode())){
				employee = designCommiRuleService.get(Employee.class, designCommiRule.getEmpCode());
			}
		}
		
		return new ModelAndView("admin/commi/designCommiRule/designCommiRule_copy")
		.addObject("designCommiRule", designCommiRule)
		.addObject("department", department).addObject("employee", employee);
	}
	
	@RequestMapping("/goView")
	public ModelAndView gogoView(HttpServletRequest request,
			HttpServletResponse response, DesignCommiRule designCommiRule) throws Exception {
		Department department = new Department();
		Employee employee = new Employee();
		if(designCommiRule.getPk() != null){
			designCommiRule = designCommiRuleService.get(DesignCommiRule.class, designCommiRule.getPk());
			if(designCommiRule != null && StringUtils.isNotBlank(designCommiRule.getDepartment())){
				department = designCommiRuleService.get(Department.class, designCommiRule.getDepartment());
			}
			if(designCommiRule != null && StringUtils.isNotBlank(designCommiRule.getEmpCode())){
				employee = designCommiRuleService.get(Employee.class, designCommiRule.getEmpCode());
			}
		}
		
		return new ModelAndView("admin/commi/designCommiRule/designCommiRule_view")
		.addObject("designCommiRule", designCommiRule)
		.addObject("department", department).addObject("employee", employee);
	}
	
	@RequestMapping("/goFloatRule")
	public ModelAndView goFloatRule(HttpServletRequest request,
			HttpServletResponse response, DesignCommiRule designCommiRule) throws Exception {
		
		return new ModelAndView("admin/commi/designCommiRule/designCommiRule_floatRule").addObject("designCommiRule", designCommiRule);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, DesignCommiRule designCommiRule){
		logger.debug("添加设计提成规则开始");
		try{
			designCommiRule.setLastUpdate(new Date());
			designCommiRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			designCommiRule.setExpired("F");
			designCommiRule.setActionLog("ADD");
			
			designCommiRuleService.save(designCommiRule);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增设计提成规则失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, DesignCommiRule designCommiRule){
		logger.debug("编辑设计提成规则开始");
		try{
			DesignCommiRule dcr = new DesignCommiRule();
			if(designCommiRule.getPk() != null){
				dcr = designCommiRuleService.get(DesignCommiRule.class, designCommiRule.getPk());
				if(dcr == null){
					ServletUtils.outPrintFail(request, response, "编辑设计提成规则失败");
					return;
				}
				dcr.setRole(designCommiRule.getRole());
				dcr.setDepartment(designCommiRule.getDepartment());
				dcr.setEmpCode(designCommiRule.getEmpCode());
				dcr.setPrior(designCommiRule.getPrior());
				dcr.setPreCommiPer(designCommiRule.getPreCommiPer()==null?0.0:designCommiRule.getPreCommiPer());
				dcr.setSubsidyPer(designCommiRule.getSubsidyPer()==null?0.0:designCommiRule.getSubsidyPer());
				dcr.setFloatRulePK(designCommiRule.getFloatRulePK());
				dcr.setCheckCommiType(designCommiRule.getCheckCommiType());
				dcr.setCheckCommiPer(designCommiRule.getCheckCommiPer()==null?0.0:designCommiRule.getCheckCommiPer());
				
				dcr.setLastUpdate(new Date());
				dcr.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				dcr.setExpired(designCommiRule.getExpired());
				dcr.setActionLog("EDIT");
				
				designCommiRuleService.update(dcr);
			} else {
				ServletUtils.outPrintFail(request, response, "编辑设计提成规则失败");
			}
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑设计提成规则失败");
		}
	}
	
	@RequestMapping("/doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response, DesignCommiRule designCommiRule){
		logger.debug("删除设计提成规则开始");
		try{
			DesignCommiRule dcr = new DesignCommiRule();
			if(designCommiRule.getPk() != null){
				dcr = designCommiRuleService.get(DesignCommiRule.class, designCommiRule.getPk());
				if(dcr != null){
					dcr.setExpired("T");
					dcr.setActionLog("EDIT");
					dcr.setLastUpdate(new Date());
					dcr.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					designCommiRuleService.update(dcr);
				}
			} else {
				ServletUtils.outPrintFail(request, response, "删除设计提成规则失败");
			}
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除设计提成规则失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			DesignCommiRule designCommiRule){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		designCommiRuleService.findPageBySql(page, designCommiRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"设计提成规则_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
