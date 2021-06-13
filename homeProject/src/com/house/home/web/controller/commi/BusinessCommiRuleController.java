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
import com.house.home.entity.commi.BusinessCommiRule;
import com.house.home.entity.workflow.Department;
import com.house.home.service.commi.BusinessCommiRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/businessCommiRule")
public class BusinessCommiRuleController extends BaseController{
	@Autowired
	private  BusinessCommiRuleService businessCommiRuleService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,BusinessCommiRule businessCommiRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		businessCommiRuleService.findPageBySql(page, businessCommiRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiRule businessCommiRule) throws Exception {
		
		return new ModelAndView("admin/commi/businessCommiRule/businessCommiRule_list").addObject("businessCommiRule", businessCommiRule);
	}
	
	/**
	 * 跳转浮动规则管理
	 * @param request
	 * @param response
	 * @param businessCommiRule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFloatRule")
	public ModelAndView goFloatRule(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiRule businessCommiRule) throws Exception {
		
		return new ModelAndView("admin/commi/businessCommiRule/businessCommiRule_floatRule").addObject("businessCommiRule", businessCommiRule);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiRule businessCommiRule) throws Exception {
		
		return new ModelAndView("admin/commi/businessCommiRule/businessCommiRule_save").addObject("businessCommiRule", businessCommiRule);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiRule businessCommiRule) throws Exception {
		Employee employee = new Employee();
		Department department = new Department();
		if(businessCommiRule.getPk() != null){
			businessCommiRule = businessCommiRuleService.get(BusinessCommiRule.class, businessCommiRule.getPk());
			if(businessCommiRule != null && StringUtils.isNotBlank(businessCommiRule.getEmpCode())){
				employee = businessCommiRuleService.get(Employee.class, businessCommiRule.getEmpCode());
			}
			if(businessCommiRule != null && StringUtils.isNotBlank(businessCommiRule.getDepartment())){
				department = businessCommiRuleService.get(Department.class, businessCommiRule.getDepartment());
			}
		}
		
		return new ModelAndView("admin/commi/businessCommiRule/businessCommiRule_update")
		.addObject("businessCommiRule", businessCommiRule)
		.addObject("employee", employee).addObject("department", department);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiRule businessCommiRule) throws Exception {
		Employee employee = new Employee();
		Department department = new Department();
		if(businessCommiRule.getPk() != null){
			businessCommiRule = businessCommiRuleService.get(BusinessCommiRule.class, businessCommiRule.getPk());
			if(businessCommiRule != null && StringUtils.isNotBlank(businessCommiRule.getEmpCode())){
				employee = businessCommiRuleService.get(Employee.class, businessCommiRule.getEmpCode());
			}
			if(businessCommiRule != null && StringUtils.isNotBlank(businessCommiRule.getDepartment())){
				department = businessCommiRuleService.get(Department.class, businessCommiRule.getDepartment());
			}
		}
		
		return new ModelAndView("admin/commi/businessCommiRule/businessCommiRule_copy")
		.addObject("businessCommiRule", businessCommiRule)
		.addObject("employee", employee).addObject("department", department);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiRule businessCommiRule) throws Exception {
		
		Employee employee = new Employee();
		Department department = new Department();
		if(businessCommiRule.getPk() != null){
			businessCommiRule = businessCommiRuleService.get(BusinessCommiRule.class, businessCommiRule.getPk());
			if(businessCommiRule != null && StringUtils.isNotBlank(businessCommiRule.getEmpCode())){
				employee = businessCommiRuleService.get(Employee.class, businessCommiRule.getEmpCode());
			}
			if(businessCommiRule != null && StringUtils.isNotBlank(businessCommiRule.getDepartment())){
				department = businessCommiRuleService.get(Department.class, businessCommiRule.getDepartment());
			}
		}
		
		return new ModelAndView("admin/commi/businessCommiRule/businessCommiRule_view")
		.addObject("businessCommiRule", businessCommiRule)
		.addObject("employee", employee).addObject("department", department);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, BusinessCommiRule businessCommiRule){
		logger.debug("添加业务提成规则开始");
		try{
			businessCommiRule.setLastUpdate(new Date());
			businessCommiRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			businessCommiRule.setExpired("F");
			businessCommiRule.setActionLog("ADD");
			businessCommiRuleService.save(businessCommiRule);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增业务提成规则失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, BusinessCommiRule businessCommiRule){
		logger.debug("添加业务提成规则开始");
		try{
			BusinessCommiRule bcr = new BusinessCommiRule();
			if(businessCommiRule.getPk() != null){
				bcr = businessCommiRuleService.get(BusinessCommiRule.class, businessCommiRule.getPk());
				if(bcr == null){
					ServletUtils.outPrintFail(request, response, "编辑业务提成规则失败");
					return;
				}
				
				bcr.setRole(businessCommiRule.getRole());
				bcr.setPosiType(businessCommiRule.getPosiType());
				bcr.setPrior(businessCommiRule.getPrior());
				bcr.setEmpCode(businessCommiRule.getEmpCode());
				bcr.setDepartment(businessCommiRule.getDepartment());
				bcr.setType(businessCommiRule.getType());
				bcr.setCommiPer(businessCommiRule.getCommiPer()==null?0.0:businessCommiRule.getCommiPer());
				bcr.setSubsidyPer(businessCommiRule.getSubsidyPer()==null?0.0:businessCommiRule.getSubsidyPer());
				bcr.setDesignAgainSubsidyPer(businessCommiRule.getDesignAgainSubsidyPer()==null?0.0:businessCommiRule.getDesignAgainSubsidyPer());
				bcr.setFloatRulePK(businessCommiRule.getFloatRulePK());
				bcr.setIsBearAgainCommi(businessCommiRule.getIsBearAgainCommi());
				bcr.setRecordCommiPer(businessCommiRule.getRecordCommiPer()==null?0.0:businessCommiRule.getRecordCommiPer());
				bcr.setDepartment(businessCommiRule.getDepartment());
				bcr.setEmpCode(businessCommiRule.getEmpCode());
				bcr.setLastUpdate(new Date());
				bcr.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				bcr.setExpired(businessCommiRule.getExpired());
				bcr.setActionLog("EDIT");
				bcr.setRecordRightCommiPer(businessCommiRule.getRecordRightCommiPer());
				bcr.setRightCommiPer(businessCommiRule.getRightCommiPer());
				
				businessCommiRuleService.update(bcr);
			} else {
				ServletUtils.outPrintFail(request, response, "编辑业务提成规则失败");
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑业务提成规则失败");
		}
	}
	
	@RequestMapping("/doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response, BusinessCommiRule businessCommiRule){
		logger.debug("删除业务提成规则开始");
		try{
			BusinessCommiRule bcr = new BusinessCommiRule();
			if(businessCommiRule.getPk() != null){
				bcr = businessCommiRuleService.get(BusinessCommiRule.class, businessCommiRule.getPk());
				if(bcr != null){
					bcr.setExpired("T");
					bcr.setActionLog("EDIT");
					bcr.setLastUpdate(new Date());
					bcr.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					businessCommiRuleService.update(bcr);
				}
			} else {
				ServletUtils.outPrintFail(request, response, "删除业务提成规则失败");
			}
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除业务提成规则失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			BusinessCommiRule businessCommiRule){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		businessCommiRuleService.findPageBySql(page, businessCommiRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业务提成规则_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
