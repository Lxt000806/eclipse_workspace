package com.house.home.client.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.PrjProblemUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.client.service.evt.DoSavePrjProblemEvt;
import com.house.home.client.service.evt.DoUpdatePrjProblemEvt;
import com.house.home.client.service.evt.GetPrjProblemListEvt;
import com.house.home.client.service.evt.GetPrjPromTypeLitsEvt;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustWorkerAppResp;
import com.house.home.client.service.resp.GetPrjProblemListResp;
import com.house.home.client.service.resp.GetPrjProblemResp;
import com.house.home.client.service.resp.PrjProblemPhotoResp;
import com.house.home.client.service.resp.PrjPromDeptResp;
import com.house.home.client.service.resp.PrjPromTypeResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.design.DesignDemoPic;
import com.house.home.entity.project.PrjProblem;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.query.PrjProblemPic;
import com.house.home.entity.query.SignInPic;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustWorkerAppService;
import com.house.home.service.project.PrjProblemService;

import java.util.List;
@RequestMapping("/client/prjProblem")
@Controller
public class ClientPrjProblemController extends ClientBaseController {

	@Autowired
	private PrjProblemService prjProblemService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjProblemList")
	public void getPrjProblemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		GetPrjProblemListEvt evt=new GetPrjProblemListEvt();
		BasePageQueryResp<GetPrjProblemListResp> respon=new BasePageQueryResp<GetPrjProblemListResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetPrjProblemListEvt) JSONObject.toBean(json, GetPrjProblemListEvt.class);
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
			PrjProblem prjProblem = new PrjProblem();
			prjProblem.setAddress(evt.getAddress());
			prjProblem.setStatus(evt.getStatus());
			prjProblem.setAppCZY(getUserContext(request).getCzybh());
			prjProblemService.getPrjProblemList(page, prjProblem);
			List<GetPrjProblemListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetPrjProblemListResp.class);
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

	@RequestMapping("/getPrjPromDeptList")
	public void getPrjPromDeptList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		BaseEvt evt=new BaseEvt();
		BaseListQueryResp<PrjPromDeptResp> respon=new BaseListQueryResp<PrjPromDeptResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetPrjProblemListEvt) JSONObject.toBean(json, GetPrjProblemListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			List<Map<String, Object>> list = prjProblemService.getPrjPromDeptList();
			List<PrjPromDeptResp> listBean=BeanConvertUtil.mapToBeanList(list, PrjPromDeptResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getPrjPromTypeList")
	public void getPrjPromTypeList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		GetPrjPromTypeLitsEvt evt=new GetPrjPromTypeLitsEvt();
		BaseListQueryResp<PrjPromTypeResp> respon=new BaseListQueryResp<PrjPromTypeResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetPrjPromTypeLitsEvt) JSONObject.toBean(json, GetPrjPromTypeLitsEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			List<Map<String, Object>> list = prjProblemService.getPrjPromTypeList(evt.getPrjPromDept());
			List<PrjPromTypeResp> listBean=BeanConvertUtil.mapToBeanList(list, PrjPromTypeResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/doSavePrjProblem")
	public void doSavePrjProblem(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		DoSavePrjProblemEvt evt=new DoSavePrjProblemEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DoSavePrjProblemEvt) JSONObject.toBean(json, DoSavePrjProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			String no = prjProblemService.getSeqNo("TPRJPROBLEM");
			PrjProblem prjProblem = new PrjProblem();
			prjProblem.setNo(no);
			prjProblem.setPromDeptCode(evt.getPromDeptCode());
			prjProblem.setPromPropCode(evt.getPromPropCode());
			prjProblem.setPromTypeCode(evt.getPromTypeCode());
			prjProblem.setRemarks(evt.getRemarks());
			prjProblem.setCustCode(evt.getCustCode());
			prjProblem.setAppCZY(getUserContext(request).getCzybh());
			prjProblem.setLastUpdatedBy(prjProblem.getAppCZY());
			prjProblem.setIsBringStop(evt.getIsBringStop());
			String result = prjProblemService.doSavePrjProblem(prjProblem);
			
			// 保存图片记录
			if(StringUtils.isNotBlank(evt.getPhotoString())){
				String[] arr = evt.getPhotoString().split(",");
				for (String photoName: arr){
					PrjProblemPic prjProblemPic = new PrjProblemPic();
					prjProblemPic.setPhotoName(photoName);
					prjProblemPic.setNo(prjProblem.getNo());
					prjProblemPic.setLastUpdate(new Date());
					prjProblemPic.setLastUpdatedBy(getUserContext(request).getCzybh());
					prjProblemPic.setActionLog("ADD");
					prjProblemPic.setExpired("F");
					prjProblemPic.setIsSendYun("1");
					prjProblemPic.setSendDate(new Date());
					prjProblemService.save(prjProblemPic);
					prjProblemPic = null;
				}
			}
			
			if(StringUtils.isNotBlank(result)){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result);
			}else{
				respon.setReturnInfo("保存成功");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	
	@RequestMapping("/getPrjProblem")
	public void getPrjProblem(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		BaseGetEvt evt=new BaseGetEvt();
		GetPrjProblemResp respon=new GetPrjProblemResp();
		
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

			Map<String, Object> map = prjProblemService.getPrjProblem(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdatePrjProblem")
	public void doUpdatePrjProblem(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		DoUpdatePrjProblemEvt evt=new DoUpdatePrjProblemEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(DoUpdatePrjProblemEvt) JSONObject.toBean(json, DoUpdatePrjProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			PrjProblem prjProblem = new PrjProblem();
			prjProblem.setPromDeptCode(evt.getPromDeptCode());
			prjProblem.setPromPropCode(evt.getPromPropCode());
			prjProblem.setPromTypeCode(evt.getPromTypeCode());
			prjProblem.setRemarks(evt.getRemarks());
			prjProblem.setAppCZY(getUserContext(request).getCzybh());
			prjProblem.setNo(evt.getNo());
			prjProblem.setOpSign(evt.getOpSign());
			prjProblem.setStatus(evt.getStatus());
			prjProblem.setIsBringStop(evt.getIsBringStop());
			prjProblem.setPhotoString(evt.getPhotoString());
			String result = prjProblemService.doUpdatePrjProblem(prjProblem);
			
			if(StringUtils.isNotBlank(result)){
				respon.setReturnCode("400001");
				respon.setReturnInfo(result);
			}else{
				respon.setReturnInfo("保存成功");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadPrjProblemPhoto")
	public void uploadSignInPhoto(HttpServletRequest request, HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoResp respon = new UploadPhotoResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());

		try {
			
			List<String> fileNameList = new ArrayList<String>();
			List<String> photoPathList = new ArrayList<String>();
			
			MultipartFormData multipartFormData = new MultipartFormData(request);
			
			FileItem fileItem = multipartFormData.getFileItem();
			
			PrjProblemUploadRule prjProblemUploadRule = new PrjProblemUploadRule(fileItem.getName(), getUserContext(request).getCzybh().trim());
			Result result = FileUploadUtils.upload(fileItem.getInputStream(), prjProblemUploadRule.getFileName(),
					prjProblemUploadRule.getFilePath());
			
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("上传文件失败，原文件名:{}",fileItem.getName());
				respon.setReturnCode("400001");
				respon.setReturnInfo("上传文件失败，原文件名:{"+fileItem.getName()+"}");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			photoPathList.add(FileUploadUtils.getFileUrl(prjProblemUploadRule.getFullName()));
			fileNameList.add(prjProblemUploadRule.getFullName());
		
			respon.setPhotoPathList(photoPathList);
			respon.setPhotoNameList(fileNameList);
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}
	
	@RequestMapping("/doDelPrjProblemPic")
	public void doDelPrjProblemPic(HttpServletRequest request,HttpServletResponse response){
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
				PrjProblemPic photo=prjProblemService.getPicByName(str);// 1654614.jpg
				if(photo!=null){
					prjProblemService.delete(photo);
					i++;
				}
				Result result = FileUploadUtils.deleteFile(str);//prjProblem/1654614.jpg
				if(Result.SUCCESS_CODE.equals(result.getCode())){
					i++;
				}
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
	
	@RequestMapping("/getPhotoList")
	public void getPhotoList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		BaseGetEvt evt=new BaseGetEvt();
		BaseListQueryResp<PrjProblemPhotoResp> respon=new BaseListQueryResp<PrjProblemPhotoResp>();
		
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

			List<Map<String, Object>> list = prjProblemService.getPhotoList(evt.getId());
			List<PrjProblemPhotoResp> listBean=BeanConvertUtil.mapToBeanList(list, PrjProblemPhotoResp.class);
			for(int i = 0;i < listBean.size();i++){
				if("1".equals(listBean.get(i).getIsSendYun())){
					listBean.get(i).setSrc(OssConfigure.cdnAccessUrl+"/"+listBean.get(i).getPhotoName());
				}else{
					listBean.get(i).setSrc(FileUploadUtils.getBaseFileUrl(request)+listBean.get(i).getPhotoName());
				}
			}
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveConfirm")
	public void doSaveConfirm(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		DoUpdatePrjProblemEvt evt=new DoUpdatePrjProblemEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json = this.getJson(request,msg,json,respon);
			evt=(DoUpdatePrjProblemEvt) JSONObject.toBean(json, DoUpdatePrjProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			if("1".equals(evt.getRemarks()) || "0".equals(evt.getRemarks())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("本功能暂未启用");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			PrjProblem prjProblem=null;
			prjProblem=prjProblemService.get(PrjProblem.class, evt.getNo());
			prjProblem.setPromDeptCode(evt.getPromDeptCode());
			prjProblem.setPromPropCode(evt.getPromPropCode());
			prjProblem.setPromTypeCode(evt.getPromTypeCode());
			prjProblem.setRemarks(evt.getRemarks());
			prjProblem.setStatus("2");
			prjProblem.setConfirmCZY(getUserContext(request).getCzybh());
			prjProblem.setConfirmDate(new Date());
			prjProblem.setLastUpdate(new Date());
			prjProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjProblem.setActionLog("Edit");
			prjProblem.setIsBringStop(prjProblem.getIsBringStop());
			if("02".equals(prjProblem.getPromDeptCode())){
				prjProblem.setDealDate(new Date());
				prjProblem.setDealCZY(getUserContext(request).getCzybh());
				prjProblem.setStatus("4");
			}
			this.prjProblemService.update(prjProblem);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}	
	
	@RequestMapping("/doCancelPrjProblem")
	public void doCancelPrjProblem(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		DoUpdatePrjProblemEvt evt=new DoUpdatePrjProblemEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json = this.getJson(request,msg,json,respon);
			evt=(DoUpdatePrjProblemEvt) JSONObject.toBean(json, DoUpdatePrjProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			PrjProblem prjProblem = prjProblemService.get(PrjProblem.class, evt.getNo());
			prjProblem.setStatus("5");
			prjProblem.setCancelCZY(getUserContext(request).getCzybh());
			prjProblem.setCancelDate(new Date());
			prjProblem.setLastUpdate(new Date());
			prjProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjProblem.setActionLog("Edit");
			prjProblemService.update(prjProblem);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
