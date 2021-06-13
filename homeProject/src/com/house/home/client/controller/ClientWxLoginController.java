package com.house.home.client.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.WxAesUtils;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.WxAppletUtils;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.client.service.evt.WxCustomerEvt;
import com.house.home.client.service.resp.WxCustomerResp;
import com.house.home.client.util.MD5EncryptionMgr;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.CustRecommend;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.CustRecommendService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.basic.WxCustomerService;
import com.house.home.service.design.ResrCustService;


@RequestMapping("/client/weChat")
@Controller
public class ClientWxLoginController extends ClientBaseController {
	@Autowired
	private WxCustomerService wxCustomerService;
	@Autowired
	private CustRecommendService custRecommendService;
	@Autowired
	private ResrCustService resrCustService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PersonMessageService personMessageService;
	
	
	private static final String APPID="wx101545fa33d2a1e5";
	
	private static final String SECRET="118127f33fed3b6f4246d9b972763faa";
	
	@RequestMapping("/weChatCustLogin")
	public void weChatCustLogin(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WxCustomerEvt evt=new WxCustomerEvt();
		WxCustomerResp respon=new WxCustomerResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(WxCustomerEvt) JSONObject.toBean(json, WxCustomerEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
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
	
	@RequestMapping("/wxCustFastAccount")
	public void wxCustFastAccount(HttpServletRequest request,HttpServletResponse response) throws Exception{
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WxCustomerEvt evt=new WxCustomerEvt();
		WxCustomerResp respon=new WxCustomerResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(WxCustomerEvt) JSONObject.toBean(json, WxCustomerEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String sessionKey=WxAppletUtils.wx_login(evt.getCode()).getString("session_key");
			String encryptedData = evt.getEncryptedData();
			String iv =evt.getIv();
			JSONObject decrypt = JSONObject.fromObject(WxAesUtils.decrypt(encryptedData, sessionKey, iv, "UTF-8"));
			CustAccount custAccount=wxCustomerService.getCustAccountByPhone(decrypt.getString("phoneNumber"),null);
			if(custAccount==null){
				custAccount=new CustAccount();
				custAccount.setLastUpdate(new Date());
				custAccount.setMm(DesUtils.encode("123456"));
				custAccount.setMobile1(decrypt.getString("phoneNumber"));
				custAccount.setRegisterDate(new Date());
				custAccount.setExpired("F");
				custAccount.setRecommender(evt.getRecommender());
				if("4".equals(evt.getRecommendSource())){
					custAccount.setRecommenderType("2");
				}else if("5".equals(evt.getRecommendSource())){
					custAccount.setRecommenderType("3");
				}else{
					custAccount.setRecommenderType("1");
				}
				wxCustomerService.save(custAccount);
				List<Map<String,Object>> codeList=wxCustomerService.getCustCodeListByPhoneFromCustomer("",decrypt.getString("phoneNumber"));
				if(codeList.size()>0){
					for(int i =0 ;i<codeList.size();i++){
						CustMapped custMapped = new CustMapped();
						custMapped.setCustCode(codeList.get(i).get("code")+"");
						custMapped.setCustAccountPK(wxCustomerService.getCustAccountByPhone(decrypt.getString("phoneNumber"),null).getPk());
						wxCustomerService.saveCustMapped(custMapped);
					}
				}
			}
			List<Map<String,Object>> custCodeList=wxCustomerService.getCustCodeListByPhone(decrypt.getString("phoneNumber"));
			if(custAccount.getUserToken()==null){
				custAccount.setUserToken(MD5EncryptionMgr.md5Encryption(custAccount.getMobile1()));
			}
			// 小程序登陆时如果有推荐相关参数 则  保存信息到tCustRecommend
			if(StringUtils.isNotBlank(evt.getRecommender())&&StringUtils.isNotBlank(evt.getRecommendSource())){
				CustRecommend custRecommend = new CustRecommend();
				CustRecommend oldCustRecommend = new CustRecommend();
				if("5".equals(evt.getRecommendSource())){
					CustAccount ca = wxCustomerService.get(CustAccount.class,Integer.parseInt(evt.getRecommender()));
					oldCustRecommend = custRecommendService.getCustRecommendByCustPhone(ca.getMobile1());
				}
				CustRecommend newCustRecommend = custRecommendService.getCustRecommendByCustPhone(decrypt.getString("phoneNumber"));
				if(null==newCustRecommend||DateUtil.dateDiff(newCustRecommend.getRecommendDate(), new Date())>=90){
					String resrCustCode = resrCustService.getSeqNo("tResrCust");
					custRecommend.setResrCustCode(resrCustCode);
					custRecommend.setCustPhone(decrypt.getString("phoneNumber"));
					custRecommend.setStatus("0");
					custRecommend.setRecommendDate(new Date());
					custRecommend.setRecommendSource(evt.getRecommendSource());
					custRecommend.setRecommender(evt.getRecommender());
					if("4".equals(evt.getRecommendSource())){
						custRecommend.setManager(evt.getRecommender());
						custRecommend.setRecommenderType("2");
					}else if("5".equals(evt.getRecommendSource())){
						if(null!=oldCustRecommend){
							//如果推荐人为客户 则 获取推荐人的管理人做为新推荐客户的管理人
							custRecommend.setManager(oldCustRecommend.getManager());
						}
						custRecommend.setRecommenderType("3");
					}else{
						custRecommend.setRecommenderType("1");
					}
					custRecommend.setLastUpdatedBy(evt.getRecommender());
					custRecommend.setLastUpdate(new Date());
					custRecommend.setActionLog("ADD");
					custRecommend.setExpired("F");
					custRecommendService.save(custRecommend);
					
					ResrCust resrCust = new ResrCust();
					resrCust.setCode(resrCustCode);
					resrCust.setBuilderCode("");
					resrCust.setStatus("1");
					resrCust.setCustResStat("0");
					resrCust.setCustKind("0");
					resrCust.setCrtDate(new Date());
					resrCust.setAddress("");
					resrCust.setSource("10");
					resrCust.setDescr(custRecommend.getCustName());
					resrCust.setMobile1(custRecommend.getCustPhone());
					resrCust.setRemark(custRecommend.getRemarks());
					if(StringUtils.isNotEmpty(custRecommend.getManager())){
						resrCust.setBusinessMan(custRecommend.getManager());
						resrCust.setDispatchDate(new Date());
						resrCust.setLastUpdatedBy(custRecommend.getManager());
					}else{
						resrCust.setBusinessMan("");
						resrCust.setLastUpdatedBy("1");
					}
					if("2".equals(custRecommend.getRecommenderType())){
						Employee employee = employeeService.get(Employee.class,custRecommend.getManager());
						resrCust.setCrtCzy(employee.getNumber());
						resrCust.setCrtCZYDept(employee.getDepartment());
					}else{
						resrCust.setCrtCzy("1");
					}
					resrCust.setLastUpdate(new Date());
					resrCust.setExpired("F");
					resrCust.setActionLog("ADD");
					this.resrCustService.save(resrCust);
					if("2".equals(custRecommend.getRecommenderType())){
						PersonMessage personMessage = new PersonMessage();
						personMessage.setCrtDate(new Date());
						personMessage.setSendDate(new Date());
						personMessage.setIsPush("1");
						personMessage.setMsgText("客户邀请成功");
						personMessage.setMsgType("20");
						personMessage.setRcvCzy(custRecommend.getManager());
						personMessage.setRcvType("3");
						personMessage.setPushStatus("0");
						personMessage.setRcvStatus("0");
						this.personMessageService.save(personMessage);
					}
				}
			}
			bind(custAccount,request);
			respon.setPhone(decrypt.getString("phoneNumber"));
			respon.setCustCodeList(custCodeList);
			respon.setPicAddr(custAccount.getPicAddr());
			respon.setNickName(custAccount.getNickName());
			respon.setUserToken(custAccount.getUserToken());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/wxLogin")
	public void wxLogin(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WxCustomerEvt evt=new WxCustomerEvt();
		WxCustomerResp respon=new WxCustomerResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(WxCustomerEvt) JSONObject.toBean(json, WxCustomerEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String strURL="https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+SECRET+"&js_code="+evt.getCode()+"&grant_type=authorization_code";
			StringBuffer buffer=null;
	        HttpURLConnection httpConn;
		    URL url=new URL(strURL);
		    httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.connect();  
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
			String line;
			buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
			        buffer.append(line);
			    }
			reader.close();
			httpConn.disconnect();
			JSONObject jsonobject = JSONObject.fromObject(buffer.toString());
			if(jsonobject.isNullObject()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			respon.setOpenId(jsonobject.getString("openid"));
			respon.setSession_key(jsonobject.getString("session_key"));
			CustAccount custAccount = wxCustomerService.getCustAccountByOpenId(jsonobject.getString("openid"));
			if(custAccount!=null){
				respon.setIsLogined(true);
			}else{
				respon.setIsLogined(false);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/wxCustFastAccountCs")
	public void wxCustFastAccountCs(HttpServletRequest request,HttpServletResponse response) throws Exception{
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WxCustomerEvt evt=new WxCustomerEvt();
		WxCustomerResp respon=new WxCustomerResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(WxCustomerEvt) JSONObject.toBean(json, WxCustomerEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			CustAccount custAccount=wxCustomerService.getCustAccountByPhone(evt.getPhone(),null);
			if(custAccount==null){
				custAccount=new CustAccount();
				custAccount.setLastUpdate(new Date());
				custAccount.setMm(DesUtils.encode("123456"));
				custAccount.setMobile1(evt.getPhone());
				custAccount.setRegisterDate(new Date());
				custAccount.setExpired("F");
				custAccount.setRecommender(evt.getRecommender());
				if("4".equals(evt.getRecommendSource())){
					custAccount.setRecommenderType("2");
				}else if("5".equals(evt.getRecommendSource())){
					custAccount.setRecommenderType("3");
				}else{
					custAccount.setRecommenderType("1");
				}
				wxCustomerService.save(custAccount);
				List<Map<String,Object>> codeList=wxCustomerService.getCustCodeListByPhoneFromCustomer("",evt.getPhone());
				if(codeList.size()>0){
					for(int i =0 ;i<codeList.size();i++){
						CustMapped custMapped = new CustMapped();
						custMapped.setCustCode(codeList.get(i).get("code")+"");
						custMapped.setCustAccountPK(wxCustomerService.getCustAccountByPhone(evt.getPhone(),null).getPk());
						wxCustomerService.saveCustMapped(custMapped);
					}
				}
			}
			List<Map<String,Object>> custCodeList=wxCustomerService.getCustCodeListByPhone(evt.getPhone());
			if(custAccount.getUserToken()==null){
				custAccount.setUserToken(MD5EncryptionMgr.md5Encryption(custAccount.getMobile1()));
			}
			//小程序登陆时如果有推荐相关参数 则  保存信息到tCustRecommend
			if(StringUtils.isNotBlank(evt.getRecommender())&&StringUtils.isNotBlank(evt.getRecommendSource())){
				CustRecommend custRecommend = new CustRecommend();
				CustRecommend oldCustRecommend = new CustRecommend();
				if("5".equals(evt.getRecommendSource())){
					CustAccount ca = wxCustomerService.get(CustAccount.class,Integer.parseInt(evt.getRecommender()));
					oldCustRecommend = custRecommendService.getCustRecommendByCustPhone(ca.getMobile1());
				}
				CustRecommend newCustRecommend = custRecommendService.getCustRecommendByCustPhone(evt.getPhone());
				if(null==newCustRecommend||DateUtil.dateDiff(newCustRecommend.getRecommendDate(), new Date())>=90){
					String resrCustCode = resrCustService.getSeqNo("tResrCust");
					custRecommend.setResrCustCode(resrCustCode);
					custRecommend.setCustPhone(evt.getPhone());
					custRecommend.setStatus("0");
					custRecommend.setRecommendDate(new Date());
					custRecommend.setRecommendSource(evt.getRecommendSource());
					custRecommend.setRecommender(evt.getRecommender());
					if("4".equals(evt.getRecommendSource())){
						custRecommend.setManager(evt.getRecommender());
						custRecommend.setRecommenderType("2");
					}else if("5".equals(evt.getRecommendSource())){
						if(null!=oldCustRecommend){
							//如果推荐人为客户 则 获取推荐人的管理人做为新推荐客户的管理人
							custRecommend.setManager(oldCustRecommend.getManager());
						}
						custRecommend.setRecommenderType("3");
					}else{
						custRecommend.setRecommenderType("1");
					}
					custRecommend.setLastUpdatedBy(evt.getRecommender());
					custRecommend.setLastUpdate(new Date());
					custRecommend.setActionLog("ADD");
					custRecommend.setExpired("F");
					custRecommendService.save(custRecommend);
					ResrCust resrCust = new ResrCust();
					resrCust.setCode(resrCustCode);
					resrCust.setBuilderCode("");
					resrCust.setStatus("0");
					resrCust.setCustResStat("0");
					resrCust.setCrtDate(new Date());
					resrCust.setAddress("");
					resrCust.setSource("10");
					resrCust.setDescr(custRecommend.getCustName());
					resrCust.setMobile1(custRecommend.getCustPhone());
					resrCust.setRemark(custRecommend.getRemarks());
					if(StringUtils.isNotEmpty(custRecommend.getManager())){
						resrCust.setBusinessMan(custRecommend.getManager());
						resrCust.setDispatchDate(new Date());
						resrCust.setLastUpdatedBy(custRecommend.getManager());
					}else{
						resrCust.setBusinessMan("");
						resrCust.setLastUpdatedBy("1");
					}
					if("2".equals(custRecommend.getRecommenderType())){
						Employee employee = employeeService.get(Employee.class,custRecommend.getManager());
						resrCust.setCrtCzy(employee.getNumber());
						resrCust.setCrtCZYDept(employee.getDepartment());
					}else{
						resrCust.setCrtCzy("1");
					}
					resrCust.setLastUpdate(new Date());
					resrCust.setExpired("F");
					resrCust.setActionLog("ADD");
					this.resrCustService.save(resrCust);
					if("2".equals(custRecommend.getRecommenderType())){
						PersonMessage personMessage = new PersonMessage();
						personMessage.setCrtDate(new Date());
						personMessage.setSendDate(new Date());
						personMessage.setIsPush("1");
						personMessage.setMsgText("客户邀请成功");
						personMessage.setMsgType("20");
						personMessage.setRcvCzy(custRecommend.getManager());
						personMessage.setRcvType("3");
						personMessage.setPushStatus("0");
						personMessage.setRcvStatus("0");
						this.personMessageService.save(personMessage);
					}
				}
			}
			bind(custAccount,request);
			respon.setPhone(evt.getPhone());
			respon.setCustCodeList(custCodeList);
			respon.setPicAddr(custAccount.getPicAddr());
			respon.setNickName(custAccount.getNickName());
			respon.setUserToken(custAccount.getUserToken());
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
