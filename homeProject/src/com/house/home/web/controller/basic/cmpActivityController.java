package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.basic.cmpActivityGift;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.GiftApp;
import com.house.home.service.basic.cmpActivityService;

@Controller
@RequestMapping("/admin/cmpActivity")
public class cmpActivityController extends BaseController { 
	
	@Autowired
	private cmpActivityService cmpActivityService;
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, cmpActivity cmpactivity) throws Exception {
		return new ModelAndView("admin/basic/cmpActivity/cmpactivity_code").addObject("cmpactivity", cmpactivity);
	}
	
	@RequestMapping("/getCmpActivity")
	@ResponseBody
	public JSONObject getGiftApp(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		cmpActivity cmpactivity = cmpActivityService.get(cmpActivity.class, id);
		if(cmpactivity == null){
			return this.out("系统中不存在no="+id+"的活动信息", false);
		}
		return this.out(cmpactivity, true);
	}
		
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_list").addObject("abc", null);
	}
	/*
	 * 新增跳转cmpActivity表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	/*
	 *cmpActivity保存 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增公司活动管理");						
		cmpActivity cmpactivity = new cmpActivity();		
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_save")
			.addObject("cmpactivity", cmpactivity).addObject("czy", this.getUserContext(request).getCzybh());
	}
	/*
	 * 快速新增跳转cmpActivity表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	/*
	 *cmpActivity保存 
	 * */
	@RequestMapping("/goQuickSave")
	public ModelAndView goQuickSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到快速新增公司活动管理");						
		cmpActivity cmpactivity = new cmpActivity();		
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_quickSave")
			.addObject("cmpactivity", cmpactivity).addObject("czy", this.getUserContext(request).getCzybh());
	}
	/*
	 *cmpActivity编辑 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑公司活动管理");
		cmpActivity cmpactivity = null;
		if (StringUtils.isNotBlank(id)){
			cmpactivity = cmpActivityService.get(cmpActivity.class, id);
		}else{
			cmpactivity = new cmpActivity();
		}
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_update")
			.addObject("cmpactivity", cmpactivity).addObject("czy", this.getUserContext(request).getCzybh());
	}
	/**
	 *cmpActivity查看 
	 * 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看公司活动管理");
		cmpActivity cmpactivity = null;
		if (StringUtils.isNotBlank(id)){
			cmpactivity = cmpActivityService.get(cmpActivity.class, id);
		}else{
			cmpactivity = new cmpActivity();
		}
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_View")
			.addObject("cmpactivity", cmpactivity).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/**
	 *cmpActivity查看 
	 * 
	 * */
	@RequestMapping("/goActSupplier")
	public ModelAndView goActSupplier(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看公司活动管理");
		cmpActivity cmpactivity = null;
		if (StringUtils.isNotBlank(id)){
			cmpactivity = cmpActivityService.get(cmpActivity.class, id);
		}else{
			cmpactivity = new cmpActivity();
		}
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_actSupplier")
			.addObject("cmpactivity", cmpactivity).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goSupplAdd")
	public ModelAndView goSupplAdd(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看公司活动管理");
		cmpActivity cmpactivity =new cmpActivity();
		cmpactivity.setNo(id);
		return new ModelAndView("admin/basic/cmpActivity/cmpActivity_supplier_add")
			.addObject("cmpactivity", cmpactivity);
	}
	
	
	
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,cmpActivity cmpactivity) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);				
		cmpActivityService.findPageBySql(page, cmpactivity);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,cmpActivityGift cmpactivitygift) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cmpActivityService.findPageBySqlDetail(page, cmpactivitygift);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSupplierJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSupplierJqGrid(HttpServletRequest request,
			HttpServletResponse response,cmpActivityGift cmpactivitygift) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cmpActivityService.findSupplierPageBySql(page, cmpactivitygift);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 查询goJqGridDetail表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */	

	/**
	 *公司活动礼品明细保存 
	 *
	 */	
	@RequestMapping("/goadd")
	public ModelAndView goadd(HttpServletRequest request, HttpServletResponse response,cmpActivityGift cmpactivitygift){
		logger.debug("跳转到添加礼品明细页面");			
		return new ModelAndView("admin/basic/cmpActivity/cmpActivityGift_add")
			.addObject("cmpactivitygift", cmpactivitygift);
	}
	/**
	 *公司活动礼品明细编辑
	 *
	 */	
	@RequestMapping("/goaddUpdate")
	public ModelAndView goaddUpdate(HttpServletRequest request, HttpServletResponse response,cmpActivityGift cmpactivitygift){
		logger.debug("跳转到添加礼品明细编辑");			
		return new ModelAndView("admin/basic/cmpActivity/cmpActivityGift_Update")
			.addObject("cmpactivitygift", cmpactivitygift);
	}
	
	/**
	 *公司活动礼品明细编辑
	 *
	 */	
	@RequestMapping("/goaddView")
	public ModelAndView goaddView(HttpServletRequest request, HttpServletResponse response,cmpActivityGift cmpactivitygift){
		logger.debug("跳转到添加礼品明细查看");			
		return new ModelAndView("admin/basic/cmpActivity/cmpActivityGift_View")
			.addObject("cmpactivitygift", cmpactivitygift);
	}
	/**
	 *公司活动礼品保存 
	 *
	 */
	@RequestMapping("/docmpActivitySave")
	public void docmpActivitySave(HttpServletRequest request,HttpServletResponse response,cmpActivity cmpactivity){
		logger.debug("公司活动礼品新增开始");		
		try {
			cmpactivity.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			cmpactivity.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			cmpactivity.setLastUpdate(new Date());
			cmpactivity.setM_umState("A");
			cmpactivity.setExpired("F"); 			
			cmpActivity iDescr = this.cmpActivityService.getByDescr(cmpactivity.getDescr());			
			if (iDescr!=null){
				ServletUtils.outPrintFail(request, response, "活动名称已存在！");
				return;
			}	
			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}		
			Result result = this.cmpActivityService.docmpActivitySave(cmpactivity);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "公司活动新增失败");
		}
	}
	/**
	 *公司活动礼品编辑 
	 *
	 */
	@RequestMapping("/docmpActivityUpdate")
	public void docmpActivityUpdate(HttpServletRequest request,HttpServletResponse response,cmpActivity cmpactivity){
		logger.debug("公司活动礼品编辑开始");		
		try {
			cmpactivity.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			cmpactivity.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			cmpactivity.setLastUpdate(new Date());
			cmpactivity.setM_umState("M");	
			if ("F".equals(cmpactivity.getExpired())){
				cmpactivity.setExpired("F");
					
			}else {
				cmpactivity.setExpired("T");
			}
	
			cmpActivity iDescr = this.cmpActivityService.getByDescrUpdate(cmpactivity.getDescr(),cmpactivity.getDescr1());			
			if (iDescr!=null){
				ServletUtils.outPrintFail(request, response, "活动名称已存在！");
				return;
			}	
			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}		
			Result result = this.cmpActivityService.docmpActivitySave(cmpactivity);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "公司活动新增失败");
		}
	}
	
	@RequestMapping("/doSaveSuppl")
	public void doSaveSuppl(HttpServletRequest request,HttpServletResponse response, String no,String code,String type){
		logger.debug("公司活动礼品编辑开始");		
		try {
			if(!cmpActivityService.existSuppl(no,code)){
				ServletUtils.outPrintFail(request, response,"该活动已存在此供应商");
				return;
			}
			this.cmpActivityService.doSaveSuppl(no,code,type,this.getUserContext(request).getCzybh());
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doDelSuppl")
	public void doDelSuppl(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("公司活动礼品编辑开始");		
		try {
			this.cmpActivityService.doDelSuppl(pk);
			ServletUtils.outPrintFail(request, response, "保存成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, cmpActivity cmpActivity){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);		
		cmpActivityService.findPageBySql(page, cmpActivity);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"公司活动管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
