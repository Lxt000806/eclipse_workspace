package com.house.home.client.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.RepositoryEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.CompletionReportResp;
import com.house.home.client.service.resp.DocDetailResp;
import com.house.home.client.service.resp.RepositoryResp;
import com.house.home.entity.basic.Doc;
import com.house.home.entity.basic.DocAttachment;
import com.house.home.entity.basic.DocDownloadLog;
import com.house.home.entity.basic.GuideTopic;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.RepositoryService;

@RequestMapping("/client/repository")
@Controller
public class ClientRepositoryController extends ClientBaseController{
	
	@Autowired
	private RepositoryService repositoryService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getGuideTopicList")
	public void getGuideTopicList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		BasePageQueryResp<RepositoryResp> respon=new BasePageQueryResp<RepositoryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);		
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
			repositoryService.getGuideTopicList(page, evt);
			List<RepositoryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), RepositoryResp.class);
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
	
	@RequestMapping("/doUpdateVisitCnt")
	public void updateVisitCnt(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		CompletionReportResp respon=new CompletionReportResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			GuideTopic guideTopic = repositoryService.get(GuideTopic.class, evt.getPk());
			guideTopic.setVisitCnt(guideTopic.getVisitCnt()+1);
			repositoryService.update(guideTopic);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDocList")
	public void getDocList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		BasePageQueryResp<RepositoryResp> respon=new BasePageQueryResp<RepositoryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);		
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
			repositoryService.getDocList(page, evt);
			List<RepositoryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), RepositoryResp.class);
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
	
	@RequestMapping("/getDocDetail")
	public void getDocDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		DocDetailResp respon=new DocDetailResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);		
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String, Object>> docAttachmentList = repositoryService.getDocAttachmentList(evt.getPk());
			if(docAttachmentList.size()>0){
				for (int i = 0; i < docAttachmentList.size(); i++) {
					docAttachmentList.get(i).put("url", FileUploadUtils.getFileUrl(docAttachmentList.get(i).get("attachment").toString()));
				}
			}
			Doc doc = repositoryService.get(Doc.class, evt.getPk());
			respon.setDocName(doc.getDocName());
			respon.setDocCode(doc.getDocCode());
			respon.setDocVersion(doc.getDocVersion());
			respon.setEnableDate(doc.getEnableDate());
			respon.setExpiredDate(doc.getExpiredDate());
			respon.setBriefDesc(doc.getBriefDesc());
			respon.setKeyWords(doc.getKeyWords());
			respon.setDocAttachmentList(docAttachmentList);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/doUpdateDownloadCnt")
	public void doUpdateDownloadCnt(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		CompletionReportResp respon=new CompletionReportResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = this.getJson(request,msg,json,respon);
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Doc doc = repositoryService.get(Doc.class, evt.getDocPk());
			doc.setDownloadCnt(doc.getDownloadCnt()+1);
			
			DocAttachment docAttachment = repositoryService.get(DocAttachment.class, evt.getPk());
			docAttachment.setDownloadCnt(docAttachment.getDownloadCnt()+1);
			
			DocDownloadLog docDownloadLog = new DocDownloadLog();
			docDownloadLog.setDocPK(evt.getDocPk());
			docDownloadLog.setAttachmentPK(evt.getPk());
			docDownloadLog.setDownloadDate(new Date());
			docDownloadLog.setDownloadCZY(evt.getCzybh());
			
			System.out.println("239");
			repositoryService.update(doc);
			repositoryService.update(docAttachment);
			repositoryService.save(docDownloadLog);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getGuideTopicListForWorker")
	public void getGuideTopicListForWorker(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		BasePageQueryResp<RepositoryResp> respon=new BasePageQueryResp<RepositoryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);		
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
			Worker worker = repositoryService.get(Worker.class, evt.getCzybh());
			evt.setWorkType12(worker.getWorkType12());
			repositoryService.getGuideTopicListForWorker(page, evt);
			List<RepositoryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), RepositoryResp.class);
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
	@RequestMapping("/getDocListForWorker")
	public void getDocListForWorker(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RepositoryEvt evt=new RepositoryEvt();
		BasePageQueryResp<RepositoryResp> respon=new BasePageQueryResp<RepositoryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);			
			evt=(RepositoryEvt) JSONObject.toBean(json, RepositoryEvt.class);		
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
			Worker worker = repositoryService.get(Worker.class, evt.getCzybh());
			evt.setWorkType12(worker.getWorkType12());
			repositoryService.getDocListForWorker(page, evt);
			List<RepositoryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), RepositoryResp.class);
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
