package com.house.home.client.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
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
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DownLoadFiles;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
//import com.house.framework.commons.utils.oss.OssManager;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.BaseQueryEvt;
import com.house.home.client.service.evt.GetNoSendItemEvt;
import com.house.home.client.service.evt.JobOrderQueryEvt;
import com.house.home.client.service.evt.PersonMessageQueryEvt;
import com.house.home.client.service.evt.PrjProDateEvt;
import com.house.home.client.service.evt.PrjProgAlarmEvt;
import com.house.home.client.service.evt.PrjProgPhotoAddEvt;
import com.house.home.client.service.evt.PrjProgPhotoEvt;
import com.house.home.client.service.evt.PrjProgUpdateEvt;
import com.house.home.client.service.evt.UploadPhotoEvt;
import com.house.home.client.service.evt.prjProgCurrentEvt;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GetNoSendItemResp;
import com.house.home.client.service.resp.JobOrderQueryResp;
import com.house.home.client.service.resp.PersonMessageQueryResp;
import com.house.home.client.service.resp.PrjProDateResp;
import com.house.home.client.service.resp.PrjProgAlarmResp;
import com.house.home.client.service.resp.PrjProgDetailQueryResp;
import com.house.home.client.service.resp.PrjProgDetailResp;
import com.house.home.client.service.resp.PrjProgPhotoDetailResp;
import com.house.home.client.service.resp.PrjProgPhotoResp;
import com.house.home.client.service.resp.PrjProgQueryResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.client.service.resp.prjProgCurrentResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.service.project.PrjProgPhotoService;
import com.house.home.service.project.PrjProgService;

/**
 * 进度相关的接口
 * 
 * @author
 * 
 */
@RequestMapping("/client/prjProg")
@Controller
public class ClientPrjProgController extends ClientBaseController {
	@Autowired
	private PrjProgService prjProgService;
	@Autowired
	private PrjProgPhotoService prjProgPhotoService;

