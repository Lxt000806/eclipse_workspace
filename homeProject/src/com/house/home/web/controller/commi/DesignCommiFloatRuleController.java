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

import com.house.home.entity.commi.DesignCommiFloatRule;
import com.house.home.service.commi.DesignCommiFloatRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/designCommiFloatRule")
public class DesignCommiFloatRuleController extends BaseController{
	@Autowired
	private  DesignCommiFloatRuleService designCommiFloatRuleService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,DesignCommiFloatRule designCommiFloatRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		designCommiFloatRuleService.findPageBySql(page, designCommiFloatRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) throws Exception {
		
		return new ModelAndView("admin/commi/designCommiFloatRule/designCommiFloatRule_save")
				.addObject("designCommiFloatRule", designCommiFloatRule)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) throws Exception {
		
		if(designCommiFloatRule.getPk() != null){
			designCommiFloatRule = designCommiFloatRuleService.get(DesignCommiFloatRule.class, designCommiFloatRule.getPk());
		}
	
		return new ModelAndView("admin/commi/designCommiFloatRule/designCommiFloatRule_update")
				.addObject("designCommiFloatRule", designCommiFloatRule)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) throws Exception {
		
		if(designCommiFloatRule.getPk() != null){
			designCommiFloatRule = designCommiFloatRuleService.get(DesignCommiFloatRule.class, designCommiFloatRule.getPk());
		}
	
		return new ModelAndView("admin/commi/designCommiFloatRule/designCommiFloatRule_view")
				.addObject("designCommiFloatRule", designCommiFloatRule)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) throws Exception {
		
		return new ModelAndView("admin/commi/designCommiFloatRule/designCommiFloatRule_add")
				.addObject("designCommiFloatRule", designCommiFloatRule);
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) throws Exception {
		
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));

		return new ModelAndView("admin/commi/designCommiFloatRule/designCommiFloatRule_addUpdate")
				.addObject("map", jsonObject);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) {
		logger.debug("浮动规则新增");
		try {
			String detailJson = request.getParameter("detailJson");
			designCommiFloatRule.setM_umState("A");
			designCommiFloatRule.setDetailJson(detailJson);
			designCommiFloatRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.designCommiFloatRuleService.doSave(designCommiFloatRule);
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
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule) {
		logger.debug("浮动规则新增");
		try {
			String detailJson = request.getParameter("detailJson");
			designCommiFloatRule.setM_umState("M");
			designCommiFloatRule.setDetailJson(detailJson);
			designCommiFloatRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.designCommiFloatRuleService.doSave(designCommiFloatRule);
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
	public void doExpired(HttpServletRequest request,HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule){
		logger.debug("过期业务提成规则开始");
		try{
			if(designCommiFloatRule.getPk() != null){
				designCommiFloatRule = designCommiFloatRuleService.get(DesignCommiFloatRule.class, designCommiFloatRule.getPk());
				if(designCommiFloatRule != null){
					designCommiFloatRule.setExpired("T");
					designCommiFloatRule.setActionLog("EDIT");
					designCommiFloatRule.setLastUpdate(new Date());
					designCommiFloatRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					designCommiFloatRuleService.update(designCommiFloatRule);
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
			HttpServletResponse response, DesignCommiFloatRule designCommiFloatRule){
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = designCommiFloatRuleService.getFloatRuleSelection();
		
		return list;
	}
}
