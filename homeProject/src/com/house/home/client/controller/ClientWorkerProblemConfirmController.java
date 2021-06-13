package com.house.home.client.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.fileUpload.impl.WorkerPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.client.service.evt.FixDutyEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.FixDutyResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.FixDutyDetail;
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.WorkerProblem;
import com.house.home.service.project.FixDutyService;
import com.house.home.service.project.WorkerProblemService;

import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetDesignDemoDetailEvt;
import com.house.home.client.service.evt.GetPrjItemDescr;
import com.house.home.client.service.evt.ItemAppQueryEvt;
import com.house.home.client.service.evt.UpdatePwdEvt;
import com.house.home.client.service.evt.WorkerProblemConfirmEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.FixDutyDetailResp;
import com.house.home.client.service.resp.GetDesignDemoDetailResp;
import com.house.home.client.service.resp.ItemAppQueryResp;
import com.house.home.client.service.resp.ItemReqDetailResp;
import com.house.home.client.service.resp.PrjProgDetailQueryResp;
import com.house.home.client.service.resp.WorkerProblemConfirmResp;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.insales.ItemApp;

@RequestMapping("/client/workerProblemConfirm")
@Controller
public class ClientWorkerProblemConfirmController extends ClientBaseController{
	@Autowired
	private FixDutyService fixDutyService;
	@Autowired
	private WorkerProblemService workerProblemService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerProblemList")
	public void getFixDutyList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerProblemConfirmEvt evt=new WorkerProblemConfirmEvt();
		BasePageQueryResp<WorkerProblemConfirmResp> respon=new BasePageQueryResp<WorkerProblemConfirmResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerProblemConfirmEvt)JSONObject.toBean(json,WorkerProblemConfirmEvt.class);
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
			page.setPageSize(10);
			WorkerProblem workerProblem = new WorkerProblem();
			workerProblem.setStatus(evt.getStatus());
			workerProblem.setNo(evt.getNo());
			workerProblem.setAddress(evt.getAddress());
			workerProblem.setWorkType12(evt.getWorkType12());
			workerProblemService.findPageBySql(page, workerProblem);
			List<WorkerProblemConfirmResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerProblemConfirmResp.class);
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
	
	@RequestMapping("/getWorkerProblemDetail")
	public void getDesignDemoDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerProblemConfirmEvt evt=new WorkerProblemConfirmEvt();
		WorkerProblemConfirmResp respon=new WorkerProblemConfirmResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerProblemConfirmEvt) JSONObject.toBean(json, WorkerProblemConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String, Object> map = this.workerProblemService.getWorkerProblemDetail(evt.getNo());
			String photoName=(String)map.get("PhotoName");
			WorkerPicUploadRule rule = new WorkerPicUploadRule(photoName, photoName.substring(0, 5));
			map.put("PhotoName", FileUploadUtils.getFileUrl(rule.getFullName()));
			if(map != null){
				BeanConvertUtil.mapToBean(map, respon);
			}
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
		WorkerProblemConfirmEvt evt=new WorkerProblemConfirmEvt();
		WorkerProblemConfirmResp respon=new WorkerProblemConfirmResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt = (WorkerProblemConfirmEvt)JSONObject.toBean(json,WorkerProblemConfirmEvt.class);
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
			WorkerProblem workerProblem = workerProblemService.get(WorkerProblem.class, evt.getNo());
			workerProblem.setStatus("2");
			workerProblem.setActionLog("EDIT");
			workerProblem.setLastUpdate(new Date());
			workerProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			workerProblem.setConfirmRemark(evt.getConfirmRemark());
			workerProblemService.update(workerProblem);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doReplenish")
	public void doReplenish(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerProblemConfirmEvt evt=new WorkerProblemConfirmEvt();
		WorkerProblemConfirmResp respon=new WorkerProblemConfirmResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt = (WorkerProblemConfirmEvt)JSONObject.toBean(json,WorkerProblemConfirmEvt.class);
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
			
			//更新工人问题反馈信息
			WorkerProblem workerProblem = workerProblemService.get(WorkerProblem.class, evt.getNo());
			workerProblem.setStatus("2");
			workerProblem.setActionLog("EDIT");
			workerProblem.setLastUpdate(new Date());
			workerProblem.setLastUpdatedBy(evt.getCzybh());
			workerProblem.setConfirmDate(new Date());
			workerProblem.setConfirmCzy(evt.getCzybh());
			workerProblem.setConfirmRemark(evt.getConfirmRemark());
			workerProblemService.update(workerProblem);
			
			//创建集成补货信息
			IntReplenish intReplenish=new IntReplenish();
			String no = workerProblemService.getSeqNo("tIntReplenish");
			intReplenish.setNo(no);
			intReplenish.setWkpbNo(evt.getNo());
			intReplenish.setCustCode(evt.getCustCode());
			intReplenish.setStatus("1");
			intReplenish.setLastUpdate(new Date());
			intReplenish.setLastUpdatedBy(evt.getCzybh());
			intReplenish.setActionLog("ADD");
			intReplenish.setExpired("F");
			intReplenish.setIsCupboard(evt.getIsCupboard());
			intReplenish.setSource("1");
			intReplenish.setRemarks(evt.getConfirmRemark());
			intReplenish.setAppCzy(evt.getCzybh());
			intReplenish.setDate(new Date());
			workerProblemService.save(intReplenish);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
