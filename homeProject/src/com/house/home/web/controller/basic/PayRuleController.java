package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.PayRule;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.basic.PayRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/payRule")
public class PayRuleController extends BaseController{
	@Autowired
	private  PayRuleService payRuleService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,PayRule payRule) throws Exception{
		
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		payRuleService.findPageBySql(page, payRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getDetailJqGrid(HttpServletRequest request, 
			HttpServletResponse response,PayRule payRule) throws Exception{
		
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		payRuleService.findDetailPageBySql(page, payRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,PayRule payRule) throws Exception{
		return new ModelAndView("admin/basic/payRule/payRule_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response,PayRule payRule) throws Exception{
		PayRule pr=new PayRule();
		if(StringUtils.isNotBlank(payRule.getNo())){
			pr=payRuleService.get(PayRule.class, payRule.getNo());
			pr.setCustType(pr.getCustType().trim());
		}
		pr.setM_umState(payRule.getM_umState());
		return new ModelAndView("admin/basic/payRule/payRule_save").addObject("payRule",pr);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response,PayRule payRule) throws Exception{
		PayRule pr=new PayRule();
		if(StringUtils.isNotBlank(payRule.getNo())){
			pr=payRuleService.get(PayRule.class, payRule.getNo());
			pr.setCustType(pr.getCustType().trim());
		}
		return new ModelAndView("admin/basic/payRule/payRule_update").addObject("payRule",pr);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,PayRule payRule) throws Exception{
		PayRule pr=new PayRule();
		if(StringUtils.isNotBlank(payRule.getNo())){
			pr=payRuleService.get(PayRule.class, payRule.getNo());
			pr.setCustType(pr.getCustType().trim());
		}
		return new ModelAndView("admin/basic/payRule/payRule_view").addObject("payRule",pr);
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request ,
			HttpServletResponse response,Integer payNum,String arr) throws Exception{
		return new ModelAndView("admin/basic/payRule/payRule_add").addObject("payNum", payNum).addObject("paynums",arr);
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request ,
			HttpServletResponse response,String arr) throws Exception{
		String detailXml="";
		detailXml= request.getParameter("detailXml");
		//Map<String , Object> map =(Map<String, Object>)request.getParameter("detailXml");
		return new ModelAndView("admin/basic/payRule/payRule_addUpdate")
		.addObject("detailXml", detailXml).addObject("paynums", arr);
	}
	
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request ,
			HttpServletResponse response) throws Exception{
		String detailXml="";
		detailXml= request.getParameter("detailXml");
		return new ModelAndView("admin/basic/payRule/payRule_addView")
		.addObject("detailXml", detailXml);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,PayRule payRule){
		logger.debug("转盘新增保存");
		try {
			payRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			payRule.setM_umState("A");
			String detailJson = request.getParameter("detailJson");
			payRule.setDetailJson(detailJson);
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "规则表无明细");
				return;
			}
			Result result = this.payRuleService.doSave(payRule);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增付款规则失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,PayRule payRule){
		logger.debug("付款规则修改");
		try {
			payRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			payRule.setM_umState("M");
			String detailJson = request.getParameter("detailJson");
			payRule.setDetailJson(detailJson);
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "规则表无明细");
				return;
			}
			Result result = this.payRuleService.doSave(payRule);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改付款规则失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			PayRule payRule){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		payRuleService.findPageBySql(page, payRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"付款规则明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
}
















