package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.jpush.Notice;
import com.house.home.client.jpush.TestJpushClient;
import com.house.home.client.service.evt.DelayExecEvt;
import com.house.home.client.service.evt.NoticeEvt;
import com.house.home.client.service.evt.PersonMessageEvt;
import com.house.home.client.service.evt.PersonMessageQueryEvt;
import com.house.home.client.service.evt.PersonMessageUpdateEvt;
import com.house.home.client.service.resp.BaseNumberResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.DelayExecResp;
import com.house.home.client.service.resp.PersonMessageDetailResp;
import com.house.home.client.service.resp.PersonMessageQueryResp;
import com.house.home.entity.basic.DelayExec;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.service.basic.PersonMessageService;

/**
 * 个人消息的接口
 * @author 
 *
 */
@RequestMapping("/client/personMessage")
@Controller
public class ClientPersonMessageController extends ClientBaseController{
	@Autowired
	private PersonMessageService personMessageService;
	/**
	 * 查看个人消息总览接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPersonMessageSummary")
	public void getPersonMessageSummary(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PersonMessageQueryEvt evt=new PersonMessageQueryEvt();
		BasePageQueryResp<PersonMessageQueryResp> respon=new BasePageQueryResp<PersonMessageQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PersonMessageQueryEvt)JSONObject.toBean(json,PersonMessageQueryEvt.class);
			
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
			page.setPageOrderBy("SendDate");
			page.setPageOrder("Desc");
			String[] msgTypes=evt.getMsgType().split(",");
			List<PersonMessageQueryResp> listBean=new ArrayList<PersonMessageQueryResp>();
			for(String msgType:msgTypes){
				PersonMessageQueryResp rep=null;
				PersonMessage personMessage = new PersonMessage();
				personMessage.setMsgType(msgType);
				personMessage.setRcvType(evt.getRcvType());
				personMessage.setRcvCzy(evt.getRcvCzy());
				personMessage.setRcvStatus("0");
				personMessage.setMsgRelCustCode(evt.getMsgRelCustCode());
				personMessageService.findPageBySqlForClient(page, personMessage);
				//对应未读消息数
				if(page.getResult().size()>0){
					rep=(PersonMessageQueryResp) BeanConvertUtil.mapToBeanList(page.getResult(), PersonMessageQueryResp.class).get(0);
					personMessage.setRcvStatus("0");
					long la = personMessageService.getMessageCount(personMessage);
					rep.setNumber(la);
				}
			
				listBean.add(rep);
			}
			
			respon.setDatas(listBean);
			respon.setHasNext(false);
			respon.setRecordSum((long) listBean.size());
			respon.setTotalPage((long) 1);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 查看个人消息列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPersonMessageList")
	public void getPersonMessageList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PersonMessageQueryEvt evt=new PersonMessageQueryEvt();
		BasePageQueryResp<PersonMessageQueryResp> respon=new BasePageQueryResp<PersonMessageQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PersonMessageQueryEvt)JSONObject.toBean(json,PersonMessageQueryEvt.class);
			
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
			page.setPageOrderBy("SendDate");
			page.setPageOrder("Desc");
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType(evt.getMsgType());
			personMessage.setRcvType(evt.getRcvType());
			personMessage.setRcvCzy(evt.getRcvCzy());
			personMessage.setRcvStatus("0");
			personMessage.setMsgRelCustCode(evt.getMsgRelCustCode());
			personMessage.setTimeoutFlag(evt.getTimeoutFlag());
			personMessage.setAddress(evt.getAddress());
			personMessage.setPrjStatus(evt.getPrjStatus());
			personMessageService.findPageBySqlForClient(page, personMessage);
			List<PersonMessageQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PersonMessageQueryResp.class);
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
	 * 查看个人消息详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPersonMessageDetail")
	public void getPersonMessageDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PersonMessageUpdateEvt evt=new PersonMessageUpdateEvt();
		PersonMessageDetailResp respon=new PersonMessageDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PersonMessageUpdateEvt)JSONObject.toBean(json,PersonMessageUpdateEvt.class);
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
			if (evt.getPk()!=null){
				PersonMessage personMessage = personMessageService.get(PersonMessage.class, evt.getPk());
				if (personMessage!=null){
					BeanUtilsEx.copyProperties(respon, personMessage);
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取个人消息未读条数接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPersonMessageNumber")
	public void getPersonMessageNumber(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PersonMessageEvt evt=new PersonMessageEvt();
		BaseNumberResp respon=new BaseNumberResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PersonMessageEvt)JSONObject.toBean(json,PersonMessageEvt.class);
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
			PersonMessage personMessage = new PersonMessage();
			personMessage.setRcvCzy(evt.getRcvCzy());
			personMessage.setRcvType(evt.getRcvType());
			personMessage.setMsgType(evt.getMsgType());
			personMessage.setRcvStatus("0");
			long la = personMessageService.getMessageCount(personMessage);
			respon.setNumber(la);
			respon.setMsgType(evt.getMsgType());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 批量修改个人消息状态接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateBatchPersonMessage")
	public void updateBatchPersonMessage(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PersonMessageEvt evt=new PersonMessageEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PersonMessageEvt)JSONObject.toBean(json,PersonMessageEvt.class);
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
			PersonMessage personMessage = new PersonMessage();
			personMessage.setRcvCzy(evt.getRcvCzy());
			personMessage.setRcvType(evt.getRcvType());
			personMessage.setMsgType(evt.getMsgType());
			if(StringUtils.isNotBlank(evt.getRcvCzy())&&StringUtils.isNotBlank(evt.getMsgType())&&
				!"5".equals(evt.getMsgType())&&!"7".equals(evt.getMsgType())&&!"1".equals(evt.getMsgType())
				&&!"18".equals(evt.getMsgType())){
				personMessageService.updateBatch(personMessage);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 修改个人消息状态接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePersonMessage")
	public void updatePersonMessage(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PersonMessageUpdateEvt evt=new PersonMessageUpdateEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PersonMessageUpdateEvt)JSONObject.toBean(json,PersonMessageUpdateEvt.class);
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
			if (evt.getPk()!=null){
				PersonMessage personMessage = personMessageService.get(PersonMessage.class, evt.getPk());
				if (personMessage!=null){
					if(!personMessageService.existsRelatedRecord(personMessage)) {
						respon.setReturnCode("400001");
						respon.setReturnInfo("无相应记录不允许完成");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
					personMessage.setRcvStatus(evt.getRcvStatus());
					if(StringUtils.isNotBlank(evt.getRemarks())){
						personMessage.setRemarks(evt.getRemarks());
					}
					personMessage.setRcvDate(new Date());
					personMessageService.update(personMessage);
				}else{
					respon.setReturnCode("300102");
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
	/**推送消息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pushPersonMessage")
	public void pushPersonMessage(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		NoticeEvt evt=new NoticeEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (NoticeEvt)JSONObject.toBean(json,NoticeEvt.class);
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
			Notice notice = new Notice();
			BeanUtilsEx.copyProperties(notice, evt);
			TestJpushClient.doPush(notice);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}

	}
	
	@RequestMapping("/doSaveDelayExec")
	public void doSaveDelayExec(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DelayExecEvt evt=new DelayExecEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = this.getJson(request,msg,json,respon);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("delayDate")) 
			json.put("delayDate", sdf.parse(json.get("delayDate").toString()));
			evt = (DelayExecEvt)JSONObject.toBean(json,DelayExecEvt.class);
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
			PersonMessage personMessage = personMessageService.get(PersonMessage.class, evt.getMsgPk());
			if(personMessage!=null){
				personMessage.setSendDate(evt.getDelayDate());
				personMessage.setPushStatus("0");
				personMessageService.update(personMessage);
			}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("数据异常，未找到信息记录");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			DelayExec delayExec = new DelayExec();
			delayExec.setMsgPk(evt.getMsgPk());
			delayExec.setCzybh(evt.getCzybh());
			delayExec.setRemarks(evt.getDelayReson());
			delayExec.setDate(evt.getDelayDate());
			delayExec.setLastUpdate(new Date());
			delayExec.setLastUpdatedBy(evt.getCzybh());
			delayExec.setActionLog("ADD");
			delayExec.setExpired("F");
			personMessageService.save(delayExec);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDelayExecList")
	public void getDelayExecList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DelayExecEvt evt=new DelayExecEvt();
		BasePageQueryResp<DelayExecResp> respon=new BasePageQueryResp<DelayExecResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DelayExecEvt)JSONObject.toBean(json,DelayExecEvt.class);
			
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
			page.setPageOrderBy("Date");
			page.setPageOrder("Asc");
			personMessageService.getDelayExecList(page, evt.getMsgPk());
			List<DelayExecResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DelayExecResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			if(listBean.size()==0){
				respon.setNoListTip("无延后记录");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getMsgInfo")
	public void getMsgInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DelayExecEvt evt=new DelayExecEvt();
		PersonMessageDetailResp respon=new PersonMessageDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DelayExecEvt)JSONObject.toBean(json,DelayExecEvt.class);
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
			Map<String,Object> map = personMessageService.getMsgInfo(evt.getMsgPk());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
