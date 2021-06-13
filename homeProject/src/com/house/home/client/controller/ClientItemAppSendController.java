package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.ItemAppSendUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.ItemAppSendArrivedQueryEvt;
import com.house.home.client.service.evt.ItemAppSendQueryEvt;
import com.house.home.client.service.evt.ItemAppSendUpdateEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ItemAppSendArrivedDetailResp;
import com.house.home.client.service.resp.ItemAppSendArrivedQueryResp;
import com.house.home.client.service.resp.ItemAppSendDetailResp;
import com.house.home.client.service.resp.ItemAppSendPhotoResp;
import com.house.home.client.service.resp.ItemAppSendQueryResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.service.driver.ItemAppSendPhotoService;
import com.house.home.service.driver.ItemAppSendService;
import com.house.home.service.driver.ItemReturnArriveService;
/**
 * 司机送货模块相关接口
 * @author Administrator
 *
 */
@RequestMapping("/client/driverItemAppSend")
@Controller
public class ClientItemAppSendController extends ClientBaseController {
	@Autowired
	private ItemAppSendService itemAppSendService;
	@Autowired
	private ItemAppSendPhotoService itemAppSendPhotoService;
	@Autowired
	private ItemReturnArriveService itemReturnArriveService;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getItemAppSendList")
	public void getItemAppSendList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppSendQueryEvt evt = new ItemAppSendQueryEvt();
		BasePageQueryResp<ItemAppSendQueryResp> respon = new BasePageQueryResp<ItemAppSendQueryResp>();	
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppSendQueryEvt) JSONObject.toBean(json, ItemAppSendQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象合法字段验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			itemAppSendService.findPageByDriver(page, evt.getId(),evt.getAddress());
			List<ItemAppSendQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppSendQueryResp.class);
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
	 * 到货列表接口
	 */
	@RequestMapping("/getItemAppSendArrivedList")
	public void getItemAppSendArrivedList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppSendArrivedQueryEvt evt = new ItemAppSendArrivedQueryEvt();
		BasePageQueryResp<ItemAppSendArrivedQueryResp> respon = new BasePageQueryResp<ItemAppSendArrivedQueryResp>();	
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppSendArrivedQueryEvt) JSONObject.toBean(json, ItemAppSendArrivedQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象合法字段验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			itemAppSendService.findPageByDriverArrived(page, evt.getId(),evt.getAddress());
			List<ItemAppSendArrivedQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),ItemAppSendArrivedQueryResp.class);
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
	 * 到货详情接口
	 * @param request
	 */
	@RequestMapping("/getItemAppSendArrivedDetail")
	public  void getItemAppSendArrivedDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		ItemAppSendArrivedDetailResp respon=new ItemAppSendArrivedDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map=itemAppSendService.getSendArrivedDetailById(evt.getId());
			List<Map<String,Object>> material=itemAppSendService.getMaterialList(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			respon.setMaterialDetail(material);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 送货详情接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getItemAppSendDetail")
	public void getItemAppSendDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		ItemAppSendDetailResp respon=new ItemAppSendDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象合法字段验证
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map=itemAppSendService.getSendDetailById(evt.getId());
			List<Map<String,Object>> material=itemAppSendService.getMaterialList(evt.getId());
			BeanConvertUtil.mapToBean(map, respon);
			respon.setMaterialDetail(material);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 送退货图片上传
	 * 送货模块图片保存到数据库，退货模块不保存到数据库
	 */
	@RequestMapping("/itemAppSendUpload")
	public void itemAppSendUpload(HttpServletRequest request,HttpServletResponse response){
			StringBuilder msg=new StringBuilder();
			UploadPhotoResp respon = new UploadPhotoResp();
			try {
				MultipartFormData multipartFormData = new MultipartFormData(request);
				String sendNo = multipartFormData.getParameter("sendNo");
				String type = multipartFormData.getParameter("type");
				// 获取多个上传文件
				List<String> fileNameList = new ArrayList<String>();
				List<String> PhotoPathList = new ArrayList<String>();
				// 遍历上传文件写入磁盘
				List<FileItem> attachments = multipartFormData.getFileItems();
	            for (FileItem attatchment : attachments) {
	                ItemAppSendUploadRule rule = new ItemAppSendUploadRule(attatchment.getName(), 
	                        		getDriverUserContext(request).getCzybh().trim());
	                
	                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
	                        		rule.getFileName(),rule.getFilePath());
	                if (!uploadResult.isSuccess()) {
	                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
	                    return;
	                }
	                fileNameList.add(rule.getFileName());
	                PhotoPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
	            }

				if(StringUtils.isNotBlank(sendNo)){
					if(fileNameList!=null && fileNameList.size()>0){
						for(String str:fileNameList){
							ItemSendPhoto photo=new ItemSendPhoto();
							photo.setActionLog("Add");
							photo.setExpired("F");
							photo.setType(type);
							photo.setLastUpdate(new Date());
							photo.setLastUpdatedBy(getDriverUserContext(request).getCzybh());
							photo.setPhotoName(str);
							photo.setSendNo(sendNo);
							photo.setIsSendYun("1");
							photo.setSendDate(new Date());
							itemAppSendPhotoService.save(photo);
						}
					}
				}
				respon.setPhotoPathList(PhotoPathList);
				respon.setPhotoNameList(fileNameList);
				returnJson(respon,response,msg,respon,request,null);	
			} catch (Exception e) {
				e.printStackTrace();
				super.exceptionHandle(respon, response, msg, request,null);
			}
	}
	/**
	 * 查看送货图片接口
	 */
	@RequestMapping("/getItemAppSendPhotoList")
	public  void getItemAppSendPhotoList(HttpServletRequest request,HttpServletResponse response){
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
			List<Map<String,Object>> list=null;
			if("2".equals(evt.getPortalType())){
				List<Map<String,Object>> itemReturnArriveList=itemReturnArriveService.findByReturnNo(evt.getId());
				list=new ArrayList<Map<String,Object>>();
				for(Map<String,Object> map:itemReturnArriveList){
					list.addAll(itemAppSendPhotoService.getPhotoList(map.get("No").toString(),evt.getPortalType()));
				}
			}else{
				list=itemAppSendPhotoService.getPhotoList(evt.getId(),evt.getPortalType());
			}
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
	 * 删除图片接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteItemAppSendPhoto")
	public void deleteItemAppSendPhoto(HttpServletRequest request,HttpServletResponse response){
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
				ItemSendPhoto photo=itemAppSendPhotoService.getByPhotoName(str);
				ItemAppSendUploadRule rule = new ItemAppSendUploadRule(str, "");
//				String path=getItemAppSendPhotoUploadPath();
//				File file = new File(path+str);
				if(photo!=null){
					itemAppSendPhotoService.delete(photo);
					FileUploadUtils.deleteFile(rule.getOriginalPath());
					i++;
				}
//				if (file.exists()){
//					file.delete();
//					i++;
//				}
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
	/**
	 * 到货保存接口
	 */
	@RequestMapping("/updateItemAppSend")
	public  void updateItemAppSend(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ItemAppSendUpdateEvt evt=new ItemAppSendUpdateEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ItemAppSendUpdateEvt) JSONObject.toBean(json, ItemAppSendUpdateEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			itemAppSendService.updateItemAppSend(evt.getId(),evt.getAddress(), evt.getRemark());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
