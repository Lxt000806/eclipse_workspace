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
import com.house.home.client.service.evt.DoSaveActivityGiftEvt;
import com.house.home.client.service.evt.DoUpdateActGiftEvt;
import com.house.home.client.service.evt.GetActGiftDetailByPkEvt;
import com.house.home.client.service.evt.GetActGiftDetailEvt;
import com.house.home.client.service.evt.GetActGiftListEvt;
import com.house.home.client.service.evt.GetActSetDetailEvt;
import com.house.home.client.service.evt.GetActivityGiftListEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.DoSaveActivityGiftResp;
import com.house.home.client.service.resp.GetActGiftDetailByPkResp;
import com.house.home.client.service.resp.GetActGiftDetailResp;
import com.house.home.client.service.resp.GetActGiftListResp;
import com.house.home.client.service.resp.GetActSetDetailResp;
import com.house.home.client.service.resp.GetActivityGiftListResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.ActivityGift;
import com.house.home.service.basic.ActivityGiftService;
import com.house.home.service.basic.ActivityService;
import com.house.home.service.basic.TicketService;

import java.util.Date;
import java.util.List;
import java.util.Map;
@RequestMapping("/client/activityGift")
@Controller
public class ClientActivityGiftController extends ClientBaseController {
	@Autowired
	private ActivityGiftService activityGiftService;
	@Autowired 
	private TicketService ticketService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getActivityGiftList")
	public void getActivityGiftList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActivityGiftListEvt evt=new GetActivityGiftListEvt();
		BasePageQueryResp<GetActivityGiftListResp> respon=new BasePageQueryResp<GetActivityGiftListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActivityGiftListEvt) JSONObject.toBean(json, GetActivityGiftListEvt.class);
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
			activityGiftService.getActivityGiftList(page,evt.getActNo(),evt.getTicketNo(),evt.getTokenCzyDescr());
			List<GetActivityGiftListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetActivityGiftListResp.class);
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
	@RequestMapping("/getActGiftList")
	public void getActGiftList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActGiftListEvt evt=new GetActGiftListEvt();
		BasePageQueryResp<GetActGiftListResp> respon=new BasePageQueryResp<GetActGiftListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActGiftListEvt) JSONObject.toBean(json, GetActGiftListEvt.class);
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
			activityGiftService.getActGiftList(page,evt.getActNo(),evt.getType(),evt.getItemCodeDescr());
			List<GetActGiftListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetActGiftListResp.class);
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

	@RequestMapping("/doSaveActivityGift")
	public void doSaveActivityGift(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoSaveActivityGiftEvt evt=new DoSaveActivityGiftEvt();
		DoSaveActivityGiftResp respon=new DoSaveActivityGiftResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DoSaveActivityGiftEvt) JSONObject.toBean(json, DoSaveActivityGiftEvt.class);
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
				if(!ticketService.existsTic(evt.getTicketNo())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("门票编号不存在");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				respon.setReturnCode("400001");
				respon.setReturnInfo("门票编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getItemCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请选择一个活动礼品");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(evt.getQty() == null || evt.getQty() <= 0){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入正确的数量");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(!activityGiftService.existCmpActGift(evt.getActNo(), evt.getItemCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请检查礼品编号是否有效");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ActivityGift activityGift = new ActivityGift();
			activityGift.setActNo(evt.getActNo());
			activityGift.setTicketNo(evt.getTicketNo());
			activityGift.setItemCode(evt.getItemCode());
			activityGift.setQty(evt.getQty());
			activityGift.setLastUpdatedBy(getUserContext(request).getCzybh());
			Map<String,Object> resultMap = activityGiftService.doSaveActivityGift(activityGift);
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
	
	@RequestMapping("/getActGiftDetail")
	public void getActGiftDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActGiftDetailEvt evt=new GetActGiftDetailEvt();
		GetActGiftDetailResp respon=new GetActGiftDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActGiftDetailEvt) JSONObject.toBean(json, GetActGiftDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> resultMap = activityGiftService.getActGiftDetail(evt.getItemCode());

			BeanConvertUtil.mapToBean(resultMap, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getActGiftDetailByPk")
	public void getActGiftDetailByPk(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetActGiftDetailByPkEvt evt=new GetActGiftDetailByPkEvt();
		GetActGiftDetailByPkResp respon=new GetActGiftDetailByPkResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetActGiftDetailByPkEvt) JSONObject.toBean(json, GetActGiftDetailByPkEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> resultMap = activityGiftService.getActGiftDetailByPk(evt.getPk());
			BeanConvertUtil.mapToBean(resultMap, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	
	@RequestMapping("/doUpdateActGift")
	public void doUpdateActGift(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoUpdateActGiftEvt evt=new DoUpdateActGiftEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DoUpdateActGiftEvt) JSONObject.toBean(json, DoUpdateActGiftEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if((evt.getPk() == null || evt.getPk() <=0) || StringUtils.isBlank(evt.getOpFlag())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("编辑失败,请重新编辑");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getItemCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请选择一个活动礼品");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(evt.getQty() == null || evt.getQty() <= 0){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入正确的数量");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(!activityGiftService.existCmpActGift(evt.getActNo(), evt.getItemCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请检查礼品编号是否有效");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> resultMap = activityGiftService.doUpdateActGift(evt,getUserContext(request).getCzybh().trim());	
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
