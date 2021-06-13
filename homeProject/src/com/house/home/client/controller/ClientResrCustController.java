package com.house.home.client.controller;

import java.text.ParseException;
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

import com.house.framework.bean.Result;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustManagementEvt;
import com.house.home.client.service.evt.ResrCustEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustConResp;
import com.house.home.client.service.resp.CustNetCnlResp;
import com.house.home.client.service.resp.CustTagResp;
import com.house.home.client.service.resp.RegionResp;
import com.house.home.client.service.resp.ResrCustPoolResp;
import com.house.home.client.service.resp.ResrCustResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Region;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.RegionService;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustConService;
import com.house.home.service.design.ResrCustService;

@RequestMapping("/client/resrCust")
@Controller
public class ClientResrCustController extends ClientBaseController {
	
	@Autowired
	private ResrCustService resrCustService;
	@Autowired
	private XtdmService xtdmService;
	@Autowired
	private CustConService custConService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private RegionService regionService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getResrCustList")
	public void getResrCustList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		BasePageQueryResp<ResrCustResp> respon=new BasePageQueryResp<ResrCustResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			setDateForGetList(json);
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
			
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			page.setPageOrderBy("LastUpdate");
			page.setPageOrder("Desc");
			
			ResrCust resrCust = new ResrCust();
			setResrCustForGetList(resrCust, evt);
			if(czybmService.hasGNQXByCzybh(evt.getCzybh(),"0826","查看本人创建记录")){
				resrCust.setHasCreateAuth("1");
			}
			if(czybmService.hasGNQXByCzybh(evt.getCzybh(),"0826","查看所有接触记录")){
				resrCust.setHasConAuth("1");
			}
			resrCustService.findPageBySql(page, resrCust);
			List<ResrCustResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ResrCustResp.class);
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
	 * 设置ResrCust，在获取主页时用
	 * @author	created by zb
	 * @date	2020-4-11--下午5:05:07
	 * @param resrCust
	 * @param evt
	 * @return
	 */
	private ResrCust setResrCustForGetList(final ResrCust resrCust, ResrCustEvt evt) {
		resrCust.setAppType(evt.getAppType());
		resrCust.setCzybh(evt.getCzybh());
		resrCust.setDescr(evt.getDescr());
		resrCust.setMobile1(evt.getMobile1());
		resrCust.setAddress(evt.getAddress());
		resrCust.setCustKind(evt.getCustKind());
		resrCust.setCustResStat(evt.getCustResStat());
		resrCust.setLastUpdateType(evt.getLastUpdateType());
		resrCust.setContactBeginDate(evt.getContactBeginDate());
		resrCust.setContactEndDate(evt.getContactEndDate());
		resrCust.setNotContLastUpdateType(evt.getNotContLastUpdateType());
		resrCust.setNotContactBeginDate(evt.getNotContactBeginDate());
		resrCust.setNotContactEndDate(evt.getNotContactEndDate());
		resrCust.setDispatchDateType(evt.getDispatchDateType());
		resrCust.setDispatchBeginDate(evt.getDispatchBeginDate());
		resrCust.setDispatchEndDate(evt.getDispatchEndDate());
		resrCust.setCustTag(evt.getTagPks());
		resrCust.setResrType(evt.getResrType());
		resrCust.setHaveCustTag(evt.getHaveCustTag());
		return resrCust;
	}
	/**
	 * 设置时间，在获取主页时用（否则无法导入到class中）
	 * @author	created by zb
	 * @date	2020-4-13--上午10:46:27
	 * @param json
	 * @return
	 */
	private JSONObject setDateForGetList(final JSONObject json) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("contactBeginDate") && !"null".equals(json.get("contactBeginDate").toString())) 
				json.put("contactBeginDate", sdf.parse(json.get("contactBeginDate").toString()));
			if(json.containsKey("contactEndDate") && !"null".equals(json.get("contactEndDate").toString()))
				json.put("contactEndDate", sdf.parse(json.get("contactEndDate").toString()));
			if(json.containsKey("notContactBeginDate") && !"null".equals(json.get("notContactBeginDate").toString())) 
				json.put("notContactBeginDate", sdf.parse(json.get("notContactBeginDate").toString()));
			if(json.containsKey("notContactEndDate") && !"null".equals(json.get("notContactEndDate").toString()))
				json.put("notContactEndDate", sdf.parse(json.get("notContactEndDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getResrCustMapperList")
	public void getResrCustMapperList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		BasePageQueryResp<ResrCustResp> respon=new BasePageQueryResp<ResrCustResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
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
			
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			
			ResrCust resrCust = new ResrCust();
			resrCust.setCode(evt.getCode());
			resrCustService.findResrCustMapperPageBySql(page, resrCust);
			List<ResrCustResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ResrCustResp.class);
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
	
	@RequestMapping("/doSaveResrCust")
	public void doSaveResrCust(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
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
			if(StringUtils.isBlank(evt.getResrCustPoolNo())){
				evt.setResrCustPoolNo("RCP0000001");
			}
			resrCustService.doClientSave(evt);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateResrCust")
	public void doUpdateResrCust(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
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
			ResrCust resrCust = new ResrCust();
			if(StringUtils.isNotBlank(evt.getCode())){
				resrCust = resrCustService.get(ResrCust.class, evt.getCode());
			}	
			resrCustService.doClientUpdate(evt,resrCust);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateResStat")
	public void doUpdateResStat(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
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
			evt = (ResrCustEvt)JSONObject.toBean(json,ResrCustEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			ResrCust resrCust = new ResrCust();
			resrCust = resrCustService.get(ResrCust.class, evt.getCode());
			if(resrCust.getDispatchDate() == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("派单时间为空，不能操作");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}else{
				if(DateUtil.dateDiff(new Date(),resrCust.getDispatchDate())>=30){
					respon.setReturnCode("400001");
					respon.setReturnInfo("只能操作派单时间在一个月内的客户");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			resrCustService.doClientUpdateCustResStat(evt);
			
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
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
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
			custCon.setCustCode("");
			custCon.setResrCustCode(evt.getCode());
			custCon.setType("3");
			custCon.setConDate(new Date());
			custCon.setRemarks(evt.getRemarks());
			custCon.setConMan(evt.getCzybh());
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(evt.getCzybh());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			custCon.setNextConDate(evt.getNextConDate());
			custCon.setConWay(evt.getConWay());
			custConService.save(custCon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateCustContact")
	public void doUpdateCustContact(HttpServletRequest request, HttpServletResponse response){
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
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
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
			custCon = resrCustService.get(CustCon.class, evt.getPk());
			if(custCon != null){
				if(!DateUtil.startOfTheDay(new Date()).toString().equals(DateUtil.startOfTheDay(custCon.getLastUpdate()).toString())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("只能修改当天接触记录");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			custCon.setRemarks(evt.getRemarks());
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(evt.getCzybh());
			custCon.setExpired("F");
			custCon.setActionLog("EDIT");
			custCon.setNextConDate(evt.getNextConDate());
			custCon.setConWay(evt.getConWay());
			custConService.update(custCon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getResrCustDetail")
	public void getResrCustDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		ResrCustResp respon=new ResrCustResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
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
			
			Map <String,Object> map = resrCustService.getResrCustDetail(evt.getCode());
			BeanConvertUtil.mapToBean(map, respon);
			
			// 非本人创建，线索池配置“是否虚拟拨号”为“是”、资源客户状态为信息待确认、信息无效的记录，隐藏手机号中间4位
			if("1".equals(respon.getIsVirtualPhone()) && !this.getUserContext(request).getCzybh().trim().equals(respon.getCrtCzy().trim())
					&& ("0".equals(respon.getCustResStat()) || "2".equals(respon.getCustResStat()))){
				if(StringUtils.isNotBlank(respon.getMobile1())){
					String mobile1 = respon.getMobile1();
					respon.setMobile1(mobile1.substring(0, 3) + "****" + mobile1.substring(7, mobile1.length()));
				}
				if(StringUtils.isNotBlank(respon.getMobile2())){
					String mobile2 = respon.getMobile2();
					respon.setMobile2(mobile2.substring(0, 3) + "****" + mobile2.substring(7, mobile2.length()));
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
			page.setPageSize(5);
			page.setPageOrderBy("LastUpdate");
			page.setPageOrder("DESC");
			CustCon custCon = new CustCon();
			if(StringUtils.isEmpty(evt.getCode())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("资源客户号为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			custCon.setResrCustCode(evt.getCode());
			custCon.setExpired("F");
			UserContext uc = new UserContext();
			Czybm czybm = czybmService.get(Czybm.class, evt.getCzybh());
			uc.setCzybh(evt.getCzybh());
			uc.setCustRight(czybm.getCustRight());
			uc.setEmnum(czybm.getEmnum());
			custCon.setIsGetCallRecord("1");
			custConService.findPageBySql(page, custCon, uc);
			List<CustConResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), CustConResp.class);
			for (int i = 0; i < listBean.size(); i++) {
				String callRecord = listBean.get(i).getCallRecord();
				//录音文件是否传至阿里云,是则返回阿里云地址
				if ("1".equals(listBean.get(i).getCallRecordStatus())) {
					callRecord = OssConfigure.cdnAccessUrl + "/custCallRecord/" + callRecord.substring(0, 5) + "/" + callRecord;
					callRecord = homeDecorBase64Encrypt(callRecord);
				} else if (StringUtils.isNotBlank(listBean.get(i).getMobileFilePath())) {
					callRecord = homeDecorBase64Encrypt(listBean.get(i).getMobileFilePath());
				}
				listBean.get(i).setMobileFilePath("");
				listBean.get(i).setCallRecord(callRecord);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getRegionList")
	public void getRegionList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		RegionResp respon=new RegionResp();
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
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(10);
			Region region = new Region();
			region.setDescr(evt.getRegionDescr());
			regionService.findPageBySql(page, region);
			List<RegionResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), RegionResp.class);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getConRemMinLen")
	public void getConRemMinLen(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		ResrCustResp respon=new ResrCustResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
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
			respon.setConRemMinLen(resrCustService.getConRemMinLen());
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doCustTeamGiveUp")
	public void doCustTeamGiveUp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		ResrCustResp respon=new ResrCustResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
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
			ResrCust rc = new ResrCust();
			rc.setCodes(evt.getCodes());
			rc.setCzybh(evt.getCzybh());
			rc.setRemark(evt.getRemark());
			Result result = resrCustService.doCustTeamGiveUp(rc);
			if(!result.isSuccess()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result.getInfo());
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doCancelCust")
	public void doCancelCust(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		ResrCustResp respon=new ResrCustResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
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
			ResrCust rc = new ResrCust();
			rc.setCodes(evt.getCodes());
			rc.setCzybh(evt.getCzybh());
			rc.setCancelRsn(evt.getCancelRsn());
			rc.setCancelRemarks(evt.getCancelRemarks());
			Result result = resrCustService.doCancelCust(rc);
			if(!result.isSuccess()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result.getInfo());
			}else{
				respon.setReturnInfo(result.getInfo());
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doCustTeamChangeBusiness")
	public void doCustTeamChangeBusiness(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		ResrCustResp respon=new ResrCustResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
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
			ResrCust rc = new ResrCust();
			rc.setCodes(evt.getCodes());
			rc.setCzybh(evt.getCzybh());
			rc.setBusinessMan(evt.getBusinessMan());
			rc.setRemark(evt.getRemark());
			Result result = resrCustService.doCustTeamChangeBusiness(rc);
			if(!result.isSuccess()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result.getInfo());
			}else{
				respon.setReturnInfo(result.getInfo());
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getResrCustTagList")
	public void getResrCustTag(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		BasePageQueryResp<CustTagResp> respon=new BasePageQueryResp<CustTagResp>();
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
			List <Map<String, Object>> resrCustTagList = resrCustService.getResrCustTagList(evt.getCode(), evt.getCzybh());
			List<CustTagResp> listBean=BeanConvertUtil.mapToBeanList(resrCustTagList, CustTagResp.class);
			respon.setDatas(listBean);
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
			List <Map<String, Object>> custNetCnlList = resrCustService.getCustNetCnlList();
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
			List <Map<String, Object>> custNetCnlList = resrCustService.getCustNetCnlListBySource(evt.getSource());
			List<CustNetCnlResp> listBean=BeanConvertUtil.mapToBeanList(custNetCnlList, CustNetCnlResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getResrCustPoolList")
	public void getResrCustPoolList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		BasePageQueryResp<ResrCustPoolResp> respon=new BasePageQueryResp<ResrCustPoolResp>();
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
			List <Map<String, Object>> resrCustPoolList = resrCustService.getResrCustPoolList(evt);
			List<ResrCustPoolResp> listBean=BeanConvertUtil.mapToBeanList(resrCustPoolList, ResrCustPoolResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getDefaultResrCustPoolNo")
	public void getDefaultResrCustPoolNo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ResrCustEvt evt=new ResrCustEvt();
		ResrCustPoolResp respon=new ResrCustPoolResp();
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
			String defaultResrCustPoolNo = resrCustService.getDefaultResrCustPoolNo(evt);
			respon.setDefaultResrCustPoolNo(defaultResrCustPoolNo);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
}
