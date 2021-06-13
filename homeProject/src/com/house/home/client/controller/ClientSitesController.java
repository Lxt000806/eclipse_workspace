package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.GetCustLoanEvt;
import com.house.home.client.service.evt.GetIntProduceEvt;
import com.house.home.client.service.evt.GetIntProgressEvt;
import com.house.home.client.service.evt.SignInQueryEvt;
import com.house.home.client.service.evt.SitePreparationEvt;
import com.house.home.client.service.evt.WorkerListEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustCheckResp;
import com.house.home.client.service.resp.GetCustLoanResp;
import com.house.home.client.service.resp.GetIntProduceResp;
import com.house.home.client.service.resp.GetIntProgressResp;
import com.house.home.client.service.resp.SignInQueryResp;
import com.house.home.client.service.resp.SitePreparationResp;
import com.house.home.client.service.resp.WorkerListResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.BuilderRep;
import com.house.home.service.basic.SignInService;
import com.house.home.service.project.PrjProgCheckService;

@RequestMapping("/client/sites")
@Controller
public class ClientSitesController extends ClientBaseController {
	@Autowired
	private PrjProgCheckService prjProgCheckService;
	@Autowired
	private SignInService signInService;
	
	
	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping("/getSitePreparationList")
	public  void getSitePreparationList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		SitePreparationEvt evt=new SitePreparationEvt();
		BasePageQueryResp<SitePreparationResp> respon=new BasePageQueryResp<SitePreparationResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("date")) 
			json.put("date", sdf.parse(json.get("date").toString()));
			evt=(SitePreparationEvt) JSONObject.toBean(json, SitePreparationEvt.class);
			
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
			prjProgCheckService.getSitePreparationList(page, evt);
			List<SitePreparationResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),SitePreparationResp.class);
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
	@SuppressWarnings("deprecation")
	@RequestMapping("/addSitePrepartion")
	public  void addSitePrepartion(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		SitePreparationEvt evt=new SitePreparationEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("beginDate")) 
			json.put("beginDate", sdf.parse(json.get("beginDate").toString()));
			if(json.containsKey("endDate")) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt=(SitePreparationEvt) JSONObject.toBean(json, SitePreparationEvt.class);
			if("2".equals(evt.getType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("不允许进行安全报备");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Date beginDate = new Date();
			beginDate.setHours(15);
			beginDate.setMinutes(0);
			beginDate.setSeconds(0);
			Date endDate = new Date();
			endDate.setHours(23);
			endDate.setMinutes(59);
			endDate.setSeconds(59);
			if(!DateUtil.isInDateRange(beginDate, endDate, new Date())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("每天15:00-24:00点才能提交");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Customer customer = prjProgCheckService.get(Customer.class, evt.getCustCode());
			if(customer != null && !"1".equals(customer.getConstructStatus().trim())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("只是正在施工工地，才需要进行工地报备");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			String result = prjProgCheckService.hasSitePrepartion(evt);
			if(!result.equals("success")){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result);
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getSitePrepartionDetail")
	public  void getSitePrepartionDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		SitePreparationEvt evt=new SitePreparationEvt();
		SitePreparationResp respon=new SitePreparationResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(SitePreparationEvt) JSONObject.toBean(json, SitePreparationEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map =prjProgCheckService.getSitePrepartionById(evt);
			BeanConvertUtil.mapToBean(map,respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@SuppressWarnings("deprecation")
	@RequestMapping("/updateSitePrepartion")
	public  void updateSitePrepartion(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		SitePreparationEvt evt=new SitePreparationEvt();
		SitePreparationResp respon=new SitePreparationResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("beginDate")) 
			json.put("beginDate", sdf.parse(json.get("beginDate").toString()));
			if(json.containsKey("endDate")) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt=(SitePreparationEvt) JSONObject.toBean(json, SitePreparationEvt.class);
			if("2".equals(evt.getType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("不允许进行安全报备");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Date beginDate = new Date();
			beginDate.setHours(15);
			beginDate.setMinutes(0);
			beginDate.setSeconds(0);
			Date endDate = new Date();
			endDate.setHours(23);
			endDate.setMinutes(59);
			endDate.setSeconds(59);
			if(!DateUtil.isInDateRange(beginDate, endDate, new Date())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("每天15:00-24:00点才能提交");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			BuilderRep br = prjProgCheckService.get(BuilderRep.class,evt.getPk());
			br.setBuildStatus(evt.getBuildStatus());
			if(evt.getBeginDate()!=null) br.setBeginDate(evt.getBeginDate());
			if(evt.getEndDate()!=null)	 br.setEndDate(evt.getEndDate());
			br.setRemark(evt.getRemark());
			br.setLastUpdate(new Date());
			br.setLastUpdatedBy(evt.getProjectMan());
			br.setExpired("F");
			br.setActionLog("EDIT");
			prjProgCheckService.update(br);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getCustCheck")
	public  void getCustCheck(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		SitePreparationEvt evt=new SitePreparationEvt();
		CustCheckResp respon=new CustCheckResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(SitePreparationEvt) JSONObject.toBean(json, SitePreparationEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map= prjProgCheckService.getCustCheck(evt);
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerList")
	public  void getWorkerList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		WorkerListEvt evt=new WorkerListEvt();
		BasePageQueryResp<WorkerListResp> respon=new BasePageQueryResp<WorkerListResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerListEvt) JSONObject.toBean(json, WorkerListEvt.class);
			
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
			prjProgCheckService.getCustWorkerList(page, evt);
			List<WorkerListResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerListResp.class);
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
	
	@RequestMapping("/getIntProgress")
	public  void getIntProgress(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		GetIntProgressEvt evt=new GetIntProgressEvt();
		GetIntProgressResp respon=new GetIntProgressResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetIntProgressEvt) JSONObject.toBean(json, GetIntProgressEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map = prjProgCheckService.getIntProgress(evt.getCustCode());
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustLoan")
	public  void getCustLoan(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		GetCustLoanEvt evt=new GetCustLoanEvt();
		GetCustLoanResp respon=new GetCustLoanResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetCustLoanEvt) JSONObject.toBean(json, GetCustLoanEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map = prjProgCheckService.getCustLoan(evt.getCustCode());
			if(map == null){
				respon.setAmount(0.0);
				respon.setFirstAmount(0.0);
				respon.setSecondAmount(0.0);
			}
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 工地签到
	 * @author	created by zb
	 * @date	2019-6-21--上午9:32:37
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjSignList")
	public void getPrjSignList(HttpServletRequest request,HttpServletResponse response){
		SignInQueryEvt evt=new SignInQueryEvt();
		BasePageQueryResp<SignInQueryResp> respon=new BasePageQueryResp<SignInQueryResp>();
		
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(SignInQueryEvt) JSONObject.toBean(json, SignInQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			Page<Map<String,Object>> page = new Page<Map<String, Object>>();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			SignIn signIn = new SignIn();
			signIn.setSignCzy(getUserContext(request).getEmnum());
			signIn.setCustCode(evt.getCustCode());
			this.signInService.getPrjSignList(page, signIn);
			List<SignInQueryResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), SignInQueryResp.class);
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
	
	@RequestMapping("/getIntProduce")
	public  void getIntProduce(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		GetIntProduceEvt evt=new GetIntProduceEvt();
		GetIntProduceResp respon=new GetIntProduceResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetIntProduceEvt) JSONObject.toBean(json, GetIntProduceEvt.class);
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map = prjProgCheckService.getIntProduce(evt.getCustCode(),evt.getSupplCode());
			BeanConvertUtil.mapToBean(map, respon);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
