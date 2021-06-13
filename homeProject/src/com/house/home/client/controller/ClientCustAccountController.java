package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.CustPicUploadRule;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.RSAUtils;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.framework.web.token.FormTokenManager;
import com.house.home.bean.basic.SmsBean;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.CustAccountEvt;
import com.house.home.client.service.evt.SmsAccountEvt;
import com.house.home.client.service.evt.UpdateCustAccountPwdEvt;
import com.house.home.client.service.evt.UpdateCustPicAndNickNameEvt;
import com.house.home.client.service.evt.WxCustAccountEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustAccountDetailResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.client.util.MD5EncryptionMgr;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.CustAccountService;
import com.house.home.service.design.CustomerService;

@RequestMapping("/client/custAccount")
@Controller
public class ClientCustAccountController extends ClientBaseController {
	@Autowired
	private CustAccountService custAccountService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FormTokenManager formTokenManager;

	
/**
 * 客户账号详情接口
 */
	@RequestMapping("/getCustAccountDetail")
	public void getCustAccountDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		CustAccountDetailResp respon=new CustAccountDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json = StringUtil.queryStringToJSONObject(request);
			}			
			evt=(BaseEvt) JSONObject.toBean(json, BaseEvt.class);

			String key = getRSAKey("privateKey");
			evt.setPortalPwd(new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(evt.getPortalPwd()), key)));
			
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),null);
			if(custAccount==null){
				respon.setReturnCode("300002");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
			if(custAccount==null){
				respon.setReturnCode("300008");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String,Object>> custCodeList=custAccountService.getCustCodeListByPhone(evt.getPortalAccount());
/*			System.out.println(custCodeList.size());
			if(custCodeList.size()==0){
				respon.setReturnCode("100005");
				respon.setReturnInfo("您不存在施工状态的工地！");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}*/
			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);
			custAccount.setUserToken(MD5EncryptionMgr.md5Encryption(custAccount.getMobile1()+new Date()));
			bind(custAccount,request);
			respon.setMobile1(custAccount.getMobile1());
			respon.setCustCodeList(custCodeList);
			respon.setPicAddr(custAccount.getPicAddr());
			respon.setNickName(custAccount.getNickName());
			respon.setToken(this.formTokenManager.newFormToken(request).getToken());
			if("1".equals(request.getHeader("AppType"))){
				respon.setUserToken(custAccount.getUserToken());
			}else{
				respon.setSessionId(request.getSession().getId());
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 客户注销接口
	 */
	@RequestMapping("/loginOutCustAccount")
	public void  loginOutCustAccount(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
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
			if("1".equals(request.getHeader("AppType"))||"4".equals(request.getHeader("AppType"))){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(6);
				RedisUtil.del(redisConnection, request.getHeader("UserToken"));
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 客户注册
	 */				
	@RequestMapping("/registerCustAccount")
	public void registerCustAccount(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustAccountEvt evt=new CustAccountEvt();
		BaseResponse respon=new BaseResponse();
		SmsBean sms = null;
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustAccountEvt) JSONObject.toBean(json,CustAccountEvt.class);
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

			if("1".equals(request.getHeader("AppType"))){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				byte[] smsBytes = RedisUtil.get(redisConnection, evt.getPortalAccount());
				if(smsBytes.length > 0){
					sms = (SmsBean) SerializeUtil.unSerialize(smsBytes);
				}
			}else{
				sms=(SmsBean) request.getSession().getAttribute(CommonConstant.SMS_CODE_KEY);
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
	
				CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),null);
				if(custAccount!=null){
					respon.setReturnCode("300010");
					respon.setReturnInfo("账号已存在,无需注册");
					returnJson(respon, response, msg, respon, request, interfaceLog);
					return;
				}
/*				Customer customer=customerService.getCustomerByMobile1(evt.getPortalAccount());
				if(customer==null){
					respon.setReturnCode("300005");
					respon.setReturnInfo("客户信息中无此手机号");
					returnJson(respon, response, msg, respon, request, interfaceLog);
					return;
				}*/
					custAccount=new CustAccount();
					custAccount.setLastUpdate(new Date());
					custAccount.setMm(DesUtils.encode(evt.getPortalPwd()));
					custAccount.setMobile1(evt.getPortalAccount());
					custAccount.setRegisterDate(new Date());
					custAccount.setExpired("F");
					custAccount.setNickName(evt.getNickName());
					custAccountService.save(custAccount);
					List<Map<String,Object>> codeList=custAccountService.getCustCodeListByPhoneFromCustomer("",evt.getPortalAccount());
					if(codeList.size()>0){
						for(int i =0 ;i<codeList.size();i++){
							CustMapped custMapped = new CustMapped();
							custMapped.setCustCode(codeList.get(i).get("code")+"");
							custMapped.setCustAccountPK(custAccount.getPk());
							custAccountService.saveCustMapped(custMapped);
						}
					}
//					customerService.updateCustAccountPk(evt.getPortalAccount());
					RedisConnection redisConnection = RedisUtil.getRedisConnection();
					redisConnection.select(7);
					RedisUtil.del(redisConnection, evt.getPortalAccount());
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}else{
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 客户修改密码接口
	 */
	@RequestMapping("/updateCustAccountPwd")
	public  void updateCustAccountPwd(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UpdateCustAccountPwdEvt evt=new UpdateCustAccountPwdEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json = StringUtil.queryStringToJSONObject(request);
			}
			evt=(UpdateCustAccountPwdEvt) JSONObject.toBean(json, UpdateCustAccountPwdEvt.class);
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
			
			String key = getRSAKey("privateKey");
			evt.setOldPwd(new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(evt.getOldPwd()), key)));
			evt.setNewPwd(new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(evt.getNewPwd()), key)));
			evt.setConfirmPwd(new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(evt.getConfirmPwd()), key)));
			
			if(StringUtils.isBlank(evt.getOldPwd())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入旧密码");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(evt.getNewPwd())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入新密码");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(evt.getConfirmPwd())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入确认密码");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if (!evt.getConfirmPwd().equals(evt.getNewPwd())){
				respon.setReturnCode("300009");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			String custPwdVarify = getCustPwdVarify();
			if(evt.getNewPwd().matches(custPwdVarify)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("密码格式不正确");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPhone(), DesUtils.encode(evt.getOldPwd()));
			if(custAccount==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("旧密码输入错误");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			custAccount.setMm(DesUtils.encode(evt.getNewPwd()));
			custAccountService.update(custAccount);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 客户忘记密码接口
	 */
	@RequestMapping("/resetCustAccountPwd")
	public  void resetCustAccountPwd(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		SmsAccountEvt evt=new SmsAccountEvt();
		BaseResponse respon=new BaseResponse();
		SmsBean sms=null;
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

			if("1".equals(request.getHeader("AppType"))){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				byte[] smsBytes = RedisUtil.get(redisConnection, evt.getPortalAccount());
				if(smsBytes.length > 0){
					sms = (SmsBean) SerializeUtil.unSerialize(smsBytes);
				}
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
				respon.setReturnInfo("验证码超时");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			if (!evt.getConfirmPwd().equals(evt.getPortalPwd())){
				respon.setReturnCode("300009");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(evt.getSmsPwd().equalsIgnoreCase(sms.getCode())&&evt.getSmsType().equalsIgnoreCase(sms.getType())){
/*				Customer customer=customerService.getCustomerByMobile1(evt.getPortalAccount());
				if(customer==null){
					respon.setReturnCode("300005");
					respon.setReturnInfo("客户信息中无此手机号");
					returnJson(respon, response, msg, respon, request, interfaceLog);
					return;
				}*/
				CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),null);
				if(custAccount==null){
					respon.setReturnCode("300005");
					respon.setReturnInfo("请先激活账号");
					returnJson(respon, response, msg, respon, request, interfaceLog);
					return;
				}
				custAccount.setMm(DesUtils.encode(evt.getPortalPwd()));
				custAccountService.update(custAccount);
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}else{
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 绑定用户上下文数据到session中
	 * 
	 * @param user
	 * @param request
	 */
	private void bind(CustAccount user, HttpServletRequest request) {
		if (null == user)
			throw new RuntimeException("待载入缓存的用户对象为空错误！");
		logger.debug("用户登入验证通过，开始绑定用户到Session中");
		UserContext uc = new UserContext();
		// 新版本请求头有AppType字段
		try {
			uc.setPhone(user.getMobile1());
			uc.setCzybh("1");
			uc.setAppType("1");
			UserContextHolder.set(uc);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("绑定用户上下文数据出现异常：", e);
			throw new RuntimeException(e);
		}

		logger.debug("绑定用户到Session中结束");
		// 新版本用户信息保存redis
		String appType = request.getHeader("AppType");
		String isToken = request.getHeader("IsToken");
		if("1".equals(appType)||"1".equals(isToken)){
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(6);
			RedisUtil.set(redisConnection, user.getUserToken(), new String(SerializeUtil.serialize(uc)),-1L);
//			redisConnection.expire(user.getUserToken().getBytes(), -1L);    //86400L
		}else{
			request.getSession().setAttribute(CommonConstant.CLIENT_APP_KEY, uc);
			//Tomcat日志无法通过USER_CONTEXT_KEY获取操作员编号，所以需要在session中直接记录操作员编号
			request.getSession().setAttribute(CommonConstant.CZYBH, uc.getPhone()); 
		}
		
	}

	@RequestMapping("/uploadCustPic")
	public void uploadCustPic(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> photoPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
                CustPicUploadRule rule = new CustPicUploadRule(attatchment.getName());
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                        		rule.getFileName(),rule.getFilePath());
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
                fileNameList.add(rule.getFileName());
                photoPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
            }
			respon.setPhotoPathList(photoPathList);
			respon.setPhotoNameList(fileNameList);
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}
	
	@RequestMapping("/updateCustPicAddrAndNickName")
	public  void updateCustPicAddrAndNickName(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UpdateCustPicAndNickNameEvt evt=new UpdateCustPicAndNickNameEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("post".equals(request.getMethod().toLowerCase())){
				json = this.getJson(request,msg,json,respon);
			}else{
				json = StringUtil.queryStringToJSONObject(request);
			}
			evt=(UpdateCustPicAndNickNameEvt) JSONObject.toBean(json, UpdateCustPicAndNickNameEvt.class);
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
			
			CustAccount custAccount = this.custAccountService.getCustAccountByPhone(evt.getPhone(), "");
			if(custAccount == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			custAccount.setPicAddr(evt.getPicAddr());
			custAccount.setNickName(evt.getNickName());
			custAccount.setLastUpdate(new Date());
			
			this.custAccountService.update(custAccount);
			
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * 小程序登陆
	 */				
	@RequestMapping("/wxCustAccount")
	public void wxCustAccount(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WxCustAccountEvt evt=new WxCustAccountEvt();
		CustAccountDetailResp respon=new CustAccountDetailResp();
		SmsBean sms = null;
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WxCustAccountEvt) JSONObject.toBean(json,WxCustAccountEvt.class);
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
			//fastLogin  是否快捷登陆（无验证码）
			if("18888888888".equals(evt.getPortalAccount())){
				evt.setFastLogin(true);
			}
			if(!evt.getFastLogin()){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				byte[] smsBytes = RedisUtil.get(redisConnection, evt.getPortalAccount());
				if(smsBytes.length > 0){
					sms = (SmsBean) SerializeUtil.unSerialize(smsBytes);
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
			}
			if(evt.getFastLogin()||evt.getSmsPwd().equalsIgnoreCase(sms.getCode())&&evt.getSmsType().equalsIgnoreCase(sms.getType())){
				CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),null);
				if(custAccount==null){
					custAccount=new CustAccount();
					custAccount.setLastUpdate(new Date());
					custAccount.setMm(DesUtils.encode("123456"));
					custAccount.setMobile1(evt.getPortalAccount());
					custAccount.setRegisterDate(new Date());
					custAccount.setExpired("F");
					custAccountService.save(custAccount);
					List<Map<String,Object>> codeList=custAccountService.getCustCodeListByPhoneFromCustomer("",evt.getPortalAccount());
					if(codeList.size()>0){
						for(int i =0 ;i<codeList.size();i++){
							CustMapped custMapped = new CustMapped();
							custMapped.setCustCode(codeList.get(i).get("code")+"");
							custMapped.setCustAccountPK(custAccount.getPk());
							custAccountService.saveCustMapped(custMapped);
						}
					}
				}
				List<Map<String,Object>> custCodeList=custAccountService.getCustCodeListByPhone(evt.getPortalAccount());
				if(!evt.getFastLogin()){
					RedisConnection redisConnection = RedisUtil.getRedisConnection();
					redisConnection.select(7);
					RedisUtil.del(redisConnection, evt.getPortalAccount());
				}
				if(custAccount.getUserToken()==null){
					custAccount.setUserToken(MD5EncryptionMgr.md5Encryption(custAccount.getMobile1()));
				}
				bind(custAccount,request);
				respon.setMobile1(custAccount.getMobile1());
				respon.setCustCodeList(custCodeList);
				respon.setPicAddr(custAccount.getPicAddr());
				respon.setNickName(custAccount.getNickName());
				respon.setUserToken(custAccount.getUserToken());
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}else{
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	
	@RequestMapping("/bindAddress")
	public void bindAddress(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WxCustAccountEvt evt=new WxCustAccountEvt();
		CustAccountDetailResp respon=new CustAccountDetailResp();
		SmsBean sms = null;
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WxCustAccountEvt) JSONObject.toBean(json,WxCustAccountEvt.class);
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
			//fastLogin  是否快捷登陆（无验证码）
			
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(7);
			byte[] smsBytes = RedisUtil.get(redisConnection, evt.getPhone());
			if(smsBytes.length > 0){
				sms = (SmsBean) SerializeUtil.unSerialize(smsBytes);
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
			if(evt.getSmsPwd().equalsIgnoreCase(sms.getCode())&&evt.getSmsType().equalsIgnoreCase(sms.getType())){
				CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),null);
				List<Map<String,Object>> codeList=custAccountService.getCustCodeListByPhoneFromCustomer(evt.getPortalAccount(),evt.getPhone());
				if(codeList.size()>0){
					//如果已经绑定过的不再绑定  
					for(int i =0 ;i<codeList.size();i++){
						CustMapped custMapped = new CustMapped();
							custMapped.setCustCode(codeList.get(i).get("code")+"");
							custMapped.setCustAccountPK(custAccount.getPk());
							custAccountService.saveCustMapped(custMapped);
					}	
				}
				List<Map<String,Object>> custCodeList=custAccountService.getCustCodeListByPhone(evt.getPortalAccount());
				redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				RedisUtil.del(redisConnection, evt.getPhone());
				if(custAccount.getUserToken()==null){
					custAccount.setUserToken(MD5EncryptionMgr.md5Encryption(custAccount.getMobile1()));
				}
				bind(custAccount,request);
				respon.setMobile1(custAccount.getMobile1());
				respon.setCustCodeList(custCodeList);
				respon.setPicAddr(custAccount.getPicAddr());
				respon.setNickName(custAccount.getNickName());
//				respon.setToken(this.formTokenManager.newFormToken(request).getToken());
//				respon.setUserToken(custAccount.getUserToken());
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}else{
				respon.setReturnCode("300102");
				respon.setReturnInfo("验证码错误");
				returnJson(respon, response, msg, respon, request, interfaceLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
}





