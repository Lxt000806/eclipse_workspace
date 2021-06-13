package com.house.home.web.controller.commi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.commi.BusinessCommiFloatRule;
import com.house.home.service.commi.BusinessCommiFloatRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/businessCommiFloatRule")
public class BusinessCommiFloatRuleController extends BaseController{
	@Autowired
	private  BusinessCommiFloatRuleService businessCommiFloatRuleService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,BusinessCommiFloatRule businessCommiFloatRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		businessCommiFloatRuleService.findPageBySql(page, businessCommiFloatRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) throws Exception {
		
		return new ModelAndView("admin/commi/businessCommiFloatRule/businessCommiFloatRule_save")
				.addObject("businessCommiFloatRule", businessCommiFloatRule)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) throws Exception {
		
		if(businessCommiFloatRule.getPk() != null){
			businessCommiFloatRule = businessCommiFloatRuleService.get(BusinessCommiFloatRule.class, businessCommiFloatRule.getPk());
		}
	
		return new ModelAndView("admin/commi/businessCommiFloatRule/businessCommiFloatRule_update")
				.addObject("businessCommiFloatRule", businessCommiFloatRule)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) throws Exception {
		
		if(businessCommiFloatRule.getPk() != null){
			businessCommiFloatRule = businessCommiFloatRuleService.get(BusinessCommiFloatRule.class, businessCommiFloatRule.getPk());
		}
	
		return new ModelAndView("admin/commi/businessCommiFloatRule/businessCommiFloatRule_view")
				.addObject("businessCommiFloatRule", businessCommiFloatRule)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) throws Exception {
		
		return new ModelAndView("admin/commi/businessCommiFloatRule/businessCommiFloatRule_add")
				.addObject("businessCommiFloatRule", businessCommiFloatRule);
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) throws Exception {
		
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));

		return new ModelAndView("admin/commi/businessCommiFloatRule/businessCommiFloatRule_addUpdate")
				.addObject("map", jsonObject);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) {
		logger.debug("浮动规则新增");
		try {
			String detailJson = request.getParameter("detailJson");
			businessCommiFloatRule.setM_umState("A");
			businessCommiFloatRule.setDetailJson(detailJson);
			businessCommiFloatRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.businessCommiFloatRuleService.doSave(businessCommiFloatRule);
			if (result.isSuccess()) {
				ServletUtils.outPrint(request, response, true, "保存成功", null, true);
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule) {
		logger.debug("浮动规则新增");
		try {
			String detailJson = request.getParameter("detailJson");
			businessCommiFloatRule.setM_umState("M");
			businessCommiFloatRule.setDetailJson(detailJson);
			businessCommiFloatRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.businessCommiFloatRuleService.doSave(businessCommiFloatRule);
			if (result.isSuccess()) {
				ServletUtils.outPrint(request, response, true, "编辑成功", null, true);
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doExpired")
	public void doExpired(HttpServletRequest request,HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule){
		logger.debug("过期业务提成规则开始");
		try{
			if(businessCommiFloatRule.getPk() != null){
				businessCommiFloatRule = businessCommiFloatRuleService.get(BusinessCommiFloatRule.class, businessCommiFloatRule.getPk());
				if(businessCommiFloatRule != null){
					businessCommiFloatRule.setExpired("T");
					businessCommiFloatRule.setActionLog("EDIT");
					businessCommiFloatRule.setLastUpdate(new Date());
					businessCommiFloatRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					businessCommiFloatRuleService.update(businessCommiFloatRule);
				}
			} else {
				ServletUtils.outPrintFail(request, response, "过期业务提成规则失败");
			}
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "过期设计提成规则失败");
		}
	}
	
	@RequestMapping("/getFloatRuleSelection")
	@ResponseBody
	public List<Map<String, Object>> getFloatRuleSelection(HttpServletRequest request ,
			HttpServletResponse response, BusinessCommiFloatRule businessCommiFloatRule){
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = businessCommiFloatRuleService.getFloatRuleSelection();
		
		return list;
	}
}
