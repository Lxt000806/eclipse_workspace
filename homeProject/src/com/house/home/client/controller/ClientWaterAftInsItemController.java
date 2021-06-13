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

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.client.service.evt.WaterAftInsItemEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.WaterAftInsItemResp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.WaterAftInsItemService;


@Controller
@RequestMapping("/client/waterAftInsItem")
public class ClientWaterAftInsItemController extends ClientBaseController {
	
	@Autowired
	private WaterAftInsItemService waterAftInsItemService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWaterAftInsItemType2List")
	public void getWaterAftInsItemType2List(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterAftInsItemEvt evt=new WaterAftInsItemEvt();
		BasePageQueryResp<WaterAftInsItemResp> respon=new BasePageQueryResp<WaterAftInsItemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WaterAftInsItemEvt)JSONObject.toBean(json,WaterAftInsItemEvt.class);
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
			page.setPageNo(1);
			page.setPageSize(1000);
			page.setPageOrderBy("LastUpdate");
			page.setPageOrder("Desc");
			waterAftInsItemService.getWaterAftInsItemType2List(page);
			List<WaterAftInsItemResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WaterAftInsItemResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWaterAftInsItemDetailList")
	public void getWaterAftInsItemDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterAftInsItemEvt evt=new WaterAftInsItemEvt();
		BasePageQueryResp<WaterAftInsItemResp> respon=new BasePageQueryResp<WaterAftInsItemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WaterAftInsItemEvt)JSONObject.toBean(json,WaterAftInsItemEvt.class);
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
			page.setPageOrderBy("LastUpdate");
			page.setPageOrder("Desc");
			waterAftInsItemService.getWaterAftInsItemDetailList(page,evt);
			List<WaterAftInsItemResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WaterAftInsItemResp.class);
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
	
	
	@RequestMapping("/doSaveWaterAftInsItemApp")
	public void doSaveWaterAftInsItemApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterAftInsItemEvt evt=new WaterAftInsItemEvt();
		WaterAftInsItemResp respon=new WaterAftInsItemResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (WaterAftInsItemEvt)JSONObject.toBean(json,WaterAftInsItemEvt.class);
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
			
			String xmlData = evt.getWaterAftInsItemAppData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
			
			Result result = waterAftInsItemService.saveWaterAftInsItemAppForProc(evt.getCustCode(),evt.getWorkerCode(), xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getIsWaterAftInsItemApp")
	public void getIsWaterAftInsItemApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterAftInsItemEvt evt=new WaterAftInsItemEvt();
		WaterAftInsItemResp respon=new WaterAftInsItemResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WaterAftInsItemEvt)JSONObject.toBean(json,WaterAftInsItemEvt.class);
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
			Customer customer = waterAftInsItemService.get(Customer.class, evt.getCustCode());
			CustType custType = waterAftInsItemService.get(CustType.class,customer.getCustType());
			if("1".equals(custType.getIsWaterAftInsItemApp())){
				respon.setIsWaterAftInsItemApp(true);
			}else{
				respon.setIsWaterAftInsItemApp(false);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWaterAftInsItemList")
	public void getWaterAftInsItemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterAftInsItemEvt evt=new WaterAftInsItemEvt();
		BasePageQueryResp<WaterAftInsItemResp> respon=new BasePageQueryResp<WaterAftInsItemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WaterAftInsItemEvt)JSONObject.toBean(json,WaterAftInsItemEvt.class);
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
			page.setPageOrderBy("LastUpdate");
			page.setPageOrder("Desc");
			waterAftInsItemService.getWaterAftInsItemList(page,evt);
			List<WaterAftInsItemResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WaterAftInsItemResp.class);
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
}
