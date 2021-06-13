package com.house.home.client.controller;

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
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.MeasureRoomEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.MeasureRoomResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;
import com.house.home.entity.query.SignInPic;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.SignInService;
import com.house.home.service.design.CustConService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.MeasureRoomService;

@Controller
@RequestMapping("/client/measureRoom")
public class ClientMeasureRoomController extends ClientBaseController{

	@Autowired
	MeasureRoomService measureRoomService;
	@Autowired
	CustomerService customerService;
	@Autowired
	BuilderService builderService;
	@Autowired
	SignInService signInService;
	@Autowired
	CustStakeholderService custStakeholderService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	CustConService custConService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMeasureRoomList")
	public void getMeasureRoomList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		MeasureRoomEvt evt=new MeasureRoomEvt();
		BasePageQueryResp<MeasureRoomResp> respon=new BasePageQueryResp<MeasureRoomResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (MeasureRoomEvt)JSONObject.toBean(json,MeasureRoomEvt.class);
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
			measureRoomService.getMeasureRoomList(page,evt);
			List<MeasureRoomResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), MeasureRoomResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/saveMeasureRoom")
	public void saveMeasureRoom(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		MeasureRoomEvt evt=new MeasureRoomEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt = (MeasureRoomEvt)JSONObject.toBean(json,MeasureRoomEvt.class);
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
			
		
			SignIn signIn = new SignIn();
			signIn.setCustCode(evt.getCustCode());
			signIn.setSignCzy(evt.getCzybh());
			signIn.setCrtDate(new Date());
			signIn.setLongitude(evt.getLongitude());
			signIn.setLatitude(evt.getLatitude());
			signIn.setAddress(evt.getAddress());
			signIn.setErrPosi("0");
			signIn.setSignInType2("51");
			signIn.setRemarks(evt.getRemarks());
			
			Customer customer=customerService.get(Customer.class,evt.getCustCode());
			if(customer.getBuilderCode()!=null){
				Builder builder=builderService.get(Builder.class,customer.getBuilderCode());
				if(builder != null && builder.getLongitude()!=null&&builder.getLongitude().longValue()!=0){
					if(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude())>PosiBean.LIMIT_DISTANCE){
						signIn.setErrPosi("1");
					}else{
						signIn.setErrPosi("0");
					}
				}
			}
			if(StringUtils.isNotBlank(signIn.getSignInType2())){
				String no = signInService.getSeqNo("tSignIn");
				signIn.setNo(no);
				String str = evt.getPhotoString();
				if (StringUtils.isNotBlank(str)){
					String[] arr = str.split(",");
					for (String photoName: arr){
						SignInPic signInPic = new SignInPic();
						signInPic.setPhotoName(photoName);
						signInPic.setNo(no);
						signInPic.setLastUpdate(new Date());
						signInPic.setLastUpdatedBy(evt.getCzybh());
						signInPic.setActionLog("ADD");
						signInPic.setExpired("F");
						signInPic.setIsSendYun("1");
						signInPic.setSendDate(new Date());
						signInService.save(signInPic);
						signInPic = null;
					}
				}
			}
			signInService.save(signIn);
			if(customer.getMeasureDate()==null){
				customer.setMeasureDate(new Date());
				customer.setLastUpdate(new Date());
				customer.setLastUpdatedBy(evt.getCzybh());
				customerService.update(customer);
			}
			String role = custStakeholderService.getRoleByCustCodeAndEmpCode(evt.getCustCode(),evt.getCzybh());
