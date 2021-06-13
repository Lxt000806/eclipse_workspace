package com.house.home.client.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.ChangeSiteReqBody;
import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.bean.basic.RongCloudBean;
import com.house.home.bean.basic.SmsBean;
import com.house.home.client.im.RongCloudHttp;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.EmployeeQueryEvt;
import com.house.home.client.service.evt.GoJqGridForOAEvt;
import com.house.home.client.service.evt.JqGridEmployeeEvt;
import com.house.home.client.service.evt.SmsAccountEvt;
import com.house.home.client.service.evt.UpdatePwdEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.EmployeeDetailResp;
import com.house.home.client.service.resp.EmployeePermissionResp;
import com.house.home.client.service.resp.GojqGridForOAResp;
import com.house.home.client.service.resp.JqGridEmployeeResp;
import com.house.home.client.util.MD5EncryptionMgr;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;

/**
 * 员工相关的接口
 * @author 
 *
 */
@RequestMapping("/client/employee")
@Controller
public class ClientEmployeeController extends ClientBaseController{
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	/**
	 * 查看员工详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getEmployeeDetail")
	public void getEmployeeDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt=new BaseEvt();
		EmployeeDetailResp respon=new EmployeeDetailResp();
		
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
			Employee employee = employeeService.getByPhoneAndMm(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
			if (employee==null){
				respon.setReturnCode("300005");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if("SUS".equals(employee.getStatus().trim())){
				respon.setReturnCode("300005");
				respon.setReturnInfo("账号已过期");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Czybm czybm = czybmService.getByEmnum(employee.getNumber());
			czybm.setPhone(employee.getPhone());
			czybm.setToken(MD5EncryptionMgr.md5Encryption(czybm.getCzybh()+new Date()));

			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);
			if("3".equals(request.getHeader("AppType")) || "2".equals(request.getHeader("AppType"))){
				respon.setToken(czybm.getToken());
			}
			bind(czybm,request);
			//respon.setRongCloudMessage(RongCloudHttp.GetRongCloudToken(RongCloudBean.RongCloud_API_URL, RongCloudBean.RongCloud_EMPLOYEE_APP_KEY, RongCloudBean.RongCloud_EMPLOYEE_APP_SECRET,czybm.getCzybh()));
			respon.setNameChi(employee.getNameChi());
			respon.setPhone(employee.getPhone());
			respon.setNumber(employee.getNumber());
			respon.setCzybh(czybm.getCzybh());
			respon.setPrjRole(czybm.getPrjRole());
			//respon.setSessionId(session.getId());
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 员工列表接口
	 */
	@RequestMapping("/getEmployeeList")
	public void getEmployeeList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		EmployeeQueryEvt evt=new EmployeeQueryEvt();
		BasePageQueryResp<EmployeeDetailResp> respon=new BasePageQueryResp<EmployeeDetailResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (EmployeeQueryEvt)JSONObject.toBean(json,EmployeeQueryEvt.class);
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
			Page page=new Page();
			page.setPageSize(evt.getPageSize());
			page.setPageNo(evt.getPageNo());
			employeeService.findPageByName(page, evt.getName());
			List<EmployeeDetailResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), EmployeeDetailResp.class);
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
	/**
	 * 用户注销接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/loginOutEmployee")
	public void loginOutEmployee(HttpServletRequest request, HttpServletResponse response){
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
			String appType = request.getHeader("AppType");
			if("3".equals(appType)){
				String userToken = request.getHeader("UserToken");
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(6);
				RedisUtil.del(redisConnection, userToken);
			}else{
				HttpSession session = request.getSession(false);
				if (session!=null) {
				    session.invalidate();
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 用户修改密码接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateEmployeePwd")
	public void updateEmployeePwd(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UpdatePwdEvt evt=new UpdatePwdEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (UpdatePwdEvt)JSONObject.toBean(json,UpdatePwdEvt.class);
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
			Employee employee = employeeService.getByPhoneAndMm(evt.getPhone(),DesUtils.encode(evt.getOldPwd()));
			if (employee==null){
				respon.setReturnCode("300005");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if (!evt.getConfirmPwd().equals(evt.getNewPwd())){
				respon.setReturnCode("300009");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Czybm czybm = czybmService.getByEmnum(employee.getNumber());
			czybm.setMm(DesUtils.encode(evt.getNewPwd()));
			czybmService.update(czybm);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 重置密码
	 */
	@RequestMapping("/resetEmployeePwd")
	public void resetEmployeePwd(HttpServletRequest request,HttpServletResponse response){	
		StringBuilder msg=new StringBuilder();
		BaseResponse respon=new BaseResponse();
		SmsBean sms = null;
		JSONObject json = new JSONObject();
		SmsAccountEvt evt=new SmsAccountEvt();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try{
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SmsAccountEvt) JSONObject.toBean(json,SmsAccountEvt.class);
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
		if("3".equals(request.getHeader("AppType")) || "2".equals(request.getHeader("AppType"))){
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
			List<Employee> list=employeeService.getByphone(evt.getPortalAccount());
			if(list==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("员工信息中无此手机号");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
				}
			Czybm czybm=null;
			for(Employee employee:list){
				czybm=czybmService.getByEmnum(employee.getNumber());
				if(czybm!=null){
					break;
				}
			}
			if(czybm==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("请先激活账号");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			czybm.setMm(DesUtils.encode(evt.getPortalPwd()));
			czybmService.update(czybm);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		}else{
			respon.setReturnCode("300102");
			respon.setReturnInfo("验证码错误");
			returnJson(respon, response, msg, respon, request, interfaceLog);
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	/**
	 * 用户注册接口
	 */
	@RequestMapping("/registerEmployee")
	public void registerEmployee(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		BaseResponse respon=new BaseResponse();
		SmsBean sms=(SmsBean) request.getSession().getAttribute(CommonConstant.SMS_CODE_KEY);
		JSONObject json = new JSONObject();
		SmsAccountEvt evt=new SmsAccountEvt();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try{
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SmsAccountEvt) JSONObject.toBean(json,SmsAccountEvt.class);
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
			List<Employee> list=employeeService.getByphone(evt.getPortalAccount());
			if(list==null){
			respon.setReturnCode("300005");
			respon.setReturnInfo("员工信息中无此手机号");
			returnJson(respon, response, msg, respon, request, interfaceLog);
			return;
			}
			if(list.size()>1){
				respon.setReturnCode("100005");
				respon.setReturnInfo("员工信息存在重复的手机号码");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			Employee employee=list.get(0);
			Czybm czybm=czybmService.getByEmnum(employee.getNumber());
			if(czybm!=null){
				respon.setReturnCode("300010");
				respon.setReturnInfo("账号已存在,无需注册");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			czybm=new Czybm();
			czybm.setCzybh(employee.getNumber());
			czybm.setBmbh(employee.getDepartment1());
			czybm.setYwxm(employee.getNameEng());
			czybm.setZwxm(employee.getNameChi());
			czybm.setJslx("OPERATOR");
			czybm.setEmnum(employee.getNumber());
			czybm.setMm(DesUtils.encode(evt.getPortalPwd()));
			czybm.setKhrq(DateUtil.getNowDateString().substring(0, 8));
			czybm.setKhsj("000000");
			czybm.setLastUpdate(new Date());
			czybm.setLastUpdatedBy("1");
			czybm.setExpired("F");
			czybm.setActionLog("SYSADD");
			czybm.setZfbz(false);
			czybm.setCustRight("1");
			czybm.setCostRight("0");
			czybm.setItemRight("JZ,ZC,RZ,JC,CG");
			czybm.setCustType("0");
			czybm.setCzylb("1");
			czybmService.save(czybm);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		}else{
			respon.setReturnCode("300102");
			respon.setReturnInfo("验证码错误");
			returnJson(respon, response, msg, respon, request, interfaceLog);
			return;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
	
	
	}
	/**
	 * 获取用户权限
	 */
	@RequestMapping("/getEmployeePermission")
	public void getEmployeePermission(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		EmployeePermissionResp respon=new EmployeePermissionResp();
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
			List<Map<String, Object>> list=czybmService.getPermission(evt.getId());
			respon.setPermissions(list);
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	/**
	 * 绑定用户上下文数据到session中
	 * 
	 * @param user
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	private void bind(Czybm user, HttpServletRequest request) throws UnsupportedEncodingException {
		if (null == user)
			throw new RuntimeException("待载入缓存的用户对象为空错误！");
		logger.debug("用户登入验证通过，开始绑定用户到Session中");

		UserContext uc = new UserContext();
		try {
			uc.setCzybh(user.getCzybh());
			uc.setZwxm(user.getZwxm());
			uc.setEmnum(user.getEmnum());
			uc.setPhone(user.getPhone());
			uc.setItemRight(user.getItemRight());
			uc.setCustRight(user.getCustRight());//补充APP用户登入客户权限 add by zzr 2018/05/11
			UserContextHolder.set(uc);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("绑定用户上下文数据出现异常：", e);
			throw new RuntimeException(e);
		}

		logger.debug("绑定用户到Session中结束");
		String appType = request.getHeader("AppType");
		if("3".equals(appType) || "2".equals(appType)){
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(6);
			System.out.println(user.getToken());
			RedisUtil.set(redisConnection, user.getToken(), SerializeUtil.serialize(uc), 604800L);
		}else{
			request.getSession().setAttribute(CommonConstant.USER_APP_KEY, uc);
			//Tomcat日志无法通过USER_CONTEXT_KEY获取操作员编号，所以需要在session中直接记录操作员编号
			request.getSession().setAttribute(CommonConstant.CZYBH, uc.getCzybh()); 
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/goJqGridEmployee")
	public void goJqGridEmployee(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		JqGridEmployeeEvt evt=new JqGridEmployeeEvt();
		BasePageQueryResp<JqGridEmployeeResp> respon=new BasePageQueryResp<JqGridEmployeeResp>();

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			UserContext uc = this.getUserContext(request);
			json = StringUtil.queryStringToJSONObject(request);
			evt = (JqGridEmployeeEvt)JSONObject.toBean(json, JqGridEmployeeEvt.class);
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
			Employee employee = new Employee();
			employee.setStartMan(evt.getStartMan());
			employee.setNameChi(evt.getNameChi());
			employee.setWfProcNo(evt.getWfProcNo());
			employee.setTaskKey(evt.getTaskKey());
			employee.setRole(evt.getRole());
			employee.setIsManager(evt.getIsManager());
			employee.setCustCode(evt.getCustCode());
			employee.setExpired(evt.getExpired());
			employeeService.findPageBySql(page, employee,uc);
			List<JqGridEmployeeResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), JqGridEmployeeResp.class);

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
	 * 易居通切换站点功能
	 * @param request
	 * @param response
	 */
	@RequestMapping("/chgCity")
	public void chgCity(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt=new BaseEvt();
		BaseResponse respon=new BaseResponse();
		String url = "";
        EmployeeDetailResp employeeDetailResp = new EmployeeDetailResp();
		long timestamp = (new Date()).getTime();
		String appType = request.getHeader("AppType");
		ChangeSiteReqBody changeSiteReqBody = new ChangeSiteReqBody();
		String chgSiteKey = SystemConfig.getProperty("key", "", "chgSiteKey");
		
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
			
			if(StringUtils.isBlank(evt.getBasePath())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("获取不到所选站点地址");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			} else {
				url = evt.getBasePath()+"client/employee/getEmployeeDetailByPhone";
			}
			
			// 创建HttpClient
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(url);
	        
	        // 超时设置，单位：毫秒
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(60000)
					.setConnectionRequestTimeout(10000)  
			        .setSocketTimeout(60000).build();  
			httpPost.setConfig(requestConfig); 
			httpPost.setHeader("AppType", "3");
			
			changeSiteReqBody.setPhone(evt.getPortalAccount());
			changeSiteReqBody.setTimestamp(timestamp);
			if(StringUtils.isNotBlank(chgSiteKey)){
				changeSiteReqBody.setSign(DesUtils.encode(evt.getPortalAccount()+String.valueOf(timestamp), chgSiteKey));
			} else {
				changeSiteReqBody.setSign(DesUtils.encode(evt.getPortalAccount()+String.valueOf(timestamp)));
			}
			String reqBody = JSON.toJSONString(changeSiteReqBody);
	        httpPost.setEntity(new StringEntity(reqBody, "UTF-8"));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());
			if(404 == httpResponse.getStatusLine().getStatusCode()){
				respon.setReturnCode("400001");
				respon.setReturnInfo("访问站点失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			};
			
            HttpEntity responseEntity = httpResponse.getEntity();
            if(responseEntity != null){
                // 将响应的内容转换成字符串
                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                employeeDetailResp = com.alibaba.fastjson.JSONObject.parseObject(result, EmployeeDetailResp.class);
            }
			
			if(employeeDetailResp == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("切换城市失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(employeeDetailResp.getToken())){
				respon.setReturnCode("400001");
				respon.setReturnInfo(employeeDetailResp.getMsg());
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if("3".equals(appType)){
				String userToken = request.getHeader("UserToken");
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(6);
				RedisUtil.del(redisConnection, userToken);
			}else{
				HttpSession session = request.getSession(false);
				if (session!=null) {
				    session.invalidate();
				}
			}
			returnJson(employeeDetailResp, response, msg, employeeDetailResp, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	/**
	 * app切换城市,通过手机号获取员工信息及token
	 * @param request
	 * @param response
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getEmployeeDetailByPhone")
	@ResponseBody
	public EmployeeDetailResp getEmployeeDetailByPhone(HttpServletRequest request, HttpServletResponse response){
		EmployeeDetailResp respon=new EmployeeDetailResp();
		try {
			String chgSiteKey = SystemConfig.getProperty("key", "", "chgSiteKey");
			// 通过流获取请求参数 
			InputStream in = request.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Streams.copy(in, out, true);
	        
	        String data = new String(out.toByteArray());
	        JSONObject jsonObj = JSONObject.fromObject(data);  
	        ChangeSiteReqBody changeSiteReqBody = (ChangeSiteReqBody) JSONObject.toBean(jsonObj, ChangeSiteReqBody.class);
			
			if(changeSiteReqBody == null){
				respon.setMsg("切换站点失败,无法获取请求参数");
				return respon;
			}
			// 超过15分钟失效
			if(((new Date()).getTime()-(changeSiteReqBody.getTimestamp()))/ (1000 * 60)>15){
				respon.setMsg("验证超时,无法切换");
				return respon;
			}
			
			String newSign = "";
			if(StringUtils.isNotBlank(chgSiteKey)){
				newSign = DesUtils.encode(changeSiteReqBody.getPhone()+String.valueOf(changeSiteReqBody.getTimestamp()), chgSiteKey);
			} else {
				newSign = DesUtils.encode(changeSiteReqBody.getPhone()+String.valueOf(changeSiteReqBody.getTimestamp()));
			}			
			// 签名匹配
			if(StringUtils.isBlank(changeSiteReqBody.getSign()) || !changeSiteReqBody.getSign().equals(newSign)){
				respon.setMsg("验证已失效,请重新切换");
				return respon;
			}
			
			Employee employee = employeeService.getByPhoneWithoutMM(changeSiteReqBody.getPhone());
			if (employee==null){
				respon.setMsg("没有此站点的权限，无法切换");
				return respon;
			}
			if("SUS".equals(employee.getStatus().trim())){
				respon.setMsg("您已从此分公司离职，无法切换");
				return respon;
			}
			
			Czybm czybm = czybmService.getByEmnum(employee.getNumber());
			czybm.setPhone(employee.getPhone());
			czybm.setToken(MD5EncryptionMgr.md5Encryption(czybm.getCzybh()+new Date()));

			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);
			
			respon.setToken(czybm.getToken());
			bind(czybm,request);
			respon.setNameChi(employee.getNameChi());
			respon.setPhone(employee.getPhone());
			respon.setNumber(employee.getNumber());
			respon.setCzybh(czybm.getCzybh());
			respon.setPrjRole(czybm.getPrjRole());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return respon;
		}
	}
}
