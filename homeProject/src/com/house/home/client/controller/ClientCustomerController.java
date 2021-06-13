package com.house.home.client.controller;

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
import com.house.framework.commons.fileUpload.impl.WorkerSignPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseItemReqQueryEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.CustComplantEvt;
import com.house.home.client.service.evt.CustEvalEvt;
import com.house.home.client.service.evt.CustomerEvt;
import com.house.home.client.service.evt.CustomerEvt;
import com.house.home.client.service.evt.CustomerInfoEvt;
import com.house.home.client.service.evt.CustomerQueryEvt;
import com.house.home.client.service.evt.CustomerRightQueryEvt;
import com.house.home.client.service.evt.FixDutyEvt;
import com.house.home.client.service.evt.GetCustomerEvt;
import com.house.home.client.service.evt.GetSddwtListEvt;
import com.house.home.client.service.evt.GetShowBuildDetailEvt;
import com.house.home.client.service.evt.GoJqGridForOAEvt;
import com.house.home.client.service.evt.ItemQueryEvt;
import com.house.home.client.service.evt.ItemSentListEvt;
import com.house.home.client.service.evt.PrjReportEvt;
import com.house.home.client.service.evt.ItemReqCountEvt;
import com.house.home.client.service.evt.RcvActEvt;
import com.house.home.client.service.evt.WaterMarkInfoEvt;
import com.house.home.client.service.resp.BaseItemChgDetailQueryResp;
import com.house.home.client.service.resp.BaseItemChgQueryResp;
import com.house.home.client.service.resp.BaseItemReqQueryResp;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryAliYunResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ContractResp;
import com.house.home.client.service.resp.CostListQueryResp;
import com.house.home.client.service.resp.CustComplaintResp;
import com.house.home.client.service.resp.CustConstractPayResp;
import com.house.home.client.service.resp.CustomerCostDetailResp;
import com.house.home.client.service.resp.CustomerDetailResp;
import com.house.home.client.service.resp.CustomerPayDetailResp;
import com.house.home.client.service.resp.CustomerQueryResp;
import com.house.home.client.service.resp.CustomerResp;
import com.house.home.client.service.resp.FixDutyDetailResp;
import com.house.home.client.service.resp.GdDetailResp;
import com.house.home.client.service.resp.GetCustomerResp;
import com.house.home.client.service.resp.GetRcvActResp;
import com.house.home.client.service.resp.GetSddwtListResp;
import com.house.home.client.service.resp.GetShowBuildDetailResp;
import com.house.home.client.service.resp.GojqGridForOAResp;
import com.house.home.client.service.resp.GxrDetailResp;
import com.house.home.client.service.resp.ItemAppDetailPageQueryResp;
import com.house.home.client.service.resp.ItemAppDetailQueryResp;
import com.house.home.client.service.resp.ItemAppQueryResp;
import com.house.home.client.service.resp.ItemChangeDetailQueryResp;
import com.house.home.client.service.resp.ItemChangeQueryResp;
import com.house.home.client.service.resp.ItemReqCountResp;
import com.house.home.client.service.resp.ItemReqQueryResp;
import com.house.home.client.service.resp.ItemSendDetailListResp;
import com.house.home.client.service.resp.ItemSendListResp;
import com.house.home.client.service.resp.PayInfoDetailResp;
import com.house.home.client.service.resp.PrjReportResp;
import com.house.home.client.service.resp.RollResp;
import com.house.home.client.service.resp.TechPhotoResp;
import com.house.home.client.service.resp.WaterMarkInfoResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustEval;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustComplaint;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.PrjProgConfirm;
import com.house.home.entity.project.WorkSign;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ShowService;
import com.house.home.service.basic.SignInService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustEvalService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.project.BaseItemChgDetailService;
import com.house.home.service.project.ItemChgDetailService;
import com.house.home.service.project.PrjProgCheckService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.project.WorkerService;
import com.house.home.service.query.CustComplaintService;

/**
 * 客户相关的接口
 * @author 
 *
 */
