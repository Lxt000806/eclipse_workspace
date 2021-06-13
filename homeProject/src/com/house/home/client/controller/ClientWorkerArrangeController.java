package com.house.home.client.controller;

import java.text.SimpleDateFormat;
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
import com.house.home.client.service.evt.WorkerArrangeEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.WorkerArrangeResp;
import com.house.home.client.service.resp.WorkType12Resp;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.WorkerArrange;
import com.house.home.service.project.WorkerArrangeService;

@RequestMapping("/client/workerArrange")
@Controller
public class ClientWorkerArrangeController extends ClientBaseController{
	
	@Autowired
	private WorkerArrangeService workerArrangeService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getOrderWorkType12List")
	public void getOrderWorkType12List(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BasePageQueryResp<WorkType12Resp> respon=new BasePageQueryResp<WorkType12Resp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			page.setPageNo(-1);
			page.setPageSize(100);
			workerArrangeService.getOrderWorkType12List(page);
			List<WorkType12Resp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkType12Resp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerArrangeList")
	public void getWorkerArrangeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BasePageQueryResp<WorkerArrangeResp> respon=new BasePageQueryResp<WorkerArrangeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			page.setPageNo(-1);
			page.setPageSize(100);
			
			Employee employee = workerArrangeService.get(Employee.class, evt.getCzybh());
			evt.setDepartment1(employee.getDepartment1());
			evt.setDepartment2(employee.getDepartment2());
			workerArrangeService.getWorkerArrangeList(page, evt);
			List<WorkerArrangeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerArrangeResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getOrderNoList")
	public void getOrderNoList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BasePageQueryResp<WorkerArrangeResp> respon=new BasePageQueryResp<WorkerArrangeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("date")) 
			json.put("date", sdf.parse(json.get("date").toString()));
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			page.setPageNo(-1);
			page.setPageSize(100);
			Employee employee = workerArrangeService.get(Employee.class, evt.getCzybh());
			evt.setDepartment1(employee.getDepartment1());
			evt.setDepartment2(employee.getDepartment2());
			workerArrangeService.getOrderNoList(page,evt);
			List<WorkerArrangeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerArrangeResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAddresses")
	public void getAddresses(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BasePageQueryResp<WorkerArrangeResp> respon=new BasePageQueryResp<WorkerArrangeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			workerArrangeService.getAddresses(page,evt);
			List<WorkerArrangeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerArrangeResp.class);
			if(listBean.size()==0){
				respon.setNoListTip("无楼盘可进行预约");
			}
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveOrderWorker")
	public void doSaveOrderWorker(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			
			WorkerArrange workerArrange = workerArrangeService.get(WorkerArrange.class, evt.getPk());
			workerArrange.setCustCode(evt.getCustCode());
			workerArrange.setCzybh(evt.getCzybh());
			workerArrange.setOrderDate(new Date());
			workerArrange.setLastUpdate(new Date());
			workerArrange.setLastUpdatedBy(evt.getCzybh());
			Result result = workerArrangeService.doOrder(getUserContext(request), workerArrange);
			if(!result.isSuccess()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			respon.setReturnInfo(e.getMessage());
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getOrderWorkerDetailList")
	public void getOrderWorkerDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BasePageQueryResp<WorkerArrangeResp> respon=new BasePageQueryResp<WorkerArrangeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("date")) 
			json.put("date", sdf.parse(json.get("date").toString()));
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			page.setPageNo(-1);
			page.setPageSize(100);
			Employee employee = workerArrangeService.get(Employee.class, evt.getCzybh());
			evt.setDepartment1(employee.getDepartment1());
			evt.setDepartment2(employee.getDepartment2());
			workerArrangeService.getOrderWorkerDetailList(page,evt);
			List<WorkerArrangeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerArrangeResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getOrderedList")
	public void getOrderedList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerArrangeEvt evt=new WorkerArrangeEvt();
		BasePageQueryResp<WorkerArrangeResp> respon=new BasePageQueryResp<WorkerArrangeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("date")) 
			json.put("date", sdf.parse(json.get("date").toString()));
			evt = (WorkerArrangeEvt)JSONObject.toBean(json,WorkerArrangeEvt.class);
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
			workerArrangeService.getOrderedList(page,evt);
			List<WorkerArrangeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerArrangeResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
