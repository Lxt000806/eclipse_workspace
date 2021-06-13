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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.WorkerEvalEvt;
import com.house.home.client.service.evt.WorkerEvalListEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.WorkerEvalListResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.WorkerEval;
import com.house.home.service.project.WorkerEvalService;

@RequestMapping("/client/workerEval")
@Controller
public class ClientWorkerEvalController extends ClientBaseController {
	
	@Autowired
	private WorkerEvalService workerEvalService;
	

	@RequestMapping("getWorkerEvalList")
	public void getWorkerEvalList(HttpServletRequest request,HttpServletResponse response,WorkerEval workerEval){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		WorkerEvalListEvt evt=new WorkerEvalListEvt();
		BasePageQueryResp<WorkerEvalListResp> respon=new BasePageQueryResp<WorkerEvalListResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerEvalListEvt) JSONObject.toBean(json, WorkerEvalListEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			workerEvalService.getWorkerEvalList(page, evt);
			List<WorkerEvalListResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerEvalListResp.class);
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
	

	@RequestMapping("/saveWorkerEval")
	public void saveWorkerEval(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		WorkerEvalEvt evt=new WorkerEvalEvt();
		BasePageQueryResp<WorkerEvalListResp> respon=new BasePageQueryResp<WorkerEvalListResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerEvalEvt) JSONObject.toBean(json, WorkerEvalEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			WorkerEval workerEval=new WorkerEval();
			workerEval.setCustCode(evt.getCustCode());
			workerEval.setWorkerCode(evt.getWorkerCode());
			workerEval.setCustWkPk(evt.getCustWkPk());
			workerEval.setType("3");
			workerEval.setEvaMan(getUserContext(request).getCzybh());
			workerEval.setScore(evt.getScore());
			workerEval.setExpired("F");
			workerEval.setRemark(evt.getRemark());
			workerEval.setM_umState("A");
			workerEval.setHealthScore(evt.getHealthScore());
			workerEval.setToolScore(evt.getToolScore());
			workerEvalService.doSave(workerEval);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/updateWorkerEval")
	public void updateWorkerEval(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		WorkerEvalEvt  evt=new WorkerEvalEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerEvalEvt) JSONObject.toBean(json, WorkerEvalEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			try{
				WorkerEval workerEval=new WorkerEval();
				workerEval.setCustCode(evt.getCustCode());
				workerEval.setWorkerCode(evt.getWorkerCode());
				workerEval.setCustWkPk(evt.getCustWkPk());
				workerEval.setType(evt.getType());
				workerEval.setEvaMan(getUserContext(request).getCzybh());
				workerEval.setScore(evt.getScore());
				workerEval.setExpired("F");
				workerEval.setRemark(evt.getRemark());
				workerEval.setM_umState("M");
				workerEval.setHealthScore(evt.getHealthScore());
				workerEval.setToolScore(evt.getToolScore());
				workerEvalService.doSave(workerEval);
			}catch (Exception e){
				respon.setReturnCode("400001");
				respon.setReturnInfo("修改验收申请失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
