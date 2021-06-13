package com.house.home.client.controller;

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
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.DoUpdateCustMsgStatusEvt;
import com.house.home.client.service.evt.GetCustMessageListEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GetCustMessageListResp;
import com.house.home.entity.basic.CustMessage;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.CustMessageService;

/**
 * 个人消息的接口
 * @author 
 *
 */
@RequestMapping("/client/custMessage")
@Controller
public class ClientCustMessageController extends ClientBaseController{
	
	@Autowired
	private CustMessageService custMessageService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustMessageList")
	public void getCustMessageList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustMessageListEvt evt=new GetCustMessageListEvt();
		BasePageQueryResp<GetCustMessageListResp> respon=new BasePageQueryResp<GetCustMessageListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustMessageListEvt)JSONObject.toBean(json,GetCustMessageListEvt.class);
			
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
			List<GetCustMessageListResp> listBean;
			if(StringUtils.isNotBlank(evt.getRcvCZY())){
				CustMessage custMessage = new CustMessage();
				custMessage.setRcvCZY(evt.getRcvCZY());
				custMessage.setRcvStatus(evt.getRcvStatus());
				this.custMessageService.getCustMessageList(page, custMessage);
				listBean = BeanConvertUtil.mapToBeanList(page.getResult(), GetCustMessageListResp.class);
			}else{
				listBean = new ArrayList<GetCustMessageListResp>();
			}
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
	
	@RequestMapping("/doUpdateCustMessageStatus")
	public void doUpdateCustMessageStatus(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoUpdateCustMsgStatusEvt evt=new DoUpdateCustMsgStatusEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (DoUpdateCustMsgStatusEvt)JSONObject.toBean(json,DoUpdateCustMsgStatusEvt.class);
			
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
			Map<String, Object> returnMap = this.custMessageService.doUpdateCustMessageStatuc(evt);
			respon.setReturnCode(returnMap.get("returnCode").toString());
			respon.setReturnInfo(returnMap.get("returnInfo").toString());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
