package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.GetRoomInfoEvt;
import com.house.home.client.service.evt.ModifyConfirmQueryEvt;
import com.house.home.client.service.evt.PrjProgCheckAddEvt;
import com.house.home.client.service.evt.PrjProgCheckQueryEvt;
import com.house.home.client.service.evt.PrjProgCheckUpdateEvt;
import com.house.home.client.service.evt.PrjProgPhotoListEvt;
import com.house.home.client.service.evt.UpdateSiteModifyEvt;
import com.house.home.client.service.evt.UploadPhotoEvt;
import com.house.home.client.service.resp.BaseListQueryResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.GetRoomInfoResp;
import com.house.home.client.service.resp.ModifyConfirmQueryResp;
import com.house.home.client.service.resp.PrjProgCheckQueryResp;
import com.house.home.client.service.resp.PrjProgCheckResp;
import com.house.home.client.service.resp.PrjProgPhotoDetailResp;
import com.house.home.client.service.resp.PrjProgPhotoResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjProgCheckService;
import com.house.home.service.project.PrjProgPhotoService;

/**
 * 工地巡检相关的接口
 * 
 * @author
 * 
 */
@RequestMapping("/client/prjProgCheck")
@Controller
public class ClientPrjProgCheckController extends ClientBaseController {
	@Autowired
	private PrjProgCheckService prjProgCheckService;
	@Autowired
	private PrjProgPhotoService prjProgPhotoService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private CustomerService customerService;

