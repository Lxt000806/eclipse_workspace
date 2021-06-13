package com.house.home.client.controller;

import java.util.Date;
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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.CustomerInfoEvt;
import com.house.home.client.service.evt.PrjConfirmAppEvt;
import com.house.home.client.service.evt.WorkerEvalEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.ItemReqQueryResp;
import com.house.home.client.service.resp.PrjConfirmAppResp;
import com.house.home.client.service.resp.WorkerEvalListResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.WorkerEval;
import com.house.home.service.basic.SignInService;
import com.house.home.service.project.PrjConfirmAppService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.project.PrjProgService;

import java.util.List;
@RequestMapping("/client/prjConfirmApp")
@Controller
public class ClientPrjConfirmAppController extends ClientBaseController {
	
	@Autowired
	private PrjConfirmAppService prjConfirmAppService;
	@Autowired
	private PrjProgService prjProgService;
	@Autowired
	private PrjProgConfirmService prjProgConfirmService;
	@Autowired
	private SignInService signInService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPrjConfirmAppList")
	public void getPrjConfirmAppList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BasePageQueryResp<PrjConfirmAppResp> respon=new BasePageQueryResp<PrjConfirmAppResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
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
			prjConfirmAppService.getPrjConfirmAppList(page, evt);
			List<PrjConfirmAppResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), PrjConfirmAppResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/addPrjConfrimApp")
	public void addPrjConfrimApp(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			try{
				evt.setProjectMan(getUserContext(request).getCzybh());
				String result = prjConfirmAppService.addPrjConfirmApp(evt);
				if(result.equals("1")){
					respon.setReturnCode("400001");
					respon.setReturnInfo(evt.getAddress()+" 该节点已经申请验收(包括草稿状态)");
				}else if(result.equals("2")){
					PrjItem1 prjItem1 = prjConfirmAppService.get(PrjItem1.class, evt.getPrjItem());
					respon.setReturnCode("400001");
					respon.setReturnInfo("请在进度页面将 "+prjItem1.getDescr()+" 项目进行完成操作后，才允许进行验收申请");
				}else if(result.equals("0")){
					respon.setReturnInfo("新增验收申请成功");
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo(result);
				}
			}catch (Exception e){
				respon.setReturnCode("400001");
				respon.setReturnInfo("新增验收申请失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getPrjConfirmAppDetail")
	public void getPrjConfirmAppDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		PrjConfirmAppResp respon=new PrjConfirmAppResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map = prjConfirmAppService.getPrjConfirmAppByPk(evt.getPk());
			if(map == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("获取信息出错,请重试");
			}else{
				BeanConvertUtil.mapToBean(map, respon);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/updatePrjConfirmApp")
	public void updatePrjConfirmApp(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			try{
				evt.setProjectMan(getUserContext(request).getCzybh());
				String result = prjConfirmAppService.updatePrjConfirmApp(evt);
				if(result.equals("1")){
					respon.setReturnCode("400001");
					respon.setReturnInfo(evt.getAddress()+" 该节点已经申请验收(包括草稿状态)");
				}else if(result.equals("2")){
					PrjItem1 prjItem1 = prjConfirmAppService.get(PrjItem1.class, evt.getPrjItem());
					respon.setReturnCode("400001");
					respon.setReturnInfo("请在进度页面将 "+prjItem1.getDescr()+" 项目进行完成操作后，才允许进行验收申请");
				}else if(result.equals("0")){
					respon.setReturnInfo("修改验收申请成功");
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo(result);
				}
			}catch (Exception e){
				respon.setReturnCode("400001");
				respon.setReturnInfo("修改验收申请失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getMaxPkCustWorker")
	public void getMaxPkCustWorker(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		WorkerEvalListResp respon=new WorkerEvalListResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map = prjConfirmAppService.getMaxPkCustWorker(evt.getCustCode(),evt.getPrjItem());
			if(map == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("获取信息出错,请重试");
			}else{
				BeanConvertUtil.mapToBean(map, respon);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getConfirmStatus")
	public void getConfirmStatus(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BasePageQueryResp<WorkerEvalListResp> respon=new BasePageQueryResp <WorkerEvalListResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = prjConfirmAppService.getConfirmStatus(evt.getCustCode(),evt.getWorkType12());
			List<WorkerEvalListResp> listBean = BeanConvertUtil.mapToBeanList(list, WorkerEvalListResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getBefWorkerList")
	public void getBefWorkerList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BasePageQueryResp<WorkerEvalListResp> respon=new BasePageQueryResp <WorkerEvalListResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjConfirmAppEvt) JSONObject.toBean(json, PrjConfirmAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			List<Map<String,Object>> list = prjConfirmAppService.getBefWorkerList(evt.getCustCode(),evt.getWorkerCode(),evt.getWorkType12());
			List<WorkerEvalListResp> listBean = BeanConvertUtil.mapToBeanList(list, WorkerEvalListResp.class);
			respon.setDatas(listBean);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/updateBefWorkerEval")
	public void updateBefWorkerEval(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerEvalEvt evt=new WorkerEvalEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerEvalEvt)JSONObject.toBean(json,WorkerEvalEvt.class);
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
			WorkerEval workerEval = prjConfirmAppService.get(WorkerEval.class, evt.getWorkerEvalId());
			if(evt.getScore()!=null&&evt.getScore()>0){
				workerEval.setScore(evt.getScore());
				workerEval.setRemark(evt.getRemark());
				workerEval.setActionLog("Edit");
				prjConfirmAppService.update(workerEval);
				respon.setReturnInfo("评价修改成功");
			}else{
				respon.setReturnInfo("评价星级不能为0");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/addBefWorkerEval")
	public void addBefWorkerEval(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		WorkerEvalEvt evt=new WorkerEvalEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (WorkerEvalEvt)JSONObject.toBean(json,WorkerEvalEvt.class);
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
			WorkerEval workerEval =new WorkerEval();
			workerEval.setCustCode(evt.getCustCode());
			workerEval.setWorkerCode(evt.getWorkerCode());
			workerEval.setCustWkPk(evt.getCustWkPk());
			workerEval.setType("2");
			workerEval.setEvaMan(evt.getEvalWorker());
			workerEval.setScore(evt.getScore());
			workerEval.setRemark(evt.getRemark());
			workerEval.setEvalWorker(evt.getEvalWorker());
			workerEval.setDate(new java.sql.Date(new Date().getTime()));
			workerEval.setActionLog("ADD");
			workerEval.setExpired("F");
			workerEval.setLastUpdate(new java.sql.Date(new Date().getTime()));
			prjConfirmAppService.save(workerEval);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/addPrjConfirmAppByWorker")
	public void addPrjConfirmAppByWorker(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjConfirmAppEvt)JSONObject.toBean(json,PrjConfirmAppEvt.class);
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
			PrjItem1 prjItem1 = this.prjConfirmAppService.get(PrjItem1.class, evt.getPrjItem());
			if(prjItem1 != null && "1".equals(prjItem1.getIsFirstPass()) && !signInService.existsFirstPass(evt.getCustCode(), evt.getPrjItem())){
				respon.setReturnCode("400001");
				respon.setReturnInfo(prjItem1.getDescr()+"节点需要初检通过后才允许申请验收");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
				PrjConfirmApp prjConfirmApp=new PrjConfirmApp();
				prjConfirmApp.setCustCode(evt.getCustCode());
				prjConfirmApp.setStatus("1");
				prjConfirmApp.setPrjItem(evt.getPrjItem());
				prjConfirmApp.setAppDate(new java.sql.Date(new Date().getTime()));
				prjConfirmApp.setRemarks(evt.getRemarks());
				prjConfirmApp.setRefConfirmNo(null);
				prjConfirmApp.setLastUpdatedBy(evt.getWorkerCode());
				prjConfirmApp.setActionLog("ADD");
				prjConfirmApp.setExpired("F");
				prjConfirmApp.setLastUpdate(new java.sql.Date(new Date().getTime()));
				prjConfirmApp.setWorkerCode(evt.getWorkerCode());
				prjConfirmAppService.save(prjConfirmApp);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/addPrjConfirmAppByProject")
	public void addPrjConfirmAppByProject(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjConfirmAppEvt)JSONObject.toBean(json,PrjConfirmAppEvt.class);
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
			
			PrjProg prjProg = prjProgService.get(PrjProg.class, evt.getPk());
			if(prjProg != null && prjProg.getEndDate()==null){
				prjProg.setEndDate(new java.sql.Date(new Date().getTime()));
				prjProg.setActionLog("Edit");
				prjConfirmAppService.update(prjProg);
			}
			if(!prjConfirmAppService.existPrjConfirmApp(evt.getCustCode(), evt.getPrjItem(), "0,1") 
					&& !prjProgConfirmService.existCustPrjItemPass(evt.getCustCode(), evt.getPrjItem())){
				signInService.saveFirstAddConfirmApp(evt.getCustCode(),evt.getPrjItem(),StringUtils.isNotBlank(evt.getIsPass())?evt.getIsPass():"1");
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	
	@RequestMapping("/addPersonMessage")
	public void addPersonMessage(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		PrjConfirmAppEvt evt=new PrjConfirmAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (PrjConfirmAppEvt)JSONObject.toBean(json,PrjConfirmAppEvt.class);
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
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType("16");
			System.out.println(evt.getMsgText());
			personMessage.setMsgText(evt.getMsgText());
			personMessage.setMsgRelNo("0");
			personMessage.setMsgRelCustCode(evt.getCustCode());
			personMessage.setCrtDate(new java.sql.Date(new Date().getTime()));
			personMessage.setSendDate(new java.sql.Date(new Date().getTime()));
			personMessage.setRcvType("1");
			personMessage.setRcvCzy(evt.getProjectMan());
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			prjConfirmAppService.save(personMessage);
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
