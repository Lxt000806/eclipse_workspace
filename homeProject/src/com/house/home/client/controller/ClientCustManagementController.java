package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.service.AuthorityService;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.CustManagementEvt;
import com.house.home.client.service.evt.ResrCustEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.BuilderNumResp;
import com.house.home.client.service.resp.CustConResp;
import com.house.home.client.service.resp.CustManagementDetailResp;
import com.house.home.client.service.resp.CustManagementResp;
import com.house.home.client.service.resp.CustNetCnlResp;
import com.house.home.client.service.resp.DesignDemoResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.BuilderNum;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.BuilderNumService;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.BaseItemPlanService;
import com.house.home.service.design.CustConService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.ItemPlanService;

@RequestMapping("/client/custManagement")
@Controller
public class ClientCustManagementController extends ClientBaseController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustConService custConService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private BuilderNumService builderNumService;
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private BaseItemPlanService baseItemPlanService;
	@Autowired
	private ItemPlanService itemPlanService;
	@Autowired
	private CustPayService custPayService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerList")
	public void getCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		BasePageQueryResp<CustManagementResp> respon=new BasePageQueryResp<CustManagementResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			Customer customer = new Customer();
			customer.setAddress(evt.getAddress());
			customer.setStatus(evt.getStatus());
			customer.setDesignMan(evt.getDesignMan());
			customer.setBusinessMan(evt.getBusinessMan());
			UserContext uc = new UserContext();
			Czybm czybm = czybmService.get(Czybm.class, evt.getCzybh());
			uc.setCzybh(evt.getCzybh());
			uc.setCustRight(czybm.getCustRight());
			uc.setEmnum(czybm.getEmnum());
			customerService.findPageBySql(page, customer, uc);
			List<CustManagementResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustManagementResp.class);
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
	
	@RequestMapping("/getCustManagementDetail")
	public void getCustManagementDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		CustManagementDetailResp respon=new CustManagementDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if (customer!=null){
				BeanUtilsEx.copyProperties(respon, customer);
				if (StringUtils.isNotBlank(customer.getDesignMan())){
					Employee employee = employeeService.get(Employee.class,customer.getDesignMan());
					if (employee!=null){
						respon.setDesignMan(employee.getNumber());
						respon.setDesignManDescr(employee.getNameChi());
					}
				}
				if (StringUtils.isNotBlank(customer.getBusinessMan())){
					Employee employee = employeeService.get(Employee.class,customer.getBusinessMan());
					if (employee!=null){
						respon.setBusinessMan(employee.getNumber());
						respon.setBusinessManDescr(employee.getNameChi());
					}
				}
				if (StringUtils.isNotBlank(customer.getAgainMan())){
					Employee employee = employeeService.get(Employee.class,customer.getAgainMan());
					if (employee!=null){
						respon.setAgainMan(employee.getNumber());
						respon.setAgainManDescr(employee.getNameChi());
					}
				}
				if (StringUtils.isNotBlank(customer.getBuilderCode())){
					Builder builder = builderService.get(Builder.class,customer.getBuilderCode());
					if (builder!=null){
						respon.setBuilderCodeDescr(builder.getDescr());
					}
				}
				List<Map<String, Object>> list = custStakeholderService.
								getListByCustCodeAndRole(evt.getCustCode(),"02");
				if(list != null && list.size() >0){
					Employee accountRoomMan = employeeService.get(Employee.class, list.get(0).get("EmpCode").toString());
					if(accountRoomMan != null){
						respon.setAccountRoomMan(accountRoomMan.getNumber());
						respon.setAccountRoomManDescr(accountRoomMan.getNameChi());
					}
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustConList")
	public void getCustConList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		BasePageQueryResp<CustConResp> respon=new BasePageQueryResp<CustConResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			page.setPageOrder("DESC");
			CustCon custCon = new CustCon();
			custCon.setCustCode(evt.getCustCode());
			custCon.setExpired("F");
			UserContext uc = new UserContext();
			Czybm czybm = czybmService.get(Czybm.class, evt.getCzybh());
			uc.setCzybh(evt.getCzybh());
			uc.setCustRight(czybm.getCustRight());
			uc.setEmnum(czybm.getEmnum());
			custConService.findPageBySql(page, custCon, uc);
			List<CustConResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustConResp.class);
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
	
	@RequestMapping("/doUpdateCustCon")
	public void doUpdateCustCon(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
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
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("nextConDate")&&!"null".equals(json.get("nextConDate").toString())) 
			json.put("nextConDate", sdf.parse(json.get("nextConDate").toString()));
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			CustCon custCon = null;
			custCon = custConService.get(CustCon.class, evt.getCustConPk());
			if(StringUtils.isNotEmpty(evt.getRemarks())){
				custCon.setRemarks(evt.getRemarks());
			}
			if(StringUtils.isNotEmpty(evt.getExpire())){
				custCon.setExpired(evt.getExpire());
			}
			custCon.setNextConDate(evt.getNextConDate());
			custCon.setConWay(evt.getConWay());
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(evt.getCzybh());
			custCon.setActionLog("EDIT");
			custConService.update(custCon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveCustContact")
	public void doSaveCustContact(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
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
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("nextConDate")&&!"null".equals(json.get("nextConDate").toString())) 
			json.put("nextConDate", sdf.parse(json.get("nextConDate").toString()));
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			CustCon custCon = new CustCon();
			custCon.setCustCode(evt.getCustCode());
			custCon.setConDate(new Date());
			custCon.setRemarks(evt.getRemarks());
			custCon.setConMan(evt.getCzybh());
			custCon.setNextConDate(evt.getNextConDate());
			custCon.setConWay(evt.getConWay());
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(evt.getCzybh());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			custConService.save(custCon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getBuilderNumList")
	public void getBuilderNumList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		BuilderNumResp respon=new BuilderNumResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			
			BuilderNum builderNum =new BuilderNum();
			builderNum.setBuilderNum(evt.getBuilderNum());
			builderNum.setBuilderCode(evt.getBuilderCode());
			builderNumService.findPageBySql(page, builderNum);
			List<BuilderNumResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), BuilderNumResp.class);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getCustTypeList")
	public void getCustTypeList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		CustManagementResp respon=new CustManagementResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			CustType custType = new CustType();
			custTypeService.findPageBySql(page, custType);
			List<CustManagementResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustManagementResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getAuthority")
	public void getAuthority(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		CustManagementResp respon=new CustManagementResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
//			Page page = new Page();
//			page.setPageNo(-1);
//			page.setPageSize(10000);
//			String czybh = getUserContext(request).getCzybh();
//			authorityService.getCZYGNQX(page,czybh, "0820");
//			List<CustManagementResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustManagementResp.class);
			String czybh = getUserContext(request).getCzybh();
			List<CustManagementResp> listBean = BeanConvertUtil.mapToBeanList(czybmService.findGNQXByCzybhAndMkdm(czybh, "0820"), CustManagementResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getBeforeParams")
	public void getBeforeParams(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		CustManagementResp respon=new CustManagementResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			Czybm czybm = czybmService.get(Czybm.class, getUserContext(request).getCzybh());
			czybm.setCustType(czybm.getCustType().trim());
			boolean hasAddress=false,hasSwt=false,hasBaseItemPlan=false,hasItemPlan=false,hasCustPay=false;
			if(StringUtils.isNotEmpty(evt.getCustCode())){
				hasBaseItemPlan = baseItemPlanService.hasBaseItemPlan(evt.getCustCode());
				hasItemPlan = itemPlanService.hasItemPlan(evt.getCustCode());
				hasCustPay = custPayService.hasCustPay(evt.getCustCode());
			}
			// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//			hasAddress = authorityService.hasAuthForCzy(czybm.getCzybh(),578);
//			hasSwt = authorityService.hasAuthForCzy(czybm.getCzybh(),579);
			hasAddress = czybmService.hasGNQXByCzybh(czybm.getCzybh(), "0201", "修改楼盘信息");
			hasSwt = czybmService.hasGNQXByCzybh(czybm.getCzybh(), "0201", "商务通");
			String hasAgainMan=czybmService.getHasAgainMan();
			respon.setHasAddress(hasAddress);
			respon.setHasAgainMan(hasAgainMan);
			respon.setHasSwt(hasSwt);
			respon.setCustRight(getUserContext(request).getCustRight());
			respon.setCustType(czybm.getCustType());
			respon.setSaleType(czybm.getSaleType());
			respon.setHasBaseItemPlan(hasBaseItemPlan);
			respon.setHasItemPlan(hasItemPlan);
			respon.setHasCustPay(hasCustPay);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getIsDelivBuilder")
	public void getIsDelivBuilder(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		CustManagementResp respon=new CustManagementResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			System.out.println(evt.getBuilderCode());
			boolean isDelivBuilder=customerService.getExistBuilderNum(evt.getBuilderCode());
			respon.setIsDelivBuilder(isDelivBuilder);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveCustomer")
	public void doSaveCustomer(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
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
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			Customer customer =new Customer();
			customer.setSaleType(evt.getSaleType());
			customer.setOldCode(evt.getOldCustCode());
			customer.setDescr(evt.getNameChi());
			customer.setGender(evt.getGender());
			customer.setMobile1(evt.getMobile1());
			customer.setCustType(evt.getCustType());
			customer.setSource(evt.getSource());
			customer.setNetChanel(evt.getNetChanel());
			customer.setBuilderCode(evt.getBuilderCode());
			customer.setBuilderNum(evt.getBuilderNum());
			customer.setAddress(evt.getAddress());
			customer.setLayout(evt.getLayout());
			customer.setArea(evt.getArea());
			customer.setDesignMan(evt.getDesignMan());
			customer.setBusinessMan(evt.getBusinessMan());
			customer.setAgainMan(evt.getAgainMan());
			customer.setStatus(evt.getStatus());
			customer.setConstructType(evt.getConstructType());
			customer.setDesignStyle(evt.getDesignType());
			customer.setPlanAmount(evt.getPlanAmount());
			customer.setRemarks(evt.getRemarks());
			customer.setActionLog("ADD");
			customer.setLastUpdate(new Date());
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customer.setExpired("F");
			customer.setCrtDate(new Date());
			customer.setMobile2(evt.getMobile2());
			customer.setEmail2(evt.getEmail2());
			customer.setAccountRoomMan(evt.getAccountRoomMan());
			customer.setResrCustCode(evt.getResrCustCode());
			customer.setMeasureDate(evt.getMeasureDate());
			if("2".equals(evt.getStatus())){
				customer.setVisitDate(new Date());
				customer.setComeTimes(1);
			}
			Builder builder=null;
			CustType custType = null;
			String builderAndBuilderNum="";
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				builder=builderService.get(Builder.class, customer.getBuilderCode());
			}
			if(StringUtils.isNotBlank(customer.getCustType())){
				custType=custTypeService.get(CustType.class, customer.getCustType());
			}
			if ("1".equals(customer.getSaleType())){
				Map<String,Object> cust = customerService.getByAddress(customer.getAddress(),evt.getCzybh());
				if (cust!=null){
					Xtdm xtdm = xtdmService.getByIdAndCbm("CUSTOMERSTATUS", cust.get("Status").toString());
					String str = xtdm==null?"":xtdm.getNote();
					respon.setReturnCode("400001");
					respon.setReturnInfo("已经存在"+customer.getAddress()+"!该客户是【"+str+"】状态");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}													
			//判读安楼盘类型为4
			if(StringUtils.isNotBlank(customer.getBuilderNum())&&customerService.getIsDelivBuilder(customer.getBuilderCode())){
				if(!customerService.getIsExistBuilderNum(customer.getBuilderCode(),customer.getBuilderNum())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该项目不存在楼栋号："+customer.getBuilderNum());
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				
			}
			if ("4".equals(customerService.getAddressType(customer.getBuilderCode()))&& "1".equals(custType.getIsAddAllInfo())){
				if(StringUtils.isNotBlank(customer.getBuilderNum())){
					if(builder!=null){
						 builderAndBuilderNum=builder.getDescr()+customer.getBuilderNum();
					}
					int i = customer.getAddress().indexOf(builderAndBuilderNum);
					if(i!=0){
						respon.setReturnCode("400001");
						respon.setReturnInfo("楼盘地址必须以项目名称+楼栋号为前缀！");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
				}
			}
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			if("2".equals(customer.getStatus())&&null==customer.getVisitDate()){
				customer.setVisitDate(customer.getCrtDate());
			}
			String str = checkAddress(customer,request);
			if (StringUtils.isNotBlank(str)){
				respon.setReturnCode("400001");
				respon.setReturnInfo(str);
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Result result = customerService.saveForProc(customer);
			if (!"1".equals(result.getCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("添加客户信息失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateCustomer")
	public void doUpdateCustomer(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
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
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			customer.setCode(evt.getCustCode());
			customer.setOldCode(evt.getOldCustCode());
			customer.setDescr(evt.getNameChi());
			customer.setGender(evt.getGender());
			customer.setMobile1(evt.getMobile1());
			customer.setCustType(evt.getCustType());
			customer.setSource(evt.getSource());
			customer.setNetChanel(evt.getNetChanel());
			customer.setBuilderCode(evt.getBuilderCode());
			customer.setBuilderNum(evt.getBuilderNum());
			customer.setAddress(evt.getAddress());
			customer.setLayout(evt.getLayout());
			customer.setArea(evt.getArea());
			customer.setDesignMan(evt.getDesignMan());
			customer.setBusinessMan(evt.getBusinessMan());
			customer.setAgainMan(evt.getAgainMan());
			customer.setStatus(evt.getStatus());
			customer.setConstructType(evt.getConstructType());
			customer.setDesignStyle(evt.getDesignType());
			customer.setPlanAmount(evt.getPlanAmount());
			customer.setRemarks(evt.getRemarks());
			customer.setActionLog("Edit");
			customer.setLastUpdate(new Date());
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customer.setExpired("F");
			customer.setMobile2(evt.getMobile2());
			customer.setEmail2(evt.getEmail2());
			customer.setAccountRoomMan(evt.getAccountRoomMan());
			Builder builder=null;
			CustType custType = null;
			String builderAndBuilderNum="";
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				builder=builderService.get(Builder.class, customer.getBuilderCode());
			}
			if(StringUtils.isNotBlank(customer.getCustType())){
				custType=custTypeService.get(CustType.class, customer.getCustType());
			}
			if(StringUtils.isNotBlank(customer.getBuilderNum())&&customerService.getIsDelivBuilder(customer.getBuilderCode())){
				if(!customerService.getIsExistBuilderNum(customer.getBuilderCode(),customer.getBuilderNum())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该项目不存在楼栋号："+customer.getBuilderNum());
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				
			}
			if ("4".equals(customerService.getAddressType(customer.getBuilderCode())) && "1".equals(custType.getIsAddAllInfo())){
				if(StringUtils.isNotBlank(customer.getBuilderNum())){
					if(builder!=null){
						 builderAndBuilderNum=builder.getDescr()+customer.getBuilderNum();
					}
					int i = customer.getAddress().indexOf(builderAndBuilderNum);
					if(i!=0){
						respon.setReturnCode("400001");
						respon.setReturnInfo("楼盘地址必须以项目名称+楼栋号为前缀！");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
				}
			}
			if ("1".equals(customer.getSaleType())){
				Map<String,Object> cust = customerService.getByAddress(customer.getAddress(),evt.getCzybh());
				if (cust!=null&&!customer.getCode().equals(cust.get("Code").toString())){
					Xtdm xtdm = xtdmService.getByIdAndCbm("CUSTOMERSTATUS", cust.get("Status").toString());
					String str = xtdm==null?"":xtdm.getNote();
					respon.setReturnCode("400001");
					respon.setReturnInfo("已经存在"+customer.getAddress()+"!该客户是【"+str+"】状态");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			
			String str = checkAddress(customer,request);
			if (StringUtils.isNotBlank(str)){
				respon.setReturnCode("400001");
				respon.setReturnInfo(str);
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Customer ct = customerService.get(Customer.class, customer.getCode());
			if (ct!=null){
				customer.setOldStatus(ct.getStatus());
			}
			String hasAgainMan=czybmService.getHasAgainMan();
			if("0".equals(hasAgainMan)){
				if("6".equals(customer.getSource())){
					customer.setAgainMan("");
				}
			}
			Result result = customerService.updateForProc(customer);
			if (!"1".equals(result.getCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("修改客户信息失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	public String checkAddress(Customer customer, HttpServletRequest request){
		return customerService.checkAddress(customer,getUserContext(request));
	}
	
	@RequestMapping("/doVisit")
	public void doVisit(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
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
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if(!"1".equals(customer.getStatus())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("只有未到公司的客户才可进行到店操作！");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			customer.setComeTimes(1);
			customer.setStatus("2");
			customer.setVisitDate(new Date());
			customer.setLastUpdate(new Date());
			customer.setActionLog("EDIT");
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customerService.update(customer);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doExpired")
	public void doExpired(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
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
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			
			String hasPay = customerService.hasPay(evt.getCustCode());
			if(StringUtils.isNotBlank(hasPay)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("存在客户付款，不允许过期！");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			customer.setLastUpdate(new Date());
			customer.setActionLog("EDIT");
			customer.setExpired("T");
			customer.setLastUpdatedBy(getUserContext(request).getCzybh());
			customerService.update(customer);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getNeedAgainMan")
	public void getNeedAgainMan(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustManagementEvt evt=new CustManagementEvt();
		CustManagementResp respon=new CustManagementResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustManagementEvt)JSONObject.toBean(json,CustManagementEvt.class);
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
			String needAgainMan="";
			Map<String,Object> chanelMap = customerService.isNeedAgainMan(evt.getNetChanel());
			if(chanelMap != null){
				needAgainMan=chanelMap.get("NeedAgainMan").toString();
			}
			respon.setNeedAgainMan(needAgainMan);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getCustNetCnlList")
	public void getCustNetCnlList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		BasePageQueryResp<CustNetCnlResp> respon=new BasePageQueryResp<CustNetCnlResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ResrCustEvt)JSONObject.toBean(json,ResrCustEvt.class);
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
			List <Map<String, Object>> custNetCnlList = customerService.getCustNetCnlList();
			List<CustNetCnlResp> listBean=BeanConvertUtil.mapToBeanList(custNetCnlList, CustNetCnlResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getCustNetCnlListBySource")
	public void getCustNetCnlListBySource(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		BasePageQueryResp<CustNetCnlResp> respon=new BasePageQueryResp<CustNetCnlResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ResrCustEvt)JSONObject.toBean(json,ResrCustEvt.class);
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
			List <Map<String, Object>> custNetCnlList = customerService.getCustNetCnlListBySource(evt.getSource());
			List<CustNetCnlResp> listBean=BeanConvertUtil.mapToBeanList(custNetCnlList, CustNetCnlResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
}
