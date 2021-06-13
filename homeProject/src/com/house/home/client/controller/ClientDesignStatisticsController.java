package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.Result;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.DesignStatisticsEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.DesignStaticticsResp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.workflow.Department;
import com.house.home.service.query.DesignPlanResultAnalyService;
import com.house.home.service.workflow.DepartmentService;

@RequestMapping("/client/designStatistics")
@Controller
public class ClientDesignStatisticsController extends ClientBaseController{
	@Autowired
	private DesignPlanResultAnalyService designPlanResultAnalyService;
	@Autowired
	private DepartmentService departmentService ;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDesignStatisticsList")
	public void getFixDutyList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DesignStatisticsEvt evt=new DesignStatisticsEvt();
		BasePageQueryResp<DesignStaticticsResp> respon=new BasePageQueryResp<DesignStaticticsResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("beginDate")&&!"null".equals(json.get("beginDate").toString())) 
				json.put("beginDate", sdf.parse(json.get("beginDate").toString()));
			if(json.containsKey("endDate")&&!"null".equals(json.get("endDate").toString()))
				json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt = (DesignStatisticsEvt)JSONObject.toBean(json,DesignStatisticsEvt.class);
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
			Customer customer = new Customer();
			customer.setDateFrom(evt.getBeginDate());
			customer.setDateTo(evt.getEndDate());
			customer.setPeriod("2");
			if(StringUtils.isNotBlank(evt.getCode()) && !StringUtils.isNotBlank(evt.getNumber())){
				customer.setDepartment(evt.getCode());
				customer.setStatistcsMethod("4");
			}else if(StringUtils.isNotBlank(evt.getNumber())){
				customer.setEmpCode(evt.getNumber());
				customer.setStatistcsMethod("1");
			}
			designPlanResultAnalyService.findPageBySql(page, customer);
			List<DesignStaticticsResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DesignStaticticsResp.class);
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
	
	@RequestMapping("/getInitData")
	public void getCustRight(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DesignStatisticsEvt evt=new DesignStatisticsEvt();
		DesignStaticticsResp respon=new DesignStaticticsResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DesignStatisticsEvt)JSONObject.toBean(json,DesignStatisticsEvt.class);
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
			Employee employee = designPlanResultAnalyService.get(Employee.class, getUserContext(request).getEmnum());
			Department department = designPlanResultAnalyService.get(Department.class, employee.getDepartment());
			respon.setCode(employee.getDepartment());
			respon.setDesc2(department.getDesc2());
			respon.setCustRight(getUserContext(request).getCustRight());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDeptList")
	public void getDeptList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DesignStatisticsEvt evt=new DesignStatisticsEvt();
		BasePageQueryResp<DesignStaticticsResp> respon=new BasePageQueryResp<DesignStaticticsResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DesignStatisticsEvt)JSONObject.toBean(json,DesignStatisticsEvt.class);
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
			String custRight=getUserContext(request).getCustRight();
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(-1);
			if(StringUtils.isNotBlank(evt.getCode())){
				departmentService.findChildDeptList(page,evt.getCode(),evt.getSearchDescr());
			}else{
				if(custRight.equals("3")){
					departmentService.findDept1List(page,evt.getSearchDescr());
				}else if(custRight.equals("2")){
					departmentService.findDeptList(page,evt.getCzybh(),evt.getSearchDescr(),"0");
				}
			}
			List<DesignStaticticsResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DesignStaticticsResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getEmpList")
	public void getEmpList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DesignStatisticsEvt evt=new DesignStatisticsEvt();
		BasePageQueryResp<DesignStaticticsResp> respon=new BasePageQueryResp<DesignStaticticsResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DesignStatisticsEvt)JSONObject.toBean(json,DesignStatisticsEvt.class);
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
			page.setPageSize(-1);
			departmentService.findEmpList(page,evt.getCode(),evt.getSearchDescr());
			List<DesignStaticticsResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DesignStaticticsResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDetailList")
	public void getDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DesignStatisticsEvt evt=new DesignStatisticsEvt();
		BasePageQueryResp<DesignStaticticsResp> respon=new BasePageQueryResp<DesignStaticticsResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("beginDate")&&!"null".equals(json.get("beginDate").toString())) 
				json.put("beginDate", sdf.parse(json.get("beginDate").toString()));
			if(json.containsKey("endDate")&&!"null".equals(json.get("endDate").toString()))
				json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt = (DesignStatisticsEvt)JSONObject.toBean(json,DesignStatisticsEvt.class);
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
			page.setPageSize(-1);
			Customer customer = new Customer();
			customer.setDateFrom(evt.getBeginDate());
			customer.setDateTo(evt.getEndDate());
			customer.setStatistcsMethod(evt.getType());
			if(StringUtils.isNotBlank(evt.getCode())){
				customer.setDepartment(evt.getCode());
			}
			designPlanResultAnalyService.findDesignStatistics(page, customer);
			System.out.println(page.getResult());
			List<DesignStaticticsResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DesignStaticticsResp.class);
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