	/**
	 * 查看工地巡检列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjProgCheckList")
	public void getPrjProgCheckList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgCheckQueryEvt evt = new PrjProgCheckQueryEvt();
		BasePageQueryResp<PrjProgCheckQueryResp> respon = new BasePageQueryResp<PrjProgCheckQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgCheckQueryEvt) JSONObject.toBean(json, PrjProgCheckQueryEvt.class);
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
			prjProgCheckService.findPageByLastUpdatedBy(page, evt.getId(),evt.getAddress(),evt.getIsModify(),evt.getCustCode());
			List<PrjProgCheckQueryResp> listBean = BeanConvertUtil.mapToBeanList(
					page.getResult(), PrjProgCheckQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 新增工地巡检接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addPrjProgCheck")
	public void addPrjProgCheck(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgCheckAddEvt evt = new PrjProgCheckAddEvt();
		BaseResponse respon = new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgCheckAddEvt) JSONObject.toBean(json, PrjProgCheckAddEvt.class);
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
			StringBuilder returnInfo = new StringBuilder();
			List<Map<String,Object>> list = prjProgCheckService.isBegin(evt.getCustCode(), evt.getPrjItem());
			if(StringUtils.isBlank(evt.getErrPosi())){
				if(evt.getLatitude() != null && evt.getLongitude() != null){
					//第一次定位判断是否在范围内
					Customer customer=customerService.get(Customer.class,evt.getCustCode());
					if(customer.getBuilderCode()!=null){
						Builder builder=builderService.get(Builder.class,customer.getBuilderCode());
						Integer offset = (builder == null || builder.getOffset() == null || builder.getOffset() == 0) ? PosiBean.LIMIT_DISTANCE : builder.getOffset();
						if(builder.getLongitude()!=null&&builder.getLongitude().longValue()!=0){
							if(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude())>offset){
								returnInfo.append("当前地点离楼盘位置过远<br/><br/>");
							}
						}
					}
				}
			}
			if(evt.getPrjItem() != null && !"16".equals(evt.getPrjItem()) && StringUtils.isBlank(evt.getIsUpPrjProg())){
				if( list.size() > 0){
					returnInfo.append("项目经理尚未填写("+evt.getPrjItemDescr()+")进度开始时间<br/><br/>");
				}
			}
			if(returnInfo.length() > 0){
				respon.setReturnCode("100006");
				returnInfo.append("是否继续?");
				respon.setReturnInfo(returnInfo.toString());
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getErrPosi())) evt.setErrPosi("0");
			if(StringUtils.isBlank(evt.getIsUpPrjProg())){
				evt.setIsUpPrjProg("0");
			}
			PrjProgCheck prjProgCheck = new PrjProgCheck();
			BeanUtilsEx.copyProperties(prjProgCheck, evt);
			prjProgCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			
			ProgCheckPlanDetail progCheckPlanDetail = new ProgCheckPlanDetail();
			progCheckPlanDetail.setPk(evt.getPk());
			progCheckPlanDetail.setAppCZY(evt.getAppCZY());
			progCheckPlanDetail.setStatus(evt.getStatus());
			progCheckPlanDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			progCheckPlanDetail.setCigaNum(evt.getCigaNum() == null?0:evt.getCigaNum());
			try{
				prjProgCheckService.addPrjProgCheck(prjProgCheck,progCheckPlanDetail,evt.getIsExecute(),list.size()==0?null:list.get(0).get("PK").toString());
			}catch (Exception e) {
				respon.setReturnCode("400001");
				respon.setReturnInfo("新增工地巡检失败");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 编辑工地巡检接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePrjProgCheck")
	public void updatePrjProgCheck(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgCheckUpdateEvt evt = new PrjProgCheckUpdateEvt();
		BaseResponse respon = new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgCheckUpdateEvt) JSONObject.toBean(json, PrjProgCheckUpdateEvt.class);
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
			PrjProgCheck prjProgCheck = prjProgCheckService.get(PrjProgCheck.class, evt.getNo());
			prjProgCheck.setPrjItem(evt.getPrjItem());
			prjProgCheck.setRemarks(evt.getRemarks());
			prjProgCheck.setModifyTime(evt.getModifyTime());
			prjProgCheck.setBuildStatus(evt.getBuildStatus());
			prjProgCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjProgCheck.setSafeProm(evt.getSafeProm());
			prjProgCheck.setVisualProm(evt.getVisualProm());
			prjProgCheck.setArtProm(evt.getArtProm());
			prjProgCheck.setActionLog("EDIT");
			prjProgCheck.setLastUpdate(new Date());
			prjProgCheck.setCigaNum(evt.getCigaNum());
			prjProgCheckService.updatePrjProgCheck(prjProgCheck, evt);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 工地整改接口
	 */
	@RequestMapping("/updateSiteModify")
	public void updateSiteModify(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UpdateSiteModifyEvt evt=new UpdateSiteModifyEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(UpdateSiteModifyEvt) JSONObject.toBean(json, UpdateSiteModifyEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			PrjProgCheck prjProgCheck=prjProgCheckService.get(PrjProgCheck.class, evt.getNo());
			if(prjProgCheck!=null){
				prjProgCheck.setRemarks(evt.getRemarks());
				prjProgCheck.setModifyComplete("0");
				prjProgCheck.setLastUpdate(new Date());
				prjProgCheck.setCompDate(null);
				prjProgCheckService.updateSiteModify(prjProgCheck);
				returnJson(respon, response, msg, respon, request,interfaceLog);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 查看工地巡检详情接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgCheck")
	public void getPrjProgCheck(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		BaseGetEvt evt = new BaseGetEvt();
		PrjProgCheckResp respon = new PrjProgCheckResp();
		
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
			Map<String,Object> map = prjProgCheckService.getByNo(evt.getId(), getUserContext(request).getCzybh());
			BeanConvertUtil.mapToBean(map, respon);
			List<String> list = prjProgPhotoService.getPhotoListByRefNo(evt.getId(),"3");
			List<String> photoList = new ArrayList<String>();
			List<String> photoNameList = new ArrayList<String>();
			if (list!=null && list.size()>0){
				for (String str : list){
					if (StringUtils.isNotBlank(str)){
						PrjProgNewUploadRule rule = new PrjProgNewUploadRule(str, str.substring(0, 5));
						String path = FileUploadUtils.getFileUrl(rule.getFullName());
						photoList.add(path);
						photoNameList.add(str);
					}
				}
			}
			respon.setPhotoList(photoList);
			respon.setPhotoNameList(photoNameList);
			//整改图片
			List<String> list_modify = prjProgPhotoService.getPhotoListByRefNo(evt.getId(),"4");
			List<String> photoList_modify = new ArrayList<String>();
			List<String> photoNameList_modify = new ArrayList<String>();
			if (list_modify!=null && list_modify.size()>0){
				for (String str : list_modify){
					if (StringUtils.isNotBlank(str)){
						PrjProgNewUploadRule rule = new PrjProgNewUploadRule(str, str.substring(0, 5));
						String path = FileUploadUtils.getFileUrl(rule.getFullName());
						photoList.add(path);
						photoList_modify.add(path);
						photoNameList_modify.add(str);
					}
				}
			}
			respon.setModifyPhotoList(photoList_modify);
			respon.setModifyPhotoNameList(photoNameList_modify);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 工程巡检图片上传(app)
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadPrjProgCheck")
	public void uploadPrjProgCheck(HttpServletRequest request,
			HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String no = multipartFormData.getParameter("no");
			String custCode = multipartFormData.getParameter("custCode");
			String prjItem = multipartFormData.getParameter("prjItem");
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
						photo.setType("3");//工地巡检
						photo.setRefNo(no);
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
	 * 获取工地巡检图片列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgCheckPhotoList")
	public void getPrjProgCheckPhotoList(HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgPhotoListEvt evt = new PrjProgPhotoListEvt();
		BaseListQueryResp<PrjProgPhotoResp> respon = new BaseListQueryResp<PrjProgPhotoResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgPhotoListEvt) JSONObject.toBean(json,PrjProgPhotoListEvt.class);
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
			List<String> list = prjProgPhotoService.getPhotoListByRefNo(evt.getId(),evt.getType());
			List<PrjProgPhotoResp> listBean = new ArrayList<PrjProgPhotoResp>();
			if (list!=null && list.size()>0){
				for (String str : list){
					if (StringUtils.isNotBlank(str)){
						FileUploadRule rule = PrjProgNewUploadRule.fromUploadedFile(str);
						String path = FileUploadUtils.getFileUrl(rule.getFullName());
						PrjProgPhotoResp prjProgPhotoResp = new PrjProgPhotoResp();
						prjProgPhotoResp.setPhotoName(path);
						listBean.add(prjProgPhotoResp);
						prjProgPhotoResp = null;
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
	 * 获取工地巡检单个图片接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrjProgCheckPhoto")
	public void getPrjProgCheckPhoto(HttpServletRequest request,
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
				PrjProgNewUploadRule rule = new PrjProgNewUploadRule(prjProgPhoto.getPhotoName(), prjProgPhoto.getPhotoName().substring(0, 5));
				String path = FileUploadUtils.getFileUrl(rule.getFullName());
				respon.setPhotoName(path);
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 获取整改次数列表
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getIsModifyList")
	public void getIsModifyList(HttpServletRequest request,
			HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjProgCheckQueryEvt evt = new PrjProgCheckQueryEvt();
		BasePageQueryResp<PrjProgCheckQueryResp> respon = new BasePageQueryResp<PrjProgCheckQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjProgCheckQueryEvt) JSONObject.toBean(json, PrjProgCheckQueryEvt.class);
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
			prjProgCheckService.getModifyList(page, evt);
			List<PrjProgCheckQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), PrjProgCheckQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 获取整整改验收列表
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getModifyConfirmList")
	public void getModifyConfirmList(HttpServletRequest request,
			HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ModifyConfirmQueryEvt evt = new ModifyConfirmQueryEvt();
		BasePageQueryResp<ModifyConfirmQueryResp> respon = new BasePageQueryResp<ModifyConfirmQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (ModifyConfirmQueryEvt) JSONObject.toBean(json, ModifyConfirmQueryEvt.class);
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
			prjProgCheckService.getModifyConfirmList(page,evt);
			List<ModifyConfirmQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), ModifyConfirmQueryResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/getRoomInfo")
	public void getRoomInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetRoomInfoEvt evt = new GetRoomInfoEvt();
		GetRoomInfoResp respon = new GetRoomInfoResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetRoomInfoEvt) JSONObject.toBean(json, GetRoomInfoEvt.class);
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
			Map<String, Object> map = prjProgCheckService.getRoomInfo(evt.getCustCode(), getUserContext(request).getCzybh());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
