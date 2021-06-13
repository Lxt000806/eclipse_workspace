package com.house.home.client.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.UpdateVersionEvt;
import com.house.home.client.service.evt.VersionEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.UpdateVersionResp;
import com.house.home.client.service.resp.VersionResp;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.Driver;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.Version;
import com.house.home.service.basic.CustAccountService;
import com.house.home.service.basic.DriverService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.basic.VersionService;

@Controller
@RequestMapping("/client/version")
public class ClientVersionController extends ClientBaseController {

	@Autowired
	private VersionService versionService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private CustAccountService custAccountService;
	@Autowired
	private PersonMessageService personMessageService;
	
	@RequestMapping("/checkVersion")
	public void checkVersion(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		VersionEvt evt = new VersionEvt();
		VersionResp respon = new VersionResp();
		boolean hasNotRead=false;
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (VersionEvt) JSONObject.toBean(json, VersionEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			Version version=null;
			//兼容以前的版本
			if(StringUtils.isNotBlank(evt.getName())){
				if(evt.getName().indexOf("employee")==0&&StringUtils.isNotBlank(evt.getPortalAccount())){
					Employee employee= employeeService.getByPhoneAndMm(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
					if(employee==null||"SUS".equals(employee.getStatus().trim())){
						HttpSession session = request.getSession(false);
						if (session!=null) {
						    session.invalidate();
						}
						return;
					}
				}
				if(evt.getName().indexOf("driver")==0&&StringUtils.isNotBlank(evt.getPortalAccount())){
					Driver driver = driverService.getByPhoneAndMm(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
					if(driver==null||"T".equals(driver.getExpired().trim())){
						HttpSession session = request.getSession(false);
						if (session!=null) {
						    session.invalidate();
						}
						return;
					}
				}
				if(evt.getName().indexOf("client")==0&&StringUtils.isNotBlank(evt.getPortalAccount())){
					CustAccount custAccount=custAccountService.getCustAccountByPhone(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
					if(custAccount==null){
						HttpSession session = request.getSession(false);
						if (session!=null) {
						    session.invalidate();
						}
						return;
					}
				}
				 version = versionService.get(Version.class, evt.getName());
			}else{
			//项目经理端
				//兼容以前版本新增员工状态验证
				if(StringUtils.isNotBlank(evt.getPortalAccount())){
					Employee employee= employeeService.getByPhoneAndMm(evt.getPortalAccount(),DesUtils.encode(evt.getPortalPwd()));
					if(employee==null||"SUS".equals(employee.getStatus().trim())){
						HttpSession session = request.getSession(false);
						if (session!=null) {
						    session.invalidate();
						}
						return;
					}
					 PersonMessage personMessage = new PersonMessage();
					 personMessage.setRcvCzy(employee.getNumber());
					 personMessage.setRcvType("1");
					// personMessage.setMsgType("5");
					 personMessage.setRcvStatus("0");
					long la = personMessageService.getNotConfirmedMessageCount(personMessage);
					if(la>0){
						hasNotRead=true;
					}
				}
			
			 version = versionService.get(Version.class, "1".equals(evt.getPortalType())?"android":"ios");
			}
			if (version!=null){
				if (version.getVersionNo().equals(evt.getVersionNo())){
					respon.setDownLoadRemark(version.getDescr());
					respon.setDownLoadUrl(version.getUrl());
					respon.setVersionNo(version.getVersionNo());
					respon.setExistNew(false);
					respon.setIsForce(false);
				}else{
					respon.setDownLoadRemark(version.getDescr());
					respon.setDownLoadUrl(version.getUrl());
					respon.setVersionNo(version.getVersionNo());
					if (compare(evt.getVersionNo(),version.getVersionNo())){
						respon.setExistNew(false);
					}else{
						respon.setExistNew(true);
					}
					String str = version.getCompatible();
					if (compare(str,evt.getVersionNo())){
						respon.setIsForce(true);
					}else{
						respon.setIsForce(false);
					}
				}
				respon.setHasNotRead(hasNotRead);
				if(("employee-android".equals(version.getName()) || "employee-ios".equals(version.getName())) && !respon.getIsForce() && (
						(StringUtils.isNotBlank(evt.getAutoCheckFlag()) && "1".equals(evt.getAutoCheckFlag())) 
						|| StringUtils.isBlank(evt.getAutoCheckFlag())
					)
				){
					respon.setExistNew(false);
				}
			}else{
				respon.setReturnCode("300102");
			}
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/updateVersion")
	public void updateVersion(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UpdateVersionEvt evt = new UpdateVersionEvt();
		UpdateVersionResp respon = new UpdateVersionResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (UpdateVersionEvt) JSONObject.toBean(json, UpdateVersionEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			if (StringUtils.isNotBlank(evt.getVersionNo()) && StringUtils.isNotBlank(evt.getCompatible())){
				Version version = versionService.get(Version.class, evt.getName());
				version.setVersionNo(evt.getVersionNo());
				version.setCompatible(evt.getCompatible());
				versionService.update(version);
				BeanUtilsEx.copyProperties(respon, version);
				respon.setReturnInfo("更新版本成功");
			}else{
				Version version = versionService.get(Version.class, evt.getName());
				BeanUtilsEx.copyProperties(respon, version);
				respon.setReturnInfo("查看版本成功");
			}
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/copyPictures")
	public void copyPictures(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseEvt evt = new VersionEvt();
		BaseResponse respon = new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseEvt) JSONObject.toBean(json, BaseEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			String prjProg = SystemConfig.getProperty("prjProg", "", "photo");
			String url1 = prjProg.substring(0,prjProg.length()-1);
			String url2 = url1.replace("prjProg", "prjProgNew");
//			String url1 = "D:/homePhoto/prjProg"; // 源文件夹
//			String url2 = "D:/homePhoto/prjProgNew"; // 目标文件夹
			File fileTag = new File(url2);
			if (!fileTag.exists()){
				fileTag.mkdir();
			}
			File[] file = (new File(url1)).listFiles(); // 获取源文件夹当前下的文件或目录
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()){
					String fileName = file[i].getName();
					if (fileName.length()>5){
						FileHelper.copyFile(file[i], new File(url2+"/"+fileName.substring(0,5)+"/" + file[i].getName()));
					}
				}
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	public boolean compare(String currentVersion, String clientVersion) {
		if (StringUtil.isEmpty(currentVersion)
				|| StringUtil.isEmpty(clientVersion)) {
			return false;
		}
		currentVersion = currentVersion.replaceAll("\\.", "");
		clientVersion = clientVersion.replaceAll("\\.", "");
		int currentValue = Integer.parseInt(currentVersion);
		int clientValue = Integer.parseInt(clientVersion);
		if (currentValue > clientValue) {
			return true;
		} else {
			return false;
		}
	}
}
