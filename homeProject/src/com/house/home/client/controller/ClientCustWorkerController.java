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
import org.springframework.web.bind.annotation.ResponseBody;

import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.service.AuthorityService;
import com.house.home.client.service.evt.CustManagementEvt;
import com.house.home.client.service.evt.CustWorkerEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustWorkerResp;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustWorkerAppService;
import com.house.home.service.project.CustWorkerService;
import com.house.home.service.project.WorkerService;


@RequestMapping("/client/custWorker")
@Controller
public class ClientCustWorkerController extends ClientBaseController  {
	@Autowired
	private CustWorkerService custWorkerService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private CustWorkerAppService custWorkerAppService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustWorkerList")
	public void getCustWorkerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker = new CustWorker();
			custWorker.setAddress(evt.getAddress());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setWorkerCode(evt.getWorkerCode());
			custWorker.setWorkerName(evt.getWorkerName());
			custWorker.setCzybh(evt.getCzybh());
			custWorkerService.getCustWorkerList(page,custWorker);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getWorkType12List")
	public void getWorkType12List(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			page.setPageSize(1000);
			custWorkerService.getWorkType12List(page,evt.getCzybh());
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getPrjRegionList")
	public void getIsSignList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			page.setPageSize(1000);
			custWorkerService.getPrjRegionList(page);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerList")
	public void getWorkerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			Worker worker = new Worker();
			worker.setNameChi(evt.getNameChi());
			worker.setWorkType12(evt.getWorkType12());
			worker.setIsSign(evt.getIsSign());
			worker.setWorkerClassify(evt.getWorkerClassify());
			worker.setPrjRegionCode(evt.getPrjRegionCode());
			worker.setCzybh(evt.getCzybh());
			custWorkerService.getWorkerList(page,worker);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
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
	
	@RequestMapping("/getAuthority")
	public void getAuthority(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		CustWorkerResp respon=new CustWorkerResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			
			// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//			List<Map<String,Object>> authorityList = authorityService.getAuthorityList(evt.getCzybh(),"0822");
//			for(int i=0;i<authorityList.size();i++){
//				if("查看".equals(authorityList.get(i).get("GNMC"))){
//					respon.setAuthorityView(true);
//				}
//				if("编辑".equals(authorityList.get(i).get("GNMC"))){
//					respon.setAuthorityAdd(true);
//				}
//			}
			if (czybmService.hasGNQXByCzybh(evt.getCzybh(), "0822", "查看")) { 
				respon.setAuthorityView(true);
			}
			
			if (czybmService.hasGNQXByCzybh(evt.getCzybh(), "0822", "编辑")) {
				respon.setAuthorityAdd(true);
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustWorkerAppList")
	public void getCustWorkerAppList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker = new CustWorker();
			custWorker.setAddress(evt.getAddress());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setDepartment2(evt.getDepartment2());
			custWorker.setCzybh(evt.getCzybh());
			custWorkerService.getCustWorkerAppList(page,custWorker);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAllCustomerList")
	public void getAllCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker = new CustWorker();
			custWorker.setAddress(evt.getAddress());
			custWorker.setIsAddAllInfo(evt.getIsAddAllInfo());
			custWorkerService.getAllCustomerList(page,custWorker);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getDepartment2List")
	public void getDepartment2List(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			page.setPageSize(1000);
			custWorkerService.getDepartment2List(page);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getAutoData")
	public void getAutoData(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		CustWorkerResp respon=new CustWorkerResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			
			String befWorkType12ConfString=custWorkerAppService.getCanArr(evt.getCustCode(), evt.getWorkType12(), "1");
			String itemArrive=custWorkerAppService.getCanArr(evt.getCustCode(), evt.getWorkType12(), "2");
			String workTypeConDay= custWorkerAppService.getWorkTypeConDay(evt.getCustCode(), evt.getWorkType12()); 
			int constructDay = Integer.parseInt(workTypeConDay);
			String appComeDateStr = custWorkerAppService.getAppComeDate(evt.getCustCode(), evt.getWorkType12());
			
			respon.setAppComeDateStr(appComeDateStr);
			respon.setConstructDay(constructDay);
			respon.setItemArrive(itemArrive);
			respon.setBefWorkType12ConfString(befWorkType12ConfString);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemArriveList")
	public void getItemArriveList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			page.setPageSize(1000);
			CustWorkerApp custWorkerApp = new CustWorkerApp();
			custWorkerApp.setCustCode(evt.getCustCode());
			custWorkerApp.setWorkType12(evt.getWorkType12());
			custWorkerAppService.findItemArrPageBySql(page,custWorkerApp);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBefConfirmList")
	public void getBefConfirmList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		BasePageQueryResp<CustWorkerResp> respon=new BasePageQueryResp<CustWorkerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			page.setPageSize(1000);
			CustWorkerApp custWorkerApp = new CustWorkerApp();
			custWorkerApp.setCustCode(evt.getCustCode());
			custWorkerApp.setWorkType12(evt.getWorkType12());
			custWorkerAppService.findWorkTypeBefPageBySql(page,custWorkerApp);
			List<CustWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerResp.class);
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
	
	@RequestMapping("/getNotify")
	public void getNotify(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		CustWorkerResp respon=new CustWorkerResp();
		
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
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorkerApp cwa1=custWorkerAppService.getByWorkerCode(evt.getCustCode(),evt.getWorkType12(),evt.getWorkerCode());
			if (cwa1!=null){
				respon.setAlreadHaveWorker(true);
			}else{
				respon.setAlreadHaveWorker(false);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getNeedWorkType2Req")
	public void getNeedWorkType2Req(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		CustWorkerResp respon=new CustWorkerResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			boolean needWorkType2Req = custWorkerAppService.getNeedWorkType2Req(evt.getCustCode(),evt.getWorkType12());
			respon.setNeedWorkType2Req(needWorkType2Req);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getIsExistsWorkerArr")
	public void getIsExistsWorkerArr(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		CustWorkerResp respon=new CustWorkerResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker = new CustWorker();
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setPk(evt.getPk());
			custWorker.setWorkerCode(evt.getWorkerCode());
			boolean isExistsWorkerArr = custWorkerService.getIsExistsWorkerArr(custWorker);
			respon.setIsExistsWorkerArr(isExistsWorkerArr);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveCustWorker")
	public void doSaveCustWorker(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
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
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("comeDate")) 
			json.put("comeDate", sdf.parse(json.get("comeDate").toString()));
			if(json.containsKey("planEnd")&&!"null".equals(json.get("planEnd").toString()))
			json.put("planEnd", sdf.parse(json.get("planEnd").toString()));
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker = new CustWorker();
			custWorker.setWorkerCode(evt.getWorkerCode());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setComeDate(evt.getComeDate());
			custWorker.setRemarks(evt.getRemarks());
			custWorker.setConstructDay(evt.getConstructDay());
			if(evt.getPlanEnd()!=null){
				custWorker.setPlanEnd(evt.getPlanEnd());
			}
			custWorker.setIsSysArrange("0");
			custWorker.setStatus("1");
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(getUserContext(request).getCzybh());
			custWorker.setExpired("F");
			custWorker.setAciontLog("Add");
			if("02".equals(evt.getWorkType12())){
				Customer customer=customerService.get(Customer.class, custWorker.getCustCode());
				if(customer!=null){
					customer.setIsWaterItemCtrl(evt.getIsWaterItemCtrl());
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(evt.getCzybh());
					customerService.update(customer);
				}
			}
			custWorkerService.save(custWorker);
			custWorkerService.updateBeginDateByWorkType12(custWorker);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveCustWorkerApp")
	public void doSaveCustWorkerApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
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
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("comeDate")) 
			json.put("comeDate", sdf.parse(json.get("comeDate").toString()));
			if(json.containsKey("planEnd")&&!"null".equals(json.get("planEnd").toString()))
			json.put("planEnd", sdf.parse(json.get("planEnd").toString()));
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorkerApp custWorkerApp=custWorkerAppService.get(CustWorkerApp.class, evt.getPk());
			CustWorker custWorker=new CustWorker();
			Customer customer=null;
			custWorkerApp.setActionLog("Edit");
			custWorkerApp.setExpired("F");
			custWorkerApp.setLastUpdate(new Date());
			custWorkerApp.setStatus("2");
			custWorkerApp.setWorkerCode(evt.getWorkerCode());
			custWorkerApp.setComeDate(evt.getComeDate());
			custWorkerApp.setLastUpdatedBy(evt.getCzybh());
			
			if(evt.getPlanEnd()!=null){
				custWorker.setPlanEnd(evt.getPlanEnd());
			}
			custWorker.setConstructDay(evt.getConstructDay());
			custWorker.setStatus("1");
			custWorker.setIsSysArrange("0");
			custWorker.setWorkerCode(evt.getWorkerCode());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setComeDate(evt.getComeDate());
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(getUserContext(request).getCzybh());
			custWorker.setRemarks(evt.getRemarks());
			custWorker.setAciontLog("Add");
			custWorker.setExpired("F");
			custWorkerService.save(custWorker);//生成工地工人安排数据
			custWorkerService.updateBeginDateByWorkType12(custWorker);
			
			Map<String , Object> map= custWorkerAppService.getCustPk();
			int pk = (Integer) map.get("pk");
			custWorkerApp.setCustWorkPk(pk);
			this.custWorkerAppService.update(custWorkerApp);//保存工人申请安排

			if(StringUtils.isNotBlank(evt.getCustCode())&&"02".equals(evt.getWorkType12())){
				customer=customerService.get(Customer.class, evt.getCustCode());
				if(customer!=null){
					customer.setIsWaterItemCtrl(evt.getIsWaterItemCtrl());
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(evt.getCzybh());
					customerService.update(customer);
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustWorkerDetail")
	public void getCustWorkerDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
		CustWorkerResp respon=new CustWorkerResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker =custWorkerService.get(CustWorker.class, evt.getCustWkPk());
			if(custWorker!=null){
				BeanUtilsEx.copyProperties(respon, custWorker);
				if (StringUtils.isNotBlank(custWorker.getWorkerCode())){
					Worker worker = workerService.get(Worker.class,custWorker.getWorkerCode());
					if (worker!=null){
						respon.setWorkerName(worker.getNameChi());
					}
				}
				if (StringUtils.isNotBlank(custWorker.getCustCode())){
					Customer customer = workerService.get(Customer.class,custWorker.getCustCode());
					if (customer!=null){
						respon.setIsWaterItemCtrl(customer.getIsWaterItemCtrl());
						respon.setAddress(customer.getAddress());
						respon.setProjectMan(customer.getProjectMan());
						if(StringUtils.isNotBlank(customer.getProjectMan())){
							Employee employee = employeeService.get(Employee.class,customer.getProjectMan());
							respon.setProjectManName(employee.getNameChi());
						}
					}
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateCustWorker")
	public void doUpdateCustWorker(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkerEvt evt=new CustWorkerEvt();
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
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("comeDate")) 
			json.put("comeDate", sdf.parse(json.get("comeDate").toString()));
			if(json.containsKey("endDate")&&!"null".equals(json.get("endDate").toString()))
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt = (CustWorkerEvt)JSONObject.toBean(json,CustWorkerEvt.class);
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
			CustWorker custWorker=custWorkerService.get(CustWorker.class, evt.getCustWkPk());
			custWorker.setWorkerCode(evt.getWorkerCode());
			custWorker.setStatus(evt.getStatus());
			custWorker.setComeDate(evt.getComeDate());
			if(evt.getEndDate()!=null){
				custWorker.setEndDate(evt.getEndDate());
			}
			
			custWorker.setRemarks(evt.getRemarks());
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(evt.getCzybh());
			custWorker.setAciontLog("Edit");
			if(evt.getPk()!=0){
				CustWorkerApp custWorkerApp = custWorkerAppService.get(CustWorkerApp.class, evt.getPk());
				if(custWorkerApp!=null){
					custWorkerApp.setWorkerCode(evt.getWorkerCode());
					custWorkerApp.setComeDate(evt.getComeDate());
					custWorkerApp.setLastUpdatedBy(evt.getCzybh());
					custWorkerApp.setLastUpdate(new Date());
					custWorkerApp.setActionLog("Edit");
					this.custWorkerAppService.update(custWorkerApp);
				}
			}
			custWorkerService.updateBeginDateByWorkType12(custWorker);
			
			if("02".equals(evt.getWorkType12())){
				Customer customer=customerService.get(Customer.class, custWorker.getCustCode());
				if(customer!=null){
					customer.setIsWaterItemCtrl(evt.getIsWaterItemCtrl());
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(evt.getCzybh());
					customerService.update(customer);
				}
			}
			
			this.custWorkerService.update(custWorker);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
}
