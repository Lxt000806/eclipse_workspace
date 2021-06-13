package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.DoUpdatePrjProblemEvt;
import com.house.home.client.service.evt.IntegrateBulletinBoardEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.PersonMessageDetailResp;
import com.house.home.client.service.resp.IntegrateBulletinBoardResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.PrjProblem;
import com.house.home.service.basic.SignInService;
import com.house.home.service.project.IntegrateBulletinBoardService;
import com.house.home.service.project.PrjJobService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.project.WorkerService;

@RequestMapping("/client/integrateBulletinBoard")
@Controller
public class ClientIntegrateBulletinBoardController extends ClientBaseController {
	
	@Autowired
	private IntegrateBulletinBoardService integrateBulletinBoardService;
	@Autowired
	private PrjJobService prjJobService;
	@Autowired
	private SignInService signInService;
	@Autowired
	private WorkerService workerService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDepartmentList")
	public void getDepartmentList(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt=new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			
			UserContext uc = getUserContext(request);
			Page page = new Page();
			page.setPageNo(1);
			page.setPageSize(100);
			integrateBulletinBoardService.getDepartmentList(page, uc);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getIntegrateBulletinBoardCountInfo")
	public void getIntegrateBulletinBoardCountInfo(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt=new IntegrateBulletinBoardEvt();
		IntegrateBulletinBoardResp respon=new IntegrateBulletinBoardResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			
			Map<String, Object> IntegrateBulletinBoardConutInfo = integrateBulletinBoardService.getIntegrateBulletinBoardCountInfo(evt);
			BeanConvertUtil.mapToBean(IntegrateBulletinBoardConutInfo, respon);
		
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDesigningList")
	public void getDesigningList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getDesigningList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getDesignedList")
	public void getDesignedList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getDesignedList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getProductionList")
	public void getProductionList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getProductionList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getShippedList")
	public void getShippedList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getShippedList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getInstallingList")
	public void getWaitConfirmPrjProblemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getInstallingList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getInstalledList")
	public void getInstalledList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getInstalledList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getSalingList")
	public void getSalingList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getSalingList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getSignOrConfirmList")
	public void getSignOrConfirmList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getSignOrConfirmList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getProductionDetailList")
	public void getProductionDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getProductionDetailList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getInstallingDetailList")
	public void getInstallingDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getInstallingDetailList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getSignOrConfirmDetailList")
	public void getSignOrConfirmDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getSignOrConfirmDetailList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
			for (int i = 0; i < listBean.size(); i++) {
				List<Map<String, Object>> photos = null;
				if("测量".equals(listBean.get(i).getType()) || "验收".equals(listBean.get(i).getType())) {
					photos = prjJobService.getPhotoList(listBean.get(i).getNo());
					if(photos == null){
						photos = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < photos.size();j++){
						if("1".equals(photos.get(j).get("isSendYun"))){
							photos.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photos.get(j).get("photoName").toString().substring(0,5).trim()+"/"+photos.get(j).get("photoName").toString());
						}else{
							photos.get(j).put("src", PathUtil.getWebRootAddress(request)+"homePhoto/prjProgNew/"+photos.get(j).get("photoName").toString().substring(0,5).trim()+"/"+photos.get(j).get("photoName").toString());
						}
					}
				}
				if("签到".equals(listBean.get(i).getType())) {
					photos = signInService.getSignInPic(listBean.get(i).getNo());
					if(photos == null){
						photos = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < photos.size();j++){
						if("1".equals(photos.get(j).get("isSendYun"))){
							photos.get(j).put("src", OssConfigure.cdnAccessUrl+"/signIn/"+photos.get(j).get("photoName").toString());
						}else{
							photos.get(j).put("src", PathUtil.getWebRootAddress(request)+"homePhoto/signIn/"+photos.get(j).get("photoName").toString());
						}
					}
				}
				listBean.get(i).setPhotos(photos);
			}
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
	@RequestMapping("/getWorkSignList")
	public void getWorkSignList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getWorkSignList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
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
	@RequestMapping("/getWorkSignDetailList")
	public void getWorkSignDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		IntegrateBulletinBoardEvt evt = new IntegrateBulletinBoardEvt();
		BasePageQueryResp<IntegrateBulletinBoardResp> respon=new BasePageQueryResp<IntegrateBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntegrateBulletinBoardEvt)JSONObject.toBean(json,IntegrateBulletinBoardEvt.class);
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
			integrateBulletinBoardService.getWorkSignDetailList(page, evt);
			List<IntegrateBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), IntegrateBulletinBoardResp.class);
			for (int i = 0; i < listBean.size(); i++) {
				List<Map<String, Object>> photos = null;
				photos = workerService.getWorkSignPic(listBean.get(i).getNo());
				if(photos == null){
					photos = new ArrayList<Map<String,Object>>();
				}
				for(int j = 0;j < photos.size();j++){
					if("1".equals(photos.get(j).get("isSendYun"))){
						photos.get(j).put("src", OssConfigure.cdnAccessUrl+"/workSignPic/"+photos.get(j).get("custCode").toString()+"/"+photos.get(j).get("photoName").toString());
					}else{
						photos.get(j).put("src", PathUtil.getWebRootAddress(request)+"homePhoto/workSignPic/"+photos.get(j).get("custCode").toString()+"/"+photos.get(j).get("photoName").toString());
					}
				}
				listBean.get(i).setPhotos(photos);
			}
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
	
}
