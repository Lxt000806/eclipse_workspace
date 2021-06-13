package com.house.home.client.controller;

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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.DoUpdatePrjProblemEvt;
import com.house.home.client.service.evt.PrjBulletinBoardEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.PersonMessageDetailResp;
import com.house.home.client.service.resp.PrjBulletinBoardResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProblem;
import com.house.home.service.project.PrjBulletinBoardService;

@RequestMapping("/client/prjBulletinBoard")
@Controller
public class ClientPrjBulletinBoardController extends ClientBaseController {
	
	@Autowired
	private PrjBulletinBoardService prjBulletinBoardService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDepartmentList")
	public void getDepartmentList(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getDepartmentList(page, uc);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getPrjBulletinBoardCountInfo")
	public void getPrjBulletinBoardCountInfo(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		PrjBulletinBoardResp respon=new PrjBulletinBoardResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			
			Map<String, Object> PrjBulletinBoardConutInfo = prjBulletinBoardService.getPrjBulletinBoardCountInfo(evt);
			BeanConvertUtil.mapToBean(PrjBulletinBoardConutInfo, respon);
		
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBuildingList")
	public void getBuildingList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt = new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getBuildingList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getPrjStageProgList")
	public void getPrjStageProgList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			Date beginDate = null;
			Date endDate = null;

			// 1:本月    2：上月    3：本年
			if("1".equals(evt.getDayRange())){
				beginDate = DateUtil.startOfTheMonth(new Date());
				endDate = new Date();
			}
			if("2".equals(evt.getDayRange())){
				beginDate = DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(),-1));
				endDate = DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),-1));
			}
			if("3".equals(evt.getDayRange())){
				beginDate = DateUtil.startOfTheYear(new Date());
				endDate = new Date();
			}
			
			prjBulletinBoardService.getPrjStageProgList(page, beginDate, endDate,evt.getStage(),evt.getPageSize(),evt.getDepartment());
			
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getConstructionList")
	public void getConstructionList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getConstructionList(page,evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getPrjTaskExecAnalyList")
	public void getPrjTaskExecAnalyList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt = new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getPrjTaskExecAnalyList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getWaitConfirmPrjProblemList")
	public void getWaitConfirmPrjProblemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt = new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getWaitConfirmPrjProblemList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getWaitDealPrjProblemList")
	public void getWaitDealPrjProblemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getWaitDealPrjProblemList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getCustProblemList")
	public void getCustProblemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getCustProblemList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getProjectManSignList")
	public void getProjectManSignList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getProjectManSignList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getPrjAgainList")
	public void getPrjAgainList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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

			Customer customer = new Customer();
			
			if(StringUtils.isNotBlank(evt.getCustType())){
				customer.setWorkerClassify(evt.getCustType());
			}
			// 1:本月   2上月   3：本年
			if("1".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
				customer.setEndDate(new Date());
			}
			
			if("2".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(),-1)));
				customer.setEndDate(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),-1)));
			}
			
			if("3".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheYear(new Date()));
				customer.setEndDate(new Date());
			}
		
			prjBulletinBoardService.getPrjAgainList(page, customer, evt.getDepartment());
			
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getPrjTaskExecAnalyDetailList")
	public void getPrjTaskExecAnalyDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PersonMessageDetailResp> respon=new BasePageQueryResp<PersonMessageDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getPrjTaskExecAnalyDetailList(page, evt.getRcvCzy());
			List<PersonMessageDetailResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PersonMessageDetailResp.class);
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
	@RequestMapping("/getPrjProblemDetailList")
	public void getPrjProblemDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getPrjProblemDetailList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
	@RequestMapping("/getCustProblemDetailList")
	public void getCustProblemDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjBulletinBoardEvt evt=new PrjBulletinBoardEvt();
		BasePageQueryResp<PrjBulletinBoardResp> respon=new BasePageQueryResp<PrjBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjBulletinBoardEvt)JSONObject.toBean(json,PrjBulletinBoardEvt.class);
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
			prjBulletinBoardService.getCustProblemDetailList(page, evt);
			List<PrjBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjBulletinBoardResp.class);
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
