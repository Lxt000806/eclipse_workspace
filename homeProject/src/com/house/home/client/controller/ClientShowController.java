package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.DoCustOrderEvt;
import com.house.home.client.service.evt.GetAdvertDetailEvt;
import com.house.home.client.service.evt.GetAdvertListEvt;
import com.house.home.client.service.evt.GetCityAppUrlListEvt;
import com.house.home.client.service.evt.GetDesignDemoDetailEvt;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildDetailEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.client.service.evt.GetXtdmListEvt;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GetAdvertDetailResp;
import com.house.home.client.service.resp.GetAdvertListResp;
import com.house.home.client.service.resp.GetCityAppUrlListResp;
import com.house.home.client.service.resp.GetCustPwdVarifyResp;
import com.house.home.client.service.resp.GetDesignDemoDetailResp;
import com.house.home.client.service.resp.GetDesignDemoListResp;
import com.house.home.client.service.resp.GetPrjItem1ListResp;
import com.house.home.client.service.resp.GetRSAPublicKeyResp;
import com.house.home.client.service.resp.GetShowBuildDetailResp;
import com.house.home.client.service.resp.GetShowBuildsResp;
import com.house.home.client.service.resp.GetXtdmListResp;
import com.house.home.client.service.resp.PhoneVarifyResp;
import com.house.home.entity.basic.Advert;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustOrder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.design.DesignDemoPic;
import com.house.home.service.basic.AdvertService;
import com.house.home.service.basic.CustOrderService;
import com.house.home.service.basic.ShowService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.XtdmService;

import java.util.List;
@RequestMapping("/client/show")
@Controller
public class ClientShowController extends ClientBaseController {
	
	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private ShowService showService;
	
	@Autowired
	private XtdmService xtdmService;
	
	@Autowired
	private CustOrderService custOrderService;
	
