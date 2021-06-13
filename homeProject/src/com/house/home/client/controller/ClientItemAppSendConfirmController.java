package com.house.home.client.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.ItemAppSendConfirmEvt;
import com.house.home.client.service.evt.ItemAppSendConfirmQueryEvt;
import com.house.home.client.service.evt.ItemAppSendExceptionEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ItemAppSendConfirmDetailResp;
import com.house.home.client.service.resp.ItemAppSendConfirmResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.driver.ItemAppSendService;
@RequestMapping("/client/itemAppSendConfirm")
@Controller
public class ClientItemAppSendConfirmController extends ClientBaseController {
	@Autowired
	private ItemAppSendService itemAppSendService;
	@Autowired
	private PersonMessageService personMessageService;
	@RequestMapping("/getItemAppSendConfirmList")
	public  void getItemAppSendConfirmList(HttpServletRequest request,HttpServletResponse response){
	    StringBuilder msg=new StringBuilder();
		JSONObject   json=new JSONObject();
		ItemAppSendConfirmQueryEvt evt=new ItemAppSendConfirmQueryEvt();
		BasePageQueryResp<ItemAppSendConfirmResp> respon=new BasePageQueryResp<ItemAppSendConfirmResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog  interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppSendConfirmQueryEvt) JSONObject.toBean(json, ItemAppSendConfirmQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			itemAppSendService.getItemAppSendConfirmList(page, evt.getProjectMan(), evt.getAddress());
			List<ItemAppSendConfirmResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), ItemAppSendConfirmResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true
					: false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getItemAppSendConfirmDetail")
	public  void getItemAppSendConfirmDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemAppSendConfirmDetailResp respon=new ItemAppSendConfirmDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String, Object> map=itemAppSendService.getSendDetailById(evt.getId());
			List<Map<String,Object>> material=itemAppSendService.getMaterialList(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			respon.setMaterialDetail(material);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/itemAppSendDoConfirm")
	public  void itemAppSendDoConfirm(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppSendConfirmEvt evt=new ItemAppSendConfirmEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppSendConfirmEvt) JSONObject.toBean(json, ItemAppSendConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ItemAppSend itemAppSend=itemAppSendService.get(ItemAppSend.class, evt.getNo());
			itemAppSend.setConfirmMan(evt.getConfirmMan());
			itemAppSend.setConfirmStatus("1");
			itemAppSendService.ItemAppSendDoConfirm(itemAppSend,new PersonMessage());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/itemAppSendDoException")
	public  void itemAppSendDoException(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppSendExceptionEvt evt=new ItemAppSendExceptionEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppSendExceptionEvt) JSONObject.toBean(json, ItemAppSendExceptionEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ItemAppSend itemAppSend=itemAppSendService.get(ItemAppSend.class, evt.getNo());
			itemAppSend.setConfirmMan(evt.getConfirmMan());
			itemAppSend.setConfirmReason(evt.getConfirmReason());
			itemAppSend.setProjectManRemark(evt.getProjectManRemark());
			itemAppSend.setConfirmStatus("2");
			itemAppSendService.ItemAppSendDoConfirm(itemAppSend,new PersonMessage());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