@RequestMapping("/client/customer")
@Controller
public class ClientCustomerController extends ClientBaseController{
	@Autowired
	private CustomerService customerService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;
	@Autowired
	private ItemChgDetailService itemChgDetailService;
	@Autowired
	private BaseItemChgDetailService baseItemChgDetailService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustEvalService custEvalService;
	@Autowired
	private CustComplaintService custComplaintService;
	@Autowired
	private ShowService showService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private PrjProgConfirmService prjProgConfirmService;
	@Autowired
	private PrjProgCheckService prjProgCheckService;
	@Autowired
	private SignInService signInService;
	/**
	 * 查看客户列表接口（项目经理）
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerList")
	public void getCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt=new CustomerQueryEvt();
		BasePageQueryResp<CustomerQueryResp> respon=new BasePageQueryResp<CustomerQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt)JSONObject.toBean(json,CustomerQueryEvt.class);
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
			if (StringUtils.isBlank(evt.getId())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			page.setPageOrderBy("ConfirmBegin");
			page.setPageOrder("Desc");
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customer.setAddress(evt.getAddress());
			customer.setStatus(evt.getStatus());
			customer.setSaleIndependence(evt.getSaleIndependence());
			customer.setExcludeComplete(evt.getExcludeComplete());
			customerService.findPageBySql_client(page, customer, null);
			List<CustomerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerQueryResp.class);
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
	/**
	 * 查看客户列表接口（项目经理）
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerList_job")
	public void getCustomerList_job(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt=new CustomerQueryEvt();
		BasePageQueryResp<CustomerQueryResp> respon=new BasePageQueryResp<CustomerQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt)JSONObject.toBean(json,CustomerQueryEvt.class);
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
			if (StringUtils.isBlank(evt.getId())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			page.setPageOrderBy("ConfirmBegin");
			page.setPageOrder("Desc");
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customer.setAddress(evt.getAddress());
			customer.setStatus(evt.getStatus());
			customerService.findPageBySql_job(page, customer, null);
			List<CustomerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerQueryResp.class);
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
	/**
	 * 查看客户列表接口（施工未报备）
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerList_report")
	public void getCustomerList_report(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt=new CustomerQueryEvt();
		BasePageQueryResp<CustomerQueryResp> respon=new BasePageQueryResp<CustomerQueryResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt)JSONObject.toBean(json,CustomerQueryEvt.class);
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
			if (StringUtils.isBlank(evt.getId())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			page.setPageOrderBy("ConfirmBegin");
			page.setPageOrder("Desc");
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customer.setAddress(evt.getAddress());
			customer.setStatus(evt.getStatus());
			customerService.findPageBySql_report(page, customer, null);
			List<CustomerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerQueryResp.class);
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
	/**
	 * 查看客户列表接口（不限部门权限）
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAllCustomerList")
	public void getAllCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt=new CustomerQueryEvt();
		BasePageQueryResp<CustomerQueryResp> respon=new BasePageQueryResp<CustomerQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt)JSONObject.toBean(json,CustomerQueryEvt.class);
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
			page.setPageOrderBy("Address,a.ConfirmBegin");
			page.setPageOrder("Desc");
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customer.setAddress(evt.getAddress());
			System.out.println("distan:"+evt.getDistance());
			System.out.println("evt.getLontitude():"+evt.getLontitude());
			System.out.println("evt.getLatitude():"+evt.getLatitude());
			customer.setDistance(evt.getDistance());
			customer.setLontitude(evt.getLontitude());
			customer.setLatitude(evt.getLatitude());
			customer.setProjectManDescr(evt.getProjectManDescr());
			if("1".equals(evt.getStatus())){
				customer.setEndCode("3");
			}
			
			customerService.getAllCustomerList(page, customer, null);
			List<CustomerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerQueryResp.class);
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
	/**
	 * 根据客户权限查看客户列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerListByCustRight")
	public void getCustomerListByCustRight(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerRightQueryEvt evt=new CustomerRightQueryEvt();
		BasePageQueryResp<CustomerQueryResp> respon=new BasePageQueryResp<CustomerQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerRightQueryEvt)JSONObject.toBean(json,CustomerRightQueryEvt.class);
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
			page.setPageOrderBy(evt.getPageOrderBy());//add by zb on 20190619
			page.setPageOrder("asc");
			Customer customer = new Customer();
			customer.setAddress(evt.getAddress());
			customer.setStakeholder(evt.getStakeholder());
			customer.setHaveGd(evt.getHaveGd());
			customer.setDistance(evt.getDistance());
			customer.setLontitude(evt.getLontitude());
			customer.setLatitude(evt.getLatitude());
			customer.setPrjItem(evt.getPrjItem());
			customer.setSaleIndependence(evt.getSaleIndependence());
			customer.setStakeholderRoll(evt.getStakeholderRoll());
			customer.setDepartment2Descr(evt.getDepartment2Descr());
			customer.setWorkType12(evt.getWorkType12());
			UserContext uc = new UserContext();
			Czybm czybm = czybmService.get(Czybm.class, evt.getCzybh());
			uc.setCzybh(evt.getCzybh());
			uc.setCustRight(czybm.getCustRight());
			uc.setEmnum(czybm.getEmnum());
			customerService.findPageBySql_custRight(page, customer, uc);
			List<CustomerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerQueryResp.class);
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
	/**
	 * 查看客户详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCustomerDetail")
	public void getCustomerDetail(HttpServletRequest request, HttpServletResponse response){
//		try {
//			Thread.currentThread().sleep(5000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		CustomerDetailResp respon=new CustomerDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			Customer customer = customerService.get(Customer.class, evt.getId());
			if (customer!=null){
				BeanUtilsEx.copyProperties(respon, customer);
				if (StringUtils.isNotBlank(customer.getCustLevel())){
					Xtdm xtdm = xtdmService.getByIdAndCbm("CUSTLEVEL", customer.getCustLevel());
					if (xtdm!=null){
						respon.setCustLevel(xtdm.getNote());
					}
				}
				if (StringUtils.isNotBlank(customer.getDesignMan())){
					Employee employee = employeeService.get(Employee.class,customer.getDesignMan());
					if (employee!=null){
						respon.setDesignManDescr(employee.getNameChi());
						respon.setDesignManPhone(employee.getPhone());
					}
				}
				if (StringUtils.isNotBlank(customer.getProjectMan())){
					Employee employee = employeeService.get(Employee.class,customer.getProjectMan());
					if (employee!=null){
						respon.setProjectManDescr(employee.getNameChi());
						respon.setProjectManPhone(employee.getPhone());
					}
				}
				// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//				Map<String, Object> czyqxMap = employeeService.getCZYGNQX(getUserContext(request).getCzybh(), "0805", "查看客户电话");
//				if(czyqxMap != null){
//					respon.setCanCallPhone("1");
//				}else{
//					respon.setCanCallPhone("0");
//				}
				if (czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0805", "查看客户电话")) {
					respon.setCanCallPhone("1");
				} else {
					respon.setCanCallPhone("0");
				}
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看工地概况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getGdDetail")
	public void getGdDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		GdDetailResp respon=new GdDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			Map<String,Object> map = customerService.getCustomerByCode(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户付款接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCustomerPayDetail")
	public void getCustomerPayDetail(HttpServletRequest request, HttpServletResponse response){
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		CustomerPayDetailResp respon=new CustomerPayDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			Map<String,Object> map = customerService.getCustomerPayByCode(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			Map<String, Object> shouldBanlanceMap = customerService.getShouldBanlance(evt.getId());
			if(shouldBanlanceMap != null){
				BeanConvertUtil.mapToBean(shouldBanlanceMap, respon);
			}
			double zjzje = customerService.getCustomerZjzjeByCode(evt.getId()).doubleValue();
			double hasPay = customerService.getCustomerHaspayByCode(evt.getId()).doubleValue();
//			double noPay1 = respon.getContractFee().doubleValue()+respon.getDesignFee().doubleValue()+zjzje-hasPay;
			double noPay = Arith.sub(Arith.add(Arith.add(Arith.add(respon.getContractFee().doubleValue(), 
					respon.getDesignFee().doubleValue()),zjzje), respon.getTax().doubleValue()),hasPay);
			double designFee = respon.getDesignFee().doubleValue();
			double nPay1 = Arith.add(respon.getFirstPay().longValue(),designFee);
			double nPay2 = Arith.add(nPay1,respon.getSecondPay().longValue());
			double nPay3 = Arith.add(nPay2,respon.getThirdPay().longValue());
			double nPay4 = Arith.add(nPay3,respon.getFourPay().longValue());
			double nowPay = 0.0;
			if (zjzje>=0){
				if (hasPay < nPay1){
					nowPay = Arith.add(Arith.sub(nPay1,hasPay),zjzje);
				}else if (hasPay < nPay2){
					nowPay = Arith.add(Arith.sub(nPay2,hasPay),zjzje);
				}else if (hasPay < nPay3){
					nowPay = Arith.add(Arith.sub(nPay3,hasPay),zjzje);
				}else if (hasPay < nPay4){
					nowPay = Arith.add(Arith.sub(nPay4,hasPay),zjzje);
				}
			}else{
				if (hasPay < nPay1+zjzje){
					nowPay = Arith.add(Arith.sub(nPay1,hasPay),zjzje);
				}else if (hasPay < nPay2+zjzje){
					nowPay = Arith.add(Arith.sub(nPay2,hasPay),zjzje);
				}else if (hasPay < nPay3+zjzje){
					nowPay = Arith.add(Arith.sub(nPay3,hasPay),zjzje);
				}else if (hasPay < nPay4+zjzje){
					nowPay = Arith.add(Arith.sub(nPay4,hasPay),zjzje);
				}
			}
			
			respon.setZjzje(zjzje);
			respon.setHasPay(hasPay);
			respon.setNoPay(noPay);
			respon.setNowPay(nowPay);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户成本支出接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getCustomerCostDetail")
	public void getCustomerCostDetail(HttpServletRequest request, HttpServletResponse response){
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		CostListQueryResp<CustomerCostDetailResp> respon=new CostListQueryResp<CustomerCostDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			Result result = customerService.getCustomerCostByCode(evt.getId());
			JSONArray jsonArray = JSONArray.fromObject(result.getJson());
			List<CustomerCostDetailResp> list 
				= (List<CustomerCostDetailResp>) JSONArray.toCollection(jsonArray, CustomerCostDetailResp.class);
			respon.setDatas(list);
			respon.setRecordSum(list==null?0l:list.size());
			respon.setAllAmountJs(list==null || list.size()==0?0l:list.get(0).getAllAmountJs());
			respon.setReturnCode(result.getCode());
			respon.setReturnInfo(result.getInfo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看付款明细列表接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPayInfoDetailList")
	public void getPayInfoDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<PayInfoDetailResp> respon=new BaseListQueryResp<PayInfoDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			List<Map<String,Object>> list = customerService.getPayInfoDetailListByCode(evt.getId());
			List<PayInfoDetailResp> listBean = BeanConvertUtil.mapToBeanList(list, PayInfoDetailResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户材料需求列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerItemReqList")
	public void getCustomerItemReqList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_itemReq(page, evt.getId(), evt.getItemType1(), evt.getItemDescr(),evt.getItemType2());
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setPageNo(page.getPageNo());
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户材料变更列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerItemChangeList")
	public void getCustomerItemChangeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemChangeQueryResp> respon=new BasePageQueryResp<ItemChangeQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_itemChange(page, evt.getId(), evt.getItemType1(),evt.getItemType2());
			List<ItemChangeQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemChangeQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setPageNo(page.getPageNo());
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户材料变更详情列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerItemChangeDetailList")
	public void getCustomerItemChangeDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<ItemChangeDetailQueryResp> respon=new BasePageQueryResp<ItemChangeDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			itemChgDetailService.findPageByNo(page,evt.getId());
			List<ItemChangeDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemChangeDetailQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户领料列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerItemAppList")
	public void getCustomerItemAppList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemAppQueryResp> respon=new BasePageQueryResp<ItemAppQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			customerService.findPageByCustomerCode_itemApp(page, evt.getId(), evt.getItemType1(),evt.getId());
			List<ItemAppQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemAppQueryResp.class);
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
	/**
	 * 查看客户领料详情列表接口
	 * @param request
	 * @param response
	 */		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerItemAppDetailList")
	public void getCustomerItemAppDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		ItemAppDetailPageQueryResp<ItemAppDetailQueryResp> respon=new ItemAppDetailPageQueryResp<ItemAppDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			page.setPageSize(-1);//evt.getPageSize()
			itemAppDetailService.findPageByNo(page,evt.getId());
			List<Map<String,Object>> list = page.getResult();
			if (list!=null && list.size()>0){
				BeanConvertUtil.mapToBean(list.get(0), respon);
			}
			List<ItemAppDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemAppDetailQueryResp.class);
	
			if(listBean != null && listBean.size() > 0){
				Double sum = 0.0;
				for(int i=0;i<listBean.size();i++){
					sum += listBean.get(i).getAllProjectCost();
				}
				listBean.get(0).setAllProjectCost(sum);
			}
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
	/**
	 * 查看干系人列表接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getGxrList")
	public void getGxrList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<GxrDetailResp> respon=new BaseListQueryResp<GxrDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			List<Map<String,Object>> list = customerService.getGxrListByCode(evt.getId(),null);
			List<GxrDetailResp> listBean = BeanConvertUtil.mapToBeanList(list, GxrDetailResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getComplaint")
	public void getComplaint(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<CustComplaintResp> respon=new BaseListQueryResp<CustComplaintResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			List<Map<String,Object>> list = custComplaintService.getCustComplaint(evt.getId());
			List<CustComplaintResp> listBean = BeanConvertUtil.mapToBeanList(list, CustComplaintResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustEval")
	public void getCustEval(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<CustComplaintResp> respon=new BaseListQueryResp<CustComplaintResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			List<Map<String,Object>> list = custComplaintService.getCustEval(evt.getId());
			List<CustComplaintResp> listBean = BeanConvertUtil.mapToBeanList(list, CustComplaintResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustAddress")
	public void getCustAddress(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<CustomerResp> respon=new BaseListQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			List<Map<String,Object>> list = custComplaintService.getCustAddress(evt.getId(),evt.getPhone());
			List<CustomerResp> listBean = BeanConvertUtil.mapToBeanList(list, CustomerResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看客户基础需求列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemReqList")
	public void getBaseItemReqList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseItemReqQueryEvt evt=new BaseItemReqQueryEvt();
		BasePageQueryResp<BaseItemReqQueryResp> respon=new BasePageQueryResp<BaseItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("fixAreaDescr", request.getParameter("fixAreaDescr"));
			evt = (BaseItemReqQueryEvt)JSONObject.toBean(json,BaseItemReqQueryEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_baseItemReq(page,evt.getCustCode(),evt.getFixAreaDescr());
			List<BaseItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemReqQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看客户基础变更列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemChgList")
	public void getBaseItemChgList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<BaseItemChgQueryResp> respon=new BasePageQueryResp<BaseItemChgQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_baseItemChange(page, evt.getId());
			List<BaseItemChgQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemChgQueryResp.class);
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
	/**
	 * 查看客户基础变更详情列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseItemChgDetailList")
	public void getBaseItemChgDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt=new BaseQueryEvt();
		BasePageQueryResp<BaseItemChgDetailQueryResp> respon=new BasePageQueryResp<BaseItemChgDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt)JSONObject.toBean(json,BaseQueryEvt.class);
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
			baseItemChgDetailService.findPageByNo(page,evt.getId());
			List<BaseItemChgDetailQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemChgDetailQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看巡检执行客户列表接口（不限部门权限）
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getExecuteCustomerList")
	public void getExecuteCustomerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt=new CustomerQueryEvt();
		BasePageQueryResp<CustomerQueryResp> respon=new BasePageQueryResp<CustomerQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt)JSONObject.toBean(json,CustomerQueryEvt.class);
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

			customerService.getExecuteCustomerList(page,evt);
			List<CustomerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerQueryResp.class);
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
	@RequestMapping("/updateCustomerInfo")
	public void updateCustomerInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerInfoEvt evt=new CustomerInfoEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerInfoEvt)JSONObject.toBean(json,CustomerInfoEvt.class);
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
			Customer customer = customerService.get(Customer.class, evt.getId());
			if(StringUtils.isNotBlank(evt.getNewBuildPass())){
				customer.setBuildPass(evt.getNewBuildPass());
				customerService.update(customer);
				respon.setReturnInfo("工地密码修改成功");
			}else{
				respon.setReturnInfo("新工地密码不能为空");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerByConditions")
	public void getCustomerByConditions(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustomerEvt evt=new GetCustomerEvt();
		BasePageQueryResp<GetCustomerResp> respon=new BasePageQueryResp<GetCustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustomerEvt)JSONObject.toBean(json,GetCustomerEvt.class);
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
			page.setPageOrderBy("ConfirmBegin");
			page.setPageOrder("Desc");
			customerService.getCustomerByConditions(page, evt);
			List<GetCustomerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), GetCustomerResp.class);
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
	
	@RequestMapping("/getRollList")
	public void getRollList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt=new BaseEvt();
		BaseListQueryResp<RollResp> respon=new BaseListQueryResp<RollResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt)JSONObject.toBean(json, BaseEvt.class);
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
			List<Map<String,Object>> list = customerService.getRollList();
			List<RollResp> listBean = BeanConvertUtil.mapToBeanList(list, RollResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getSddwtList")
	public void getSddwtList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetSddwtListEvt evt=new GetSddwtListEvt();
		BasePageQueryAliYunResp<GetSddwtListResp> respon=new BasePageQueryAliYunResp<GetSddwtListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetSddwtListEvt)JSONObject.toBean(json, GetSddwtListEvt.class);
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
			List<GetSddwtListResp> listBean = null;
			Map<String, Object> ossMap = null;
			if(StringUtils.isNotBlank(evt.getCustCode())){
				ossMap = OssManager.getFilesByDir("sddwt/"+evt.getCustCode()+"/", evt.getMarker(), evt.getPageSize());
				listBean = new ArrayList<GetSddwtListResp>();
				List list = (List) ossMap.get("fileList");
				for(int i = 0;i < list.size();i++){
					GetSddwtListResp getSddwtListResp = new GetSddwtListResp();
					getSddwtListResp.setSrc(OssConfigure.accessUrl+"/sddwt/"+evt.getCustCode()+"/"+list.get(i).toString());
					listBean.add(getSddwtListResp);
				}
			}else{
				listBean = new ArrayList<GetSddwtListResp>();
			}
			respon.setDatas(listBean);
			respon.setHasNext(ossMap != null ? Boolean.parseBoolean(ossMap.get("hasNext").toString()) : false);
			respon.setMarker(evt.getMarker());
			respon.setNextMarker(ossMap != null && ossMap.get("marker") != null ? ossMap.get("marker").toString() : evt.getMarker());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	
	@RequestMapping("/getPrjReport")
	public void getPrjReport(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjReportEvt evt=new PrjReportEvt();
		BasePageQueryResp<PrjReportResp> respon=new BasePageQueryResp<PrjReportResp>();

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjReportEvt)JSONObject.toBean(json, PrjReportEvt.class);
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
			customerService.getPrjReport(page,evt.getCustCode());
			List<PrjReportResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjReportResp.class);
			for(int i=0;i<listBean.size();i++){
				listBean.get(i).setPreUrl(OssConfigure.cdnAccessUrl+"/prjProgNew/");
				listBean.get(i).setSignInPreUrl(OssConfigure.cdnAccessUrl+"/signIn/");
				listBean.get(i).setWorkSignPreUrl(OssConfigure.cdnAccessUrl+"/workSignPic/");
				listBean.get(i).setPreUrlNoSend(OssConfigure.cdnAccessUrl+"/prjProgNew/");
				listBean.get(i).setEmpSignNotSendUrl(OssConfigure.cdnAccessUrl+"/signIn/");
				listBean.get(i).setWorkSignNotSendUrl(OssConfigure.cdnAccessUrl+"/workSignPic/");
			}
			respon.setDatas(listBean);
			respon.setPageNo(page.getPageNo());
			respon.setHasNext(page.getTotalPages()>evt.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalPages()==evt.getPageNo()||page.getTotalPages()==0?0l:1l);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看客户已发货领料列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemSendList")
	public void getItemSendList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemSentListEvt evt=new ItemSentListEvt();
		BasePageQueryResp<ItemSendListResp> respon=new BasePageQueryResp<ItemSendListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemSentListEvt)JSONObject.toBean(json,ItemSentListEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findItemSendListPage(page,evt.getCustCode(),evt.getItemType1());
			List<ItemSendListResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemSendListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看客户已发货领料明细列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemSendDetailList")
	public void getItemSendDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemSentListEvt evt=new ItemSentListEvt();
		BasePageQueryResp<ItemSendDetailListResp> respon=new BasePageQueryResp<ItemSendDetailListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemSentListEvt)JSONObject.toBean(json,ItemSentListEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findItemSendDetailListPage(page,evt.getNo());
			List<ItemSendDetailListResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemSendDetailListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setPageNo(page.getPageNo());
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看客户已发货领料明细列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getConstractPay")
	public void getConstractPay(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerEvt evt=new CustomerEvt();
		BasePageQueryResp<CustConstractPayResp> respon=new BasePageQueryResp<CustConstractPayResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerEvt)JSONObject.toBean(json,CustomerEvt.class);
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
			
			Customer customer = new Customer();
			customerService.getConstractPayPage(page,evt.getCode());
			List<CustConstractPayResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustConstractPayResp.class);
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
	
	@RequestMapping("/doSaveCustEval")
	public void doSaveCustEval(HttpServletRequest request, HttpServletResponse response){
		System.out.println("enter");
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustEvalEvt evt=new CustEvalEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (CustEvalEvt)JSONObject.toBean(json,CustEvalEvt.class);
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
			CustEval custEval = null;
			custEval=custEvalService.get(CustEval.class, evt.getCustCode());
			if(custEval!=null){
				custEval.setPrjScore(evt.getPrjScope());
				custEval.setDesignScore(evt.getDesignScope());
				custEval.setActionLog("Edit");
				custEvalService.update(custEval);
				respon.setReturnInfo("修改评价成功");
			} else {
				custEval=new CustEval();
				custEval.setCustCode(evt.getCustCode());
				custEval.setPrjScore(evt.getPrjScope());
				custEval.setDesignScore(evt.getDesignScope());
				custEval.setLastUpdate(new Date());
				custEval.setLastUpdatedBy(evt.getCustCode());
				custEval.setExpired("F");
				custEval.setActionLog("Add");
				custEvalService.save(custEval);
				respon.setReturnInfo("评价成功");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doComplaint")
	public void doComplaint(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustComplantEvt evt=new CustComplantEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (CustComplantEvt)JSONObject.toBean(json,CustComplantEvt.class);
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
			CustComplaint custComplaint=new CustComplaint();
			if(StringUtils.isNotBlank(evt.getNo())){
				custComplaint=custComplaintService.get(CustComplaint.class, evt.getNo());
				custComplaint.setRemarks(evt.getRemarks());
				custComplaint.setLastUpdate(new Date());
				custComplaint.setLastUpdatedBy(evt.getCustCode());
				custComplaintService.update(custComplaint);
			}else {
				custComplaint.setNo(custComplaintService.getSeqNo("tCustComplaint"));
				custComplaint.setCustCode(evt.getCustCode());
				custComplaint.setCrtCZY(evt.getCustCode());
				custComplaint.setLastUpdatedBy(evt.getCustCode());
				custComplaint.setSource("客户App");
				custComplaint.setRemarks(evt.getRemarks());
				custComplaint.setLastUpdate(new Date());
				custComplaint.setStatus("0");
				custComplaint.setExpired("F");
				custComplaint.setActionLog("Add");
				custComplaint.setCrtDate(new Date());
				custComplaintService.save(custComplaint);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getItemReqCount")
	public void getItemReqCount(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReqCountEvt evt=new ItemReqCountEvt();
		BaseListQueryResp<ItemReqCountResp> respon=new BaseListQueryResp<ItemReqCountResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemReqCountEvt)JSONObject.toBean(json,ItemReqCountEvt.class);
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
			List<Map<String,Object>> list = customerService.getItemReqCount(evt.getCustCode());
			List<ItemReqCountResp> listBean = BeanConvertUtil.mapToBeanList(list, ItemReqCountResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getItemType12ReqCount")
	public void getItemType12ReqCount(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReqCountEvt evt=new ItemReqCountEvt();
		BaseListQueryResp<ItemReqCountResp> respon=new BaseListQueryResp<ItemReqCountResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemReqCountEvt)JSONObject.toBean(json,ItemReqCountEvt.class);
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
			List<Map<String,Object>> list = customerService.getItemType12ReqCount(evt.getCustCode());
			List<ItemReqCountResp> listBean = BeanConvertUtil.mapToBeanList(list, ItemReqCountResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getJCReqCount")
	public void getJCReqCount(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReqCountEvt evt=new ItemReqCountEvt();
		BasePageQueryResp<ItemReqCountResp> respon=new BasePageQueryResp<ItemReqCountResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemReqCountEvt)JSONObject.toBean(json,ItemReqCountEvt.class);
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
			List<Map<String,Object>> list = customerService.getJCReqCount(evt.getCustCode());
			List<ItemReqCountResp> listBean = BeanConvertUtil.mapToBeanList(list, ItemReqCountResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustomerItemType12DetailNeeds")
	public void getCustomerItemType12DetailNeeds(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemReqCountEvt evt=new ItemReqCountEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemReqCountEvt)JSONObject.toBean(json,ItemReqCountEvt.class);
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
			Customer customer = new Customer();
			customer.setCode(evt.getCustCode());
			customer.setItemType1(evt.getItemType12());
			customer.setIsCupboard(evt.getIsCupboard());
			List<Map<String,Object>> list = customerService.getCustomerItemType12DetailNeeds(customer);
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(list, ItemReqQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getBuildPassword")
	public void getBuildPassword(HttpServletRequest request, HttpServletResponse response){
		System.out.println("getBuildPassword");
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerEvt evt=new CustomerEvt();
		BasePageQueryResp<CustomerResp> respon=new BasePageQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerEvt)JSONObject.toBean(json,CustomerEvt.class);
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
			List<Map<String,Object>> list = customerService.getBuildPassword(evt.getCode(),evt.getCustWkPk());
			List<CustomerResp> listBean = BeanConvertUtil.mapToBeanList(list, CustomerResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAreaDescr")
	public void getAreaDescr(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseItemReqQueryEvt evt=new BaseItemReqQueryEvt();
		BasePageQueryResp<BaseItemReqQueryResp> respon=new BasePageQueryResp<BaseItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("fixAreaDescr", request.getParameter("fixAreaDescr"));
			evt = (BaseItemReqQueryEvt)JSONObject.toBean(json,BaseItemReqQueryEvt.class);
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
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_areaDescr(page,evt.getCustCode(),evt.getFixAreaDescr());
			List<BaseItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), BaseItemReqQueryResp.class);
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
	@RequestMapping("/getItemTypeByCustCode")
	public void getItemTypeByCustCode(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			page.setPageNo(evt.getPageNo());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_itemTypeByCustCode(page, evt.getId());
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqQueryResp.class);
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
	@RequestMapping("/getItemType2ByCustCode")
	public void getItemType2ByCustCode(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			page.setPageNo(evt.getPageNo());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_itemType2ByCustCode(page, evt.getId(),evt.getItemType1());
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqQueryResp.class);
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
	@RequestMapping("/getItemTypeChgByCustCode")
	public void getChgItemTypeByCustCode(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			page.setPageNo(evt.getPageNo());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_itemTypeChgByCustCode(page, evt.getId());
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqQueryResp.class);
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
	@RequestMapping("/getItemType2ChgByCustCode")
	public void getChgItemType2ByCustCode(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			page.setPageNo(evt.getPageNo());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findPageByCustomerCode_itemType2ChgByCustCode(page, evt.getId(),evt.getItemType1());
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqQueryResp.class);
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
	@RequestMapping("/getItemType1List")
	public void getItemType1List(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemQueryEvt evt=new ItemQueryEvt();
		BasePageQueryResp<ItemReqQueryResp> respon=new BasePageQueryResp<ItemReqQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (ItemQueryEvt)JSONObject.toBean(json,ItemQueryEvt.class);
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
			page.setPageNo(evt.getPageNo());
			Customer customer = new Customer();
			customer.setProjectMan(evt.getId());
			customerService.findItemType1ListPage(page);
			List<ItemReqQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemReqQueryResp.class);
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
	
	/**
	 * cjm 190308
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getShowBuildDetail")
	public void getShowBuildDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetShowBuildDetailEvt evt=new GetShowBuildDetailEvt();
		GetShowBuildDetailResp respon=new GetShowBuildDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetShowBuildDetailEvt) JSONObject.toBean(json, GetShowBuildDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Customer customer = this.showService.get(Customer.class, evt.getCustCode());
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = this.showService.get(Builder.class, customer.getBuilderCode());
				if(builder != null){
					respon.setAddress(builder.getDescr());
				}
			}
			CustType custType =showService.get(CustType.class, customer.getCustType());
			respon.setArea(customer.getArea());
			respon.setCustTypeDescr(custType.getDesc1());
			if(StringUtils.isNotBlank(evt.getGdbb())){
				respon.setAddress(customer.getAddress());
			}
			respon.setBeginDate(customer.getConfirmBegin());
			respon.setCustDescr(customer.getDescr().substring(0, 1));
			respon.setPlanEndDate(DateUtil.addDate(customer.getConfirmBegin(), customer.getConstructDay()));
			List<Map<String, Object>> list = this.showService.getPrjProgConfirm(evt.getCustCode());
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			for(int i = 0; i < list.size(); i++){
				List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(list.get(i).get("prjProgConfirmNo").toString(), null);
				if(photoList == null || (photoList != null && photoList.size() < 3)){
					photoList = new ArrayList<Map<String,Object>>();
				}
				for(int j = 0;j < photoList.size();j++){
					if("1".equals(photoList.get(j).get("isSendYun").toString())){
						photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
					}else{
						String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
						photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
					}
				}
				list.get(i).put("photos", photoList);
			}
			respon.setDatas(list);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看客户详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCanCallWorker")
	public void getCanCallWorker(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		CustomerDetailResp respon=new CustomerDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			Customer customer = customerService.get(Customer.class, evt.getId());
			if (customer!=null){
				if(czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0805", "查看工人电话")){
					respon.setCanCallPhone("1");
				}else{
					respon.setCanCallPhone("0");
				}
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 工地播报客户评价
	 * add by cjm 2019-04-24
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSaveCustPrjEval")
	public void doSaveCustPrjEval(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjReportEvt evt=new PrjReportEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (PrjReportEvt)JSONObject.toBean(json,PrjReportEvt.class);
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

			//巡检评价
			if("1".equals(evt.getTypeCode())){
				PrjProgCheck prjProgCheck = new PrjProgCheck();
				prjProgCheck=prjProgCheckService.get(PrjProgCheck.class, evt.getNo());
				prjProgCheck.setCustScore(evt.getCustScore());
				prjProgCheck.setCustRemarks(evt.getCustRemarks());
				prjProgCheckService.update(prjProgCheck);
				respon.setReturnInfo("点评成功");
			}
			//工人签到评价
			if("2".equals(evt.getTypeCode())){
				WorkSign workSign = new WorkSign();
				workSign=workerService.get(WorkSign.class, evt.getPks());
				workSign.setCustScore(evt.getCustScore());
				workSign.setCustRemarks(evt.getCustRemarks());
				workerService.update(workSign);
				respon.setReturnInfo("点评成功");
			}
			//验收评价
			if("3".equals(evt.getTypeCode())){
				PrjProgConfirm prjProgConfirm = new PrjProgConfirm();
				prjProgConfirm=prjProgConfirmService.get(PrjProgConfirm.class, evt.getNo());
				prjProgConfirm.setCustScore(evt.getCustScore());
				prjProgConfirm.setCustRemarks(evt.getCustRemarks());
				prjProgConfirmService.update(prjProgConfirm);
				respon.setReturnInfo("点评成功");
			}
			//员工签到点评
			if("4".equals(evt.getTypeCode())){
				SignIn signIn = new SignIn();
				signIn=signInService.get(SignIn.class, evt.getPks());
				signIn.setCustScore(evt.getCustScore());
				signIn.setCustRemarks(evt.getCustRemarks());
				signInService.update(signIn);
				respon.setReturnInfo("点评成功");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 获取工艺图片
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getTechPhotoUrlList")
	public void getTechPhotoUrlList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjReportEvt evt=new PrjReportEvt();
		BasePageQueryResp<TechPhotoResp> respon=new BasePageQueryResp<TechPhotoResp>();

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjReportEvt)JSONObject.toBean(json, PrjReportEvt.class);
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
			customerService.getTechPhotoUrlList(page,evt.getCustCode());
			List<TechPhotoResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), TechPhotoResp.class);
			for(int i=0;i<listBean.size();i++){
				if("1".equals(listBean.get(i).getWorkSignPic())){
					listBean.get(i).setPhotoUrl(OssConfigure.cdnAccessUrl+"/workSignPic/"+evt.getCustCode());
				}else if("0".equals(listBean.get(i).getWorkSignPic())){
					listBean.get(i).setPhotoUrl(OssConfigure.cdnAccessUrl+"/workSignPic/"+evt.getCustCode());
				}else if("1".equals(listBean.get(i).getEmpSignPic())){
					listBean.get(i).setPhotoUrl(OssConfigure.cdnAccessUrl+"/signIn");
				}else if("0".equals(listBean.get(i).getEmpSignPic())){
					listBean.get(i).setPhotoUrl(OssConfigure.cdnAccessUrl+"/signIn");
				}
			}
			respon.setDatas(listBean);
			respon.setPageNo(page.getPageNo());
			respon.setHasNext(page.getTotalPages()>evt.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalPages()==evt.getPageNo()||page.getTotalPages()==0?0l:1l);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/goJqGridForOA")
	public void goJqGridForOA(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GoJqGridForOAEvt evt=new GoJqGridForOAEvt();
		BasePageQueryResp<GojqGridForOAResp> respon=new BasePageQueryResp<GojqGridForOAResp>();

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GoJqGridForOAEvt)JSONObject.toBean(json, GoJqGridForOAEvt.class);
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
			page.setPageSize(5);
			Customer customer = new Customer();
			customer.setAuthorityCtrl(evt.getAuthorityCtrl());
			customer.setAddress(evt.getAddress());
			customer.setStatus(evt.getStatus());
			customer.setEmpCode(evt.getEmpCode());
			customer.setDesignMan(evt.getDesignMan());
			customer.setCodes(evt.getCodes());
			customer.setEndCode(evt.getEndCode());
			customerService.findPageForOABySql(page, customer,this.getUserContext(request));
			List<GojqGridForOAResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), GojqGridForOAResp.class);

			respon.setDatas(listBean);
			respon.setPageNo(page.getPageNo());
			respon.setHasNext(false);
			respon.setRecordSum(1l);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 查看合同信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getContractInfo")
	public void getContractInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ContractResp respon=new ContractResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt)JSONObject.toBean(json,BaseGetEvt.class);
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
			Map<String,Object> map = customerService.getContractInfo(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseCheckItemPlanList")
	public void getBaseCheckItemPlanList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BasePageQueryResp<FixDutyDetailResp> respon=new BasePageQueryResp<FixDutyDetailResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (FixDutyEvt)JSONObject.toBean(json,FixDutyEvt.class);
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
			customerService.getBaseCheckItemPlanList(page,evt.getCustCode(),evt.getWorkType12());
			List<FixDutyDetailResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), FixDutyDetailResp.class);
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
	
	@RequestMapping("/getWaterMarkInfo")
	public void getWaterMarkInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WaterMarkInfoEvt evt=new WaterMarkInfoEvt();
		WaterMarkInfoResp respon=new WaterMarkInfoResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WaterMarkInfoEvt)JSONObject.toBean(json,WaterMarkInfoEvt.class);
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
			Employee employee = employeeService.get(Employee.class, evt.getCzybh());
			Map<String,Object> map = customerService.getWaterMarkInfo(evt.getCode());
			BeanConvertUtil.mapToBean(map, respon);
			respon.setPrjCheckCzyDescr(employee.getNameChi());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getResignNotify")
	public void getCheckPayInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustomerEvt evt = new CustomerEvt();
		BaseResponse respon = new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerEvt) JSONObject.toBean(json, CustomerEvt.class);
			interfaceLog.setRequestContent(json.toString());
			
			Customer customer = new Customer();
			customer = customerService.get(Customer.class, evt.getCode());
			
			String notify = customerService.resignNotify(customer.getCode(), customer.getStatus(), customer.getEndCode());
			respon.setReturnInfo(notify);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
