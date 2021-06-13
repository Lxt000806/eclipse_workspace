package com.house.home.client.controller;

import java.util.ArrayList;
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
import com.house.home.client.service.evt.GetCustComplaintDetailEvt;
import com.house.home.client.service.evt.GetCustComplaintListEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.GetCustComplaintDetailResp;
import com.house.home.client.service.resp.GetCustComplaintListResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.query.CustComplaintService;

@RequestMapping("/client/custComplaint")
@Controller
public class ClientCustComplaintController extends ClientBaseController {
	
	@Autowired
	private CustComplaintService custComplaintService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustComplaintList")
	public void getCustComplaintList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustComplaintListEvt evt=new GetCustComplaintListEvt();
		BasePageQueryResp<GetCustComplaintListResp> respon=new BasePageQueryResp<GetCustComplaintListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustComplaintListEvt)JSONObject.toBean(json,GetCustComplaintListEvt.class);
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
			custComplaintService.getCustComplaintList(page, evt, getUserContext(request));
			List<GetCustComplaintListResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), GetCustComplaintListResp.class);
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
	
	@RequestMapping("/getCustComplaintDetail")
	public void getCustComplaintDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustComplaintDetailEvt evt=new GetCustComplaintDetailEvt();
		GetCustComplaintDetailResp respon=new GetCustComplaintDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustComplaintDetailEvt)JSONObject.toBean(json,GetCustComplaintDetailEvt.class);
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
			Map<String, Object> map = custComplaintService.getCustComplaintDetail(evt.getNo());
			BeanConvertUtil.mapToBean(map, respon);
			List<Map<String, Object>> list = custComplaintService.getCustComplaintDetailList(evt.getNo());
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			respon.setComplaintDetailList(list);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustComplaintListForCust")
	public void getCustComplaintListForCust(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustComplaintListEvt evt=new GetCustComplaintListEvt();
		BasePageQueryResp<GetCustComplaintListResp> respon=new BasePageQueryResp<GetCustComplaintListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustComplaintListEvt)JSONObject.toBean(json,GetCustComplaintListEvt.class);
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
			custComplaintService.getCustComplaintList_forCust(page, evt);
			List<GetCustComplaintListResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), GetCustComplaintListResp.class);
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
}
