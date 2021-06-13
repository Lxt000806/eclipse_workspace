package com.house.home.web.controller.commi;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.commi.BusinessCommiFloatRule;
import com.house.home.entity.commi.BusinessCommiRule;
import com.house.home.entity.commi.MainCommiRuleNew;
import com.house.home.service.commi.MainCommiRuleNewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/mainCommiRuleNew")
public class MainCommiRuleNewController extends BaseController{
	@Autowired
	private  MainCommiRuleNewService mainCommiRuleNewService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiRuleNewService.findPageBySql(page, mainCommiRuleNew);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goItemDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiRuleNewService.findItemDetailPageBySql(page, mainCommiRuleNew);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		return new ModelAndView("admin/commi/mainCommiRuleNew/mainCommiRuleNew_list")
			.addObject("mainCommiRuleNew", mainCommiRuleNew);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		return new ModelAndView("admin/commi/mainCommiRuleNew/mainCommiRuleNew_save")
				.addObject("mainCommiRuleNew", mainCommiRuleNew)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		if(StringUtils.isNotBlank(mainCommiRuleNew.getNo())){
			mainCommiRuleNew = this.mainCommiRuleNewService.get(MainCommiRuleNew.class, mainCommiRuleNew.getNo());
		}
		
		return new ModelAndView("admin/commi/mainCommiRuleNew/mainCommiRuleNew_update")
				.addObject("mainCommiRuleNew", mainCommiRuleNew)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		if(StringUtils.isNotBlank(mainCommiRuleNew.getNo())){
			mainCommiRuleNew = this.mainCommiRuleNewService.get(MainCommiRuleNew.class, mainCommiRuleNew.getNo());
		}
		
		return new ModelAndView("admin/commi/mainCommiRuleNew/mainCommiRuleNew_view")
				.addObject("mainCommiRuleNew", mainCommiRuleNew)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		return new ModelAndView("admin/commi/mainCommiRuleNew/mainCommiRuleNew_add")
				.addObject("mainCommiRuleNew", mainCommiRuleNew);
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) throws Exception {
		
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));

		return new ModelAndView("admin/commi/mainCommiRuleNew/mainCommiRuleNew_addUpdate")
				.addObject("map", jsonObject);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) {
		logger.debug("浮动规则新增");
		try {
			String detailJson = request.getParameter("detailJson");
			mainCommiRuleNew.setM_umState("A");
			mainCommiRuleNew.setDetailJson(detailJson);
			mainCommiRuleNew.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.mainCommiRuleNewService.doSave(mainCommiRuleNew);
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
			HttpServletResponse response, MainCommiRuleNew mainCommiRuleNew) {
		logger.debug("浮动规则新增");
		try {
			String detailJson = request.getParameter("detailJson");
			mainCommiRuleNew.setM_umState("M");
			mainCommiRuleNew.setDetailJson(detailJson);
			mainCommiRuleNew.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.mainCommiRuleNewService.doSave(mainCommiRuleNew);
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
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			MainCommiRuleNew mainCommiRuleNew){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		mainCommiRuleNewService.findPageBySql(page, mainCommiRuleNew);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"新主材提成规则_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
