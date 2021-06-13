package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.home.entity.basic.*;
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
import com.house.framework.commons.fileUpload.impl.WorkerPicUploadRule;
import com.house.framework.commons.fileUpload.impl.WorkerSignPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.AddWokerCostEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.ClientWorkerProblemEvt;
import com.house.home.client.service.evt.CustWorkInfoEvt;
import com.house.home.client.service.evt.GetWaterAreaCfmEvt;
import com.house.home.client.service.evt.GetWorkPrjItemListEvt;
import com.house.home.client.service.evt.InstallInfoEvt;
import com.house.home.client.service.evt.WokerCostApplyEvt;
import com.house.home.client.service.evt.WorkerAppEvt;
import com.house.home.client.service.evt.WorkerItemAppEvt;
import com.house.home.client.service.evt.WorkerMessageEvt;
import com.house.home.client.service.evt.WorkerSignInEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustWorkInfoResp;
import com.house.home.client.service.resp.GetWaterAreaCfmResp;
import com.house.home.client.service.resp.GetWorkPrjItemListResp;
import com.house.home.client.service.resp.InstallInfoResp;
import com.house.home.client.service.resp.SiteConstructQueryResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.client.service.resp.WokerCostApplyResp;
import com.house.home.client.service.resp.WorkType2Resp;
import com.house.home.client.service.resp.WorkerMessageQueryResp;
import com.house.home.client.service.resp.WorkerPrjItemResp;
import com.house.home.client.service.resp.WorkerProblemResp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PrjItem2;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.basic.WorkerMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CupMeasure;
import com.house.home.entity.project.CustIntProg;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.WaterAreaCfm;
import com.house.home.entity.project.WorkSign;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.WorkerProblem;
import com.house.home.entity.project.WorkerProblemPic;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.WorkerMessageService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CupMeasureService;
import com.house.home.service.project.CustWorkerAppService;
import com.house.home.service.project.WorkerProblemService;
import com.house.home.service.project.WorkerService;

@RequestMapping("/client/worker")
@Controller
public class ClientWorkerAppController extends ClientBaseController{
	@Autowired
	private WorkerService workerService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private WorkerProblemService workerProblemService;
	@Autowired
	private WorkerMessageService workerMessageService;
	@Autowired
	private CupMeasureService cupMeasureService;
	@Autowired
	private CustWorkerAppService custWorkerAppService;
	
