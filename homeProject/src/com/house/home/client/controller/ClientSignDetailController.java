package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.SignDetailEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.SignDetailNoSignResp;
import com.house.home.client.service.resp.SignDetailResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.project.SignDetailService;

@RequestMapping("/client/signDetail")
@Controller
public class ClientSignDetailController extends ClientBaseController {
	
	@Autowired
	private SignDetailService signDetailService;
	@Autowired
	private CzybmService czybmService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMainPageSignList")
	public void getMainPageSignList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignDetailEvt evt=new SignDetailEvt();
		BasePageQueryResp<SignDetailResp> respon=new BasePageQueryResp<SignDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SignDetailEvt)JSONObject.toBean(json,SignDetailEvt.class);
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

			Customer customer = new Customer();
			
			customer.setBeginDate(new Date());
			customer.setEndDate(new Date());
			UserContext uc = this.getUserContext(request);
			signDetailService.getCheckConfirmList(page, customer, uc);
			
			List<SignDetailResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SignDetailResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getSignDetail")
	public void getSignDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignDetailEvt evt=new SignDetailEvt();
		BasePageQueryResp<SignDetailResp> respon=new BasePageQueryResp<SignDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SignDetailEvt)JSONObject.toBean(json,SignDetailEvt.class);
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

			Customer customer = new Customer();

			customer.setWorkType12Dept(evt.getWorkType12Dept());
			customer.setWorkerClassify(evt.getWorkerClassify());
			customer.setCheckManDescr(evt.getCheckMan());
			customer.setBeginDate(new Date());
			customer.setEndDate(new Date());
			
			// 1:本日   	2:上日 	3：本周	4：本月
			if("1".equals(evt.getDayRange())){
				customer.setBeginDate(new Date());
				customer.setEndDate(new Date());
			}
			if("2".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.addDate(new Date(), -1));
				customer.setEndDate(DateUtil.addDate(new Date(), -1));
			}
			if("3".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.getFirstDayOfWeek(new Date()));
				customer.setEndDate(new Date());
			}
			if("4".equals(evt.getDayRange())){
				customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
				customer.setEndDate(DateUtil.endOfTheMonth(new Date()));
			}
			
			if(StringUtils.isNotBlank(evt.getType())){
				customer.setType(evt.getType());
			}
			if(StringUtils.isNotBlank(evt.getNo())){
				customer.setNo(evt.getNo());
			}
			if(StringUtils.isNotBlank(evt.getDeptType())){
				customer.setDepType(evt.getDeptType());
			}
			UserContext uc = this.getUserContext(request);
			signDetailService.getSignDetail(page, customer, uc);
			List<SignDetailResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SignDetailResp.class);
			String url = "";
			
			if(listBean != null && listBean.size() > 0){
				for(int i = 0;i < listBean.size();i++){
					List<Map<String, Object>> photoList =  new ArrayList<Map<String,Object>>();
					List<Map<String, Object>> allPhotoList =  new ArrayList<Map<String,Object>>();
					if("1".equals(customer.getType())){
						allPhotoList = signDetailService.getPrjProgConfirmPhoto(listBean.get(i).getNo(), 0);
					}
					if("2".equals(customer.getType())){
						allPhotoList = signDetailService.getSignInPic(listBean.get(i).getNo(), 0);
					}
					if("3".equals(customer.getType())){
						allPhotoList = signDetailService.getWorkSignInPic(listBean.get(i).getNo(), 0);
					}
					if(allPhotoList == null){
						allPhotoList = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < allPhotoList.size();j++){
						if("1".equals(customer.getType())){
							if("1".equals(allPhotoList.get(j).get("hasSend").toString())){
								url = OssConfigure.cdnAccessUrl+"/prjProgNew/"+allPhotoList.get(j).get("src").toString().substring(0, 5)+"/";
							} else {
								url = getPrjProgPhotoDownloadPath(request, allPhotoList.get(j).get("src").toString());
							}
						}
						if("2".equals(customer.getType())){
							if("1".equals(allPhotoList.get(j).get("hasSend").toString())){
								url = OssConfigure.cdnAccessUrl+"/signIn/";
							} else {
								url = getSignInPhotoDownloadPath(request);
							}
						}
						if("3".equals(customer.getType())){
							if("1".equals(allPhotoList.get(j).get("hasSend").toString())){
								url = OssConfigure.cdnAccessUrl+"/workSignPic/"+allPhotoList.get(j).get("CustCode").toString()+"/";
							} else {
								url = getWorkSignInPicDownLoadPath(request, allPhotoList.get(j).get("CustCode").toString());
							}
						}
						allPhotoList.get(j).put("src", url+allPhotoList.get(j).get("src").toString());
						if(j < 3) {
							Map<String, Object> photo = new HashMap<String, Object>();
							photo.putAll(allPhotoList.get(j));
							photoList.add(photo);
						}
					}
					if(allPhotoList.size()>0){
						listBean.get(i).setTotalNum(allPhotoList.get(0).get("totalNum").toString());
					}
					listBean.get(i).setPhotos(photoList);
					listBean.get(i).setAllPhotos(allPhotoList);
				}
			}
			
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum((long)page.getPageNo() * page.getPageSize());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPicMore")
	public void getPicMore(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignDetailEvt evt=new SignDetailEvt();
		BasePageQueryResp<SignDetailResp> respon=new BasePageQueryResp<SignDetailResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SignDetailEvt)JSONObject.toBean(json,SignDetailEvt.class);
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

			Customer customer = new Customer();

			if(StringUtils.isNotBlank(evt.getType())){
				customer.setType(evt.getType());
			}
			if(StringUtils.isNotBlank(evt.getNo())){
				customer.setNo(evt.getNo());
			}
			if(StringUtils.isNotBlank(evt.getDeptType())){
				customer.setDepType(evt.getDeptType());
			}
			
			List<SignDetailResp> listBean = new ArrayList<SignDetailResp>();
			SignDetailResp resp = new SignDetailResp();
			listBean.add(resp);
			
			String src = "";
			if(listBean != null && listBean.size() > 0){
				for(int i = 0;i < listBean.size();i++){
					List<Map<String, Object>> photoList =  new ArrayList<Map<String,Object>>();
					if("1".equals(customer.getType())){
						photoList = signDetailService.getPicMore(customer);
					}
					if("2".equals(customer.getType())){
						photoList = signDetailService.getPicMore(customer);
					}
					if("3".equals(customer.getType())){
						photoList = signDetailService.getPicMore(customer);
					}
					if(photoList == null){
						photoList = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < photoList.size();j++){
						if("1".equals(customer.getType())){
							if("1".equals(photoList.get(j).get("hasSend").toString())){
								src = OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/";
							} else {
								src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
							}
						}
						if("2".equals(customer.getType())){
							if("1".equals(photoList.get(j).get("hasSend").toString())){
								src = OssConfigure.cdnAccessUrl+"/signIn/";
							} else {
								src = getSignInPhotoDownloadPath(request);
							}
						}
						if("3".equals(customer.getType())){
							if("1".equals(photoList.get(j).get("hasSend").toString())){
								src = OssConfigure.cdnAccessUrl+"/workSignPic/"+photoList.get(j).get("CustCode").toString()+"/";
							} else {
								src = getWorkSignInPicDownLoadPath(request, photoList.get(j).get("CustCode").toString());
							}
						}
						photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
					}
					listBean.get(i).setPhotos(photoList);
				}
			}
			
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum((long)page.getPageNo() * page.getPageSize());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getNoSignList")
	public void getNoSignList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignDetailEvt evt=new SignDetailEvt();
		BasePageQueryResp<SignDetailNoSignResp> respon=new BasePageQueryResp<SignDetailNoSignResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SignDetailEvt)JSONObject.toBean(json,SignDetailEvt.class);
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

			Boolean iSAdminAssign = false;
			if(czybmService.hasGNQXByCzybh(evt.getCzybh(),"0834","查看工人电话")){
				iSAdminAssign = true;
			}
			List<Map<String, Object>> result = signDetailService.getNoSignList(page, evt, iSAdminAssign);
			System.out.println(result);
			List<SignDetailNoSignResp> listBean = BeanConvertUtil.mapToBeanList(result, SignDetailNoSignResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjNoSignList")
	public void getPrjNoSignList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignDetailEvt evt=new SignDetailEvt();
		BasePageQueryResp<SignDetailNoSignResp> respon=new BasePageQueryResp<SignDetailNoSignResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (SignDetailEvt)JSONObject.toBean(json,SignDetailEvt.class);
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

			signDetailService.getPrjNoSignList(page, evt);
			
			List<SignDetailNoSignResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SignDetailNoSignResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(page.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
}











