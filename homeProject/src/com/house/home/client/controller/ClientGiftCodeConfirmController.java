package com.house.home.client.controller;

import java.util.Date;
import java.util.List;

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
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.GiftCodeConfirmEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GiftCodeConfirmResp;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.GiftCodeConfirm;
import com.house.home.service.basic.CustAccountService;
import com.house.home.service.project.GiftCodeConfirmService;


@RequestMapping("/client/giftCodeConfirm")
@Controller
public class ClientGiftCodeConfirmController extends ClientBaseController{
	
	@Autowired
	GiftCodeConfirmService giftCodeConfirmService;
	@Autowired
	CustAccountService custAccountService;
	
	@RequestMapping("/getGiftCodeConfirmList")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getGiftCodeConfirmList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		BasePageQueryResp<GiftCodeConfirmResp> respon=new BasePageQueryResp<GiftCodeConfirmResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GiftCodeConfirmEvt) JSONObject.toBean(json, GiftCodeConfirmEvt.class);
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
			page.setPageNo(-1);
			page.setPageSize(10000);
			giftCodeConfirmService.getGiftCodeConfirmList(page, evt.getPhone());
			List<GiftCodeConfirmResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GiftCodeConfirmResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getGiftAppList")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getGiftAppList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		BasePageQueryResp<GiftCodeConfirmResp> respon=new BasePageQueryResp<GiftCodeConfirmResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GiftCodeConfirmEvt) JSONObject.toBean(json, GiftCodeConfirmEvt.class);
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
			page.setPageNo(-1);
			page.setPageSize(10000);
			giftCodeConfirmService.getGiftAppList(page, evt.getCustCode());
			List<GiftCodeConfirmResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GiftCodeConfirmResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getBaseData")
	public void getBaseData(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		GiftCodeConfirmResp respon=new GiftCodeConfirmResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GiftCodeConfirmEvt) JSONObject.toBean(json, GiftCodeConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String phone = "";
			String custCode = "";
			String text = DesUtils.decode(evt.getText());
			System.out.println(text);
			long nowDate = new Date().getTime();
			long crtDate = Long.parseLong(text.substring(0, 13));
			if(nowDate-crtDate>24*60*60*1000){
				respon.setReturnCode("400001");
				respon.setReturnInfo("该二维码已过了有效期");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(text.length()>24){
				custCode=text.substring(24,32);
				Customer customer = giftCodeConfirmService.get(Customer.class, custCode);
				if(customer!=null){
					respon.setAddress(customer.getAddress());
					respon.setCustCode(custCode);
				}
			}
			phone = text.substring(13,24);
			CustAccount custAccount = giftCodeConfirmService.getCustAccount(phone);
			if(custAccount!=null){
				respon.setNickName(custAccount.getNickName());
				respon.setPhone(phone);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustomerList")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		BasePageQueryResp<GiftCodeConfirmResp> respon=new BasePageQueryResp<GiftCodeConfirmResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GiftCodeConfirmEvt) JSONObject.toBean(json, GiftCodeConfirmEvt.class);
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
			page.setPageSize(5);
			
			giftCodeConfirmService.getCustomerList(page, evt.getAddress());
			List<GiftCodeConfirmResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GiftCodeConfirmResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doConfirm")
	public void doConfirm(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json = StringUtil.queryStringToJSONObject(request);
			}
			evt = (GiftCodeConfirmEvt)JSONObject.toBean(json,GiftCodeConfirmEvt.class);
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
			GiftCodeConfirm giftCodeConfirm = new GiftCodeConfirm();
			giftCodeConfirm.setPhone(evt.getPhone());
			giftCodeConfirm.setCustCode(evt.getCustCode());
			giftCodeConfirm.setIsNoAddress(evt.getIsNoAddress());
			giftCodeConfirm.setRemarks(evt.getRemarks());
			giftCodeConfirm.setLastUpdate(new Date());
			giftCodeConfirm.setLastUpdatedBy(evt.getCzybh());
			giftCodeConfirm.setActionLog("ADD");
			giftCodeConfirm.setExpired("F");
			this.giftCodeConfirmService.save(giftCodeConfirm);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	@RequestMapping("/getCustomerListByPhone")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getCustListByPhone(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		BasePageQueryResp<GiftCodeConfirmResp> respon=new BasePageQueryResp<GiftCodeConfirmResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GiftCodeConfirmEvt) JSONObject.toBean(json, GiftCodeConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<GiftCodeConfirmResp> listBean=BeanConvertUtil.mapToBeanList(custAccountService.getGiftCustCodeListByPhone(evt.getPhone()), GiftCodeConfirmResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	@RequestMapping("/desEnCode")
	public void desEnCode(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GiftCodeConfirmEvt evt=new GiftCodeConfirmEvt();
		BasePageQueryResp<GiftCodeConfirmResp> respon=new BasePageQueryResp<GiftCodeConfirmResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GiftCodeConfirmEvt) JSONObject.toBean(json, GiftCodeConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			respon.setReturnInfo(DesUtils.encode(evt.getText()));
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
