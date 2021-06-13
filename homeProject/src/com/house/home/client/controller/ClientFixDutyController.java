package com.house.home.client.controller;

import java.sql.Timestamp;
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
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.client.service.evt.FixDutyEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.FixDutyResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.FixDutyDetail;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.project.FixDutyService;

import com.house.framework.web.login.UserContext;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.FixDutyDetailResp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;

@RequestMapping("/client/fixDuty")
@Controller
public class ClientFixDutyController extends ClientBaseController{
	@Autowired
	private FixDutyService fixDutyService;
	@Autowired
	private PersonMessageService personMessageService;
	
	@RequestMapping("/getFixDutyListForApp")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getFixDutyListForApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon=new BasePageQueryResp<FixDutyResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FixDutyEvt) JSONObject.toBean(json, FixDutyEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(1);
			page.setPageSize(10000);
			fixDutyService.getFixDutyList(page, evt);
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), FixDutyResp.class);
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
	
	@RequestMapping("/getFixDutyListForPrjMan")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getFixDutyListForPrjMan(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon=new BasePageQueryResp<FixDutyResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FixDutyEvt) JSONObject.toBean(json, FixDutyEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(1);
			page.setPageSize(10000);
			fixDutyService.getFixDutyListForPrjMan(page, evt);
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), FixDutyResp.class);
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
	
	@RequestMapping("/getFixDutyDetailList")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getFixDutyDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon=new BasePageQueryResp<FixDutyResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FixDutyEvt) JSONObject.toBean(json, FixDutyEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(1);
			page.setPageSize(10000);
			fixDutyService.getFixDutyDetailList(page, evt);
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), FixDutyResp.class);
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
	
	@RequestMapping("/getBaseCheckItemListForApp")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getBaseCheckItemListForApp(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon=new BasePageQueryResp<FixDutyResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FixDutyEvt) JSONObject.toBean(json, FixDutyEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			if(StringUtils.isNotBlank(evt.getCustCode())){
				Customer customer = fixDutyService.get(Customer.class, evt.getCustCode());
				CustType custType = fixDutyService.get(CustType.class, customer.getCustType());
				evt.setWorkerClassify(custType.getWorkerClassify());
			}
			fixDutyService.getBaseCheckItemList(page, evt);
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), FixDutyResp.class);
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
	
	
	@RequestMapping("/addFixDuty")
	public void addFixDuty(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		FixDutyResp respon=new FixDutyResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
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
			FixDuty fixDuty = new FixDuty();
			BeanUtilsEx.copyProperties(fixDuty, evt);
	        
	        fixDuty.setM_umState("A");
			String xmlData = evt.getXmlData();
			JSONArray array= JSONArray.fromObject(xmlData);
			double offerPrj=0.0;
			double material=0.0;
			for(int i=0;i<array.size();i++){
				JSONObject jsonObject2=JSONObject.fromObject(array.get(i));
		        FixDutyDetail fdd=(FixDutyDetail)JSONObject.toBean(jsonObject2, FixDutyDetail.class);
		        offerPrj+=fdd.getQty()*fdd.getOfferPri();
		        material+=fdd.getQty()*fdd.getMaterial();
			}
			fixDuty.setOfferPrj(offerPrj);
			fixDuty.setMaterial(material);
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
			Result result = fixDutyService.saveForProc(fixDuty, xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	
	@RequestMapping("/recoveryFixDuty")
	public void recoveryFixDuty(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		FixDutyResp respon=new FixDutyResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
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
			FixDuty fixDuty = fixDutyService.get(FixDuty.class, evt.getNo());
			if (fixDuty==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if ("1".equals(evt.getStatus())){//收回状态修改为草稿
				if (fixDuty.getStatus().trim().equals("2")){
					fixDuty.setStatus(evt.getStatus());
					fixDuty.setActionLog("Edit");
					fixDuty.setLastUpdate(new Timestamp(new Date().getTime()));
					fixDuty.setLastUpdatedBy(evt.getAppCzy());
					fixDutyService.recoveryFixDuty(fixDuty);
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非提交状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/updateFixDutyStatus")
	public void updateFixDutyStatus(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		FixDutyResp respon=new FixDutyResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
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
			FixDuty fixDuty = fixDutyService.get(FixDuty.class, evt.getNo());
			if (fixDuty==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if ("3".equals(evt.getStatus())){//收回状态修改为草稿
				if (fixDuty.getStatus().trim().equals("2")){
					fixDuty.setStatus(evt.getStatus());
					fixDuty.setPrjMan(evt.getPrjMan());
					fixDuty.setPrjRemark(evt.getPrjRemark());
					fixDuty.setIsPrjAllDuty(evt.getIsPrjAllDuty());
					fixDuty.setActionLog("Edit");
					fixDuty.setPrjConfirmDate(new Timestamp(new Date().getTime()));
					fixDuty.setLastUpdate(new Timestamp(new Date().getTime()));
					fixDuty.setLastUpdatedBy(evt.getAppCzy());
					fixDutyService.update(fixDuty);
					PersonMessage pm = new PersonMessage();
					pm.setMsgType("1");
					pm.setMsgRelNo(evt.getNo());
					pm.setProgmsgType("8");
					PersonMessage personMessage = personMessageService.getPersonMessageByCondition(pm);
					if(personMessage != null){
						personMessage.setRcvStatus("1");
						personMessage.setRcvDate(new Date());
						personMessage.setDealNo(fixDuty.getNo());
						personMessageService.update(personMessage);
					}
				}else{
					respon.setReturnCode("100000");
					respon.setReturnInfo("非提交状态的不能修改");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 修改预领料接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateFixDuty")
	public void updateFixDuty(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		FixDutyResp respon=new FixDutyResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
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
			FixDuty fixDuty = new FixDuty();
			BeanUtilsEx.copyProperties(fixDuty, evt);
			fixDuty.setM_umState("M");
			String xmlData = evt.getXmlData();
			xmlData = XmlConverUtil.jsonToXmlNoHead(xmlData);
			Result result = fixDutyService.saveForProc(fixDuty, xmlData);
			if (!result.isSuccess()){
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getFixDutyList")
	public void getFixDutyList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon=new BasePageQueryResp<FixDutyResp>();
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
			page.setPageNo(evt.getPageNo());
			page.setPageSize(10);
			FixDuty fixDuty = new FixDuty();
			fixDuty.setCzybh(evt.getCzybh());
			fixDuty.setNo(evt.getNo());
			fixDuty.setCustCode(evt.getCustCode());
			fixDuty.setStatus(evt.getStatus());
			fixDuty.setAddress(evt.getAddress());
			fixDuty.setWorkType12(evt.getWorkType12());
			fixDuty.setDepartment2s(evt.getDepartment2s());
			UserContext uc = getUserContext(request);
			fixDutyService.getFixDutyList(page,fixDuty, uc);
			List<FixDutyResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), FixDutyResp.class);
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
	@RequestMapping("/getFixDutyDetail")
	public void getFixDutyDetail(HttpServletRequest request, HttpServletResponse response){
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
			page.setPageNo(evt.getPageNo());
			page.setPageSize(100);
			FixDuty fixDuty = new FixDuty();
			fixDuty.setCzybh(evt.getCzybh());
			fixDuty.setNo(evt.getNo());
			fixDutyService.getFixDutyDetail(page,fixDuty);
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
	
	@RequestMapping("/doCommit")
	public void doCommit(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
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
			Object jso = json.get("objData");
			String objData = jso.toString();
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
			FixDuty fixDuty = null;
			fixDuty = fixDutyService.get(FixDuty.class, evt.getNo());
			fixDuty.setStatus("4");
			fixDuty.setCfmMan(evt.getCzybh());
			fixDuty.setCfmMaterial(evt.getCfmMaterial());
			fixDuty.setCfmOfferPrj(evt.getCfmOfferPrj());
			fixDuty.setCfmRemark(evt.getRemarks());
			fixDuty.setCfmDate(new Date());
			fixDuty.setLastUpdate(new Date());
			fixDuty.setLastUpdatedBy(evt.getCzybh());
			fixDutyService.update(fixDuty);
			
			objData = XmlConverUtil.jsonToXmlNoHead(objData);
			Result result = fixDutyService.doUpdateFixDutyDetail(fixDuty, objData);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doCallBack")
	public void doCallBack(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			Object jso = json.get("objData");
			String objData = jso.toString();
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
			FixDuty fixDuty = null;
			fixDuty = fixDutyService.get(FixDuty.class, evt.getNo());
			fixDuty.setStatus("3");
			fixDuty.setCfmRemark(evt.getRemarks());
			fixDuty.setLastUpdate(new Date());
			fixDuty.setLastUpdatedBy(evt.getCzybh());
			fixDutyService.update(fixDuty);
			
			objData = XmlConverUtil.jsonToXmlNoHead(objData);
			Result result = fixDutyService.doUpdateFixDutyDetail(fixDuty, objData);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseCheckItemList")
	public void getBaseCheckItemList(HttpServletRequest request, HttpServletResponse response){
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
			page.setPageNo(evt.getPageNo());
			page.setPageSize(100);
			fixDutyService.getBaseCheckItemList(page,evt.getBaseCheckItemCode(),evt.getCustCode());
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
	
	@RequestMapping("/getPrjItemDescr")
	public void getPrjItemDescr(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		FixDutyEvt evt = new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon = new BasePageQueryResp<FixDutyResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FixDutyEvt) JSONObject.toBean(json, FixDutyEvt.class);
			interfaceLog.setContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			fixDutyService.getPrjItemDescr(page, evt.getCzybh());
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(),FixDutyResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBaseCheckItemDetailList")
	public void getBaseCheckItemDetailList(HttpServletRequest request, HttpServletResponse response){
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
			page.setPageNo(evt.getPageNo());
			page.setPageSize(100);
			fixDutyService.getBaseCheckItemDetailList(page,evt.getCustCode(),evt.getWorkType12());
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
	
	@RequestMapping("/getFixDutyDetailInfo")
	public void getFixDutyDetailInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FixDutyEvt evt=new FixDutyEvt();
		FixDutyResp respon=new FixDutyResp();
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
			Map<String, Object> map = fixDutyService.getFixDutyDetailInfo(evt.getNo());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getDepartment2List")
	public void getDepartment2List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		FixDutyEvt evt = new FixDutyEvt();
		BasePageQueryResp<FixDutyResp> respon = new BasePageQueryResp<FixDutyResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(FixDutyEvt) JSONObject.toBean(json, FixDutyEvt.class);
			interfaceLog.setContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(-1);
			page.setPageSize(100);
			fixDutyService.getDepartment2List(page);
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(),FixDutyResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
