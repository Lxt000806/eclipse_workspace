package com.house.home.client.controller;

import java.io.File;
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

import com.house.framework.commons.fileUpload.impl.ItemAppSendUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.jpush.Notice;
import com.house.home.client.jpush.TestJpushClient;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.ItemAppReturnQueryEvt;
import com.house.home.client.service.evt.ItemReturnArriveSaveEvt;
import com.house.home.client.service.evt.ItemReturnArrivedQueryEvt;
import com.house.home.client.service.evt.UpdateItemReturnReceiveEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ItemAppReturnDetailResp;
import com.house.home.client.service.resp.ItemAppReturnQueryResp;
import com.house.home.client.service.resp.ItemAppSendPhotoResp;
import com.house.home.client.service.resp.ItemReturnArrivedResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.driver.ItemReturn;
import com.house.home.entity.driver.ItemReturnArrive;
import com.house.home.entity.driver.ItemReturnDetail;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.service.driver.ItemAppSendPhotoService;
import com.house.home.service.driver.ItemAppSendService;
import com.house.home.service.driver.ItemReturnArriveService;
import com.house.home.service.driver.ItemReturnDetailService;
import com.house.home.service.driver.ItemReturnService;
@Controller
@RequestMapping("/client/driverItemAppReturn")
public class ClientItemAppReturnController extends ClientBaseController {
	@Autowired
	private ItemReturnService itemReturnService;
	@Autowired
	private ItemReturnDetailService itemReturnDetailService;
	@Autowired
	private ItemReturnArriveService itemReturnArriveService;
	@Autowired
	private ItemAppSendService itemAppSendService;
	@Autowired
	private ItemAppSendPhotoService itemAppSendPhotoService;
	@RequestMapping("/getItemAppReturnList")
	public void getItemAppReturnList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppReturnQueryEvt evt=new ItemAppReturnQueryEvt();
		BasePageQueryResp<ItemAppReturnQueryResp> respon=new BasePageQueryResp<ItemAppReturnQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppReturnQueryEvt) JSONObject.toBean(json, ItemAppReturnQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page=new Page();
			page.setPageSize(evt.getPageSize());
			page.setPageNo(evt.getPageNo());
			itemReturnService.findPageByDriver(page, evt);
			List<ItemAppReturnQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppReturnQueryResp.class);
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
	 * 已退货列表接口
	 */
	@RequestMapping("/getItemReturnArrivedList")
	public void getItemReturnArrivedList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemReturnArrivedQueryEvt evt=new ItemReturnArrivedQueryEvt();
		BasePageQueryResp<ItemReturnArrivedResp> respon=new BasePageQueryResp<ItemReturnArrivedResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemReturnArrivedQueryEvt) JSONObject.toBean(json, ItemReturnArrivedQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page=new Page();
			page.setPageSize(evt.getPageSize());
			page.setPageNo(evt.getPageNo());
			itemReturnService.findPageByDriverArrived(page, evt);
			List<ItemReturnArrivedResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemReturnArrivedResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true
					: false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	/**
	 * 退货详情接口
	 */
	@RequestMapping("/getItemAppReturnDetail")
	public  void getItemAppReturnDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemAppReturnDetailResp respon=new ItemAppReturnDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String, Object> map=itemReturnDetailService.getReturnDetailById(evt.getId());
			List<Map<String,Object>> material=itemReturnDetailService.getMaterialList(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			respon.setMaterialDetail(material);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 更改收货接口
	 */
	@RequestMapping("/updateItemReturnReceive")
	public void updateItemReturnReceive(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UpdateItemReturnReceiveEvt evt=new UpdateItemReturnReceiveEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			String materialDetail=json.getString("materialDetail");
			json.put("materialDetail", null);
			evt=(UpdateItemReturnReceiveEvt) JSONObject.toBean(json, UpdateItemReturnReceiveEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			ItemReturn itemReturn=itemReturnService.get(ItemReturn.class, evt.getId());
			if(itemReturn!=null){
				itemReturn.setStatus("4");
				itemReturn.setLastUpdate(new Date());
				itemReturn.setGetDate(new Date());
				itemReturnService.updateItemReturnReceive(itemReturn,evt,materialDetail);
				returnJson(respon, response, msg, respon, request,interfaceLog);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	/**
	 * 供选择待退货材料列表
	 */
	@RequestMapping("/getReturnMaterial")
	public void getReturnMaterial(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemAppReturnDetailResp respon=new ItemAppReturnDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> material=itemReturnDetailService.getReturnMaterial(evt.getId());
			respon.setMaterialDetail(material);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 退货到货保存接口
	 */
	@RequestMapping("/saveItemReturnArrive")
	public void saveItemReturnArrive(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemReturnArriveSaveEvt evt=new ItemReturnArriveSaveEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemReturnArriveSaveEvt) JSONObject.toBean(json, ItemReturnArriveSaveEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			ItemReturnArrive itemReturnArrive=new ItemReturnArrive();
			itemReturnArrive.setAddress(evt.getAddress());
			itemReturnArrive.setArriveDate(new Date());
			itemReturnArrive.setDriverCode(evt.getCode());
			itemReturnArrive.setDriverRemark(evt.getRemark());
			itemReturnArrive.setReturnNo(evt.getId());
			itemReturnArrive.setLastUpdate(new Date());
			itemReturnArrive.setNo(itemReturnArriveService.getSeqNo("titemReturnArr"));
			itemReturnService.saveItemReturnArrive(itemReturnArrive,evt.getPk(),evt.getPhotoNames(),evt.getId());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	/**
	 * erp用
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemReturnArrivedPhotoList")
	public void getItemReturnArrivedPhotoList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemAppSendPhotoResp respon=new ItemAppSendPhotoResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list=itemAppSendPhotoService.getPhotoList(evt.getId(),evt.getPortalType());
			List<Map<String,Object>> reList = new ArrayList<Map<String,Object>>();
			if (list!=null && list.size()>0){
				for (Map<String,Object> m : list){
					String str = String.valueOf(m.get("photoName"));
					if (StringUtils.isNotBlank(str)){
						Map<String,Object> map=new HashMap<String,Object>();
						ItemAppSendUploadRule rule = new ItemAppSendUploadRule(str,"");
						map.put("src",FileUploadUtils.getFileUrl(rule.getOriginalPath()));
						map.put("photoName", str);		
						reList.add(map);
					}
				}
			}
			respon.setPhotoList(reList);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 删除退货图片接口
	 * 只删除文件
	 */
	@RequestMapping("/deleteItemAppReturnPhoto")
	public void deleteItemAppReturnPhoto(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new  JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
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
			String[] arr = evt.getId().split(",");
			int i = 0;
			for(String str:arr){
				ItemAppSendUploadRule rule = new ItemAppSendUploadRule(str, "");
				FileUploadUtils.deleteFile(rule.getOriginalPath());
				i++;
			}
			if (i==0){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
