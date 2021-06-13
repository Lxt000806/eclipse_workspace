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

import com.house.home.entity.commi.CommiExpr;
import com.house.home.entity.commi.CommiExprRule;
import com.house.home.entity.workflow.Department;
import com.house.home.service.commi.CommiExprRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/commiExprRule")
public class CommiExprRuleController extends BaseController{
	
	@Autowired
	private  CommiExprRuleService commiExprRuleService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,CommiExprRule commiExprRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiExprRuleService.findPageBySql(page, commiExprRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, CommiExprRule commiExprRule) throws Exception {
		
		
		return new ModelAndView("admin/commi/commiExprRule/commiExprRule_list")
			.addObject("commiExprRule", commiExprRule);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, CommiExprRule commiExprRule) throws Exception {
		
		return new ModelAndView("admin/commi/commiExprRule/commiExprRule_save")
			.addObject("commiExprRule", commiExprRule);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, CommiExprRule commiExprRule) throws Exception {
		
		Department department = new Department();
		if(commiExprRule.getPk() != null){
			commiExprRule = commiExprRuleService.get(CommiExprRule.class, commiExprRule.getPk());
			if(StringUtils.isNotBlank(commiExprRule.getDepartment())){
				department = commiExprRuleService.get(Department.class, commiExprRule.getDepartment());
			}
		}
		
		return new ModelAndView("admin/commi/commiExprRule/commiExprRule_update")
			.addObject("commiExprRule", commiExprRule).addObject("department", department);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, CommiExprRule commiExprRule) throws Exception {
		
		Department department = new Department();
		if(commiExprRule.getPk() != null){
			commiExprRule = commiExprRuleService.get(CommiExprRule.class, commiExprRule.getPk());
			if(StringUtils.isNotBlank(commiExprRule.getDepartment())){
				department = commiExprRuleService.get(Department.class, commiExprRule.getDepartment());
			}
		}
		
		return new ModelAndView("admin/commi/commiExprRule/commiExprRule_copy")
			.addObject("commiExprRule", commiExprRule).addObject("department", department);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goVIew(HttpServletRequest request,
			HttpServletResponse response, CommiExprRule commiExprRule) throws Exception {
		
		Department department = new Department();
		if(commiExprRule.getPk() != null){
			commiExprRule = commiExprRuleService.get(CommiExprRule.class, commiExprRule.getPk());
			if(StringUtils.isNotBlank(commiExprRule.getDepartment())){
				department = commiExprRuleService.get(Department.class, commiExprRule.getDepartment());
			}
		}
		
		return new ModelAndView("admin/commi/commiExprRule/commiExprRule_view")
			.addObject("commiExprRule", commiExprRule).addObject("department", department);
	}
	
	@RequestMapping("/goCommiExpr")
	public ModelAndView goCommiExpr(HttpServletRequest request,
			HttpServletResponse response, CommiExpr commiExpr) throws Exception {
		
		return new ModelAndView("admin/commi/commiExprRule/commiExprRule_commiExpr")
			.addObject("commiExpr", commiExpr);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, CommiExprRule commiExprRule){
		logger.debug("添加提成公式开始");
		try{
			commiExprRule.setActionLog("ADD");
			commiExprRule.setLastUpdate(new Date());
			commiExprRule.setExpired("F");
			commiExprRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiExprRuleService.save(commiExprRule);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加提成公式失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, CommiExprRule commiExprRule){
		logger.debug("编辑提成公式规则开始");
		try{
			if(commiExprRule.getPk() == null){
				ServletUtils.outPrintFail(request, response, "编辑提成公式规则失败");
			}
			
			CommiExprRule cer = new CommiExprRule();
			cer = commiExprRuleService.get(CommiExprRule.class, commiExprRule.getPk());
			cer.setCustType(commiExprRule.getCustType());
			cer.setRole(commiExprRule.getRole());
			cer.setDepartment(commiExprRule.getDepartment());
			cer.setPrior(commiExprRule.getPrior());
			cer.setPreCommiExprPK(commiExprRule.getPreCommiExprPK());
			cer.setCheckCommiExprPK(commiExprRule.getCheckCommiExprPK());
			cer.setActionLog("Edit");
			cer.setLastUpdate(new Date());
			cer.setExpired("F");
			cer.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiExprRuleService.update(cer);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑提成公式失败");
		}
	}
	
	@RequestMapping("/doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response, CommiExprRule commiExprRule){
		logger.debug("提成公式规则删除开始");
		try{
			if(commiExprRule.getPk() == null){
				ServletUtils.outPrintFail(request, response, "提成公式规则删除失败");
			}
			
			CommiExprRule cer = new CommiExprRule();
			cer = commiExprRuleService.get(CommiExprRule.class, commiExprRule.getPk());
			cer.setActionLog("Edit");
			cer.setLastUpdate(new Date());
			cer.setExpired("T");
			cer.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiExprRuleService.update(cer);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "提成公式规则删除失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CommiExprRule commiExprRule){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		commiExprRuleService.findPageBySql(page, commiExprRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"计算公式规则_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
