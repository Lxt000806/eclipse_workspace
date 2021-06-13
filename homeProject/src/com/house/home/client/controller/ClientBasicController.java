package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.FormTokenManager;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.CustomerEvt;
import com.house.home.client.service.evt.CustomerQueryEvt;
import com.house.home.client.service.evt.FixAreaEvt;
import com.house.home.client.service.evt.GetDepartment2ListEvt;
import com.house.home.client.service.evt.IntMeasureBrandEvt;
import com.house.home.client.service.evt.ItemType2QueryEvt;
import com.house.home.client.service.evt.PositionEvt;
import com.house.home.client.service.evt.PrjItemEvt;
import com.house.home.client.service.evt.WorkType12Evt;
import com.house.home.client.service.evt.WorkType2QueryEvt;
import com.house.home.client.service.evt.WorkerQueryEvt;
import com.house.home.client.service.resp.AppServerUrlResp;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.CustomerResp;
import com.house.home.client.service.resp.EmployeeBaseInfoResp;
import com.house.home.client.service.resp.FixAreaResp;
import com.house.home.client.service.resp.GetDepartment2ListResp;
import com.house.home.client.service.resp.IntMeasureBrandQueryResp;
import com.house.home.client.service.resp.ItemType1Resp;
import com.house.home.client.service.resp.ItemTypeQueryResp;
import com.house.home.client.service.resp.PrjItemResp;
import com.house.home.client.service.resp.SalaryTypeGet;
import com.house.home.client.service.resp.SalaryTypeResp;
import com.house.home.client.service.resp.SignInQueryResp;
import com.house.home.client.service.resp.TokenIdResp;
import com.house.home.client.service.resp.WorkType12DeptResp;
import com.house.home.client.service.resp.WorkType12Resp;
import com.house.home.client.service.resp.WorkerQueryResp;
import com.house.home.client.service.resp.XtdmQueryResp;
import com.house.home.entity.basic.AppServerUrl;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.basic.WorkType1;
import com.house.home.entity.basic.WorkType12Dept;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.FixArea;
import com.house.home.entity.project.SalaryType;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.Worker;
import com.house.home.entity.workflow.Department;
import com.house.home.service.basic.AppServerUrlService;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.IntMeasureBrandService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.ItemType1Service;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.basic.WorkType12DeptService;
import com.house.home.service.basic.WorkType1Service;
import com.house.home.service.basic.WorkType2Service;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.FixAreaService;
import com.house.home.service.project.PrjProgService;
import com.house.home.service.project.SalaryTypeService;
import com.house.home.service.project.WorkType12Service;
import com.house.home.service.project.WorkerService;
import com.house.home.service.workflow.DepartmentService;

