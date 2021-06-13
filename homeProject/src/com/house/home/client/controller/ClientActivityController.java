package com.house.home.client.controller;

import java.util.Date;
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

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.ActivityQueryEvt;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.TicketDetailEvt;
import com.house.home.client.service.evt.TicketQueryEvt;
import com.house.home.client.service.resp.ActivityResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GetCurrActivityResp;
import com.house.home.client.service.resp.TicketResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Ticket;
import com.house.home.service.basic.ActivityService;
import com.house.home.service.basic.TicketService;

import java.util.List;
@RequestMapping("/client/activity")
@Controller
public class ClientActivityController extends ClientBaseController {
	@Autowired
	private ActivityService activityService;
	@Autowired
	private TicketService ticketService;
	@RequestMapping("/getActivityList")
	public void getActivityList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ActivityQueryEvt evt=new ActivityQueryEvt();
		BasePageQueryResp<ActivityResp> respon=new BasePageQueryResp<ActivityResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ActivityQueryEvt) JSONObject.toBean(json, ActivityQueryEvt.class);
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
			activityService.findActByName(page, evt.getActName());
			List<ActivityResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), ActivityResp.class);
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
	@RequestMapping("/getTicketList")
	public void getTicketList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		TicketQueryEvt evt=new TicketQueryEvt();
		BasePageQueryResp<TicketResp> respon=new BasePageQueryResp<TicketResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(TicketQueryEvt) JSONObject.toBean(json, TicketQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(!"actSet".equals(evt.getRequestFromPage()) && StringUtils.isBlank(evt.getActNo())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("活动编号不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			ticketService.findTicketByAct(page, evt.getActNo(), evt.getStatus(),evt.getBusinessMan(),evt.getCustName(),evt.getTicketNo(),evt.getIsSign());
			List<TicketResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), TicketResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	@RequestMapping("/getTicketDetail")
	public void getTicketDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		TicketDetailEvt evt=new TicketDetailEvt();
		TicketResp respon=new TicketResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(TicketDetailEvt) JSONObject.toBean(json, TicketDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map=ticketService.findTicketByActAndNo(evt.getActNo(), evt.getTicketNo());
			if(map==null){
				respon.setReturnCode("200002");
				respon.setReturnInfo("未找到该门票");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/updateTicket")
	public  void updateTicket(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Ticket ticket=ticketService.get(Ticket.class,Integer.parseInt(evt.getId()));
			if(ticket.getPk() <= 0){
				respon.setReturnCode("100006");
				respon.setReturnInfo("该门票号不存在");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
/*			if("1".equals(ticket.getStatus())){
				respon.setReturnCode("100006");
				respon.setReturnInfo("该门票未发放，无法签到！");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}*/
			if("3".equals(ticket.getStatus())){
				respon.setReturnCode("100006");
				respon.setReturnInfo("该门票已经签到，无须再签到！");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ticket.setSignDate(new Date());
			ticket.setStatus("3");
			ticket.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			ticket.setLastUpdate(new Date());
			ticket.setActionLog("EDIT");
			ticketService.update(ticket);
			respon.setReturnInfo("【"+ticket.getTicketNo()+"】门票号签到成功");
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getCurrActivity")
	public  void getCurrActivity(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		GetCurrActivityResp respon=new GetCurrActivityResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			respon.setActNo(activityService.getCurrActivity());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
}
