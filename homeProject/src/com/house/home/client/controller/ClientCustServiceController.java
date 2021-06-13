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
import com.house.home.client.service.evt.DoCompleteCustServiceEvt;
import com.house.home.client.service.evt.DoSaveCustServiceEvt;
import com.house.home.client.service.evt.DoSaveFeedBackCustServiceEvt;
import com.house.home.client.service.evt.DoUpdateCustServiceEvt;
import com.house.home.client.service.evt.GetCustServiceDetailEvt;
import com.house.home.client.service.evt.GetCustServiceListEvt;
import com.house.home.client.service.evt.GoCompleteCustServiceEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.DoCompleteCustServiceResp;
import com.house.home.client.service.resp.DoSaveCustServiceResp;
import com.house.home.client.service.resp.GetCustServiceDetailResp;
import com.house.home.client.service.resp.GetCustServiceListResp;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.CustService;
import com.house.home.service.basic.CzyGnqxService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.project.CustServiceService;

import java.util.Date;
import java.util.List;
import java.util.Map;
@RequestMapping("/client/custService")
@Controller
public class ClientCustServiceController extends ClientBaseController {
	
	@Autowired
	private CustServiceService custServiceService;
	
	@Autowired
	private CzyGnqxService czyGnqxService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustServiceList")
	public void getCustServiceList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetCustServiceListEvt evt=new GetCustServiceListEvt();
		BasePageQueryResp<GetCustServiceListResp> respon=new BasePageQueryResp<GetCustServiceListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetCustServiceListEvt) JSONObject.toBean(json, GetCustServiceListEvt.class);
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
			CustService custService = new CustService();
			custService.setAddress(evt.getAddress());
			custService.setAppStatus(evt.getAppStatus());
			String Cyzbh = this.getUserContext(request).getCzybh();
			if(czyGnqxService.getCzyGnqx("0836","查看全部",Cyzbh) != null){
				custServiceService.AppfindBySql(page, custService);
			}else{
				Employee employee = employeeService.get(Employee.class, Cyzbh);
				if(employee != null){
					custService.setServiceMan(employee.getNumber());
					custServiceService.AppfindBySql(page, custService);
				}
			}
			List<GetCustServiceListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetCustServiceListResp.class);
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
	
	@RequestMapping("/doSaveCustService")
	public void doSaveCustService(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoSaveCustServiceEvt evt=new DoSaveCustServiceEvt();
		DoSaveCustServiceResp respon=new DoSaveCustServiceResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json=StringUtil.queryStringToJSONObject(request);
			}
			evt=(DoSaveCustServiceEvt) JSONObject.toBean(json, DoSaveCustServiceEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getCustCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("楼盘不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(evt.getRemarks())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("说明不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(evt.getRemarks().length() > 400){
				respon.setReturnCode("400001");
				respon.setReturnInfo("说明内容不能超过400字");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
/*
			if(StringUtils.isBlank(evt.getType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("类型不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}*/
			
			Map<String, Object> resultMap = custServiceService.doSaveCustService(evt);
			
			respon.setReturnCode(resultMap.get("returnCode").toString());
			respon.setReturnInfo(resultMap.get("returnInfo").toString());
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	

	@RequestMapping("/getCustServiceDetail")
	public void getCustServiceDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetCustServiceDetailEvt evt=new GetCustServiceDetailEvt();
		GetCustServiceDetailResp respon=new GetCustServiceDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetCustServiceDetailEvt) JSONObject.toBean(json, GetCustServiceDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Map<String, Object> map = custServiceService.getCustServiceDetail(evt.getNo());
			
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	
	@RequestMapping("/doUpdateCustService")
	public void doUpdateCustService(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoUpdateCustServiceEvt evt=new DoUpdateCustServiceEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt=(DoUpdateCustServiceEvt) JSONObject.toBean(json, DoUpdateCustServiceEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("楼盘不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getRemarks())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("说明不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(evt.getRemarks().length() > 400){
				respon.setReturnCode("400001");
				respon.setReturnInfo("说明内容不能超过400字");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
/*			if(StringUtils.isBlank(evt.getType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("类型不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}*/
			
			Map<String, Object> resultMap = custServiceService.doUpdateCustService(evt);
			
			respon.setReturnCode(resultMap.get("returnCode").toString());
			respon.setReturnInfo(resultMap.get("returnInfo").toString());
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/goComplete")
	public void goComplete(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GoCompleteCustServiceEvt evt=new GoCompleteCustServiceEvt();
		DoCompleteCustServiceResp respon=new DoCompleteCustServiceResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GoCompleteCustServiceEvt) JSONObject.toBean(json, GoCompleteCustServiceEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Map<String, Object> map = custServiceService.goComplete(evt.getNo());
			
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	@RequestMapping("/doComplete")
	public void doComplete(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoCompleteCustServiceEvt evt=new DoCompleteCustServiceEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json=StringUtil.queryStringToJSONObject(request);
			}
			evt=(DoCompleteCustServiceEvt) JSONObject.toBean(json, DoCompleteCustServiceEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getDealMan())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("处理人不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(evt.getDealDate()==null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("处理时间不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			CustService cs = this.custServiceService.get(CustService.class, evt.getNo());
			cs.setDealMan(evt.getDealMan());
			cs.setDealDate(evt.getDealDate());
			cs.setLastUpdate(new Date());
			cs.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			cs.setActionLog("Edit");
			cs.setStatus("2");
			custServiceService.update(cs);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	@RequestMapping("/doSaveFeedBack")
	public void doSaveFeedBack(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoSaveFeedBackCustServiceEvt evt=new DoSaveFeedBackCustServiceEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json=StringUtil.queryStringToJSONObject(request);
			}
			evt=(DoSaveFeedBackCustServiceEvt) JSONObject.toBean(json, DoSaveFeedBackCustServiceEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(StringUtils.isBlank(evt.getNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(evt.getFeedBackRemarkTemp())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("追加的反馈不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			CustService cs = this.custServiceService.get(CustService.class, evt.getNo());
			String feedBackRemark = cs.getFeedBackRemark();
			if(StringUtils.isBlank(feedBackRemark)){
				feedBackRemark = evt.getFeedBackRemarkTemp();
			}else{
				feedBackRemark = feedBackRemark + "\n" + evt.getFeedBackRemarkTemp();
			}
			cs.setFeedBackRemark(feedBackRemark);
			cs.setLastUpdate(new Date());
			cs.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			cs.setActionLog("Edit");
			custServiceService.update(cs);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
}
