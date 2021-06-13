package com.house.home.web.controller.finance;

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

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.finance.GiftCheckOutService;

@Controller
@RequestMapping("/admin/giftCheckOut")
public class GiftCheckOutController extends BaseController {
	
	@Autowired
	public GiftCheckOutService giftCheckOutService;
	
	/**
	 * 主页面查看
	 * @param request 
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftCheckOut giftCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftCheckOutService.findPageBySql(page, giftCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/getIssueDetailJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getIssueDetailJqgrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftCheckOutService.findIssueDetailPageBySql(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftCheckOutService.findDetailPageBySql(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetail_depaJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetail_depaJqGrid(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftCheckOutService.findDetail_depaPageBySql(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetail_custDepaJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetail_custDepaJqGrid(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftCheckOutService.findDetail_custDepaPageBySql(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAppJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp,GiftCheckOut giftCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftCheckOutService.findAppPageBySql(page, giftApp,giftCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{

		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_list");
			
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response,GiftCheckOut giftCheckOut) throws Exception{
		
		Employee employee=giftCheckOutService.get(Employee.class, this.getUserContext(request).getCzybh());
		giftCheckOut.setStatus("1");
		giftCheckOut.setAppCzy(this.getUserContext(request).getCzybh());
		giftCheckOut.setDate(new Date());
		giftCheckOut.setAppCzyDescr(employee.getNameChi());
		
		
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_save").addObject("giftCheckOut",giftCheckOut);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView Update(HttpServletRequest request ,
			HttpServletResponse response,GiftCheckOut giftCheckOut ) throws Exception{
		Employee employee2=new Employee();
		Employee employee=giftCheckOutService.get(Employee.class, this.getUserContext(request).getCzybh());
		WareHouse wareHouse=new WareHouse();
		
		GiftCheckOut gco=null;
		if(giftCheckOut.getNo()!=null){
			gco=giftCheckOutService.get(GiftCheckOut.class, giftCheckOut.getNo());
			if(employee!=null){
				gco.setConfirmCzyDescr(employee.getNameChi()==null?"":employee.getNameChi());
			}
			
			if(StringUtils.isNotBlank(gco.getAppCzy())){
				employee2=giftCheckOutService.get(Employee.class, gco.getAppCzy());
				if(employee!=null){
					gco.setAppCzyDescr(employee2.getNameChi()==null?"":employee2.getNameChi());
				}
			}
			if(StringUtils.isNotBlank(gco.getWhCode())){
				wareHouse=giftCheckOutService.get(WareHouse.class, gco.getWhCode());
				gco.setWhCodeDescr(wareHouse.getDesc1());
			}
		}
	
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_update").addObject("giftCheckOut",gco);
			
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,GiftCheckOut giftCheckOut ) throws Exception{
		Employee employee2=new Employee();
		Employee employee=giftCheckOutService.get(Employee.class, this.getUserContext(request).getCzybh());
		WareHouse wareHouse=new WareHouse();
		GiftCheckOut gco=null;
		if(giftCheckOut.getNo()!=null){
			gco=giftCheckOutService.get(GiftCheckOut.class, giftCheckOut.getNo());
			gco.setConfirmCzyDescr(employee.getNameChi()==null?"":employee.getNameChi());
			if(StringUtils.isNotBlank(gco.getAppCzy())){
				employee2= giftCheckOutService.get(Employee.class,gco.getAppCzy());
				gco.setAppCzyDescr(employee2.getNameChi()==null?"":employee2.getNameChi());
			}
			if(StringUtils.isNotBlank(gco.getWhCode())){
				wareHouse=giftCheckOutService.get(WareHouse.class, gco.getWhCode());
				gco.setWhCodeDescr(wareHouse.getDesc1());
			}
		}
			
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_view").addObject("giftCheckOut",gco);
	}
	
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request ,
			HttpServletResponse response ,GiftCheckOut giftCheckOut) throws Exception{
		Employee employee2=new Employee();
		Employee employee=giftCheckOutService.get(Employee.class, this.getUserContext(request).getCzybh());
		WareHouse wareHouse=new WareHouse();

		GiftCheckOut gco=null;
		if(giftCheckOut.getNo()!=null){
			gco=giftCheckOutService.get(GiftCheckOut.class, giftCheckOut.getNo());
			gco.setConfirmCzy(this.getUserContext(request).getCzybh());
			gco.setConfirmCzyDescr(employee.getNameChi()==null?"":employee.getNameChi());
			if(StringUtils.isNotBlank(gco.getAppCzy())){
				employee2= giftCheckOutService.get(Employee.class,gco.getAppCzy());
				gco.setAppCzyDescr(employee2.getNameChi()==null?"":employee2.getNameChi());
			}
			if(StringUtils.isNotBlank(gco.getWhCode())){
				wareHouse=giftCheckOutService.get(WareHouse.class, gco.getWhCode());
				gco.setWhCodeDescr(wareHouse.getDesc1());
			}
			gco.setCheckDate(new Date());
			gco.setConfirmDate(new Date());
			gco.setDate(new Date());

		}
			
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_check").addObject("giftCheckOut",gco);
	}
	
	@RequestMapping("/goReCheck")
	public ModelAndView goReCheck(HttpServletRequest request ,
			HttpServletResponse response ,GiftCheckOut giftCheckOut) throws Exception{
		Employee employee2=new Employee();
		Employee employee=giftCheckOutService.get(Employee.class, this.getUserContext(request).getCzybh());
		WareHouse wareHouse=new WareHouse();

		GiftCheckOut gco=null;
		if(giftCheckOut.getNo()!=null){
			gco=giftCheckOutService.get(GiftCheckOut.class, giftCheckOut.getNo());
			gco.setConfirmCzyDescr(employee.getNameChi()==null?"":employee.getNameChi());
			if(StringUtils.isNotBlank(gco.getAppCzy())){
				employee2= giftCheckOutService.get(Employee.class,gco.getAppCzy());
				gco.setAppCzyDescr(employee2.getNameChi()==null?"":employee2.getNameChi());
			}
			if(StringUtils.isNotBlank(gco.getWhCode())){
				wareHouse=giftCheckOutService.get(WareHouse.class, gco.getWhCode());
				gco.setWhCodeDescr(wareHouse.getDesc1());
			}
		}
			
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_reCheck").addObject("giftCheckOut",gco);
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request ,
			HttpServletResponse response,String arr,String whCode ) throws Exception{
		GiftCheckOut giftCheckOut=new GiftCheckOut();
		giftCheckOut.setAllDetail(arr);
		giftCheckOut.setWhCode(whCode);
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_add")
		.addObject("giftCheckOut",giftCheckOut);
	}	
	
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request ,
			HttpServletResponse response ,String no) throws Exception{
		
		return new ModelAndView("admin/finance/giftCheckOut/giftCheckOut_print").addObject("no", no);
			
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			GiftCheckOut giftCheckOut ){
		logger.debug("礼品出库记账新增开始");
		try {
			giftCheckOut.setTemp(1);
			giftCheckOut.setLastUpdate(new Date());
			giftCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftCheckOut.setExpired("F");
			giftCheckOut.setActionLog("Add");
			String detailJson = request.getParameter("detailJson");
			giftCheckOut.setDetailJson(detailJson);
			Result result =this.giftCheckOutService.saveGiftCheckOut(giftCheckOut) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "出库记账新增失败");
		}
	}
	
	/**
	 * 礼品出库记账
	 * 
	 * */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			GiftCheckOut giftCheckOut ){
		logger.debug("礼品出库记账新增开始");
		try {
			giftCheckOut.setTemp(2);
			giftCheckOut.setLastUpdate(new Date());
			giftCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			giftCheckOut.setDetailJson(detailJson);
			Result result =this.giftCheckOutService.doUpdate(giftCheckOut) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"编辑成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "礼品出库记账编辑失败");
		}
	}
	
	/**
	 * 审核通过 
	 * 
	 * 
	 */
	@RequestMapping("/doCheck")
	public void doCheck(HttpServletRequest request,HttpServletResponse response,
			GiftCheckOut giftCheckOut ){
		logger.debug("礼品出库记账新增开始");
		try {
			GiftCheckOut gco=giftCheckOutService.get(GiftCheckOut.class, giftCheckOut.getNo());
			
			giftCheckOut.setStatus("2");
			giftCheckOut.setLastUpdate(new Date());
			giftCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftCheckOut.setConfirmDate(new Date());
			giftCheckOut.setCheckDate(new Date());
			giftCheckOut.setDate(gco.getDate());
			this.giftCheckOutService.update(giftCheckOut);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "礼品出库记账编辑失败");
		}
	}
	
	/**
	 * 审核通取消
	 * 
	 * 
	 */
	@RequestMapping("/doCheckCancel")
	public void doCheckCancel(HttpServletRequest request,HttpServletResponse response,
			GiftCheckOut giftCheckOut ){
		logger.debug("礼品出库记账新增开始");
		try {
			giftCheckOut.setTemp(3);
			giftCheckOut.setLastUpdate(new Date());
			giftCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftCheckOut.setConfirmCzy(this.getUserContext(request).getCzybh());
			giftCheckOut.setConfirmDate(new Date());
			String detailJson = request.getParameter("detailJson");
			giftCheckOut.setDetailJson(detailJson);
			Result result =this.giftCheckOutService.doCheckCancel(giftCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "礼品出库记账编辑失败");
		}
	}
	
	/**
	 * 反审核 
	 * 
	 * 
	 */				
	@RequestMapping("/doReturnCheck")
	public void doReturnCheck(HttpServletRequest request,HttpServletResponse response,
			GiftCheckOut giftCheckOut ){
		logger.debug("礼品出库记账新增开始");
		try {
			giftCheckOut.setTemp(4);
			giftCheckOut.setLastUpdate(new Date());
			giftCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftCheckOut.setConfirmCzy(this.getUserContext(request).getCzybh());
			giftCheckOut.setConfirmDate(new Date());
			String detailJson = request.getParameter("detailJson");
			giftCheckOut.setDetailJson(detailJson);
			Result result =	this.giftCheckOutService.doReturnCheck(giftCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "礼品出库记账编辑失败");
		}
	}
	
	
}
