package com.house.home.web.controller.project;

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
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.project.CustComplaint;
import com.house.home.entity.project.CustWorker;
import com.house.home.service.design.CustomerService;
import com.house.home.service.query.CustComplaintService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/custComplaint")
public class CustComplaintController extends BaseController{
	@Autowired
	private  CustComplaintService custComplaintService;
	@Autowired
	private CustomerService customerService;
	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustComplaint custComplaint) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String czybh=this.getUserContext(request).getCzybh();
		if(StringUtils.isNotBlank(request.getParameter("openComponent_employee_crtCZY"))
				&&StringUtils.isBlank(custComplaint.getCrtCZY())){
			custComplaint.setCrtCZY(request.getParameter("openComponent_employee_crtCZY"));
		}
		custComplaintService.findPageBySql(page, custComplaint);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustComplaint custComplaint) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String czybh=this.getUserContext(request).getCzybh();
		custComplaintService.findDetailPageBySql(page, custComplaint);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goComplaintDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goComplaintDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustComplaint custComplaint) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String czybh=this.getUserContext(request).getCzybh();
		custComplaintService.findComplaintDetailPageBySql(page, custComplaint);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response , CustComplaint custComplaint) throws Exception{
		logger.debug("客户投诉管理");
		
		
		return new ModelAndView("admin/project/custComplaint/custComplaint_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response , CustComplaint custComplaint) throws Exception{
		logger.debug("客户投诉管理");
		this.getUserContext(request).getCzybh();
		this.getUserContext(request).getZwxm();
		
		return new ModelAndView("admin/project/custComplaint/custComplaint_save")
		.addObject("uc", this.getUserContext(request))
		.addObject("crtDate", new Date());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response , String no) throws Exception{
		logger.debug("客户投诉管理");
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		CustComplaint custComplaint =new CustComplaint();
		custComplaint=custComplaintService.get(CustComplaint.class, no);
		Map<String , Object> map =custComplaintService.findPageBySql(page, custComplaint).getResult().get(0);
		System.out.println(map.get("status"));
		return new ModelAndView("admin/project/custComplaint/custComplaint_update").addObject("map", map)
				.addObject("uc", this.getUserContext(request));
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response , String no) throws Exception{
		logger.debug("客户投诉管理");
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);

		CustComplaint custComplaint =new CustComplaint();
		custComplaint=custComplaintService.get(CustComplaint.class, no);
		Map<String , Object> map =custComplaintService.findPageBySql(page, custComplaint).getResult().get(0);
		
		
		return new ModelAndView("admin/project/custComplaint/custComplaint_view").addObject("map", map);
	}

	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("客户投诉管理");
		return new ModelAndView("admin/project/custComplaint/custComplaint_detail");
	}

	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request ,
			HttpServletResponse response , String no) throws Exception{
		logger.debug("客户投诉管理");
		return new ModelAndView("admin/project/custComplaint/custComplaint_add").addObject("crtDate", new Date());
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("客户投诉管理");
		Map<String , String[]> map2 =request.getParameterMap();
		return new ModelAndView("admin/project/custComplaint/custComplaint_addUpdate").addObject("map", map2);
	}
	
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("客户投诉管理");
		Map<String , String[]> map2 =request.getParameterMap();
		return new ModelAndView("admin/project/custComplaint/custComplaint_addView").addObject("map", map2);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			CustComplaint custComplaint){
		logger.debug("客户投诉管理新增");
		try {
			custComplaint.setM_umState("A");
			custComplaint.setCrtCZY(this.getUserContext(request).getCzybh());
			custComplaint.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custComplaint.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			custComplaint.setDetailJson(detailJson);
			Result result =this.custComplaintService.doSave(custComplaint) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "客户投诉新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			CustComplaint custComplaint){
		logger.debug("客户投诉管理编辑");
		try {
			if(StringUtils.isBlank(custComplaint.getExpired())){
				custComplaint.setExpired("F");
			}
			custComplaint.setM_umState("M");
			custComplaint.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			custComplaint.setDetailJson(detailJson);
			Result result =this.custComplaintService.doUpdate(custComplaint) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "客户投诉编辑失败");
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/getCustInfo")
	public Map<String , Object> getCustInfo(HttpServletRequest request ,
			HttpServletResponse response , String custCode){
		
		return custComplaintService.getCustInfo(custCode);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustComplaint custComplaint){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		custComplaintService.findPageBySql(page, custComplaint);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"客户投诉管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doComplaintDetailExcel")
	public void doComplaintDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			CustComplaint custComplaint){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		custComplaintService.findComplaintDetailPageBySql(page, custComplaint);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"客户投诉管理明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