	@Autowired
	private XtcsService xtcsService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAdvertList")
	public void getActivityList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetAdvertListEvt evt=new GetAdvertListEvt();
		BasePageQueryResp<GetAdvertListResp> respon=new BasePageQueryResp<GetAdvertListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetAdvertListEvt) JSONObject.toBean(json, GetAdvertListEvt.class);
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
			Advert advert = new Advert();
			advert.setAdvType(evt.getAdvType());
			advertService.getAdvertList(page, advert);
			List<GetAdvertListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetAdvertListResp.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getShowBuilds")
	public void getShowBuilds(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetShowBuildsEvt evt=new GetShowBuildsEvt();
		BasePageQueryResp<GetShowBuildsResp> respon=new BasePageQueryResp<GetShowBuildsResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetShowBuildsEvt) JSONObject.toBean(json, GetShowBuildsEvt.class);
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
			showService.getShowBuilds(page, evt);
			List<GetShowBuildsResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetShowBuildsResp.class);
			if(listBean != null && listBean.size() > 0){
				for(int i = 0;i < listBean.size();i++){
					List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(listBean.get(i).getPrjProgConfirmNo(),0);
					if(photoList == null){
						photoList = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < photoList.size();j++){
						if("1".equals(photoList.get(j).get("isSendYun").toString())){
							photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
						}else{
							String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
							photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
						}
					}
					listBean.get(i).setPhotos(photoList);
				}
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

	
	@RequestMapping("/getShowBuildDetail")
	public void getShowBuildDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetShowBuildDetailEvt evt=new GetShowBuildDetailEvt();
		GetShowBuildDetailResp respon=new GetShowBuildDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetShowBuildDetailEvt) JSONObject.toBean(json, GetShowBuildDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			Customer customer = this.showService.get(Customer.class, evt.getCustCode());
			respon.setCustCode(evt.getCustCode());
			if(customer.getAddress().length()>4){
				respon.setEmpAddress(customer.getAddress().substring(0,customer.getAddress().length()-4)+"**"+customer.getAddress().substring(customer.getAddress().length()-2));
			}else{
				respon.setEmpAddress("**"+customer.getAddress().substring(customer.getAddress().length()-2));
			}
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = this.showService.get(Builder.class, customer.getBuilderCode());
				if(builder != null){
					respon.setAddress(builder.getDescr());
				}
			}
			if(StringUtils.isNotBlank(customer.getDesignMan())){
				Employee employee = this.showService.get(Employee.class, customer.getDesignMan());
				if(employee != null ){
					respon.setDesignMan(employee.getNameChi());
				}
			}
			if(StringUtils.isNotBlank(customer.getProjectMan())){
				Employee projectMan  = this.showService.get(Employee.class, customer.getProjectMan());
				if(projectMan != null){
					respon.setProjectMan(projectMan.getNameChi());
				}
			}
			CustType custType =showService.get(CustType.class, customer.getCustType());
			respon.setArea(customer.getArea());
			respon.setCustTypeDescr(custType.getDesc1());
			if(StringUtils.isNotBlank(evt.getGdbb())){
				respon.setAddress(customer.getAddress());
			}
			respon.setBeginDate(customer.getConfirmBegin());
			respon.setCustDescr(customer.getDescr().substring(0, 1));
			respon.setPlanEndDate(DateUtil.addDate(customer.getConfirmBegin(), customer.getConstructDay()));
			respon.setGender(customer.getGender());
			List<Map<String, Object>> list = this.showService.getPrjProgConfirm(evt.getCustCode());
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			for(int i = 0; i < list.size(); i++){
				List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(list.get(i).get("prjProgConfirmNo").toString(), null);
				if(photoList == null || (photoList != null && photoList.size() < 3)){
					photoList = new ArrayList<Map<String,Object>>();
				}
				for(int j = 0;j < photoList.size();j++){
					if("1".equals(photoList.get(j).get("isSendYun").toString())){
						photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
					}else{
						String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
						photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
					}
				}
				list.get(i).put("photos", photoList);
			}
			respon.setDatas(list);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCityAppUrlList")
	public void getCityAppUrlList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetCityAppUrlListEvt evt=new GetCityAppUrlListEvt();
		BasePageQueryResp<GetCityAppUrlListResp> respon=new BasePageQueryResp<GetCityAppUrlListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetCityAppUrlListEvt) JSONObject.toBean(json, GetCityAppUrlListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String, Object>> list = this.showService.getCityAppUrlList(evt.getLongitude(), evt.getLatitude());
			List<GetCityAppUrlListResp> listBean = BeanConvertUtil.mapToBeanList(list, GetCityAppUrlListResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getPrjItem1List")
	public void getPrjItem1List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		BasePageQueryResp<GetPrjItem1ListResp> respon=new BasePageQueryResp<GetPrjItem1ListResp>();
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
			List<Map<String, Object>> list = this.showService.getPrjItem1List();
			List<GetPrjItem1ListResp> listBean = BeanConvertUtil.mapToBeanList(list, GetPrjItem1ListResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getAdvertDetail")
	public void getAdvertDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetAdvertDetailEvt evt=new GetAdvertDetailEvt();
		GetAdvertDetailResp respon=new GetAdvertDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetAdvertDetailEvt) JSONObject.toBean(json, GetAdvertDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String, Object> map = this.advertService.getAdvertDetail(evt.getPk());
			
			if(map != null){
				BeanConvertUtil.mapToBean(map, respon);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doCustOrder")
	public void doCustOrder(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		DoCustOrderEvt evt=new DoCustOrderEvt();
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
			evt=(DoCustOrderEvt) JSONObject.toBean(json, DoCustOrderEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(evt.getCustDescr())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入您的名字");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if(evt.getCustDescr().length() > 60){
				respon.setReturnCode("400001");
				respon.setReturnInfo("您输入的称呼过长,请限制在60字以内");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(StringUtils.isBlank(evt.getCustPhone())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请输入您的手机号码");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			String phoneVarify = getPhoneVarify();
			if(evt.getCustPhone().length() > 20 || !evt.getCustPhone().matches(phoneVarify)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("您输入的联系方式不正确,请核对后再试");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if(this.custOrderService.existsCustOrder(evt.getCustPhone())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("您已进行过预约,有疑问请咨询客户");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			CustOrder custOrder = new CustOrder();
			custOrder.setMobile1(evt.getCustPhone());
			custOrder.setDescr(evt.getCustDescr());
			custOrder.setDate(new Date());
			
			this.custOrderService.save(custOrder);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getXtdmList")
	public void getXtdmList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetXtdmListEvt evt=new GetXtdmListEvt();
		BasePageQueryResp<GetXtdmListResp> respon=new BasePageQueryResp<GetXtdmListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetXtdmListEvt) JSONObject.toBean(json, GetXtdmListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getId())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("Id 不能为空");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Xtdm> list = this.xtdmService.getById(evt.getId());
			List<GetXtdmListResp> listBean = new ArrayList<GetXtdmListResp>();
			for(int i = 0;i < list.size();i++){
				GetXtdmListResp resp = new GetXtdmListResp();
				resp.setDescr(list.get(i).getNote());
				resp.setValue(list.get(i).getCbm());
				listBean.add(resp);
			}
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDesignDemoList")
	public void getDesignDemoList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetDesignDemoListEvt evt=new GetDesignDemoListEvt();
		BasePageQueryResp<GetDesignDemoListResp> respon=new BasePageQueryResp<GetDesignDemoListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetDesignDemoListEvt) JSONObject.toBean(json, GetDesignDemoListEvt.class);
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
			showService.getDesignDemoList(page, evt);
			List<GetDesignDemoListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetDesignDemoListResp.class);
			//地址修改备注
			for(int i = 0;i < listBean.size();i++){
				DesignDemo designDemo = this.showService.get(DesignDemo.class, listBean.get(i).getNo());
				DesignDemoPic designDemoPic = this.showService.get(DesignDemoPic.class, listBean.get(i).getPicAddrPk());
				if(designDemo != null && designDemoPic != null){
					if("1".equals(designDemoPic.getIsSendYun())){
						if(StringUtils.isNotBlank(designDemo.getCustCode())){
							listBean.get(i).setPicAddr(OssConfigure.cdnAccessUrl+"/designDemoPic/"+designDemo.getCustCode()+"/"+designDemo.getNo().trim()+"/"+designDemoPic.getPhotoName());
						}else {
							listBean.get(i).setPicAddr(OssConfigure.cdnAccessUrl+"/designDemoPic/"+designDemo.getNo().trim()+"/"+designDemoPic.getPhotoName());
						}
					}else{
						if(StringUtils.isNotBlank(designDemo.getCustCode())){
							listBean.get(i).setPicAddr(PathUtil.getWebRootAddress(request)+"homePhoto/designDemoPic/"+designDemo.getCustCode()+"/"+designDemo.getNo().trim()+"/"+designDemoPic.getPhotoName());
						}else {
							listBean.get(i).setPicAddr(PathUtil.getWebRootAddress(request)+"homePhoto/designDemoPic/"+designDemo.getNo().trim()+"/"+designDemoPic.getPhotoName());
						}
					}
				}
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
	
	@RequestMapping("/getDesignDemoDetail")
	public void getDesignDemoDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetDesignDemoDetailEvt evt=new GetDesignDemoDetailEvt();
		GetDesignDemoDetailResp respon=new GetDesignDemoDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetDesignDemoDetailEvt) JSONObject.toBean(json, GetDesignDemoDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String, Object> map = this.showService.getDesignDemoDetail(evt.getNo());
			if(map != null){
				BeanConvertUtil.mapToBean(map, respon);
			}
			List<Map<String, Object>> photos = this.showService.getDesignDemoDetailPhotos(evt.getNo());
			if(photos == null){
				photos = new ArrayList<Map<String,Object>>();
			}
			for(int i = 0;i < photos.size();i++){
				DesignDemo designDemo = this.showService.get(DesignDemo.class, photos.get(i).get("No").toString());
				if(designDemo != null){
					if("1".equals(photos.get(i).get("IsSendYun"))){
						if(StringUtils.isNotBlank(designDemo.getCustCode())){
							photos.get(i).put("src", OssConfigure.cdnAccessUrl+"/designDemoPic/"+designDemo.getCustCode()+"/"+designDemo.getNo().trim()+"/"+photos.get(i).get("src").toString());
						}else{
							photos.get(i).put("src", OssConfigure.cdnAccessUrl+"/designDemoPic/"+designDemo.getNo().trim()+"/"+photos.get(i).get("src").toString());
						}
					}else{
						if(StringUtils.isNotBlank(designDemo.getCustCode())){
							photos.get(i).put("src", PathUtil.getWebRootAddress(request)+"homePhoto/designDemoPic/"+designDemo.getCustCode()+"/"+designDemo.getNo().trim()+"/"+photos.get(i).get("src").toString());
						}else {
							photos.get(i).put("src", PathUtil.getWebRootAddress(request)+"homePhoto/designDemoPic/"+designDemo.getNo().trim()+"/"+photos.get(i).get("src").toString());
						}
					}
				}
			}
			respon.setPhotos(photos);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getPhoneVarify")
	public void getPhoneVarify(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseListQueryResp<PhoneVarifyResp> respon = new BaseListQueryResp<PhoneVarifyResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("numRule", ClientBaseController.getPhoneVarify());
			list.add(map);
			List<PhoneVarifyResp> listBean = BeanConvertUtil.beanToBeanList(list, PhoneVarifyResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean!=null?listBean.size():0l);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/getCustPwdVarify")
	public void getCustPwdVarify(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustPwdVarifyResp respon = new GetCustPwdVarifyResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			respon.setCustPwdRule("(^[0-9]*$)|(^[a-zA-Z]*$)");
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/getRSAPublicKey")
	public void getRSAPublicKey(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetRSAPublicKeyResp respon = new GetRSAPublicKeyResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			respon.setPublicKey(getRSAKey("publicKey"));
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