@Controller
@RequestMapping("/client/basic")
public class ClientBasicController extends ClientBaseController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemType1Service itemType1Service;
	@Autowired
	private AppServerUrlService appServerUrlService;
	@Autowired
	private ItemType2Service itemType2Service;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PrjProgService prjProgService;
	@Autowired
	private SalaryTypeService salaryTypeService;
	@Autowired
	private WorkType1Service workType1Service;
	@Autowired
	private WorkType2Service workType2Service;
	@Autowired
	private WorkType12Service workType12Service;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private IntMeasureBrandService intMeasureBrandService;
	@Autowired
	private Department2Service department2Service;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private FixAreaService fixAreaService;
	@Resource(name = "authorityCacheManager")
	private ICacheManager authorityCacheManager;
	@Resource(name = "menuCacheManager")
	private ICacheManager menuCacheManager;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private WorkType12DeptService workType12DeptService;
	
	/**
	 * 获取员工材料类型
	 */
	@RequestMapping("/getItemTypeList")
	public  void getItemTypeList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<ItemTypeQueryResp> respon = new BaseListQueryResp<ItemTypeQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String, Object>> list = itemService.getItemTypeById(evt.getId());
			List<ItemTypeQueryResp> listBean = new ArrayList<ItemTypeQueryResp>();
			listBean=BeanConvertUtil.mapToBeanList(list, ItemTypeQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
		}
	}
	
	/**获取材料类型1
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemType1List")
	public void getItemType1List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		BaseListQueryResp<ItemType1Resp> respon = new BaseListQueryResp<ItemType1Resp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<ItemType1> list = itemType1Service.findByNoExpired();
			List<ItemType1Resp> listBean = new ArrayList<ItemType1Resp>();
			if (list!=null && list.size()>0){
				for (ItemType1 item : list){
					ItemType1Resp re = new ItemType1Resp();
					BeanUtilsEx.copyProperties(re, item);
					re.setCode(re.getCode().trim());
					listBean.add(re);
					re = null;
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取材料类型2
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemType2List")
	public void getItemType2List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ItemType2QueryEvt evt = new ItemType2QueryEvt();
		BasePageQueryResp<ItemTypeQueryResp> respon = new BasePageQueryResp<ItemTypeQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ItemType2QueryEvt) JSONObject.toBean(json, ItemType2QueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(-1);
			ItemType2 itemType2 = new ItemType2();
			itemType2.setItemType1(evt.getItemType1());
			itemType2.setCode(evt.getCode());
			//新版本工人app直传worktype12限制材料，旧版本仍然匹配工人工种分类12
			if(StringUtils.isBlank(evt.getWorkType12())) {
				if(StringUtils.isNotBlank(evt.getWorkerCode())){
					itemType2.setWorkerCode(evt.getWorkerCode());
				}
			}else{
				if("32".equals(evt.getWorkType12().trim())){
					evt.setWorkType12("02");
				}
				itemType2.setWorkType12(evt.getWorkType12());
			}
			itemType2Service.findPageBySql(page, itemType2);
			List<ItemTypeQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemTypeQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取工种类型1
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getWorkType1List")
	public void getWorkType1List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		BaseListQueryResp<ItemType1Resp> respon = new BaseListQueryResp<ItemType1Resp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<WorkType1> list = workType1Service.findByNoExpired();
			List<ItemType1Resp> listBean = new ArrayList<ItemType1Resp>();
			if (list!=null && list.size()>0){
				for (WorkType1 item : list){
					ItemType1Resp re = new ItemType1Resp();
					BeanUtilsEx.copyProperties(re, item);
					re.setCode(re.getCode().trim());
					listBean.add(re);
					re = null;
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取工种类型2
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkType2List")
	public void getWorkType2List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkType2QueryEvt evt = new WorkType2QueryEvt();
		BasePageQueryResp<ItemTypeQueryResp> respon = new BasePageQueryResp<ItemTypeQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkType2QueryEvt) JSONObject.toBean(json, WorkType2QueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(-1);
			page.setPageOrderBy("dispSeq");
			page.setPageOrder("asc");
			WorkType2 workType2 = new WorkType2();
			workType2.setWorkType1(evt.getWorkType1());
			workType2Service.findPageBySql(page, workType2);
			List<ItemTypeQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ItemTypeQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取工种类型12
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getWorkType12List")
	public void getWorkType12List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		BaseListQueryResp<WorkType12Resp> respon = new BaseListQueryResp<WorkType12Resp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<WorkType12> list = workType12Service.findByNoExpired();
			List<WorkType12Resp> listBean = new ArrayList<WorkType12Resp>();
			if (list!=null && list.size()>0){
				for (WorkType12 item : list){
					WorkType12Resp re = new WorkType12Resp();
					BeanUtilsEx.copyProperties(re, item);
					re.setCode(re.getCode().trim());
					listBean.add(re);
					re = null;
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取app服务端地址
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getAppServerUrlList")
	public void getAppServerUrlList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		BaseListQueryResp<AppServerUrlResp> respon = new BaseListQueryResp<AppServerUrlResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<AppServerUrl> list = appServerUrlService.loadAll(AppServerUrl.class);
			List<AppServerUrlResp> listBean = new ArrayList<AppServerUrlResp>();
			if (list!=null && list.size()>0){
				for (AppServerUrl item : list){
					AppServerUrlResp re = new AppServerUrlResp();
					BeanUtilsEx.copyProperties(re, item);
					listBean.add(re);
					re = null;
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**根据ID获取系统代码列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getXtdmListById")
	public void getXtdmListById(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		BaseListQueryResp<XtdmQueryResp> respon = new BaseListQueryResp<XtdmQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			List<Xtdm> list = xtdmService.getById(evt.getId());
			List<XtdmQueryResp> listBean = BeanConvertUtil.beanToBeanList(list, XtdmQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean!=null?listBean.size():0l);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**根据用户权限获取工地列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCustomerByUserCustRight")
	public void getCustomerByUserCustRight(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerEvt evt = new CustomerEvt();
		BaseListQueryResp<CustomerResp> respon = new BaseListQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerEvt) JSONObject.toBean(json, CustomerEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = customerService.getByUserCustRight(evt.getId(),evt.getStatus(),evt.getAddress());
			List<CustomerResp> listBean = BeanConvertUtil.beanToBeanList(list, CustomerResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean!=null?listBean.size():0l);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取工地待验收的客户列表
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getCheckCustomer")
	public void getCheckCustomer(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt = new CustomerQueryEvt();
		BasePageQueryResp<CustomerResp> respon = new BasePageQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt) JSONObject.toBean(json, CustomerQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			customerService.getCheckCustomerByPage(page,evt.getId(),evt.getStatus(),evt.getAddress());
			List<CustomerResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), CustomerResp.class);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getPrjCheckCustomer")
	public void getPrjCheckCustomer(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerQueryEvt evt = new CustomerQueryEvt();
		BasePageQueryResp<CustomerResp> respon = new BasePageQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerQueryEvt) JSONObject.toBean(json, CustomerQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			customerService.getPrjCheckCustomerByPage(page,evt.getId(),evt.getStatus(),evt.getAddress());
			List<CustomerResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), CustomerResp.class);
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
	
	/**获取施工项目
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjItem1List")
	public void getPrjItem1List(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		BaseListQueryResp<ItemType1Resp> respon = new BaseListQueryResp<ItemType1Resp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<PrjItem1> list = prjProgService.getPrjItem1List();
			List<ItemType1Resp> listBean = new ArrayList<ItemType1Resp>();
			if (list!=null && list.size()>0){
				for (PrjItem1 item : list){
					ItemType1Resp re = new ItemType1Resp();
					BeanUtilsEx.copyProperties(re, item);
					re.setCode(re.getCode().trim());
					listBean.add(re);
					re = null;
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**根据客户编号和施工项目名称获取施工项目
	 * @param reqest
	 * @param response
	 */
	@RequestMapping("/getPrjItemList")
	public void getPrjItemList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjItemEvt evt = new PrjItemEvt();
		BaseListQueryResp<PrjItemResp> respon = new BaseListQueryResp<PrjItemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjItemEvt) JSONObject.toBean(json, PrjItemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = prjProgService.getPrjProgByCodeAndPrjItemDescr(evt.getCustCode(),evt.getPrjItemDescr());
			List<PrjItemResp> listBean = BeanConvertUtil.beanToBeanList(list, PrjItemResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean!=null?listBean.size():0l);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取工资类型列表接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSalaryTypeList")
	public void getSalaryTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		BaseListQueryResp<SalaryTypeResp> respon = new BaseListQueryResp<SalaryTypeResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<SalaryType> list = salaryTypeService.findByNoExpired();
			List<SalaryTypeResp> listBean = new ArrayList<SalaryTypeResp>();
			if (list!=null && list.size()>0){
				for (SalaryType item : list){
					SalaryTypeResp re = new SalaryTypeResp();
					BeanUtilsEx.copyProperties(re, item);
					re.setCode(re.getCode().trim());
					if(!"03".equals(item.getCode().trim())){
						listBean.add(re);
					}
					re = null;
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取工资类型接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSalaryType")
	public void getSalaryType(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		SalaryTypeGet respon = new SalaryTypeGet();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map = salaryTypeService.findByCode(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 查看工人列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerList")
	public void getWorkerList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerQueryEvt evt=new WorkerQueryEvt();
		BasePageQueryResp<WorkerQueryResp> respon=new BasePageQueryResp<WorkerQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerQueryEvt)JSONObject.toBean(json,WorkerQueryEvt.class);
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
			worker.setWorkType1(evt.getWorkType1());
			worker.setNameChi(evt.getNameChi());
			worker.setIsLifeCost(evt.getIsLifeCost());
			worker.setEmpCode(evt.getEmpCode());
			worker.setIsWithHold(evt.getIsWithHold());
			worker.setPlatform("1");
			worker.setCustCode(evt.getCustCode());
			worker.setWorkType2(evt.getWorkType2());
			worker.setAppType(evt.getAppType());
			workerService.findPageBySql(page, worker);
			List<WorkerQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkerQueryResp.class);
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
	 * 获取集成测量品牌
	 */
	@RequestMapping("/getIntMeasureBrandList")
	public void getIntMeasureBrandList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		IntMeasureBrandEvt evt=new IntMeasureBrandEvt();
		BaseListQueryResp<IntMeasureBrandQueryResp> respon = new BaseListQueryResp<IntMeasureBrandQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (IntMeasureBrandEvt) JSONObject.toBean(json, IntMeasureBrandEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String, Object>> list = intMeasureBrandService.getByType(evt.getType());
			List<IntMeasureBrandQueryResp> listBean = new ArrayList<IntMeasureBrandQueryResp>();
			listBean=BeanConvertUtil.mapToBeanList(list, IntMeasureBrandQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(list==null?0l:list.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
		}
	}
	/**
	 * 刷新权限缓存
	 */
	@RequestMapping("/refreshCache")
	public void refreshCache(HttpServletRequest request,HttpServletResponse response){
		authorityCacheManager.refresh();
		menuCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response,"刷新权限缓存成功");
		
	}
	/**
	 * 获取二级部门列表
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getDepartment2List")
	public void getDepartment2List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetDepartment2ListEvt evt=new GetDepartment2ListEvt();
		BasePageQueryResp<GetDepartment2ListResp> respon = new BasePageQueryResp<GetDepartment2ListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetDepartment2ListEvt) JSONObject.toBean(json, GetDepartment2ListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			Department2 department2 = new Department2();
			department2.setDesc1(evt.getDesc1());
			department2.setExpired("F");
			department2.setDepType(evt.getDepType());
			department2Service.findPageBySql(page, department2);
			List<GetDepartment2ListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetDepartment2ListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/hasZCReq")
	public void hasZCReq(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustomerEvt evt = new CustomerEvt();
		BaseListQueryResp<CustomerResp> respon = new BaseListQueryResp<CustomerResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustomerEvt) JSONObject.toBean(json, CustomerEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = customerService.getHasZCReq(evt.getCode());
			if(list==null){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<CustomerResp> listBean = BeanConvertUtil.beanToBeanList(list, CustomerResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean!=null?listBean.size():0l);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * app获取token 
	 * 返回 tokenId
	 */
	@RequestMapping("/getTokenId")
	public void getTokenId(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new BaseEvt();
		TokenIdResp respon = new TokenIdResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			FormTokenManager formTokenManager = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class);
			FormToken formToken = formTokenManager.newFormToken(request);
			respon.setTokenId(formToken.getToken());		
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}


	/**
	 * 部门管理
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getDepartmentList")
	public void getDepartmentList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetDepartment2ListEvt evt=new GetDepartment2ListEvt();
		BasePageQueryResp<GetDepartment2ListResp> respon = new BasePageQueryResp<GetDepartment2ListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetDepartment2ListEvt) JSONObject.toBean(json, GetDepartment2ListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			Department department = new Department();
			department.setDesc2(evt.getDesc1());
			department.setExpired("F");
			department.setDepType(evt.getDepType());
			department.setSalfDept(evt.getSelfDept());
			UserContext uc = this.getUserContext(request);
			departmentService.findPageBySql(page, department, uc);
			List<GetDepartment2ListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetDepartment2ListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 获取装修区域
	 * @author	created by zb
	 * @date	2019-2-27--下午4:32:58
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getFixAreaList")
	public void getFixAreaList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixAreaEvt evt = new FixAreaEvt();
		BasePageQueryResp<FixAreaResp> respon = new BasePageQueryResp<FixAreaResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (FixAreaEvt) JSONObject.toBean(json, FixAreaEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(-1);
			FixArea fixArea = new FixArea();
			fixArea.setCustCode(evt.getCustCode());
			fixArea.setItemType1(evt.getItemType1());
			fixAreaService.findPageBySql(page, fixArea);
			List<FixAreaResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), FixAreaResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**获取员工基本信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getEmployeeBaseInfo")
	public void getEmployeeBaseInfo(HttpServletRequest request,HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		EmployeeBaseInfoResp respon = new EmployeeBaseInfoResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Employee employee = employeeService.get(Employee.class, evt.getId());
			if(employee!=null){
				respon.setNumber(employee.getNumber());
				respon.setNameChi(employee.getNameChi());
				respon.setDepartment(employee.getDepartment());
				respon.setDepartment1(employee.getDepartment1());
				respon.setDepartment2(employee.getDepartment2());
				respon.setDepartment3(employee.getDepartment3());
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getErrPosition")
	public void getErrPosition(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PositionEvt evt = new PositionEvt();
		SignInQueryResp respon = new SignInQueryResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PositionEvt) JSONObject.toBean(json, PositionEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			respon.setErrPosition("0");
			Customer customer=customerService.get(Customer.class,evt.getCustCode());
			if(customer.getBuilderCode()!=null){
				Builder builder=builderService.get(Builder.class,customer.getBuilderCode());
				Integer offset = (builder == null || builder.getOffset() == null || builder.getOffset() == 0) ? PosiBean.LIMIT_DISTANCE : builder.getOffset();
				if(builder.getLongitude()!=null&&builder.getLongitude().longValue()!=0){
					if(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude())>offset){
						respon.setErrPosition("1");
					}
				}
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkType12Dept")
	public void getWorkType12Dept(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkType12Evt evt = new WorkType12Evt();
		BasePageQueryResp<WorkType12DeptResp> respon = new BasePageQueryResp<WorkType12DeptResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkType12Evt) JSONObject.toBean(json, WorkType12Evt.class);

			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			Page page = new Page();
			WorkType12Dept workType12Dept = new WorkType12Dept();
			workType12Dept.setWorkType12(evt.getCode());
			workType12Dept.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			workType12DeptService.getWorkType12DeptList(page, workType12Dept);
			List<WorkType12DeptResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), WorkType12DeptResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
