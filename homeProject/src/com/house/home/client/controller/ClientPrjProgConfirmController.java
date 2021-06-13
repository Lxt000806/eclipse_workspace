package com.house.home.client.controller;

import java.text.SimpleDateFormat;
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
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.ConfirmCustomFiledEvt;
import com.house.home.client.service.evt.CustomerQueryEvt;
import com.house.home.client.service.evt.GetConfirmInfoEvt;
import com.house.home.client.service.evt.GetPrjItemDescr;
import com.house.home.client.service.evt.GetSiteConfirmDetailEvt;
import com.house.home.client.service.evt.GetSiteConfirmStatusEvt;
import com.house.home.client.service.evt.PrjProgConfirmEvt;
import com.house.home.client.service.evt.SavePrjProgConfirmEvt;
import com.house.home.client.service.evt.WorkerItemAppEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ConfirmCustomFiledResp;
import com.house.home.client.service.resp.CustomerResp;
import com.house.home.client.service.resp.FixDutyResp;
import com.house.home.client.service.resp.GetConfirmInfoResp;
import com.house.home.client.service.resp.GetSiteConfirmStatusResp;
import com.house.home.client.service.resp.PrjProgConfirmResp;
import com.house.home.client.service.resp.PrjProgDetailQueryResp;
import com.house.home.client.service.resp.SiteConfirmDetailResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.client.service.resp.WorkerItemAppResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.PrjProgConfirm;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.WorkType12;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjProgCheckService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.project.PrjProgPhotoService;
@Controller
@RequestMapping("/client/prjProgConfirm")
public class ClientPrjProgConfirmController extends ClientBaseController {
	@Autowired
	private PrjProgConfirmService prjProgConfirmService;
	@Autowired
	private PrjProgPhotoService prjProgPhotoService;
	@Autowired
	private PrjProgCheckService prjProgCheckService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BuilderService builderService;
	@RequestMapping("/getSiteConfirmList")
	public void getSiteConfirmList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		PrjProgConfirmEvt evt = new PrjProgConfirmEvt();
		BasePageQueryResp<PrjProgConfirmResp> respon=new BasePageQueryResp<PrjProgConfirmResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjProgConfirmEvt) JSONObject.toBean(json, PrjProgConfirmEvt.class);
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
			/**
			 * 接口和工人APP共用,利用标记区分
			 * fromPageFlag 标志为1时,为工人APP获取列表,需控制验收工人及客户号
			 * add by zzr 2018/04/27
			 */
			if("1".equals(evt.getFromPageFlag())){
				CustWorker custWorker = prjProgConfirmService.get(CustWorker.class, evt.getCustWkPk());
				if(custWorker != null){
					evt.setCustCode(custWorker.getCustCode());
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo("执行出错");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
			}
			prjProgConfirmService.getSiteConfirmListById(page, evt.getId(),evt.getCustCode(), evt.getFromPageFlag());
			List<PrjProgConfirmResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),PrjProgConfirmResp.class);
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
	@RequestMapping("/getSiteConfirmDetail")
	public void getSiteConfirmDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetSiteConfirmDetailEvt evt=new GetSiteConfirmDetailEvt();
		SiteConfirmDetailResp respon=new SiteConfirmDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetSiteConfirmDetailEvt) JSONObject.toBean(json, GetSiteConfirmDetailEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = null;
			WorkType12 workType12 = null;
			if("workerApp".equals(evt.getPlatform())){
				custWorker = prjProgConfirmService.get(CustWorker.class, evt.getCustWkPk());
				workType12 = prjProgConfirmService.get(WorkType12.class, custWorker.getWorkType12());
			}
			Map<String, Object> map=prjProgConfirmService.getSiteConfirmDetail(evt.getId(), evt.getPlatform(), 
																			   "workerApp".equals(evt.getPlatform())?getUserContext(request).getCzybh():"", 
																			   "workerApp".equals(evt.getPlatform())?custWorker.getCustCode():"",
																			   "workerApp".equals(evt.getPlatform())?workType12.getConfPrjItem():"");
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getPrjItemDescr")
	public void getPrjItemDescr(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetPrjItemDescr evt = new GetPrjItemDescr();
		BasePageQueryResp<PrjProgDetailQueryResp> respon = new BasePageQueryResp<PrjProgDetailQueryResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetPrjItemDescr) JSONObject.toBean(json, GetPrjItemDescr.class);
			interfaceLog.setContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			if(!"1".equals(evt.getFromPageFlag()) && StringUtils.isBlank(evt.getPrjRole())){
				evt.setPrjRole(prjProgConfirmService.get(Czybm.class, getUserContext(request).getCzybh().trim()).getPrjRole());
			}
			prjProgConfirmService.getPrjItemDescr(page, evt.getId(),evt.getPrjRole(),evt.getPrjItem());
			List<PrjProgDetailQueryResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(),PrjProgDetailQueryResp.class);
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
	
	@RequestMapping("/getWorkerAppPrjItemDescr")
	public void getWorkerAppPrjItemDescr(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetPrjItemDescr evt = new GetPrjItemDescr();
		BasePageQueryResp<PrjProgDetailQueryResp> respon = new BasePageQueryResp<PrjProgDetailQueryResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetPrjItemDescr) JSONObject.toBean(json, GetPrjItemDescr.class);
			interfaceLog.setContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			if(!"1".equals(evt.getFromPageFlag()) && StringUtils.isBlank(evt.getPrjRole())){
				evt.setPrjRole(prjProgConfirmService.get(Czybm.class, getUserContext(request).getCzybh().trim()).getPrjRole());
			}
			prjProgConfirmService.getWorkerAppPrjItemDescr(page, evt.getId(),evt.getPrjRole(),evt.getPrjItem(),evt.getWorkerApp());
			List<PrjProgDetailQueryResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(),PrjProgDetailQueryResp.class);
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
	 * 图片上传
	 */
	@RequestMapping("/prjProgPhotoUpload")
	public void prjProgPhotoUpload(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		UploadPhotoResp respon = new UploadPhotoResp();
		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
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
			
			respon.setPhotoPathList(fileRealPathList);
			respon.setPhotoNameList(fileNameList);
			returnJson(respon,response,msg,respon,request,null);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}
	/**
	 * 验收保存接口
	 */
	@RequestMapping("/savePrjProgConfirm")
	public void savePrjProgConfirm(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		SavePrjProgConfirmEvt evt=new SavePrjProgConfirmEvt();
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
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("endDate") && json.get("endDate") != null) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt=(SavePrjProgConfirmEvt) JSONObject.toBean(json, SavePrjProgConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			if("2".equals(evt.getAppType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理APP，工地验收功能已经停用！");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			StringBuilder returnInfo = new StringBuilder();
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
			if(returnInfo.length() > 0){
				respon.setReturnCode("100006");
				returnInfo.append("是否继续?");
				respon.setReturnInfo(returnInfo.toString());
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getErrPosi())) evt.setErrPosi("0");
			PrjProgConfirm prjProgConfirm=new PrjProgConfirm();
			prjProgConfirm.setNo(prjProgConfirmService.getSeqNo("tPrjProgConfirm"));
			prjProgConfirm.setCustCode(evt.getCustCode());
			prjProgConfirm.setPrjItem(evt.getPrjItem());
			prjProgConfirm.setDate(new Date());
			prjProgConfirm.setRemarks(evt.getRemarks());
			prjProgConfirm.setAddress(evt.getAddress());
			prjProgConfirm.setIsPass(evt.getIsPass());
			prjProgConfirm.setErrPosi(evt.getErrPosi());
			prjProgConfirm.setPrjWorkable(evt.getPrjWorkable());
			if(evt.getIsPushCust()==null){
				prjProgConfirm.setIsPushCust(evt.getIsPass());
			}else{
				prjProgConfirm.setIsPushCust(evt.getIsPushCust());
			}
			prjProgConfirm.setLastUpdate(new Date());
			prjProgConfirm.setFromCzy(evt.getFromCzy());
			if("1".equals(evt.getFromPageFlag())){
				Map<String, Object> map = prjProgConfirmService.getSiteConfirmStatus(evt.getCustCode(), evt.getPrjItem());
				if(map != null){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该工地已验收通过");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
				prjProgConfirm.setLastUpdatedBy("1");
				prjProgConfirm.setWorkerCode(getUserContext(request).getCzybh());
			}else{
				prjProgConfirm.setLastUpdatedBy(getUserContext(request).getCzybh());
			}
			if("16".equals(evt.getPrjItem())){
				prjProgConfirm.setAppCheck(evt.getAppCheck());
			}else{
				prjProgConfirm.setAppCheck("0");
			}
			prjProgConfirm.setExpired("F");
			prjProgConfirm.setActionLog("ADD");
			if("1".equals(prjProgConfirm.getIsPass())) prjProgConfirm.setPrjLevel(evt.getPrjLevel());
			try{
				prjProgConfirmService.savePrjProgConfirm(prjProgConfirm, evt.getPhotoNameList(),evt.getPortalType(),evt.getEndDate(),evt.getConfirmCustomFiledValue());
			}catch (Exception e) {
				respon.setReturnCode("400001");
				respon.setReturnInfo("新增工地验收失败");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	/**
	 * 删除图片接口
	 */
	@RequestMapping("/prjProgPhotoDelete")
	public void prjProgPhotoDelete(HttpServletRequest request,HttpServletResponse response){
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
				PrjProgPhoto photo=prjProgPhotoService.getByPhotoName(str);
				PrjProgNewUploadRule rule = new PrjProgNewUploadRule(str,str.substring(0, 5));
				if(photo!=null){
					prjProgPhotoService.delete(photo);
					FileUploadUtils.deleteFile(rule.getOriginalPath());
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
	@RequestMapping("/confirmPass")
	public void confirmPass(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
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
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			PrjProgCheck prjProgCheck=prjProgCheckService.get(PrjProgCheck.class, evt.getId());
			UserContext uc=this.getUserContext(request);
			prjProgCheck.setConfirmCzy(uc.getCzybh());
			prjProgCheck.setConfirmDate(new Date());
			prjProgCheck.setLastUpdate(new Date());
			prjProgCheck.setLastUpdatedBy(uc.getCzybh());
			prjProgCheck.setActionLog("EDIT");
			prjProgCheckService.update(prjProgCheck);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/getUnPassConfirmInfo")
	public void getUnPassConfirmInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		GetConfirmInfoEvt evt=new GetConfirmInfoEvt();
		GetConfirmInfoResp respon=new GetConfirmInfoResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetConfirmInfoEvt) JSONObject.toBean(json, GetConfirmInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Map<String,Object> map = prjProgConfirmService.getConfirmInfo(evt.getCustCode(), evt.getPrjItem());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjConfirmByPage")
	public void getPrjConfirmByPage(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		CustomerQueryEvt evt = new CustomerQueryEvt();
		BasePageQueryResp<CustomerResp> respon = new BasePageQueryResp<CustomerResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustomerQueryEvt) JSONObject.toBean(json, CustomerQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
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
			if(StringUtils.isBlank(evt.getPrjRole())){
				evt.setPrjRole(prjProgConfirmService.get(Czybm.class, getUserContext(request).getCzybh().trim()).getPrjRole());
			}
			prjProgConfirmService.getPrjConfirmByPage(page,evt.getId(),evt.getAddress(),evt.getPrjItem(),evt.getPrjRole(),evt.getRegionCode(),evt.getIsLeaveProblem());
			List<CustomerResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), CustomerResp.class);
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
	 * 工人申请验收接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerAppPrjConfirmByPage")
	public void getWorkerAppPrjConfirmByPage(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		CustomerQueryEvt evt = new CustomerQueryEvt();
		BasePageQueryResp<CustomerResp> respon = new BasePageQueryResp<CustomerResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustomerQueryEvt) JSONObject.toBean(json, CustomerQueryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt, "evt");
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
			if(StringUtils.isBlank(evt.getPrjRole())){
				evt.setPrjRole(prjProgConfirmService.get(Czybm.class, getUserContext(request).getCzybh().trim()).getPrjRole());
			}
			prjProgConfirmService.getWorkerAppPrjConfirmByPage(page,evt.getId(),evt.getAddress(),evt.getPrjItem(),evt.getPrjRole());
			List<CustomerResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), CustomerResp.class);
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
	 * 验收保存接口
	 */
	@RequestMapping("/doUpdatePrjProgConfirm")
	public void doUpdatePrjProgConfirm(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		SavePrjProgConfirmEvt evt=new SavePrjProgConfirmEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("endDate")) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt=(SavePrjProgConfirmEvt) JSONObject.toBean(json, SavePrjProgConfirmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}

			if("2".equals(evt.getAppType())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("项目经理APP，工地验收功能已经停用！");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getPrjItem())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请先选择施工阶段!");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			if("1".equals(evt.getIsPass()) && StringUtils.isBlank(evt.getPrjLevel())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请选择验收级别!");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<String> list = prjProgPhotoService.getPhotoListByRefNo(evt.getNo(), "2");
			if(StringUtils.isBlank(evt.getPhotoNameList()) && (list == null || list.size()==0)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("请先拍照!");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			if(!"1".equals(evt.getFromPageFlag())){
				evt.setLastUpdatedBy(getUserContext(request).getCzybh());
			}else{
				if(("0".equals(prjProgConfirmService.get(PrjProgConfirm.class, evt.getNo()).getIsPass()) && "1".equals(evt.getIsPass()))
						&& prjProgConfirmService.existCustPrjItemPass(evt.getCustCode(), evt.getPrjItem())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该工地已验收通过");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
			}
			prjProgConfirmService.updatePrjProgConfirm(evt);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}

	/**
	 * 获取工地验收状态
	 */
	@RequestMapping("/getSiteConfirmStatus")
	public void getSiteConfirmStatus(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetSiteConfirmStatusEvt evt=new GetSiteConfirmStatusEvt();
		GetSiteConfirmStatusResp respon=new GetSiteConfirmStatusResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("endDate")) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt=(GetSiteConfirmStatusEvt) JSONObject.toBean(json, GetSiteConfirmStatusEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = prjProgConfirmService.get(CustWorker.class, evt.getCustWkPk());
			WorkType12 workType12 = prjProgConfirmService.get(WorkType12.class, custWorker.getWorkType12());
			Map<String, Object> map = prjProgConfirmService.getSiteConfirmStatus(custWorker.getCustCode(), workType12.getConfPrjItem());
			if(map != null){
				respon.setExists("1");
			}else{
				respon.setExists("0");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	 /**
	 * 获取工地验收状态
	 */
	@RequestMapping("/getAllowItemApp")
	public void getAllowItemApp(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		WorkerItemAppResp respon=new WorkerItemAppResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("endDate")) 
			json.put("endDate", sdf.parse(json.get("endDate").toString()));
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			String allowItemApp = prjProgConfirmService.getAllowItemApp(evt.getId());
			respon.setStatus(allowItemApp);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getRegionList")
	public void getRegionList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		PrjProgConfirmEvt evt = new PrjProgConfirmEvt();
		BasePageQueryResp<FixDutyResp> respon = new BasePageQueryResp<FixDutyResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjProgConfirmEvt) JSONObject.toBean(json, PrjProgConfirmEvt.class);
			interfaceLog.setContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
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
			prjProgCheckService.getRegionList(page);
			List<FixDutyResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(),FixDutyResp.class);
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
	 
	@RequestMapping("/getConfirmCustomFiledList")
	public void getConfirmCustomFiledList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		ConfirmCustomFiledEvt evt=new ConfirmCustomFiledEvt();
		BasePageQueryResp<ConfirmCustomFiledResp> respon=new BasePageQueryResp <ConfirmCustomFiledResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ConfirmCustomFiledEvt) JSONObject.toBean(json, ConfirmCustomFiledEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = prjProgConfirmService.getConfirmCustomFiledList(evt.getPrjItem(), evt.getPrjProgConfirmNo());
			List<ConfirmCustomFiledResp> listBean = BeanConvertUtil.mapToBeanList(list, ConfirmCustomFiledResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
