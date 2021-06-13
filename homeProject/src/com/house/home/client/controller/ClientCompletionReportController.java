package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.CompletionReportEvt;
import com.house.home.client.service.resp.CompletionReportResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.CustWorker;
import com.house.home.service.project.CompletionReportService;

@Controller
@RequestMapping("/client/completionReport")
public class ClientCompletionReportController extends ClientBaseController {

	@Autowired
	private CompletionReportService completionReportService;
	
	@RequestMapping("/getCompletionReportInfo")
	public void getCompletionReportInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CompletionReportEvt evt=new CompletionReportEvt();
		CompletionReportResp respon=new CompletionReportResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(CompletionReportEvt) JSONObject.toBean(json, CompletionReportEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map = completionReportService.getCompletionReportInfo(evt.getCustWkPk());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CompletionReportEvt evt=new CompletionReportEvt();
		CompletionReportResp respon=new CompletionReportResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("conPlanEnd")&&!"null".equals(json.get("conPlanEnd").toString())){
				json.put("conPlanEnd", sdf.parse(json.get("conPlanEnd").toString()));
			}
			evt=(CompletionReportEvt) JSONObject.toBean(json, CompletionReportEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			CustWorker custWorker = completionReportService.get(CustWorker.class, evt.getCustWkPk());
			custWorker.setConPlanEnd(evt.getConPlanEnd());
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(custWorker.getWorkerCode());
			custWorker.setAciontLog("EDIT");
			completionReportService.update(custWorker);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustWorkerInfo")
	public void getCustWorkerInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CompletionReportEvt evt=new CompletionReportEvt();
		CompletionReportResp respon=new CompletionReportResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(CompletionReportEvt) JSONObject.toBean(json, CompletionReportEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map = completionReportService.getNowProgress(evt.getCustWkPk());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
