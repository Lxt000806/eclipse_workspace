package com.house.home.client.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.bean.basic.SmsBean;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.resp.CustAccountDetailResp;
import com.house.home.client.service.resp.DriverDetailResp;
import com.house.home.client.service.resp.EmployeeDetailResp;
import com.house.home.client.service.resp.WorkerDetailResp;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Driver;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SendMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.CustAccountService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.DriverService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.SendMessageService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.WorkerService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;


/**
 * 短信相关接口
 * @author lcj
 *
 */
@RequestMapping("/client/sms")
@Controller
public class ClientSmsController  extends ClientBaseController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private  CustAccountService custAccountService;
	/**
	 * 项目经理App获取验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSmsPassword")
	public void getSmsPassword(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("portalAccount");
		String type= request.getParameter("smsType");
		StringBuilder msg = new StringBuilder();
		EmployeeDetailResp respon=new EmployeeDetailResp();
		//查询是否公司员工并是否激活
		List<Employee> list= employeeService.getByphone(phone);	
		try {
			if(list==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("员工信息中无此手机号");
				returnJson(respon,response,msg,respon,request,null);
				return;
			}
			if(list.size()>1){
				respon.setReturnCode("100005");
				respon.setReturnInfo("员工信息存在重复的手机号码");
				returnJson(respon,response,msg,respon,request,null);
				return;
			}
			Czybm czybm=null;
			for(Employee employee:list){
				czybm=czybmService.getByEmnum(employee.getNumber());
				if(czybm!=null){
					break;
				}
			}

			if(czybm!=null){
				//账号已激活
				if("1".equals(type)){
					//激活操作
					respon.setReturnCode("300005");
					respon.setReturnInfo("账号已存在,无需注册");
					returnJson(respon,response,msg,respon,request,null);
				}else if("2".equals(type)){
					//修改密码
					SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_RETPWD);
					this.SendMsg(sms,type,"项目经理App");
					sms.setDate(DateUtil.DateToString(new Date()));
					if("3".equals(request.getHeader("AppType")) || "2".equals(request.getHeader("AppType"))){
						RedisConnection redisConnection = RedisUtil.getRedisConnection();
						redisConnection.select(7);
						RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
					}else{
						request.getSession().setAttribute(CommonConstant.SMS_CODE_KEY, sms);
					}
					returnJson(respon,response,msg,respon,request,null);
				}
			}else{
				//账号未激活
				if("1".equals(type)){
					//激活操作
					SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_REGISTER);
					this.SendMsg(sms,type,"项目经理App");
					sms.setDate(DateUtil.DateToString(new Date()));
					if("3".equals(request.getHeader("AppType")) || "2".equals(request.getHeader("AppType"))){
						RedisConnection redisConnection = RedisUtil.getRedisConnection();
						redisConnection.select(7);
						RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
					}else{
						request.getSession().setAttribute(CommonConstant.SMS_CODE_KEY, sms);
					}
					returnJson(respon,response,msg,respon,request,null);
				}else if("2".equals(type)){
					//修改密码
					respon.setReturnCode("300005");
					respon.setReturnInfo("请先激活账号");
					returnJson(respon,response,msg,respon,request,null);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 司机app获取验证码
	 */
	@RequestMapping("/getDriverSmsPassword")
	public void getDriverSmsPassword(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("portalAccount");
		String type= request.getParameter("smsType");
		StringBuilder msg = new StringBuilder();
		DriverDetailResp respon=new DriverDetailResp();
		Driver driver=driverService.getByPhone(phone);	
		try {
			if(driver==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("员工信息中无此手机号");
				returnJson(respon,response,msg,respon,request,null);
				return;
			}
			//修改密码
			SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_RETPWD);
			this.SendMsg(sms,type,"司机App");
			sms.setDate(DateUtil.DateToString(new Date()));
			if("6".equals(request.getHeader("AppType"))){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
			}else{
				request.getSession().setAttribute(CommonConstant.SMS_CODE_KEY, sms);
			}
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 工人app获取验证码
	 */
	@RequestMapping("/getWorkerSmsPassword")
	public void getWorkerSmsPassword(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("portalAccount");
		String type= request.getParameter("smsType");
		StringBuilder msg = new StringBuilder();
		WorkerDetailResp respon=new WorkerDetailResp();
		Worker worker=workerService.getByPhone(phone);	
		try {
			if(worker==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("工人信息中无此手机号");
				returnJson(respon,response,msg,respon,request,null);
				return;
			}
			//修改密码
			SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_RETPWD);
			this.SendMsg(sms,type,"工人App");
			sms.setDate(DateUtil.DateToString(new Date()));
			if("5".equals(request.getHeader("AppType"))){
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(7);
				RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
			}else{
				request.getSession().setAttribute(CommonConstant.SMS_CODE_KEY, sms);
			}
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户App获取验证码接口
	 */
	@RequestMapping("/getCustAccountSmsPassword")
	public void getCustAccountSmsPassword(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("portalAccount");
		String type= request.getParameter("smsType");
		StringBuilder msg = new StringBuilder();
		CustAccountDetailResp respon=new CustAccountDetailResp();
		//查询是否公司客户并是否激活
		//Customer customer=customerService.getCustomerByMobile1(phone);
		try {
/*			if(customer==null){
				respon.setReturnCode("300005");
				respon.setReturnInfo("客户信息中无此手机号");
				returnJson(respon,response,msg,respon,request,null);
				return;
			}*/
			CustAccount custAccount=custAccountService.getCustAccountByPhone(phone,null);

			if(custAccount!=null){
				//账号已激活
				if("1".equals(type)){
					//激活操作
					respon.setReturnCode("300005");
					respon.setReturnInfo("账号已存在,无需注册");
					returnJson(respon,response,msg,respon,request,null);
				}else if("2".equals(type)){
					//修改密码
					SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_RETPWD);
					this.SendMsg(sms,type,"有家客户端");
					sms.setDate(DateUtil.DateToString(new Date()));
					// 新版本用户信息保存redis
					String appType = request.getHeader("AppType");
					if("1".equals(appType)){
						RedisConnection redisConnection = RedisUtil.getRedisConnection();
						redisConnection.select(7);
						RedisUtil.set(redisConnection, custAccount.getMobile1(), new String(SerializeUtil.serialize(sms)),1800L);
//						redisConnection.expire(custAccount.getMobile1().getBytes(), 1800L);
						System.out.println(sms.getCode());
					}else{
						request.getSession().setAttribute(CommonConstant.SMS_CODE_KEY, sms);
					}
					returnJson(respon,response,msg,respon,request,null);
				}else if("3".equals(type)){
					SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_REGISTER);
					sms.setDate(DateUtil.DateToString(new Date()));
					// 新版本用户信息保存redis
					RedisConnection redisConnection = RedisUtil.getRedisConnection();
					redisConnection.select(7);
					RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
//					redisConnection.expire(phone.getBytes(), 1800L);
					this.SendMsg(sms, "1", "有家客户端");
//					System.out.println(sms.getCode());
					respon.setSmsPassWord(sms.getCode());
				}
			}else{
				//账号未激活
				if("1".equals(type)){
					//激活操作
					SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_REGISTER);
					this.SendMsg(sms,type,"有家客户端");
					sms.setDate(DateUtil.DateToString(new Date()));
					// 新版本用户信息保存redis
					String appType = request.getHeader("AppType");
					if("1".equals(appType)){
						RedisConnection redisConnection = RedisUtil.getRedisConnection();
						redisConnection.select(7);
						RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
//						redisConnection.expire(phone.getBytes(), 1800L);
						System.out.println(sms.getCode());
					}else{
						request.getSession().setAttribute(CommonConstant.SMS_CODE_KEY, sms);
					}
					returnJson(respon,response,msg,respon,request,null);
				}else if("2".equals(type)){
					//修改密码
					respon.setReturnCode("300005");
					respon.setReturnInfo("请先激活账号");
					returnJson(respon,response,msg,respon,request,null);
				}else if("3".equals(type)){
					SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_REGISTER);
					sms.setDate(DateUtil.DateToString(new Date()));
					// 新版本用户信息保存redis
					RedisConnection redisConnection = RedisUtil.getRedisConnection();
					redisConnection.select(7);
					RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
//					redisConnection.expire(phone.getBytes(), 1800L);
					this.SendMsg(sms, "1", "有家客户端");
//					System.out.println(sms.getCode());
					respon.setSmsPassWord(sms.getCode());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 发送短信
	 */
	public  void SendMsg(SmsBean sms, String type,String appName) throws Exception{
		TaobaoClient client = new DefaultTaobaoClient(sms.ALIDAYU_API_URL, sms.ALIDAYU_APP_KEY, sms.ALIDAYU_APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName(sms.ALIDAYU_SMS_SIGN);
		JSONObject json=new JSONObject();
		String product="";
		json.put("code", sms.getCode());
		if(sms.ALIDAYU_SMS_REGISTER.equals(type)){
			product=appName;

		}else{
			product="";
		}
		json.put("product", product);
		req.setSmsParamString(json.toString());
		req.setRecNum(sms.getPhone());
		req.setSmsTemplateCode(sms.SMS_TEMPLATE_CODE.get(type));
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		SendMessage message=new SendMessage();
		message.setCrtDate(new Date());
		message.setRemarks("短信类型"+sms.getType()+rsp.getBody());
		message.setCustPhone(sms.getPhone());
		message.setMsgType("2");
		if(rsp.isSuccess()){
			message.setSendDate(new Date());
			message.setSendStatus("1");
		}else{
			message.setSendStatus("2");
		}
		sendMessageService.save(message);
	}
	
	/**
	 * 小程序获取验证码接口
	 */
	@RequestMapping("/getWxCustAccountSmsPassword")
	public void getWxCustAccountSmsPassword(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("portalAccount");
		StringBuilder msg = new StringBuilder();
		CustAccountDetailResp respon=new CustAccountDetailResp();
		try {
			//获取验证码
			//激活操作
			SmsBean sms=new SmsBean(phone,StringUtil.getRandomNum(6),SmsBean.ALIDAYU_SMS_REGISTER);
			//this.SendMsg(sms,type,"有家客户端");
			sms.setDate(DateUtil.DateToString(new Date()));
			// 新版本用户信息保存redis
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(7);
			RedisUtil.set(redisConnection, phone, new String(SerializeUtil.serialize(sms)),1800L);
//			redisConnection.expire(phone.getBytes(), 1800L);
			this.SendMsg(sms, "1", "有家装饰微信小程序");
//			System.out.println(sms.getCode());
//			respon.setSmsPassWord(sms.getCode());
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}




