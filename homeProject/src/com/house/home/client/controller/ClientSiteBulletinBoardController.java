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
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.SiteBulletinBoardEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.SiteBulletinBoardResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.service.project.SiteBulletinBoardService;

@RequestMapping("/client/siteBulletinBoard")
@Controller
public class ClientSiteBulletinBoardController extends ClientBaseController {
	
	@Autowired
	private SiteBulletinBoardService siteBulletinBoardService;
	
	@RequestMapping("/getSiteBulletinBoardCountInfo")
	public void getSiteBulletinBoardCountInfo(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt=new BaseEvt();
		SiteBulletinBoardResp respon=new SiteBulletinBoardResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt)JSONObject.toBean(json,BaseEvt.class);
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
			
			Map<String, Object> siteBulletinBoardConutInfo = siteBulletinBoardService.getSiteBulletinBoardCountInfo();
			BeanConvertUtil.mapToBean(siteBulletinBoardConutInfo, respon);
		
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
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			siteBulletinBoardService.getBuildingList(page);
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			siteBulletinBoardService.getConstructionList(page,evt.getWorkerClassify());
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			siteBulletinBoardService.getWaitConfirmPrjProblemList(page);
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			siteBulletinBoardService.getWaitDealPrjProblemList(page);
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			siteBulletinBoardService.getCustProblemList(page);
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
	@RequestMapping("/getPrjCheckOrConfirmList")
	public void getPrjCheckOrConfirmList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			siteBulletinBoardService.getPrjCheckOrConfirmList(page, evt.getCountType());
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			siteBulletinBoardService.getPrjProblemDetailList(page, evt.getPrjProblemType(), evt.getDepartment2());
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			siteBulletinBoardService.getCustProblemDetailList(page, evt.getDepartment2());
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
	@RequestMapping("/getPrjCheckOrConfirmDetailList")
	public void getPrjCheckOrConfirmDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			if("confirmPage".equals(evt.getPageName())){
				siteBulletinBoardService.getConfirmDetailList(page, evt.getCountType(), evt.getNumber());
			}else{
				siteBulletinBoardService.getCheckDetailList(page, evt.getCountType(), evt.getNumber());
			}
			
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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

			// 1:上周    2：本月    3：上月
			if("1".equals(evt.getDayRange())){
				beginDate = DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()),-7);
				endDate = DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()), -1);
			}
			if("2".equals(evt.getDayRange())){
				beginDate = DateUtil.startOfTheMonth(new Date());
				endDate = new Date();
			}
			if("3".equals(evt.getDayRange())){
				beginDate = DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(),-1));
				endDate = DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),-1));
			}
			
			siteBulletinBoardService.getPrjStageProgList(page, beginDate, endDate,evt.getStage(),evt.getPageSize());
			
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
	@RequestMapping("/getWorkerArrangeList")
	public void getWorkerArrangeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			// 1:上周   2本周   3：上月    4：本月
			if("1".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()),-7));
				customer.setEndDate(DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()), -1));
			}
			
			if("2".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.getFirstDayOfWeek(new Date()));
				customer.setEndDate(new Date());
			}
			
			if("3".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(),-1)));
				customer.setEndDate(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),-1)));
			}
			if("4".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
				customer.setEndDate(new Date());
			}
			
			siteBulletinBoardService.getWorkerArrangeList(page, customer);
			
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
	@RequestMapping("/getWorkerCompletedList")
	public void getWorkerCompletedList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			// 1:上周   2本周   3：上月    4：本月
			if("1".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()),-7));
				customer.setEndDate(DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()), -1));
			}
			
			if("2".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.getFirstDayOfWeek(new Date()));
				customer.setEndDate(new Date());
			}
			
			if("3".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(),-1)));
				customer.setEndDate(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),-1)));
			}
			if("4".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
				customer.setEndDate(new Date());
			}
			siteBulletinBoardService.getWorkerCompletedList(page, customer);
			
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
		SiteBulletinBoardEvt evt=new SiteBulletinBoardEvt();
		BasePageQueryResp<SiteBulletinBoardResp> respon=new BasePageQueryResp<SiteBulletinBoardResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SiteBulletinBoardEvt)JSONObject.toBean(json,SiteBulletinBoardEvt.class);
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
			// 1:上周   2本周   3：上月    4：本月
			if("1".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()),-7));
				customer.setEndDate(DateUtil.addDate(DateUtil.getFirstDayOfWeek(new Date()), -1));
			}
			
			if("2".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.getFirstDayOfWeek(new Date()));
				customer.setEndDate(new Date());
			}
			
			if("3".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(),-1)));
				customer.setEndDate(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(),-1)));
			}
			if("4".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
				customer.setEndDate(new Date());
			}
			siteBulletinBoardService.getPrjAgainList(page, customer);
			
			List<SiteBulletinBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SiteBulletinBoardResp.class);
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
