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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BuilderEvt;
import com.house.home.client.service.evt.GetDesignDemoDetailEvt;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildDetailEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.client.service.evt.ItemQueryEvt;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.DesignDemoResp;
import com.house.home.client.service.resp.DesignXtdmResp;
import com.house.home.client.service.resp.GetDesignDemoDetailResp;
import com.house.home.client.service.resp.GetDesignDemoListResp;
import com.house.home.client.service.resp.GetShowBuildDetailResp;
import com.house.home.client.service.resp.GetShowBuildsResp;
import com.house.home.client.service.resp.ItemReqQueryResp;
import com.house.home.client.service.resp.XtdmQueryResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.design.DesignDemoPic;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.ShowService;
import com.house.home.service.design.DesignDemoService;

@RequestMapping("/client/designDemo")
@Controller
public class ClientDesignDemoController extends ClientBaseController{
	
	@Autowired
	private DesignDemoService designDemoService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private ShowService showService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getDesignDemoPage")
	public void getDesignDemoPage(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BuilderEvt evt=new BuilderEvt();
		DesignDemoResp respon=new DesignDemoResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("itemDescr", request.getParameter("itemDescr"));
			evt = (BuilderEvt)JSONObject.toBean(json,BuilderEvt.class);
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
			page.setPageSize(10);
			
			Builder builder =new Builder();
			builder.setCode(evt.getCode());
			builder.setDescr(evt.getDescr());
			
		    builderService.getBuilderList(page,builder);
			List<DesignDemoResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), DesignDemoResp.class);
			
			Map<String, Object> map=designDemoService.getQty(evt.getCode());
			
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setDatas(listBean);
			respon.setOrderNum(Integer.parseInt(map.get("orderNum").toString()));
			respon.setConstructNum(Integer.parseInt(map.get("constructNum").toString()));
			respon.setEndNum(Integer.parseInt(map.get("endNum").toString()));
			respon.setNotBeginNum(Integer.parseInt(map.get("notBeginNum").toString()));
			respon.setContractNum(respon.getNotBeginNum()+respon.getConstructNum()+respon.getEndNum());
			respon.setTotalNum(respon.getOrderNum()+respon.getContractNum());
			respon.setDesignDemoNum(Integer.parseInt(map.get("designDemoNum").toString()));
			returnJson(respon, response, msg, respon, request, interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
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
						}else{
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getXtdmListById")
	public void getXtdmListById(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		DesignXtdmResp respon = new DesignXtdmResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			
			Page page = new Page();

			designDemoService.getById(page,evt.getId());
			List<XtdmQueryResp> listBean = BeanConvertUtil.beanToBeanList(page.getResult(), XtdmQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean!=null?listBean.size():0l);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
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
			designDemoService.getShowBuilds(page, evt);
			List<GetShowBuildsResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetShowBuildsResp.class);
			if(listBean != null && listBean.size() > 0){
				for(int i = 0;i < listBean.size();i++){
					if(listBean.get(i).getAddress().length()>4){
						listBean.get(i).setAddress(listBean.get(i).getAddress().substring(0,listBean.get(i).getAddress().length()-4)+"**"+listBean.get(i).getAddress().substring(listBean.get(i).getAddress().length()-2));
					}else{
						listBean.get(i).setAddress("**"+listBean.get(i).getAddress().substring(listBean.get(i).getAddress().length()-2));
					}
					List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(listBean.get(i).getPrjProgConfirmNo(), 3);
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
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				Builder builder = this.showService.get(Builder.class, customer.getBuilderCode());
				if(builder != null){
					respon.setAddress(builder.getDescr());
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
	
	
}