//			Employee employee = employeeService.get(Employee.class,evt.getCzybh());
//			String context = role+" "+employee.getNameChi()+" 上门量房，说明："+evt.getRemarks();
			String context = role+" 上门量房，说明："+evt.getRemarks();
			CustCon custCon = new CustCon();
			custCon.setCustCode(evt.getCustCode());
			custCon.setConDate(new Date());
			custCon.setRemarks(context);
			custCon.setConMan(evt.getCzybh());
			custCon.setType("2");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(evt.getCzybh());
			custCon.setActionLog("ADD");
			custCon.setExpired("F");
			custConService.save(custCon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCustomerListByCustRight")
	public void getCustomerListByCustRight(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		MeasureRoomEvt evt=new MeasureRoomEvt();
		BasePageQueryResp<MeasureRoomResp> respon=new BasePageQueryResp<MeasureRoomResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (MeasureRoomEvt)JSONObject.toBean(json,MeasureRoomEvt.class);
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
			measureRoomService.findPageBySql_custRight(page, evt);
			List<MeasureRoomResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), MeasureRoomResp.class);
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
	
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping("/getMeasureRoomPhotoList")
	public void getMeasureRoomPhotoList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MeasureRoomEvt evt=new MeasureRoomEvt();
		BasePageQueryResp<MeasureRoomResp> respon=new BasePageQueryResp<MeasureRoomResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(MeasureRoomEvt) JSONObject.toBean(json, MeasureRoomEvt.class);
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
			List<Map<String, Object>> photoList  = measureRoomService.getMeasureRoomPhotoList(evt);
			for(int j = 0;j < photoList.size();j++){
				if(photoList.get(j).get("pk") != null){
					photoList.get(j).put("pk", photoList.get(j).get("pk"));
					if("1".equals(photoList.get(j).get("isSendYun").toString())){
						photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/signIn/"
								+photoList.get(j).get("photoName").toString());
					}else{
						String src = getSignInPhotoDownloadPath(request);
						photoList.get(j).put("src", src+photoList.get(j).get("photoName").toString());
					}
				}
			}
			System.out.println(photoList);
			List<MeasureRoomResp> listBean = BeanConvertUtil.mapToBeanList(photoList, MeasureRoomResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doMeasureRoomPhotoDelete")
	public void doMeasureRoomPhotoDelete(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MeasureRoomEvt evt=new MeasureRoomEvt();
		BasePageQueryResp<MeasureRoomResp> respon=new BasePageQueryResp<MeasureRoomResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=this.getJson(request,msg,json,respon);
			evt=(MeasureRoomEvt) JSONObject.toBean(json, MeasureRoomEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			SignInPic signInPic = signInService.get(SignInPic.class, evt.getPk());
			if(signInPic!=null){
				signInPic.setExpired("T");
				signInService.update(signInPic);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateMeasureRoom")
	public void doUpdateMeasureRoom(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MeasureRoomEvt evt=new MeasureRoomEvt();
		BasePageQueryResp<MeasureRoomResp> respon=new BasePageQueryResp<MeasureRoomResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=this.getJson(request,msg,json,respon);
			evt=(MeasureRoomEvt) JSONObject.toBean(json, MeasureRoomEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			SignIn signIn = signInService.get(SignIn.class, evt.getSignInPk());
			signIn.setRemarks(evt.getRemarks());
			signInService.update(signIn);
//			Employee employee = employeeService.get(Employee.class,evt.getCzybh());
			CustCon custCon = new CustCon();
			custCon.setCustCode(signIn.getCustCode());
			custCon.setConDate(new Date());
//			custCon.setRemarks(employee.getNameChi()+" 修改量房信息");
			custCon.setRemarks("修改量房信息");
			custCon.setConMan(evt.getCzybh());
			custCon.setType("2");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(evt.getCzybh());
			custCon.setActionLog("ADD");
			custCon.setExpired("F");
			custConService.save(custCon);
			String str = evt.getPhotoString();
			if (StringUtils.isNotBlank(str)){
				String[] arr = str.split(",");
				for (String photoName: arr){
					
					SignInPic signInPic = signInService.getPicByName(photoName);
					if(signInPic==null){
						SignInPic newSignInPic = new SignInPic();
						newSignInPic.setPhotoName(photoName);
						newSignInPic.setNo(signIn.getNo());
						newSignInPic.setLastUpdate(new Date());
						newSignInPic.setLastUpdatedBy(evt.getCzybh());
						newSignInPic.setActionLog("ADD");
						newSignInPic.setExpired("F");
						newSignInPic.setIsSendYun("1");
						newSignInPic.setSendDate(new Date());
						signInService.save(newSignInPic);
					}else{
						signInPic.setLastUpdate(new Date());
						signInPic.setLastUpdatedBy(evt.getCzybh());
						signInPic.setActionLog("EDIT");
						signInPic.setExpired("F");
						signInPic.setIsSendYun("1");
						signInPic.setSendDate(new Date());
						signInService.update(signInPic);
					}
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
}
