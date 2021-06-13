package com.house.home.client.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.bean.basic.SmsBean;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.SmsAccountEvt;
import com.house.home.client.service.evt.UpdatePwdEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.WorkerDetailResp;
import com.house.home.client.util.MD5EncryptionMgr;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.WorkerService;


@RequestMapping("/client/worker")
@Controller
public class ClientWorkerController extends ClientBaseController{
	@Autowired
	private WorkerService workerService;
	/**
	 * 查看司机详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getWorkerDetail")
	public void getWorkerDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt=new BaseEvt();
		WorkerDetailResp respon=new WorkerDetailResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt)JSONObject.toBean(json,BaseEvt.class);
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
			Worker worker = workerService.getByPhoneAndMm(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
			if (worker==null){
				respon.setReturnCode("300005");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if("T".equals(worker.getExpired().trim())){
				respon.setReturnCode("300005");
				respon.setReturnInfo("账号已过期");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);

			worker.setToken(MD5EncryptionMgr.md5Encryption(worker.getCode() + new Date()));
			if("5".equals(request.getHeader("AppType"))){
				respon.setToken(worker.getToken());
			}
			bind(worker,request);
			respon.setCode(worker.getCode());
			respon.setNameChi(worker.getNameChi());
			respon.setPhone(worker.getPhone());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 司机注销接口
	 */
	@RequestMapping("/loginOutWorker")
	public void loginOutWorker(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt=new BaseEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt)JSONObject.toBean(json,BaseEvt.class);
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
			HttpSession session = request.getSession(false);
			if (session!=null) {
			    session.invalidate();
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 司机修改密码接口
	 */
	@RequestMapping("/updateWorkerPwd")
	public void updateWorkerPwd(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UpdatePwdEvt evt=new UpdatePwdEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(UpdatePwdEvt) JSONObject.toBean(json, UpdatePwdEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Worker worker=workerService.getByPhoneAndMm(evt.getPhone(),DesUtils.encode(evt.getOldPwd()));
			if(worker==null){
				respon.setReturnCode("300005");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if (!evt.getConfirmPwd().equals(evt.getNewPwd())){
				respon.setReturnCode("300009");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			worker.setMm(DesUtils.encode(evt.getNewPwd()));
			workerService.update(worker);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 重置密码
	 */
	@RequestMapping("/resetWorkerPwd")
	public void resetWorkerPwd(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		SmsAccountEvt evt=new SmsAccountEvt();
		BaseResponse respon=new BaseResponse();
		SmsBean sms= null;
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(SmsAccountEvt) JSONObject.toBean(json, SmsAccountEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象合法字段验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if("5".equals(request.getHeader("AppType"))){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				byte[] smsBytes = RedisUtil.get(redisConnection, evt.getPortalAccount());
				if(smsBytes != null && smsBytes.length > 0){
					sms = (SmsBean) SerializeUtil.unSerialize(smsBytes);
				}
				redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				RedisUtil.del(redisConnection, evt.getPortalAccount());
			}else{
				sms = (SmsBean) request.getSession().getAttribute(CommonConstant.SMS_CODE_KEY);
			}
			if(sms==null){
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			if(DateUtil.getDiffTime(sms.getDate(), DateUtil.DateToString(new Date()))>60){
				//验证码超时
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			if (!evt.getConfirmPwd().equals(evt.getPortalPwd())){
				respon.setReturnCode("300009");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(evt.getSmsPwd().equalsIgnoreCase(sms.getCode())&&evt.getSmsType().equalsIgnoreCase(sms.getType())){
				Worker worker=workerService.getByPhone(evt.getPortalAccount());
				if(worker==null){
					respon.setReturnCode("300005");
					respon.setReturnInfo("工人信息中无此手机号");
					returnJson(respon, response, msg, respon, request, interfaceLog);
					return;
					}
				
				worker.setMm(DesUtils.encode(evt.getPortalPwd()));
				workerService.update(worker);
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}else{
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 绑定用户上下文数据到session中
	 * 
	 * @param user
	 * @param request
	 */
	private void bind(Worker user, HttpServletRequest request) {
		if (null == user)
			throw new RuntimeException("待载入缓存的用户对象为空错误！");
		logger.debug("用户登入验证通过，开始绑定用户到Session中");

		UserContext uc = new UserContext();
		try {
			uc.setPhone(user.getPhone());
			uc.setCzybh(user.getCode());
			UserContextHolder.set(uc);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("绑定用户上下文数据出现异常：", e);
			throw new RuntimeException(e);
		}

		logger.debug("绑定用户到Session中结束");
		String appType = request.getHeader("AppType");
		if("5".equals(appType)){
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(8);
			RedisUtil.set(redisConnection, user.getToken(), SerializeUtil.serialize(uc), 604800L);
		}else{
			request.getSession().setAttribute(CommonConstant.USER_APP_KEY, uc);
			
			//Tomcat日志无法通过USER_CONTEXT_KEY获取操作员编号，所以需要在session中直接记录操作员编号
			request.getSession().setAttribute(CommonConstant.CZYBH, uc.getCzybh()); 
		}
	}
}
