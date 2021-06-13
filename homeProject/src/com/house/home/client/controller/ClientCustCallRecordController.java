package com.house.home.client.controller;

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
import com.house.framework.commons.fileUpload.impl.CustCallRecordUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.bean.DoSaveCallListBean;
import com.house.home.client.service.evt.DoSaveCallListEvt;
import com.house.home.client.service.evt.GetCallListOneMonthEvt;
import com.house.home.client.service.evt.GetDeviceCallRecordInfoEvt;
import com.house.home.client.service.evt.UploadCustCallRecordEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.DoSaveCallListResp;
import com.house.home.client.service.resp.GetCallListOneMonthResp;
import com.house.home.client.service.resp.GetDeviceCallRecordInfoResp;
import com.house.home.client.service.resp.UploadCustCallRecordResp;
import com.house.home.entity.basic.CallRecord;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.CustCallRecordService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.MobileModelInfoService;
@RequestMapping("/client/custCallRecord")
@Controller
public class ClientCustCallRecordController extends ClientBaseController {
	
	@Autowired
	private CustCallRecordService custCallRecordService;
	@Autowired
	private MobileModelInfoService mobileModelInfoService;
	@Autowired
	private CzybmService czybmService;

	@RequestMapping("/getDeviceRecordInfo")
	public void getDeviceRecordInfo(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetDeviceCallRecordInfoEvt evt=new GetDeviceCallRecordInfoEvt();
		GetDeviceCallRecordInfoResp respon=new GetDeviceCallRecordInfoResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetDeviceCallRecordInfoEvt)JSONObject.toBean(json,GetDeviceCallRecordInfoEvt.class);
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
			
			//根据产商和型号查找相关信息
			Map<String, Object> map = this.mobileModelInfoService.getMobileModelInfo(evt.getManufacturer(), evt.getModel(), evt.getVersion());
			
			//若匹配不到相关信息,则记录用户产商以及型号信息
			if (map == null) {
				this.mobileModelInfoService.saveMobileModelInfo(evt.getManufacturer(), evt.getModel(), getUserContext(request).getCzybh());
			}
			
			//匹配不到信息,或者录音文件获取为空,则根据产商获取相关信息
			if (map == null || map.get("CallRecordPath") == null || StringUtils.isBlank(map.get("CallRecordPath").toString())) {
				map = this.mobileModelInfoService.getMobileModelInfo(evt.getManufacturer(), "", "");
			}
			
			if(map != null){
				BeanConvertUtil.mapToBean(map, respon);
			}
			
			Long maxConDate = DateUtil.addDate(new Date(), 1).getTime();
			UserContext uc = getUserContext(request);
			if(czybmService.hasGNQXByCzybh(uc.getCzybh(), "0826", "查看")){
				maxConDate = this.custCallRecordService.getLastCustConByCZY(uc.getCzybh());
			}
			
			respon.setBeginDate(maxConDate);
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	

	@RequestMapping("/doSaveCallList")
	public void doSaveCallList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoSaveCallListEvt evt=new DoSaveCallListEvt();
		DoSaveCallListResp respon=new DoSaveCallListResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = getJson(request, msg, json, respon);
			evt = (DoSaveCallListEvt)JSONObject.toBean(json,DoSaveCallListEvt.class);
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
			
			//解码
			if (StringUtils.isNotBlank(evt.getCallList())) {
				evt.setCallList(homeDecorBase64Decrypt(evt.getCallList()));
			}
			
			Long maxConDate = DateUtil.addDate(new Date(), 1).getTime();
			UserContext uc = getUserContext(request);
			if(czybmService.hasGNQXByCzybh(uc.getCzybh(), "0826", "查看")){
				maxConDate = evt.getBeginTime() != null && evt.getBeginTime() > 0 ? evt.getBeginTime() : this.custCallRecordService.getLastCustConByCZY(uc.getCzybh());
			}

			Result result = new Result();
			if (evt.getCallListLength() > 0) {
				result = this.custCallRecordService.doSaveCallList(XmlConverUtil.jsonToXmlNoHead(evt.getCallList()), uc.getCzybh(), maxConDate);
			} else {
				// 未执行保存存储过程,则默认执行成功
				result.setCode("1");
			}
			
			List<Map<String, Object>> list = this.custCallRecordService.getUnUploadCallRecordList(getUserContext(request).getCzybh());
			
			List<DoSaveCallListBean> unUploadCallRecordList = BeanConvertUtil.mapToBeanList(list, DoSaveCallListBean.class);
			
			if (!result.isSuccess()) {
				respon.setReturnCode(result.getCode());
				respon.setReturnInfo(result.getInfo());
			}
			
			respon.setUnUploadRecordList(unUploadCallRecordList);
			
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/uploadCustCallRecord")
	public  void uploadCustCallRecord(HttpServletRequest request,HttpServletResponse response){

		StringBuilder msg = new StringBuilder();
		UploadCustCallRecordEvt evt = new UploadCustCallRecordEvt();
		UploadCustCallRecordResp respon = new UploadCustCallRecordResp();

		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			Integer pk = Integer.parseInt(multipartFormData.getParameter("pk"));
			evt.setPk(pk);
			// 获取多个上传文件
			CallRecord callRecord = null;
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
            	callRecord = this.custCallRecordService.get(CallRecord.class, evt.getPk());
                CustCallRecordUploadRule rule = new CustCallRecordUploadRule(callRecord.getName(), 
                        		getUserContext(request).getCzybh());
                
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                        		rule.getFileName(),rule.getFilePath());
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "录音文件上传失败：内部转存错误");
                    return;
                }
            }
			
			callRecord.setStatus("1");
			this.custCallRecordService.update(callRecord);
			
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCallListOneMonth")
	public void getCallListOneMonth(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCallListOneMonthEvt evt=new GetCallListOneMonthEvt();
		BasePageQueryResp<GetCallListOneMonthResp> respon=new BasePageQueryResp<GetCallListOneMonthResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCallListOneMonthEvt)JSONObject.toBean(json,GetCallListOneMonthEvt.class);
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
			
			Date date = DateUtil.startOfTheMonth(new Date());
			
			this.custCallRecordService.getCallListOneMonth(page, getUserContext(request).getCzybh(), date);
			
			List<GetCallListOneMonthResp> list = BeanConvertUtil.mapToBeanList(page.getResult(), GetCallListOneMonthResp.class);
			
			for (int i = 0; i < list.size(); i++) {
				String callRecord = list.get(i).getCallRecord();
				//录音文件是否传至阿里云,是则返回阿里云地址
				if ("1".equals(list.get(i).getStatus())) {
					callRecord = OssConfigure.cdnAccessUrl + "/custCallRecord/" + callRecord.substring(0, 5) + "/" + callRecord;
					callRecord = homeDecorBase64Encrypt(callRecord);
				} else if (StringUtils.isNotBlank(list.get(i).getMobileFilePath())) {
					callRecord = homeDecorBase64Encrypt(list.get(i).getMobileFilePath());
				}
				list.get(i).setMobileFilePath("");
				list.get(i).setCallRecord(callRecord);
			}
			
			respon.setDatas(list);
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
	
	
}
