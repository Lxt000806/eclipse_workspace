package com.house.home.client.controller;

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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.DoSaveActivitySetEvt;
import com.house.home.client.service.evt.GetActSetDetailEvt;
import com.house.home.client.service.evt.GetActSupplDetailEvt;
import com.house.home.client.service.evt.GetActSupplListEvt;
import com.house.home.client.service.evt.GetActivitySetListEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.DoSaveActivitySetResp;
import com.house.home.client.service.resp.GetActSetDetailResp;
import com.house.home.client.service.resp.GetActSupplDetailResp;
import com.house.home.client.service.resp.GetActSupplListResp;
import com.house.home.client.service.resp.GetActivitySetListResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.ActivitySet;
import com.house.home.service.basic.ActivitySetService;

import java.util.Date;
import java.util.List;
import java.util.Map;
@RequestMapping("/client/activitySet")
@Controller
public class ClientActivitySetController extends ClientBaseController {
	
	@Autowired
	private ActivitySetService activitySetService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getActivitySetList")
	public void getActivitySetList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActivitySetListEvt evt=new GetActivitySetListEvt();
		BasePageQueryResp<GetActivitySetListResp> respon=new BasePageQueryResp<GetActivitySetListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActivitySetListEvt) JSONObject.toBean(json, GetActivitySetListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			activitySetService.getActivitySetList(page,evt.getActNo(),evt.getTicketNo(),evt.getTokenCzyDescr());
			List<GetActivitySetListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetActivitySetListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getActSupplList")
	public void getActSupplList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActSupplListEvt evt=new GetActSupplListEvt();
		BasePageQueryResp<GetActSupplListResp> respon=new BasePageQueryResp<GetActSupplListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActSupplListEvt) JSONObject.toBean(json, GetActSupplListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			activitySetService.getActSupplList(page,evt.getActNo(),evt.getSupplType(),evt.getSupplCodeDescr());
			List<GetActSupplListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetActSupplListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveActivitySet")
	public void doSaveActivitySet(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoSaveActivitySetEvt evt=new DoSaveActivitySetEvt();
		DoSaveActivitySetResp respon=new DoSaveActivitySetResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DoSaveActivitySetEvt) JSONObject.toBean(json, DoSaveActivitySetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getActNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("活动不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getTicketNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("门票编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getSupplCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("供应商不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getSupplType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请检查是否存在该商家");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ActivitySet activitySet = new ActivitySet();
			activitySet.setActNo(evt.getActNo());
			activitySet.setTicketNo(evt.getTicketNo());
			activitySet.setSupplCode(evt.getSupplCode());
			activitySet.setSupplType(evt.getSupplType());
			activitySet.setLastUpdatedBy(getUserContext(request).getCzybh());
			Map<String,Object> resultMap = activitySetService.doSaveActivitySet(activitySet);
			if(!"1".equals(resultMap.get("returnCode").toString())){
				respon.setReturnCode(resultMap.get("returnCode").toString());
				respon.setReturnInfo(resultMap.get("returnInfo").toString());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getActSupplDetail")
	public void getActSupplDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActSupplDetailEvt evt=new GetActSupplDetailEvt();
		GetActSupplDetailResp respon=new GetActSupplDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActSupplDetailEvt) JSONObject.toBean(json, GetActSupplDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> resultMap = activitySetService.getActSupplDetail(evt.getSupplCode());
			BeanConvertUtil.mapToBean(resultMap, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getActSetDetail")
	public void getActSetDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActSetDetailEvt evt=new GetActSetDetailEvt();
		GetActSetDetailResp respon=new GetActSetDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActSetDetailEvt) JSONObject.toBean(json, GetActSetDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> resultMap = activitySetService.getActSetDetail(evt.getPk());
			BeanConvertUtil.mapToBean(resultMap, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	
	@RequestMapping("/doActSetCancel")
	public void doActSetCancel(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActSetDetailEvt evt=new GetActSetDetailEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActSetDetailEvt) JSONObject.toBean(json, GetActSetDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> resultMap = activitySetService.doActSetCancel(evt.getPk(),getUserContext(request).getCzybh().trim());	
			if(!"1".equals(resultMap.get("returnCode").toString())){
				respon.setReturnCode(resultMap.get("returnCode").toString());
				respon.setReturnInfo(resultMap.get("returnInfo").toString());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
