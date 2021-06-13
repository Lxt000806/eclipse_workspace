package com.house.home.client.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustProblemEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustProblemResp;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustComplaint;
import com.house.home.entity.project.CustProblem;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.project.CustProblemService;

@RequestMapping("/client/custProblem")
@Controller
public class ClientCustProblemController extends ClientBaseController{

	@Autowired
	private CustProblemService custProblemService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private CzybmService czybmService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustProblemList")
	public void getCustProblemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BasePageQueryResp<CustProblemResp> respon=new BasePageQueryResp<CustProblemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustProblemEvt)JSONObject.toBean(json,CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			UserContext uc = this.getUserContext(request);
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());

			CustProblem custProblem = new CustProblem();
			custProblem.setAppQuery("1");
			custProblem.setStatus(evt.getStatus());
			custProblem.setAppQueryType(evt.getType());
			page.setPageOrderBy("infoDate");
			page.setPageOrder("desc");
			
			custProblemService.findPageBySql(page, custProblem, uc);
			List<CustProblemResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustProblemResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustProblemDtl")
	public void getCustProblemDtl(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BasePageQueryResp<CustProblemResp> respon=new BasePageQueryResp<CustProblemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustProblemEvt)JSONObject.toBean(json,CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());

			CustProblem custProblem = new CustProblem();
			Customer customer = new Customer();
			Employee employee = new Employee();
			Employee dealCzy = new Employee();
			Map<String, Object> data = new HashMap<String, Object>();
			Xtdm xtdm = null;
			CustComplaint custComplaint = new CustComplaint();
			
			if(evt.getPk() != null && evt.getPk() > 0){
				boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0835", "转售后");

				custProblem = custProblemService.get(CustProblem.class, evt.getPk());
				data.put("pk", evt.getPk());
				data.put("authByCzybh", hasAuthByCzybh);
				if(custProblem != null){
					custComplaint = custProblemService.get(CustComplaint.class, custProblem.getNo());
					if(custComplaint != null){
						customer = custProblemService.get(Customer.class, custComplaint.getCustCode());
						data.put("infoDate", custProblem.getInfoDate());
						data.put("remarks", custComplaint.getRemarks());
						data.put("dealRemarks", custProblem.getDealRemarks());
						xtdm = xtdmService.getByIdAndCbm("PROMSTATUS", custProblem.getStatus());
						data.put("status", custProblem.getStatus());
						data.put("statusDescr", xtdm.getNote());
						if(StringUtils.isNotBlank(custProblem.getDealCZY())){
							dealCzy = custProblemService.get(Employee.class, custProblem.getDealCZY());
							data.put("dealCzyDescr", dealCzy.getNameChi());
						}
						if(customer != null){
							data.put("custName", customer.getDescr());
							data.put("address", customer.getAddress());
							data.put("custCheckDate", customer.getCheckOutDate());
							data.put("custPhone", StringUtils.isNotBlank(customer.getMobile1())?customer.getMobile1():customer.getMobile2());

							if(StringUtils.isNotBlank(customer.getProjectMan())){
								employee = custProblemService.get(Employee.class, customer.getProjectMan());
								data.put("projectPhone", employee.getPhone());
								data.put("projectManDescr", employee.getNameChi());
							}
							xtdm = xtdmService.getByIdAndCbm("CUSTOMERSTATUS", customer.getStatus());
							data.put("custStatus", xtdm.getNote());
						}
					}
				}
			}
			
			returnJson(data,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getToCustService")
	public void getToCustService(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BasePageQueryResp<CustProblemResp> respon=new BasePageQueryResp<CustProblemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustProblemEvt)JSONObject.toBean(json,CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());

			CustProblem custProblem = new CustProblem();
			Customer customer = new Customer();
			Employee employee = new Employee();
			Department2 department2 = new Department2();
			Map<String, Object> data = new HashMap<String, Object>();
			Xtdm xtdm = null;
			CustComplaint custComplaint = new CustComplaint();
			
			if(evt.getPk() != null && evt.getPk() > 0){
				custProblem = custProblemService.get(CustProblem.class, evt.getPk());
				data.put("pk", evt.getPk());
				
				if(custProblem != null){
					custComplaint = custProblemService.get(CustComplaint.class, custProblem.getNo());
					if(custComplaint != null){
						data.put("no", custComplaint.getNo());
						data.put("repEmp", this.getUserContext(request).getEmnum());
						data.put("repEmpDescr", this.getUserContext(request).getZwxm());
						data.put("repDate", DateUtil.DateToString(new Date(), "yyyy-MM-dd hh:mm:ss"));
						data.put("statusDescr", "1  待处理");
						data.put("status", "1");
						data.put("remarks", custComplaint.getRemarks());
						customer = custProblemService.get(Customer.class, custComplaint.getCustCode());
						if(customer != null){
							data.put("custCode", customer.getCode());
							data.put("custDescr", customer.getDescr());
							data.put("address", customer.getAddress());
							data.put("mobile", StringUtils.isNotBlank(customer.getMobile1())?customer.getMobile1():customer.getMobile2());
							data.put("checkOutDate", DateUtil.DateToString(customer.getCustCheckDate(), "yyyy-MM-dd hh:mm:ss"));
							data.put("consEndDate", DateUtil.DateToString(customer.getConsEndDate(), "yyyy-MM-dd hh:mm:ss"));
							
							employee = custProblemService.get(Employee.class, customer.getProjectMan());
							if(employee != null){
								data.put("projectMan", employee.getNumber());
								data.put("projectManDescr", employee.getNameChi());
								xtdm = xtdmService.getByIdAndCbm("EMPSTS",employee.getStatus());
								if(xtdm != null){
									data.put("empStatus",xtdm.getNote());
									data.put("projectManDescr", employee.getNameChi()+"("+xtdm.getNote()+")");
								}
								department2 = custProblemService.get(Department2.class, employee.getDepartment2()); 
								
								if(department2 != null){
									data.put("deptDescr", department2.getDesc1());
								}
							}
						}
					}
				}
			}
			
			returnJson(data,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doComplete")
	public void doSaveComment(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (CustProblemEvt) JSONObject.toBean(json, CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			CustProblem custProblem = new CustProblem();
			String no = ""; 
			String dealRemarks = "";
			if(StringUtils.isNotBlank(evt.getDealRemarks())){
				dealRemarks = DateUtil.DateToString(new Date(),"MM-dd")+this.getUserContext(request).getZwxm()+"反馈"+evt.getDealRemarks();
			}
	    	if(evt.getPk() != null){
	    		custProblem = custProblemService.get(CustProblem.class, evt.getPk());
	    		if(custProblem != null){
	    			if(!"2".endsWith(custProblem.getStatus())){
	    				respon.setReturnCode("400001");
	    				respon.setReturnInfo("该投诉单不是正在处理状态，无法进行完成操作");
	    				returnJson(respon, response, msg, respon, request,interfaceLog);
	    				return;
	    			} else {
		    			no = custProblem.getNo();
		    			evt.setDealRemarks(dealRemarks+"\r\n"+custProblem.getDealRemarks());
		    			custProblemService.dealCustCompaint(no, evt.getPk(), evt.getDealDate(), evt.getDealRemarks());
	    			}
	    		}
	    	}
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateDealRemarks")
	public void doUpdateDealRemarks(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (CustProblemEvt) JSONObject.toBean(json, CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			CustProblem custProblem = new CustProblem();
			String dealRemarks = "";
			if(StringUtils.isNotBlank(evt.getDealRemarks())){
				dealRemarks = DateUtil.DateToString(new Date(),"MM-dd")+this.getUserContext(request).getZwxm()+"反馈"+evt.getDealRemarks();
			}
			
	    	if(evt.getPk() != null){
	    		custProblem = custProblemService.get(CustProblem.class, evt.getPk());
	    		if(custProblem != null && StringUtils.isNotBlank(evt.getDealRemarks())){
	    			custProblem.setDealRemarks(dealRemarks+"\r\n"+custProblem.getDealRemarks());
	    			custProblem.setLastUpdate(new Date());
	    			custProblem.setActionLog("Edit");
	    			custProblem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
	    			custProblemService.update(custProblem);
	    		}
	    	}
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/doReceive")
	public void doReceive(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (CustProblemEvt) JSONObject.toBean(json, CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			CustProblem custProblem = new CustProblem();
	    	if(evt.getPk() != null){
	    		custProblem = custProblemService.get(CustProblem.class, evt.getPk());
	    		if(custProblem != null){
	    			if(!"0".endsWith(custProblem.getStatus())){
	    				respon.setReturnCode("400001");
	    				respon.setReturnInfo("该投诉单不是待处理状态，无法接收");
	    				returnJson(respon, response, msg, respon, request,interfaceLog);
	    				return;
	    			}
	    			if(evt.getRcvDate() == null){
	    				respon.setReturnCode("400001");
	    				respon.setReturnInfo("接收时间为空，接收失败");
	    				returnJson(respon, response, msg, respon, request,interfaceLog);
	    				return;
	    			}
	    			if(StringUtils.isNotBlank(evt.getEmpNum())){
	    				respon.setReturnCode("400001");
	    				respon.setReturnInfo("接收人为空，接收失败");
	    				returnJson(respon, response, msg, respon, request,interfaceLog);
	    				return;
	    			}
	    			
	    			custProblem.setRcvDate(evt.getRcvDate());
	    			custProblem.setDealCZY(evt.getEmpNum());
    				custProblem.setLastUpdate(new Date());
    				custProblem.setActionLog("Edit");
    				custProblem.setStatus("2");
    				custProblem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
    				custProblemService.update(custProblem);
	    		}
	    	}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/doToCustService")
	public void doToCustService(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustProblemEvt evt=new CustProblemEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (CustProblemEvt) JSONObject.toBean(json, CustProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			custProblemService.doToCustService(evt,this.getUserContext(request));
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