	/**
	 * 在建工地列表接口
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getSiteConstructList")
	public void getSiteConstructList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerAppEvt evt=new WorkerAppEvt();
		BasePageQueryResp<SiteConstructQueryResp> respon=new BasePageQueryResp<SiteConstructQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerAppEvt) JSONObject.toBean(json, WorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
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
			workerService.getSiteConstructList(page, evt.getCode(),evt.getStatus(),evt.getAddress());
			List<SiteConstructQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),SiteConstructQueryResp.class);
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
	@RequestMapping("/getWorkerPrjItem")
	public void getWorkerPrjItem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerAppEvt evt = new WorkerAppEvt();
		WorkerPrjItemResp respon = new WorkerPrjItemResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerAppEvt) JSONObject.toBean(json, WorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			Map<String,Object> map = workerService.getWorkerPrjItem(evt);
			BeanConvertUtil.mapToBean(map, respon);
			if("1".equals(respon.getCanSign())){
				respon.setSignInCmpFlag(false);
			}else{
				respon.setSignInCmpFlag(true);
			}
			Map<String,Object> maxSeqMap = workerService.getPrjItem2MaxSeq(evt.getWorkType12());
			String maxSeq = ""+maxSeqMap.get("maxSeq");
            if((Integer.parseInt(maxSeq) == respon.getSeq() && respon.getCrtDate() != null && 
            		!("0".equals(evt.getCustWorkerStatus()) && respon.getEndDays()<=30) ) || StringUtils.isBlank(respon.getCode())){
				//最后一个节点并且已经阶段完成页面显示信息
				respon.setPi2Descr("无");
				respon.setSignDescr("所有阶段已经完成；");
			}else{
				//如果签到次数未达到要求显示提示信息
				if(respon.getNeedDays() > 0){
					respon.setSignDescr("至少还需要签到 "+respon.getNeedDays()+" 天才能完成当前阶段；");
				}
			}
            if("1".equals(evt.getFromCostApply())){
            	WorkType2 workType2 = new WorkType2();
            	workType2 = workerService.get(WorkType2.class, evt.getWorkType2());
            	List<Map<String,Object>> list =workerService.getWokerCostApply(evt.getCustCode(),evt.getSalaryType(),evt.getCode(),null,null, evt.getWorkType2(),"");
            	List<Map<String, Object>> listWorkType12 = workerService.getWokerCostApplyWorkType12(evt.getCustCode(),evt.getSalaryType(),evt.getCode(),null,null, "", evt.getWorkType12());

				List<WokerCostApplyResp> listBean = BeanConvertUtil.mapToBeanList(list,WokerCostApplyResp.class);
				List<WokerCostApplyResp> listBeanWorkType12 = BeanConvertUtil.mapToBeanList(listWorkType12,WokerCostApplyResp.class);
				//限制申请次数
				List<Map<String,Object>> appList = workerService.getWokerCostApply(evt.getCustCode(),evt.getSalaryType(),evt.getCode(),null,null, "", evt.getWorkType12());
				Double costCount = 0.0;
            	if("0".equals(workType2.getSalaryCtrlType())){
    				for(int i=0;i<listBeanWorkType12.size();i++){
    					costCount += listBeanWorkType12.get(i).getRealAmount();//listBean.get(i).getWorkAppAmount();
    				}
            	}else{
    				for(int i=0;i<listBean.size();i++){
    					costCount += listBean.get(i).getRealAmount();//listBean.get(i).getWorkAppAmount();
    				}
            	}
				respon.setButtonDisable(true);
				respon.setCostCount(costCount);
				if(/*(*/respon.getMax() <= appList.size() /*&& StringUtils.isBlank(evt.getWorkType2())) || 
						(respon.getCanApplyCount() <= listBean.size() && StringUtils.isNotBlank(evt.getWorkType2()))*/){
					respon.setTipDescr("已申请工资不允许重复申请");
				} else {
					if(listBean.size() < respon.getCanApplyCount()-1){
						respon.setButtonDisable(false);
					}else if(listBean.size() == respon.getCanApplyCount()-1){
						if("1".equals(respon.getIsAppWork())){
							if(respon.getCrtDate() == null){
								respon.setTipDescr("节点阶段完成后才允许申请工资");
							}else{
								respon.setButtonDisable(false);
							}
						}else{
							if(listBean.size() < respon.getCanApplyCount()){
								respon.setButtonDisable(false);
							}else{
								respon.setTipDescr("该节点不允许申请工资");
							}
						}
					}else{
						respon.setTipDescr("该节点不允许申请工资");
					}
				}
            }
            //新版本显示信息以及按钮由后台控制,旧版本不是！兼容旧版本部分
            if(evt.getCustWkPk()==null || evt.getCustWkPk() == 0){
            	String tips = "今天已经签到了"+workerService.getWorkSignInCount(evt.getCustWkPk(),evt.getCustCode(),evt.getCode()).get("todaySignIn")+"次;";//修改旧版本今日签到次数统计
            	if(respon.getNeedDays() > 0) tips += respon.getSignDescr();//兼容旧版本显示信息提示
    			respon.setSignDescr(tips);
    			if(Integer.parseInt(maxSeq) == respon.getSeq() && respon.getCrtDate() != null) respon.setPk(0);
            }

            if(StringUtils.isNotBlank(respon.getCode()) && "22".equals(respon.getCode().trim())) {
            	ItemType1 itemType1 = workerService.get(ItemType1.class, "JC");
				respon.setIsSpecReq(itemType1.getIsSpecReq());
			}
            
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	@RequestMapping("/workerSignIn")
	public void workerSignIn(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerSignInEvt evt = new WorkerSignInEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog = new InterfaceLog(clazz,javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerSignInEvt)JSONObject.toBean(json,WorkerSignInEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			if("1".equals(evt.getStatus())){
				Customer customer = customerService.get(Customer.class,evt.getCustCode());
				if(customer.getBuilderCode()!=null){
					Builder builder = builderService.get(Builder.class, customer.getBuilderCode());
					if(builder.getLongitude()!=null&&builder.getLongitude().longValue()!=0){
						int limitDistance = builder.getOffset()>0?builder.getOffset():PosiBean.LIMIT_DISTANCE;
						System.out.println(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude()));
						if(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude())>limitDistance){
							respon.setReturnCode("100006");
							respon.setReturnInfo("当前地点离楼盘位置过远,无法签到和阶段完成");
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}else{
							respon.setReturnCode("100005");
							respon.setReturnInfo("");
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}
					}else{
						respon.setReturnCode("100006");
						respon.setReturnInfo("该楼盘项目名称位置异常");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return;
					}
				}else{
					respon.setReturnCode("100006");
					respon.setReturnInfo("该楼盘没有项目名称,无法判断距离");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}

			if(!("2".equals(evt.getStatus()))){
				Map<String,Object> map = workerService.getWorkSignInCount(evt.getCustWkPk(),evt.getCustCode(),evt.getWorkerCode());
				String todaySignIn = ""+map.get("todaySignIn");
				if(Integer.parseInt(todaySignIn)>1){
					respon.setReturnCode("400002");
					respon.setReturnInfo("今天已经签到过了2次了,不能继续签到");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			Map<String, Object> existConfirmMap = workerService.existPrjProgConfirm(evt.getCustWkPk());
			if(existConfirmMap == null || "0".equals(existConfirmMap.get("existConfirm").toString())){
				respon.setReturnCode("400002");
				respon.setReturnInfo("请点击更多->验收，先进行"+existConfirmMap.get("Descr")+"节点验收,然后才能签到");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if(StringUtils.isBlank(evt.getPrjItem2())){
				respon.setReturnCode("400002");
				respon.setReturnInfo("没有对应的二级节点不能签到");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			WorkSign workSign = new WorkSign();
			workSign.setCustCode(evt.getCustCode());
			workSign.setWorkerCode(evt.getWorkerCode());
			workSign.setPrjItem2(evt.getPrjItem2());
			workSign.setAddress(evt.getAddress());
			workSign.setCrtDate(new Date());
			workSign.setCustWkPk(evt.getCustWkPk());
			workSign.setNo(workerService.getSeqNo("tWorkSign"));
			workSign.setIsLeaveProblem(evt.getIsLeaveProblem());
			workSign.setLeaveProblemRemark(evt.getLeaveProblemRemark());
			workSign.setLastUpdatedBy(getUserContext(request).getCzybh());
			CustWorker custWorker = workerService.get(CustWorker.class, evt.getCustWkPk());
			
			//工地工人安排如果当前施工状态为停工，改为施工。
			if("2".equals(custWorker.getStatus().trim())){
				custWorker.setStatus("1");
			}
			if("17".equals(custWorker.getWorkType12().trim())){
				boolean hasSign = workerService.getWorkSignByCustWkPk(evt.getCustWkPk());
				if(!hasSign){
					CustIntProg custIntProg = workerService.get(CustIntProg.class, evt.getCustCode());
					if(custIntProg!=null){
						custIntProg.setIntInstallDate(new Date());
						workerService.update(custIntProg);
					}else{
						System.err.println(new Date()+" workerSignIn "+evt.getCustWkPk());
					}
				}
			}
			
			if("18".equals(custWorker.getWorkType12().trim())){
				boolean hasSign = workerService.getWorkSignByCustWkPk(evt.getCustWkPk());
				if(!hasSign){
					CustIntProg custIntProg = workerService.get(CustIntProg.class, evt.getCustCode());
					if(custIntProg!=null){
						custIntProg.setCupInstallDate(new Date());
						workerService.update(custIntProg);
					}else{
						System.err.println(new Date()+" workerSignIn "+evt.getCustWkPk());
					}
				}
			}
			
			if("2".equals(evt.getStatus())){
				workSign.setIsComplete("1");
				PrjItem2 prjItem2 = workerService.get(PrjItem2.class, evt.getPrjItem2());
				ItemType1 itemType1 = workerService.get(ItemType1.class, "JC");
				if(("22".equals(evt.getPrjItem2())||"21".equals(evt.getPrjItem2())) && "1".equals(itemType1.getIsSpecReq().trim())){
					if(evt.getCupUpHigh()==null||"".equals(evt.getCupUpHigh())){
						evt.setCupUpHigh(0.0);
					}
					if(evt.getCupDownHigh()==null||"".equals(evt.getCupDownHigh())){
						evt.setCupDownHigh(0.0);
					}
					double cupDownHigh=0.0;
					double cupUpHigh=0.0;
					CupMeasure cupMeasure = new CupMeasure();
					if("22".equals(evt.getPrjItem2())){
						cupDownHigh = cupMeasureService.getCupDownHigh(evt.getCustCode());
						cupUpHigh = cupMeasureService.getCupUpHigh(evt.getCustCode());
						cupMeasure.setIsCupboard("1");
					}else{
						cupDownHigh = cupMeasureService.getBathDownHigh(evt.getCustCode());
						cupUpHigh = cupMeasureService.getBathUpHigh(evt.getCustCode());
						cupMeasure.setIsCupboard("0");
					}
					//如果工人填写的（地柜+吊柜米数）不超过（解单地柜+吊柜米数）+0.2，橱柜测量表的状态置为2.已确认，确认时间为当前时间，确认人为空，否则置为1.已提报 by cjg 20200325
					if(evt.getCupDownHigh()+evt.getCupUpHigh()<=cupDownHigh+cupUpHigh+0.2){
						cupMeasure.setStatus("2");
						cupMeasure.setConfirmDate(new Date());
					}else{
						cupMeasure.setStatus("1");
					}
					cupMeasure.setCustCode(evt.getCustCode());
					cupMeasure.setWorkerCode(evt.getWorkerCode());
					cupMeasure.setCupDownHigh(evt.getCupDownHigh());
					cupMeasure.setCupUpHigh(evt.getCupUpHigh());
					cupMeasure.setAppDate(new Date());
					cupMeasure.setLastUpdate(new Date());
					cupMeasure.setLastUpdatedBy(evt.getWorkerCode());
					cupMeasure.setActionLog("ADD");
					cupMeasure.setExpired("F");
					cupMeasureService.save(cupMeasure);
				}
				//当前阶段为后期水电安装时，要填写马桶数量和浴室柜数量，保存到客户表 add by zb on 20191213
				if ("15".equals(evt.getPrjItem2())) {
					if(evt.getToiletQty()==null||"".equals(evt.getToiletQty())){
						evt.setToiletQty(0.0);
					}
					if(evt.getCabinetQty()==null||"".equals(evt.getCabinetQty())){
						evt.setCabinetQty(0.0);
					}
					Customer customer = this.workerService.get(Customer.class, evt.getCustCode());
					customer.setToiletQty(evt.getToiletQty());
					customer.setCabinetQty(evt.getCabinetQty());
					this.workerService.update(customer);
				}
				if("17".equals(evt.getPrjItem2())&&null!=evt.getWaterArea()){
					Double waterCtrArea=workerService.getWaterArea(evt);
					WaterAreaCfm waterAreaCfm=this.workerService.get(WaterAreaCfm.class, evt.getCustCode());
					if(waterAreaCfm == null) {
						waterAreaCfm = new WaterAreaCfm();
						waterAreaCfm.setAppDate(new Date());
						waterAreaCfm.setCustCode(evt.getCustCode());
						waterAreaCfm.setStatus(evt.getWaterArea() > waterCtrArea ? "1" : "2");
						waterAreaCfm.setWaterArea(evt.getWaterArea());
						waterAreaCfm.setWorkerCode(getUserContext(request).getCzybh());
						waterAreaCfm.setExpired("F");
						waterAreaCfm.setLastUpdate(new Date());
						waterAreaCfm.setLastUpdatedBy(getUserContext(request).getCzybh());
						waterAreaCfm.setActionLog("ADD");
						workerService.save(waterAreaCfm);
					}
				}
				if(prjItem2 != null && "1".equals(prjItem2.getIsUpEndDate())){
					if(custWorker != null){
						custWorker.setEndDate(workSign.getCrtDate());
						workerService.update(custWorker);
					}
				}
			}else{
				workSign.setIsComplete("0");
				workerService.update(custWorker);
			}
			
			workerService.saveWorkSign(workSign,evt.getPhotoNameList(),evt.getPhotoCodeList());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	@RequestMapping("/getCustWorkInfo")
	public void getCustWorkInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkInfoEvt evt = new CustWorkInfoEvt();
		CustWorkInfoResp respon = new CustWorkInfoResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			Map<String,Object> map = workerService.getCustWorkInfo(evt.getWorkerCode(),evt.getCustCode(),evt.getCustWkPk());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWokerCostApply")
	public void getWokerCostApply(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WokerCostApplyEvt evt=new WokerCostApplyEvt();
		BasePageQueryResp<WokerCostApplyResp> respon=new BasePageQueryResp<WokerCostApplyResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WokerCostApplyEvt) JSONObject.toBean(json, WokerCostApplyEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			// if true 是兼容工人app 1.0的版本
			if("1".equals(evt.getStatus())){
				List<Map<String,Object>> list = workerService.getWokerCostApply(evt.getCustCode(),evt.getSalaryType(),evt.getWorkerCode(),null,null, null,null);
				List<WokerCostApplyResp> listBean = BeanConvertUtil.mapToBeanList(list,WokerCostApplyResp.class);
				respon.setDatas(listBean);
				respon.setHasNext(false);
				respon.setRecordSum((long) list.size());
				respon.setTotalPage((long) 1);
				respon.setPageNo(evt.getPageNo());
			}else{
				Page page = new Page();
				page.setPageNo(evt.getPageNo());
				page.setPageSize(evt.getPageSize());
				String workType2 = "";
				CustWorker custWorker = this.workerService.get(CustWorker.class, evt.getCustWkPk());
				if(evt.getCustWkPk() != null && evt.getCustWkPk() > 0){
					if(custWorker != null && StringUtils.isNotBlank(custWorker.getWorkType12())){
						WorkType12 workType12= this.workerService.get(WorkType12.class, custWorker.getWorkType12());
						if(workType12 != null){
							workType2 = workType12.getOfferWorkType2();
						}
					}
				}
				evt.setWorkType2("");
				evt.setWorkType12(custWorker.getWorkType12());
				evt.setCustWkPk(null);
				workerService.getWokerCostApply(page,evt);
				List<WokerCostApplyResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WokerCostApplyResp.class);
				respon.setDatas(listBean);
				respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true
						: false);
				respon.setRecordSum(page.getTotalCount());
				respon.setTotalPage(page.getTotalPages());
				respon.setPageNo(evt.getPageNo());
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	@RequestMapping("/addWorkerCost")
	public void addWorkerCost(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		AddWokerCostEvt evt = new AddWokerCostEvt();
		BaseResponse respon = new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (AddWokerCostEvt) JSONObject.toBean(json, AddWokerCostEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}

			CustWorker custWorker = this.workerService.get(CustWorker.class, Integer.parseInt(evt.getCustWkPk()));
			//当workType2为空, 工人安排pk不为空时,通过工人安排pk自动设置workType2
			if(StringUtils.isBlank(evt.getWorkType2()) && StringUtils.isNotBlank(evt.getCustWkPk()) && Integer.parseInt(evt.getCustWkPk()) > 0){
				if(custWorker != null && StringUtils.isNotBlank(custWorker.getWorkType12())){
					WorkType12 workType12= this.workerService.get(WorkType12.class, custWorker.getWorkType12());
					if(workType12 != null){
						evt.setWorkType2(workType12.getOfferWorkType2());
					}
				}
			}
			if(custWorker != null){
				evt.setWorkType12(custWorker.getWorkType12());
			}
			List<Map<String,Object>> list = workerService.getWokerCostApply(evt.getCustCode(),evt.getSalaryType(),evt.getWorkerCode(),Integer.parseInt(evt.getCustWkPk()),"1", "", custWorker.getWorkType12());
			Map<String, Object> map = workerService.getCanApplyTimes(evt);
			if(list.size()>=Integer.parseInt(map.get("canApplyTimes").toString())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("你已经申请了"+list.size()+"次,无法继续提交");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			if(!workerService.addWokerCost(evt)){
				respon.setReturnCode("400001");
				respon.setReturnInfo("工资申请失败");
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return;
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}	
	}

	@RequestMapping("/getWorkSignInCount")
	public void getWorkSignInCount(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		CustWorkInfoEvt evt = new CustWorkInfoEvt();
		CustWorkInfoResp respon = new CustWorkInfoResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			Map<String,Object> map = workerService.getWorkSignInCount(evt.getCustWkPk(),evt.getCustCode(),evt.getWorkerCode());
			BeanConvertUtil.mapToBean(map, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}	
	}

	@RequestMapping("/getWorkPrjItemList")
	public void getWorkPrjItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetWorkPrjItemListEvt evt=new GetWorkPrjItemListEvt();
		BasePageQueryResp<GetWorkPrjItemListResp> respon=new BasePageQueryResp<GetWorkPrjItemListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetWorkPrjItemListEvt) JSONObject.toBean(json, GetWorkPrjItemListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = workerService.getWorkPrjItemList(evt.getCustWkPk());
			WorkType12 workType12 = null;
			CustWorker custWorker = workerService.get(CustWorker.class, evt.getCustWkPk());
			
			if (custWorker != null) {
				workType12 = workerService.get(WorkType12.class, custWorker.getWorkType12());
				if (workType12 != null) {
					
				}
			}
			List<GetWorkPrjItemListResp> listBean = BeanConvertUtil.mapToBeanList(list,GetWorkPrjItemListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(false);
			respon.setRecordSum((long) list.size());
			respon.setTotalPage((long) 1);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getPhotoNum")
	public void getPhotoNum(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetWorkPrjItemListEvt evt=new GetWorkPrjItemListEvt();
		GetWorkPrjItemListResp respon=new GetWorkPrjItemListResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetWorkPrjItemListEvt) JSONObject.toBean(json, GetWorkPrjItemListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = workerService.getWorkPrjItemList(evt.getCustWkPk());
			WorkType12 workType12 = null;
			CustWorker custWorker = workerService.get(CustWorker.class, evt.getCustWkPk());
			
			if (custWorker != null) {
				workType12 = workerService.get(WorkType12.class, custWorker.getWorkType12());
				if (workType12 != null) {
					respon.setMaxPhotoNum(workType12.getMaxPhotoNum());
					respon.setMinPhotoNum(workType12.getMinPhotoNum());
				}
			}
			List<GetWorkPrjItemListResp> listBean = BeanConvertUtil.mapToBeanList(list,GetWorkPrjItemListResp.class);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getTechPhotoList")
	public void getTechPhotoList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetWorkPrjItemListEvt evt=new GetWorkPrjItemListEvt();
		BasePageQueryResp<GetWorkPrjItemListResp> respon=new BasePageQueryResp<GetWorkPrjItemListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetWorkPrjItemListEvt) JSONObject.toBean(json, GetWorkPrjItemListEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setPk(evt.getCustWkPk());
			custWorker.setSourceType(evt.getSourceType());
			List<Map<String,Object>> list=workerService.getTechPhotoList(custWorker);
			List<GetWorkPrjItemListResp> listBean = BeanConvertUtil.mapToBeanList(list,GetWorkPrjItemListResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 工地工人问题反馈图片上传
	 * @param request
	 * @param response
	 */
	@RequestMapping("/workerPicUpload")
	public void workerPicUpload(HttpServletRequest request,HttpServletResponse response){
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
				WorkerPicUploadRule rule =
                        new WorkerPicUploadRule(fileNameNew, fileNameNew.substring(0,5));
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
	
	@RequestMapping("/saveWorkerProblem")
	public void saveWorkerProblem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ClientWorkerProblemEvt evt=new ClientWorkerProblemEvt();
		//SavePrjProgConfirmEvt evt1=new SavePrjProgConfirmEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			evt=(ClientWorkerProblemEvt) JSONObject.toBean(json, ClientWorkerProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			WorkerProblem workerProblem=new WorkerProblem();
			workerProblem.setNo(workerProblemService.getSeqNo("tWorkerProblem"));
			workerProblem.setCustWkPk(evt.getCustWkPk());
			workerProblem.setDate(evt.getDate());
			workerProblem.setRemark(evt.getRemarks().replaceAll("	", ""));
			workerProblem.setStopDay(evt.getStopDay());
			workerProblem.setType(evt.getType());
			workerProblem.setLastUpdate(new Date());
			workerProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			workerProblem.setExpired("F");
			workerProblem.setM_umState("A");
			workerProblem.setActionLog("ADD");
			workerProblem.setStatus("1");
			workerProblemService.saveWorkerProblem(workerProblem,evt.getPhotoNameList());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/updateWorkerProblem")
	public void updateWorkerProblem(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ClientWorkerProblemEvt evt=new ClientWorkerProblemEvt();
		//SavePrjProgConfirmEvt evt1=new SavePrjProgConfirmEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd" );
			evt=(ClientWorkerProblemEvt) JSONObject.toBean(json, ClientWorkerProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if (errors.hasErrors()) {
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			WorkerProblem workerProblem=workerProblemService.get(WorkerProblem.class, evt.getNo());
			workerProblem.setType(evt.getType());
			workerProblem.setRemark(evt.getRemarks());
			workerProblem.setStopDay(evt.getStopDay());
			workerProblem.setLastUpdate(new Date());
			workerProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			workerProblem.setExpired("F");
			workerProblem.setActionLog("Edit");
			workerProblem.setM_umState("E");
			if(!"1".equals(workerProblem.getStatus())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("已经确认或已经处理不允许修改");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			workerProblemService.saveWorkerProblem(workerProblem,evt.getPhotoNameList());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/workerPicDelete")
	public void workerPicDelete(HttpServletRequest request,HttpServletResponse response){
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
				WorkerProblemPic workerProblemPic=workerProblemService.getByPhotoName(str);
				WorkerPicUploadRule rule = new WorkerPicUploadRule(str,"");
//				String path=getWorkerPicUploadPath(str);
//				File file = new File(path+str);
				if(workerProblemPic!=null){
					workerProblemService.delete(workerProblemPic);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerProblemList")
	public void getWorkerProblemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerItemAppEvt evt=new WorkerItemAppEvt();
		BasePageQueryResp<WorkerProblemResp> respon=new BasePageQueryResp<WorkerProblemResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerItemAppEvt) JSONObject.toBean(json, WorkerItemAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
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
			page.setPageSize(20);
			workerService.getWorkerProblemList(page, evt.getCustWkPk());
			List<WorkerProblemResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerProblemResp.class);
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
	
	@RequestMapping("/getWorkerPicList")
	public void getWorkerPicList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UploadPhotoResp responPic = new UploadPhotoResp();
		ClientWorkerProblemEvt evt=new ClientWorkerProblemEvt();
		//BasePageQueryResp<ClientWorkerProblemEvt> respon=new BasePageQueryResp<ClientWorkerProblemEvt>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		List<String> fileNameList = new ArrayList<String>();
		List<String> photoPathList = new ArrayList<String>();
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ClientWorkerProblemEvt) JSONObject.toBean(json, ClientWorkerProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				responPic.setReturnCode("400001");
				responPic.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(responPic, response, msg, responPic, request,interfaceLog);
				return;
			}
			List<Map<String , Object>> photoNameList=workerProblemService.getWorkerPicList(evt.getNo());
			if(photoNameList!=null){
				if(photoNameList.size()>0){
					for (int i=0;i<photoNameList.size();i++){
						String PhotoName=photoNameList.get(i).get("PhotoName").toString();
						WorkerPicUploadRule rule = new WorkerPicUploadRule(PhotoName, PhotoName.substring(0, 5));
						String PhotoPath=FileUploadUtils.getFileUrl(rule.getFullName());
						photoPathList.add(PhotoPath);
						fileNameList.add(PhotoName);
					}
				}
			}
			responPic.setPhotoPathList(photoPathList);
			responPic.setPhotoNameList(fileNameList);
			returnJson(responPic,response,msg,responPic,request,null);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(responPic, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerMessageSummary")
	public void getWorkerMessageSummary(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerMessageEvt evt=new WorkerMessageEvt();
		BasePageQueryResp<WorkerMessageQueryResp> respon=new BasePageQueryResp<WorkerMessageQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerMessageEvt) JSONObject.toBean(json, WorkerMessageEvt.class);
			interfaceLog.setRequestContent(json.toString());
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
			page.setPageSize(10);
			WorkerMessageQueryResp rep=null;
			WorkerMessage workerMessage=new WorkerMessage();
			workerMessage.setMsgType("");
			workerMessage.setRcvCZY(evt.getRcvCzy());
			workerMessage.setRcvStatus("0");
			workerMessage.setMsgRelCustCode(evt.getMsgRelCustCode());
			workerMessageService.getWorkerMessageNum(page, workerMessage);
			workerMessageService.doUpdateMessageStatus(evt.getRcvCzy());
			List<WorkerMessageQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerMessageQueryResp.class);
			
			respon.setDatas(listBean);
			System.out.println(page.getTotalPages()+"    "+page.getPageNo());
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerMessageDetail")
	public void getWorkerMessageDetail(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		WorkerMessageEvt evt=new WorkerMessageEvt();
		BasePageQueryResp<WorkerMessageQueryResp> respon=new BasePageQueryResp<WorkerMessageQueryResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerMessageEvt) JSONObject.toBean(json, WorkerMessageEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			
			WorkerMessageQueryResp rep=null;
			
			workerMessageService.getMessageDetail(page, evt.getPk());
			List<WorkerMessageQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),WorkerMessageQueryResp.class);
			
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMessageDetail")
	public void getMessageDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		WorkerMessageEvt evt = new WorkerMessageEvt();
		BasePageQueryResp<WorkerMessageQueryResp> respon = new BasePageQueryResp<WorkerMessageQueryResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerMessageEvt) JSONObject.toBean(json, WorkerMessageEvt.class);
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
			
			workerMessageService.getMessageDetail(page, evt.getPk());
			List<WorkerMessageQueryResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), WorkerMessageQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getConfirmPicList")
	public void getConfirmPicList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		UploadPhotoResp responPic = new UploadPhotoResp();
		ClientWorkerProblemEvt evt=new ClientWorkerProblemEvt();
		//BasePageQueryResp<ClientWorkerProblemEvt> respon=new BasePageQueryResp<ClientWorkerProblemEvt>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		List<String> fileNameList = new ArrayList<String>();
		List<String> photoPathList = new ArrayList<String>();
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(ClientWorkerProblemEvt) JSONObject.toBean(json, ClientWorkerProblemEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				responPic.setReturnCode("400001");
				responPic.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(responPic, response, msg, responPic, request,interfaceLog);
				return;
			}
			List<Map<String , Object>> photoNameList=workerProblemService.getConfirmPicList(evt.getNo());
			if(photoNameList!=null){
				if(photoNameList.size()>0){
					for (int i=0;i<photoNameList.size();i++){
						String PhotoName=photoNameList.get(i).get("PhotoName").toString();
						PrjProgNewUploadRule rule = new PrjProgNewUploadRule(PhotoName, PhotoName.substring(0, 5));
						String PhotoPath = FileUploadUtils.getFileUrl(rule.getFullName());
						photoPathList.add(PhotoPath);
						fileNameList.add(PhotoName);
					}
				}
			}
			responPic.setPhotoPathList(photoPathList);
			responPic.setPhotoNameList(fileNameList);
			returnJson(responPic,response,msg,responPic,request,null);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(responPic, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getMessageNum")
	public void getMessageNum(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		WorkerMessageEvt evt = new WorkerMessageEvt();
		BasePageQueryResp<WorkerMessageQueryResp> respon = new BasePageQueryResp<WorkerMessageQueryResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkerMessageEvt) JSONObject.toBean(json, WorkerMessageEvt.class);
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
			
			workerMessageService.getMessageNum(page, evt.getRcvCzy());
			List<WorkerMessageQueryResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), WorkerMessageQueryResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getRatedSalaryList")
	public void getRatedSalaryList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		BasePageQueryResp<CustWorkInfoResp> respon=new BasePageQueryResp<CustWorkInfoResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setWorkType2(evt.getWorkType2());
			WorkType2 workType2 = new WorkType2();
			custWorker.setSalaryCtrlType("0");
			if(StringUtils.isNotBlank(evt.getWorkType2())){
				workType2 = workerService.get(WorkType2.class, evt.getWorkType2());
				if(workType2!=null){
					custWorker.setSalaryCtrlType(workType2.getSalaryCtrlType());
				}
			}
			List<Map<String,Object>> list = workerService.getRatedSalaryList(custWorker,evt.getAppType());
			List<CustWorkInfoResp> listBean = BeanConvertUtil.mapToBeanList(list, CustWorkInfoResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/getRatedSalary")
	public void getRatedSalary(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		CustWorkInfoResp respon=new CustWorkInfoResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setWorkType2(evt.getWorkType2());
			WorkType2 workType2 = new WorkType2();
			custWorker.setSalaryCtrlType("0");
			if(StringUtils.isNotBlank(evt.getWorkType2())){
				workType2 = workerService.get(WorkType2.class, evt.getWorkType2());
				if(workType2!=null){
					custWorker.setSalaryCtrlType(workType2.getSalaryCtrlType());
				}
			}
			Map<String,Object> ratedSalary= workerService.getRatedSalary(custWorker,evt.getAppType());
			Customer customer = new Customer();
			CustType custType = new CustType();
			if(StringUtils.isNotBlank(evt.getCustCode())){
				customer = customerService.get(Customer.class, evt.getCustCode());
				if(customer != null){
					custType = customerService.get(CustType.class, customer.getCustType());
					if(custType != null){
						ratedSalary.put("baseSpec", custType.getBaseSpec());
					}
				}
			}
			
			if (ratedSalary==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			BeanConvertUtil.mapToBean(ratedSalary, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}	
	
	@RequestMapping("/getAllowGetMatrail")
	public void getAllowGetMatrail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		CustWorkInfoResp respon=new CustWorkInfoResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setPk(evt.getCustWkPk());
			Map<String,Object> allowGetMatrail= workerService.getAllowGetMatrail(custWorker);
			if(allowGetMatrail==null){
				respon.setAllowGetMatrail(true);
			}else{
				respon.setAllowGetMatrail(false);
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 工地工人问题反馈图片上传
	 * @param request
	 * @param response
	 */
	@RequestMapping("/workerSignPicUpload")
	public void workerSignPicUpload(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		UploadPhotoResp respon = new UploadPhotoResp();
		try {
			
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String custCode = multipartFormData.getParameter("custCode");
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> PhotoPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
                WorkerSignPicUploadRule rule = new WorkerSignPicUploadRule(attatchment.getName(), 
                        		custCode,getUserContext(request).getCzybh());
                
                Result uploadResult =
                        FileUploadUtils.upload(attatchment.getInputStream(), 
                        		rule.getFileName(),rule.getFilePath());
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
                fileNameList.add(rule.getFileName());
                PhotoPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
            }
			
			respon.setPhotoPathList(PhotoPathList);
			System.out.println(PhotoPathList);
			respon.setPhotoNameList(fileNameList);
			returnJson(respon,response,msg,respon,request,null);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}
	
	@RequestMapping("/hasSign")
	public void hasSign(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerSignInEvt evt = new WorkerSignInEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog = new InterfaceLog(clazz,javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerSignInEvt)JSONObject.toBean(json,WorkerSignInEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			if("3".equals(evt.getStatus())){
				Map<String,Object> map = workerService.getWorkSignInCount(evt.getCustWkPk(),evt.getCustCode(),evt.getWorkerCode());
				String todaySignIn = ""+map.get("todaySignIn");
				if(Integer.parseInt(todaySignIn)>1){
					respon.setReturnCode("400002");
					respon.setReturnInfo("今天已经签到过了2次了,不能继续签到");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getBefSameWorker")
	public void getBefSameWorker(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		CustWorkInfoResp respon=new CustWorkInfoResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setPk(evt.getCustWkPk());
			custWorker.setCustCode(evt.getCustCode());
			
			Map<String,Object> befSameWorker= workerService.getBefSameWorker(custWorker);
			if (befSameWorker == null) {
				befSameWorker = custWorkerAppService.getCustWorkerAppDataByCustWorkPk(evt.getCustWkPk());
			}
			BeanConvertUtil.mapToBean(befSameWorker, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getSalaryCtrlAndAppAmount")
	public void getSalaryCtrlAndAppAmount(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		CustWorkInfoResp respon=new CustWorkInfoResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setWorkType2(evt.getWorkType2());
			Map<String,Object> appAmount= workerService.getAppAmount(custWorker);
			Map<String,Object> salaryCtrl= workerService.getSalaryCtrl(custWorker);
			if (appAmount==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			if (salaryCtrl==null){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			appAmount.putAll(salaryCtrl);
			BeanConvertUtil.mapToBean(appAmount, respon);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	/**
	 * 工种分类2根据tPrjItem2人工工资进行选择
	 * @author	created by zb
	 * @date	2019-7-4--下午2:50:14
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getWorkType2")
	public void getWorkType2(HttpServletRequest request,HttpServletResponse response){
		WorkerAppEvt evt = new WorkerAppEvt();
		BasePageQueryResp<WorkType2Resp> respon = new BasePageQueryResp<WorkType2Resp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerAppEvt) JSONObject.toBean(json, WorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			List<Map<String, Object>> list = workerService.getWorkType2(evt);
			if (null == list) {
				respon.setReturnCode("400002");
				respon.setReturnInfo("节点阶段完成后才允许申请工资");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<WorkType2Resp> listBean = BeanConvertUtil.mapToBeanList(list, WorkType2Resp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getAllowSecondSignIn")
	public void getAllowSecondSignIn(HttpServletRequest request,HttpServletResponse response){
		WorkerAppEvt evt = new WorkerAppEvt();
		BasePageQueryResp<WorkType2Resp> respon = new BasePageQueryResp<WorkType2Resp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerAppEvt) JSONObject.toBean(json, WorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors = new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request, interfaceLog);
				return ;
			}
			Map<String, Object> map = workerService.getAllowSecondSignIn(evt.getCustWkPk());
			Integer lastSignInMinAgo = Integer.parseInt(""+map.get("lastSignInMinAgo"));
			if(lastSignInMinAgo < 30){
				respon.setReturnCode("400002");
				respon.setReturnInfo("再过"+(30-lastSignInMinAgo)+"分钟后可进行第二次签到");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request, interfaceLog);
		}
	}
	
	@RequestMapping("/getWorkerRatedSalaryList")
	public void getWorkerRatedSalaryList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		BasePageQueryResp<CustWorkInfoResp> respon=new BasePageQueryResp<CustWorkInfoResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
			List<Map<String,Object>> list = workerService.getWorkerRatedSalaryList(evt.getCustCode());
			if(null == list){
				respon.setNoListTip("没有定额信息");
				returnJson(respon, response, msg, respon, request,interfaceLog);
			}
			List<CustWorkInfoResp> listBean = BeanConvertUtil.mapToBeanList(list, CustWorkInfoResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getWorkerRatedSalary")
	public void getWorkerRatedSalary(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		CustWorkInfoEvt evt=new CustWorkInfoEvt();
		BasePageQueryResp<CustWorkInfoResp> respon=new BasePageQueryResp<CustWorkInfoResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkInfoEvt) JSONObject.toBean(json, CustWorkInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = new CustWorker();
			custWorker.setCustCode(evt.getCustCode());
			custWorker.setWorkType12(evt.getWorkType12());
			List<Map<String,Object>> list = workerService.getWorkerRatedSalary(custWorker);
			List<CustWorkInfoResp> listBean = BeanConvertUtil.mapToBeanList(list, CustWorkInfoResp.class);
			respon.setDatas(listBean);
			respon.setRecordSum(listBean==null?0l:listBean.size());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getInstallInfoList")
	public void getInstallInfoList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		InstallInfoEvt evt=new InstallInfoEvt();
		BasePageQueryResp<InstallInfoResp> respon=new BasePageQueryResp<InstallInfoResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(InstallInfoEvt) JSONObject.toBean(json, InstallInfoEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			CustWorker custWorker = workerService.get(CustWorker.class, evt.getCustWkPk());
			List<Map<String,Object>> list = workerService.getInstallInfoList(custWorker);
			if(null == list){
				respon.setNoListTip("无相关信息");
				returnJson(respon, response, msg, respon, request,interfaceLog);
			}
			List<InstallInfoResp> listBean = BeanConvertUtil.mapToBeanList(list, InstallInfoResp.class);
			respon.setDatas(listBean);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/getWaterAreaCfm")
	public void getWaterAreaCfm(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetWaterAreaCfmEvt evt=new GetWaterAreaCfmEvt();
		GetWaterAreaCfmResp respon=new GetWaterAreaCfmResp();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetWaterAreaCfmEvt) JSONObject.toBean(json, GetWaterAreaCfmEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}

			respon.setWaterArea(-1d);
			if(StringUtils.isNotBlank(evt.getCustCode())) {
				WaterAreaCfm waterAreaCfm = this.workerService.get(WaterAreaCfm.class, evt.getCustCode());
				if(waterAreaCfm != null) {
					respon.setWaterArea(waterAreaCfm.getWaterArea());
				}
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