	/**
	 * 查看进度列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjProgList")
	public void getPrjProgList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt = new BaseQueryEvt();
		BasePageQueryResp<PrjProgQueryResp> respon = new BasePageQueryResp<PrjProgQueryResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt) JSONObject.toBean(json, BaseQueryEvt.class);
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
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			prjProgService.findPageByProjectMan(page, evt.getId());
			List<PrjProgQueryResp> listBean = BeanConvertUtil.mapToBeanList(
					page.getResult(), PrjProgQueryResp.class);
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
	 * 查看进度明细列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjProgDetailList")
	public void getPrjProgDetailList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseQueryEvt evt = new BaseQueryEvt();
		BasePageQueryResp<PrjProgDetailQueryResp> respon = new BasePageQueryResp<PrjProgDetailQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseQueryEvt) JSONObject.toBean(json, BaseQueryEvt.class);
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
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			prjProgService.findPageByCustCode(page, evt.getId());
			List<PrjProgDetailQueryResp> listBean = BeanConvertUtil
					.mapToBeanList(page.getResult(),
							PrjProgDetailQueryResp.class);
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
 * 获取进度工期
 */
	@RequestMapping("/getPrjProgDate")
	public void getPrjProgDate(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		PrjProDateEvt evt=new PrjProDateEvt();
		PrjProDateResp respon=new PrjProDateResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjProDateEvt) JSONObject.toBean(json, PrjProDateEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String, Object> map = prjProgService.getDelayAndRemain(evt.getId(), evt.getCode());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 查看进度详情接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgDetail")
	public void getPrjProgDetail(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		PrjProgDetailResp respon = new PrjProgDetailResp();
		
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
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String, Object> map = prjProgService.getPrjProgByPk(Integer
					.parseInt(evt.getId()));
			BeanConvertUtil.mapToBean(map, respon);
			
			
			List<Map<String,Object>> list = prjProgPhotoService.getPhotoList(
					respon.getCustCode(), respon.getPrjItem());
			List<String> reList = new ArrayList<String>();
			List<String> reNameList = new ArrayList<String>();
			if (list!=null && list.size()>0){
				for (Map<String,Object> m : list){
					String str = String.valueOf(m.get("photoName"));
					if (StringUtils.isNotBlank(str)){
						PrjProgNewUploadRule rule = new PrjProgNewUploadRule(str, str.substring(0, 5));
						String path = FileUploadUtils.getFileUrl(rule.getFullName());
						reNameList.add(str);
						reList.add(path);
					}
				}
			}
			respon.setPhotoList(reList);
			respon.setPhotoNameList(reNameList);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	/**
	 * 修改进度接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePrjProg")
	public void updatePrjProg(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgUpdateEvt evt = new PrjProgUpdateEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("beginDate")) 
			json.put("beginDate", sdf.parse(json.get("beginDate").toString()));
			if(json.containsKey("endDate")) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt = (PrjProgUpdateEvt) JSONObject.toBean(json,
					PrjProgUpdateEvt.class);
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
			PrjProg prjProg = prjProgService.get(PrjProg.class, evt.getPk());
			if (prjProg != null) {
				if (evt.getBeginDate() == null && evt.getEndDate() == null) {
					respon.setReturnCode("400001");
					respon.setReturnInfo("开始时间和结束时间不能同时为空");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
				if (evt.getBeginDate() != null) {
					prjProg.setBeginDate(evt.getBeginDate());
					prjProg.setLastUpdate(new Date());
					prjProg.setLastUpdatedBy(getUserContext(request).getCzybh());
					Result result = prjProgService.updatePrjProgForProc(prjProg.getPk(), "1", evt.getBeginDate(), 
							getUserContext(request).getCzybh(),evt.getCustCode(),evt.getPrjItem());
					if (!result.isSuccess()){
						respon.setReturnCode("400001");
						respon.setReturnInfo("修改进度失败");
						returnJson(respon, response, msg, respon, request,interfaceLog);
						return;
					}
				}
				if (evt.getEndDate() != null) {
					prjProg.setEndDate(evt.getEndDate());
					prjProg.setLastUpdate(new Date());
					prjProg.setLastUpdatedBy(getUserContext(request).getCzybh());
					Result result = prjProgService.updatePrjProgForProc(prjProg.getPk(), "2", evt.getEndDate(), 
							getUserContext(request).getCzybh(),null,null);
					if (!result.isSuccess()){
						respon.setReturnCode("400001");
						respon.setReturnInfo("修改进度失败");
						returnJson(respon, response, msg, respon, request,interfaceLog);
						return;
					}
				}
			} else {
				respon.setReturnCode("300102");
			}

			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	/**
	 * 增加项目进度图片接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addPrjProgPhoto")
	public void addPrjProgPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgPhotoAddEvt evt = new PrjProgPhotoAddEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgPhotoAddEvt) JSONObject.toBean(json,
					PrjProgPhotoAddEvt.class);
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
			PrjProgPhoto prjProgPhoto = new PrjProgPhoto();
			BeanUtilsEx.copyProperties(prjProgPhoto, evt);
			prjProgPhoto.setLastUpdate(new Date());
			prjProgPhoto.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjProgPhoto.setActionLog("Add");
			prjProgPhoto.setExpired("F");
			prjProgPhoto.setIsPushCust("1");
			prjProgPhoto.setIsSendYun("1");
			prjProgPhoto.setSendDate(new Date());
			prjProgPhotoService.save(prjProgPhoto);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/prjProgUpload")
	public void prjProgUpload(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			json = StringUtil.queryStringToJSONObject(request);
			String photoContent = null;
			if (json.has("photoContent")){
				photoContent = json.getString("photoContent");
			}
			String url = request.getParameter("url");
			if (StringUtils.isBlank(photoContent)
					&& StringUtils.isNotBlank(url)) {
				byte[] data = null;
				// 读取图片字节数组
				InputStream in = null;
				try {
					in = new FileInputStream(url);
					data = new byte[in.available()];
					in.read(data);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (in != null) {
						in.close();
					}
				}
				// 对字节数组Base64编码
				photoContent = Base64.encodeBase64URLSafeString(data);
				json.put("photoContent", photoContent);
			}

			evt = (UploadPhotoEvt) JSONObject
					.toBean(json, UploadPhotoEvt.class);
			// 对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,null);
				return;
			}

			String[] imgStrs = evt.getPhotoContent().split("[|]");
			List<String> list = new ArrayList<String>();
			for (String imgStr : imgStrs) {
				String str = generateImage(imgStr, evt.getType());
				list.add(str);
			}
			respon.setPhotoNameList(list);

			returnJson(respon, response, msg, respon, request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}

	/**
	 * 图片上传(新)
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/prjProgUploadNew")
	public void prjProgUploadNew(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			String str = request.getParameter("type");
			Integer type = null;
			if (StringUtils.isNotBlank(str)) {
				type = Integer.parseInt(str);
			} else {
				type = 0;
			}
			String path = "";
			switch (type.intValue()) {
			case 0:
				path = getPrjProgPhotoUploadPath("");
				break;
			case 1:
				path = getOtherPhotoUploadPath();
				break;
			}
			// 获取上传的文件集合
			Part part1 = request.getPart("file1");
			Part part2 = request.getPart("file2");
			Part part3 = request.getPart("file3");
			String file1Descr = request.getParameter("file1Descr");
			String file2Descr = request.getParameter("file2Descr");
			String file3Descr = request.getParameter("file3Descr");
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			FileUploadServerMgr.writePart(part1, file1Descr, path, list);
			FileUploadServerMgr.writePart(part2, file2Descr, path, list);
			FileUploadServerMgr.writePart(part3, file3Descr, path, list);
			// Collection<Part> parts = request.getParts();
			respon.setPhotoList(list);

			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}

	/**
	 * 图片上传(app)
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/prjProgUploadApp")
	public void prjProgUploadApp(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String prjItem = multipartFormData.getParameter("prjItem");
			String custCode = multipartFormData.getParameter("custCode");
			String lastUpdatedBy = multipartFormData.getParameter("lastUpdatedBy");
			String photoPath = "";
			String fileNameNew = "";
			String firstFileName = "";
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> fileRealPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
            	firstFileName = attatchment.getName().substring(
            			attatchment.getName().lastIndexOf("\\") + 1);
				String formatName = firstFileName
						.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
				fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
                PrjProgNewUploadRule rule =
                        new PrjProgNewUploadRule(fileNameNew, fileNameNew.substring(0,5));
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                		rule.getFileName(),rule.getFilePath());
                fileNameList.add(fileNameNew);
                fileRealPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
            }
            
			if (StringUtils.isNotBlank(prjItem) && StringUtils.isNotBlank(custCode)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						PrjProgPhoto photo = new PrjProgPhoto();
						photo.setCustCode(custCode);
						photo.setPrjItem(prjItem);
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						if (StringUtils.isNotBlank(lastUpdatedBy)){//delphi调用接口用
							photo.setLastUpdatedBy(lastUpdatedBy);
						}else{
							photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						}
						photo.setActionLog("Add");
						photo.setExpired("F");
						photo.setType("1");
						photo.setIsPushCust("1");
						photo.setIsSendYun("1");
						photo.setSendDate(new Date());
						prjProgPhotoService.save(photo);
					}
				}
			}
			respon.setPhotoPathList(fileRealPathList);
			respon.setPhotoNameList(fileNameList);
			
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}

	/**
	 * 查看工程进度提醒模板详情接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgAlarmDetail")
	public void getPrjProgAlarmDetail(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgAlarmEvt evt = new PrjProgAlarmEvt();
		PrjProgAlarmResp respon = new PrjProgAlarmResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgAlarmEvt) JSONObject.toBean(json,
					PrjProgAlarmEvt.class);
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
			Map<String, Object> map = prjProgService.getPrjProgAlarm(
					evt.getCode(), evt.getPrjItem(), evt.getDayType());
			BeanConvertUtil.mapToBean(map, respon);
			String str = respon.getMsgTextTemp();
			if (StringUtils.isNotBlank(str)) {
				// 替换{0}
				// str = MessageFormat.format(str, "");
				str = respon.getAddress() + "：" + str;
			}
			respon.setMsgTextTemp(str);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 获取工程进度图片列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgPhotoList")
	public void getPrjProgPhotoList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgPhotoEvt evt = new PrjProgPhotoEvt();
		BaseListQueryResp<PrjProgPhotoResp> respon = new BaseListQueryResp<PrjProgPhotoResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgPhotoEvt) JSONObject.toBean(json,
					PrjProgPhotoEvt.class);
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
			List<Map<String,Object>> list = prjProgPhotoService.getPhotoList(evt.getCustCode(), evt.getPrjItem());
			List<PrjProgPhotoResp> listBean = BeanConvertUtil.mapToBeanList(list, PrjProgPhotoResp.class);
			if (listBean!=null && listBean.size()>0){
				for (PrjProgPhotoResp m : listBean){
					String str = m.getPhotoName();
					if (StringUtils.isNotBlank(str)){
						FileUploadRule rule = PrjProgNewUploadRule.fromUploadedFile(str);
						String path = FileUploadUtils.getFileUrl(rule.getFullName());
						m.setPhotoName(path);
					}
				}
			}
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 获取工程进度单个图片接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgPhoto")
	public void getPrjProgPhoto(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		PrjProgPhotoDetailResp respon = new PrjProgPhotoDetailResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (BaseGetEvt) JSONObject.toBean(json,
					BaseGetEvt.class);
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
			PrjProgPhoto prjProgPhoto = prjProgPhotoService.get(PrjProgPhoto.class, Integer.parseInt(evt.getId()));
			if (prjProgPhoto!=null && StringUtils.isNotBlank(prjProgPhoto.getPhotoName())){
				PrjProgNewUploadRule rule = new PrjProgNewUploadRule(prjProgPhoto.getPhotoName(), 
						prjProgPhoto.getPhotoName().substring(0, 5));
				String path = FileUploadUtils.getFileUrl(rule.getFullName());
				respon.setPhotoName(path);
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getPrjProgCurrent")
	public void getPrjProgCurrent(HttpServletRequest request,HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		prjProgCurrentEvt evt = new prjProgCurrentEvt();
		prjProgCurrentResp respon = new prjProgCurrentResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (prjProgCurrentEvt) JSONObject.toBean(json, prjProgCurrentEvt.class);
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
			Map<String,Object> map = prjProgService.getPrjProgCurrentById( evt.getCustCode());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 下载工程进度图片列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downloadPrjProgPhotoList")
	public void downloadPrjProgPhotoList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgPhotoEvt evt = new PrjProgPhotoEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgPhotoEvt) JSONObject.toBean(json,
					PrjProgPhotoEvt.class);
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
			List<Map<String,Object>> list = prjProgPhotoService.getPhotoList(evt.getCustCode(), evt.getPrjItem());
			List<String> fileList = new ArrayList<String>();
			String filePath = "";
			if (list!=null && list.size()>0){
				for (Map<String,Object> map : list){
					String str = String.valueOf(map.get("photoName"));
					FileUploadRule rule = PrjProgNewUploadRule.fromUploadedFile(str);
					String photoUrl = FileUploadUtils.getFileUrl(rule.getFullName());
					fileList.add(photoUrl);
				    if (StringUtils.isBlank(filePath)){
				    	filePath = "d:/"+evt.getCustCode()+"/";
				    }
			    	DownLoadFiles.saveUrlAs(photoUrl, filePath + str);
				}
			}
			respon.setReturnInfo("下载的文件位置："+filePath);
			//ZipUtil.downLoadFiles(fileList, request, response);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/getNoSendItem")
	public void getNoSendItem(HttpServletRequest request,HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetNoSendItemEvt evt = new GetNoSendItemEvt();
		BaseListQueryResp<GetNoSendItemResp> respon = new BaseListQueryResp<GetNoSendItemResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetNoSendItemEvt) JSONObject.toBean(json, GetNoSendItemEvt.class);
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
			Xtcs xtcs = prjProgService.get(Xtcs.class, "NoSendTip");
			List<GetNoSendItemResp> list = null;
			if("1".equals(xtcs.getQz())){
				list = BeanConvertUtil.mapToBeanList(prjProgService.getNoSendItem(evt), GetNoSendItemResp.class);
			}else{
				list = new ArrayList<GetNoSendItemResp>();
			}
			respon.setDatas(list);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getJobOrderList")
	public void getJobOrderList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		JobOrderQueryEvt evt=new JobOrderQueryEvt();
		BasePageQueryResp<JobOrderQueryResp> respon=new BasePageQueryResp<JobOrderQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (JobOrderQueryEvt)JSONObject.toBean(json,JobOrderQueryEvt.class);
			
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
			page.setPageNo(-1);
			page.setPageSize(1000);
			prjProgService.getJobOrderList(page, evt.getCustCode(), evt.getPrjItem(), evt.getWorkType12());
			List<JobOrderQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), JobOrderQueryResp.class);
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
	@RequestMapping("/getAlarmPrjItemList")
	public void getAlarmPrjItemList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		JobOrderQueryEvt evt=new JobOrderQueryEvt();
		BasePageQueryResp<JobOrderQueryResp> respon=new BasePageQueryResp<JobOrderQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (JobOrderQueryEvt)JSONObject.toBean(json,JobOrderQueryEvt.class);
			
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
			page.setPageNo(-1);
			page.setPageSize(1000);
			prjProgService.getAlarmPrjItemList(page, evt.getCustCode());
			List<JobOrderQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), JobOrderQueryResp.class);
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
	@RequestMapping("/getAlarmWorkType12List")
	public void getAlarmWorkType12List(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		JobOrderQueryEvt evt=new JobOrderQueryEvt();
		BasePageQueryResp<JobOrderQueryResp> respon=new BasePageQueryResp<JobOrderQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			
			json = StringUtil.queryStringToJSONObject(request);
			evt = (JobOrderQueryEvt)JSONObject.toBean(json,JobOrderQueryEvt.class);
			
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
			page.setPageNo(-1);
			page.setPageSize(1000);
			prjProgService.getAlarmWorkType12List(page, evt.getCustCode());
			List<JobOrderQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), JobOrderQueryResp.class);
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
