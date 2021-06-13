package com.house.home.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.WfProcPhotoUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.workflow.WorkflowUtils;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.DoApplyEvt;
import com.house.home.client.service.evt.DoCompleteWfProcInstEvt;
import com.house.home.client.service.evt.DoSaveWfProcCommentEvt;
import com.house.home.client.service.evt.DoTransferEvt;
import com.house.home.client.service.evt.EmpCardEvt;
import com.house.home.client.service.evt.ExpenseAdvanceEvt;
import com.house.home.client.service.evt.FindWfProcInstEvt;
import com.house.home.client.service.evt.GetApplyListEvt;
import com.house.home.client.service.evt.GetCustStakeholderEvt;
import com.house.home.client.service.evt.GetDeptCodeEvt;
import com.house.home.client.service.evt.GetOperatorEvt;
import com.house.home.client.service.evt.GetWfProcInstDataEvt;
import com.house.home.client.service.evt.GetWfProcInstNumEvt;
import com.house.home.client.service.evt.GetWfProcInstPageHtmlEvt;
import com.house.home.client.service.evt.GoJqGridByProcInstNoEvt;
import com.house.home.client.service.evt.RcvActEvt;
import com.house.home.client.service.evt.UploadPhotoEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.EmployeeDetailResp;
import com.house.home.client.service.resp.FindWfProcInstResp;
import com.house.home.client.service.resp.GetApplyListResp;
import com.house.home.client.service.resp.GetCustStakeholderResp;
import com.house.home.client.service.resp.GetDeptCodeResp;
import com.house.home.client.service.resp.GetEmpCardListResp;
import com.house.home.client.service.resp.GetExpenseAdvanceResp;
import com.house.home.client.service.resp.GetOperatorResp;
import com.house.home.client.service.resp.GetRcvActResp;
import com.house.home.client.service.resp.GetWfProcInstCandidateResp;
import com.house.home.client.service.resp.GetWfProcInstDataResp;
import com.house.home.client.service.resp.GetWfProcInstNumResp;
import com.house.home.client.service.resp.GetWfProcInstPageHtmlResp;
import com.house.home.client.service.resp.GoJqGridByProcInstNoResp;
import com.house.home.client.service.resp.JqGridEmployeeResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Position;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.service.design.CustPayService;
import com.house.home.service.workflow.WfProcInstService;
import com.house.home.service.workflow.WorkflowService;

@Controller
@RequestMapping("/client/wfProcInst")
public class ClientWfProcInstController extends ClientBaseController {
	
	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private CustPayService custPayService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getApplyList")
	public void getApplyList(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetApplyListEvt evt = new GetApplyListEvt();
		BasePageQueryResp<GetApplyListResp> respon = new BasePageQueryResp<GetApplyListResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetApplyListEvt) JSONObject.toBean(json, GetApplyListEvt.class);
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
			page.setPageSize(-1);
			page.setPageOrderBy("DispSeq");
			page.setPageOrder("asc");
			WfProcInst wfProcInst = new WfProcInst();
			this.wfProcInstService.getApplyListByJqgrid(page, wfProcInst);
			for(int i = 0; i < page.getResult().size(); i++){
				System.out.println(page.getResult().get(i));
			}
			List<GetApplyListResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), GetApplyListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWfProcInstNum")
	public void getWfProcInstNum(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetWfProcInstNumEvt evt = new GetWfProcInstNumEvt();
		GetWfProcInstNumResp respon = new GetWfProcInstNumResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetWfProcInstNumEvt) JSONObject.toBean(json, GetWfProcInstNumEvt.class);
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
			page.setPageNo(1);
			page.setPageSize(-1);
			WfProcInst wfProcInst = new WfProcInst();
			UserContext uc = getUserContext(request);
			if(uc == null || StringUtils.isBlank(uc.getCzybh())){
				respon.setWaitNum(0);
			}else{
				if(evt.getType() == null || evt.getType() == 0){
					evt.setType(1);
				}
				wfProcInst.setType(evt.getType());
				wfProcInst.setUserId(uc.getCzybh());
				if("4".equals(evt.getType()+"")){
					wfProcInst.setRcvStatus("0");
				}
				this.wfProcInstService.findWfProcInstPageBySql(page, wfProcInst);
				respon.setWaitNum(page.getResult().size());
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getOperator")
	public void getOperator(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetOperatorEvt evt = new GetOperatorEvt();
		GetOperatorResp respon = new GetOperatorResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetOperatorEvt) JSONObject.toBean(json, GetOperatorEvt.class);
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
			List<Object> operateList = wfProcInstService.getOperator(evt.getEl(), evt.getPdID(), evt.getDepartment(), evt.getWfProcNo(), getUserContext(request).getCzybh());
			
			if(operateList == null){
				operateList = new ArrayList<Object>();
				respon.setMistakeMsg("当前条件没有设置审批人，请联系管理员("+wfProcInstService.getActUser("erpOfficer")+")修改流程");
			}
			respon.setOperateList(operateList);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getDeptCode")
	public void getDeptCode(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetDeptCodeEvt evt = new GetDeptCodeEvt();
		GetDeptCodeResp respon = new GetDeptCodeResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetDeptCodeEvt) JSONObject.toBean(json, GetDeptCodeEvt.class);
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
			List<Map<String, Object>>deptList = wfProcInstService.getDeptListByCzybh(getUserContext(request).getCzybh());
			respon.setDeptList(deptList);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getEmpInfo")
	public void getEmpInfo(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetDeptCodeEvt evt = new GetDeptCodeEvt();
		JqGridEmployeeResp respon = new JqGridEmployeeResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetDeptCodeEvt) JSONObject.toBean(json, GetDeptCodeEvt.class);
			interfaceLog.setRequestContent(json.toString());
			
			Employee employee = wfProcInstService.get(Employee.class, ClientBaseController.getUserContext(request).getEmnum());
			if(employee != null){
				Double advanceAmount = wfProcInstService.getAdvanceAmount(this.getUserContext(request).getEmnum());
				if(advanceAmount != null){
					respon.setAdvanceAmount(advanceAmount);
				}
				respon.setPhone(employee.getPhone());
				respon.setNumber(employee.getNumber());
				respon.setNameChi(employee.getNameChi());
				respon.setJoinDate(employee.getJoinDate());
				Department2 department2 = wfProcInstService.get(Department2.class, employee.getDepartment2()==null ? "":employee.getDepartment2());
				if(department2 != null ){
					if("1".equals(department2.getDepType()) || "2".equals(department2.getDepType()) || "0".equals(department2.getDepType())){
						respon.setDepType("前端员工");
					}else {
						respon.setDepType("后端员工");
					}
					if("1".equals(employee.getIsLead())){
						respon.setType("部门领导");
					}else {
						if("1".equals(department2.getDepType()) || "2".equals(department2.getDepType()) || "0".equals(department2.getDepType())){
							respon.setType("前端员工");
						}else {
							respon.setType("后端员工");
						}
					}
					respon.setDepartment2Descr(department2.getDesc2());
					respon.setDepartment2Code(department2.getCode());
				}else {
					Department1 department1 = wfProcInstService.get(Department1.class, employee.getDepartment1() == null ? "":employee.getDepartment1());
					if(department1 != null ){
						if("1".equals(department1.getDepType()) || "2".equals(department1.getDepType()) || "0".equals(department1.getDepType())){
							respon.setDepType("前端员工");
						}else {
							respon.setDepType("后端员工");
						}
						if("1".equals(employee.getIsLead())){
							respon.setType("部门领导");
						}else {
							if("1".equals(department1.getDepType()) || "2".equals(department1.getDepType()) || "0".equals(department1.getDepType())){
								respon.setType("前端员工");
							}else {
								respon.setType("后端员工");
							}
						}
						respon.setDepartment2Descr(department1.getDesc2());
						respon.setDepartment2Code(department1.getCode());
					}
				}
				Position position = wfProcInstService.get(Position.class, employee.getPosition()==null ?"":employee.getPosition());
				if(position != null ){
					respon.setPositionDescr(position.getDesc2());
				}
				if("1".equals(employee.getIsLead())){
					respon.setType("部门领导");
				}
				
				Map<String, Object> map = wfProcInstService.getEmpCompany(employee.getDepartment());
				if(map != null){
					respon.setCompany(map.get("CmpDescr").toString());
					respon.setCompanyCode(map.get("Code").toString());
				}
			}
			Map<String, Object> datas = new HashMap<String, Object>();
			
			List<Map<String, Object>> list = wfProcInstService.getProcTaskTableStru(evt.getWfProcNo(), "startevent");
			if(list !=null){
				for(Map<String , Object> map :list){
					datas.put(map.get("TableCode").toString()+"_"+map.get("StruCode").toString()+"_Edit", map.get("IsEdit").toString());
					datas.put(map.get("TableCode").toString()+"_"+map.get("StruCode").toString()+"_Show", map.get("IsVisible").toString());
				}
			}
			datas.put("taskDefKey", "startevent");
			respon.setDatas(datas);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/doApply")
	public void doApply(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoApplyEvt evt = new DoApplyEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {

			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (DoApplyEvt) JSONObject.toBean(json, DoApplyEvt.class);
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
			Map<String, String[]> map = new HashMap<String, String[]>();
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				String[] values = new String[1];
				values[0] = json.getString(key);
				map.put(key, values);
			}
			Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(map);

			UserContext uc = getUserContext(request);

	    	if (uc == null || StringUtils.isBlank(uc.getCzybh())) {
	    		respon.setReturnCode("400001");
	    		respon.setReturnInfo("未找到当前操作员编号，无法查询待办任务！");
	    		logger.error("未找到当前操作员编号，无法查询待办任务！");
				returnJson(respon, response, msg, respon, request,interfaceLog);
	    		return;
	    	}
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
																	.processDefinitionKey(evt.getProcKey())
																	.latestVersion()
																	.singleResult();
			evt.setProcessDefinitionId(processDefinition.getId());
			
			// 发起时取回生成的流程实例编号作为消息推送时的数据
			Map<String, String > pushWfProcInstNo = new HashMap<String, String>();
	    	wfProcInstService.doStartProcInst(formProperties, evt.getProcessDefinitionId(), uc.getCzybh(), evt.getDetailJson(),pushWfProcInstNo);
	    	if(StringUtils.isNotBlank(evt.getActName()) &&StringUtils.isNotBlank(evt.getBank()) && StringUtils.isNotBlank(evt.getCardId())){
	    		wfProcInstService.doSaveAccount(evt.getActName(), evt.getBank(), evt.getCardId(),evt.getSubBranch(), this.getUserContext(request).getCzybh());
	    	}
	    	if(StringUtils.isNotBlank(pushWfProcInstNo.get("pushWfProcInstNo"))){
    			wfProcInstService.doPushTaskToOperator("", pushWfProcInstNo.get("pushWfProcInstNo"));
    		}
	    	returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/getCustStakeholder")
	public void getCustStakeholder(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetCustStakeholderEvt evt = new GetCustStakeholderEvt();
		GetCustStakeholderResp respon = new GetCustStakeholderResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetCustStakeholderEvt) JSONObject.toBean(json, GetCustStakeholderEvt.class);
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
			Map<String, Object> obj = wfProcInstService.getCustStakeholder(evt.getRoll(), evt.getCustCode());
			respon.setObj(obj);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/findWfProcInst")
	public void findWfProcInst(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		FindWfProcInstEvt evt = new FindWfProcInstEvt();
		BasePageQueryResp<FindWfProcInstResp> respon = new BasePageQueryResp<FindWfProcInstResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (FindWfProcInstEvt) JSONObject.toBean(json, FindWfProcInstEvt.class);
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
			
	    	UserContext uc = getUserContext(request);
			
	    	if(uc != null && StringUtils.isNotBlank(uc.getCzybh())){
	    		WfProcInst wfProcInst = new WfProcInst();
	    		wfProcInst.setType(evt.getType());
	    		wfProcInst.setUserId(uc.getCzybh());
	    		wfProcInst.setProcKey(evt.getProcKey());
	    		wfProcInst.setSummary(evt.getSummary());
	    		wfProcInst.setStatus(evt.getStatus());
	    		wfProcInst.setNo(evt.getNo());
	    		wfProcInst.setRcvStatus(evt.getRcvStatus());
	        	wfProcInstService.findWfProcInstPageBySql(page, wfProcInst);
	    	}
			List<FindWfProcInstResp> listBean =  BeanConvertUtil.mapToBeanList(page.getResult(), FindWfProcInstResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getWfProcInstData")
	public void getWfProcInstData(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetWfProcInstDataEvt evt = new GetWfProcInstDataEvt();
		GetWfProcInstDataResp respon = new GetWfProcInstDataResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetWfProcInstDataEvt) JSONObject.toBean(json, GetWfProcInstDataEvt.class);
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
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
																	   		.processInstanceId(evt.getProcessInstanceId())
																	   		.singleResult();
			WfProcInst wfProcInst = this.wfProcInstService.getWfProcInstByActProcInstId(evt.getProcessInstanceId());
		
			String procKey = wfProcInstService.getProcKeyByNo(wfProcInst.getWfProcNo());
			
			List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(evt.getProcessInstanceId()).list();
			
			List<Map<String, Object>> tables = this.wfProcInstService.getTables(wfProcInst.getWfProcNo());
			
			Map<String, Object> datas = new HashMap<String, Object>();
			Map<String , Object> detailMap = new HashMap<String, Object>();
			for(int i = 0; i < tables.size(); i++){
				List<Map<String, Object>> rows = this.wfProcInstService.getTableInfo(tables.get(i).get("Code").toString(), wfProcInst.getNo());
				int detailNum= this.wfProcInstService.getDetailNum(tables.get(i).get("Code").toString(), wfProcInst.getNo()); 
				detailMap.put(tables.get(i).get("Code").toString(), detailNum);
				for(int j = 0; j < rows.size(); j++){
					for(Entry<String, Object> entry : rows.get(j).entrySet()){
						datas.put("fp__"+tables.get(i).get("Code")+"__"+j+"__"+entry.getKey(), entry.getValue() != null ? entry.getValue() : "");
					}
				}
			}
			List<Map<String, Object>> photoList = wfProcInstService.findWfProcInstPicByNo(wfProcInst.getNo());
			if(photoList != null){
				if(photoList.size()>0){
					for(int i=0;i<photoList.size();i++){
						photoList.get(i).put("src", FileUploadUtils.getFileUrl(photoList.get(i).get("photoName").toString()));
					}
				}
			}
			if(taskQuery.size()>0){
				List<Map<String, Object>> list = wfProcInstService.getProcTaskTableStru(wfProcInst.getWfProcNo(), taskQuery.get(0).getTaskDefinitionKey());
				if(list !=null){
					for(Map<String , Object> map :list){
						datas.put(map.get("TableCode").toString()+"_"+map.get("StruCode").toString()+"_Edit", map.get("IsEdit").toString());
						datas.put(map.get("TableCode").toString()+"_"+map.get("StruCode").toString()+"_Show", map.get("IsVisible").toString());
					}
				}
				datas.put("taskDefKey", taskQuery.get(0).getTaskDefinitionKey());
				datas.put("activityId", taskQuery.get(0).getTaskDefinitionKey());
			}
			datas.put("wfProcNo", wfProcInst.getWfProcNo());
			datas.put("procKey", procKey);
			datas.put("photoUrlList", photoList);
			respon.setDatas(datas);
			respon.setDetailDatas(detailMap);
			respon.setProcKey(procKey);
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/doCancelProcInst")
	public void doCancelProcInst(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoCompleteWfProcInstEvt evt = new DoCompleteWfProcInstEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DoCompleteWfProcInstEvt) JSONObject.toBean(json, DoCompleteWfProcInstEvt.class);
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
	    	UserContext uc = getUserContext(request);
			
	    	if(uc != null && StringUtils.isNotBlank(uc.getCzybh())){
	    		wfProcInstService.doCompleteTask(evt.getTaskId(), uc.getCzybh(), "cancel", evt.getComment(),"",null);
	    		if(StringUtils.isNotBlank(evt.getWfProcInstNo())){
	    			wfProcInstService.doPushTaskToOperator("cancel", evt.getWfProcInstNo());
	    		}
	    	}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("保存异常");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
	    	}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/doApprovalTask")
	public void doApprovalTask(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoCompleteWfProcInstEvt evt = new DoCompleteWfProcInstEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (DoCompleteWfProcInstEvt) JSONObject.toBean(json, DoCompleteWfProcInstEvt.class);
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
	    	UserContext uc = getUserContext(request);
	    	Map<String, String[]> map = new HashMap<String, String[]>();
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				String[] values = new String[1];
				values[0] = json.getString(key);
				map.put(key, values);
			}
			Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(map);

			boolean isProcOperator = wfProcInstService.isProcOperator(evt.getTaskId(), uc.getCzybh());
			if(!isProcOperator){
		        respon.setReturnCode("400001");
				respon.setReturnInfo("操作失败，您不是当前任务执行人");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
	    	if(uc != null && StringUtils.isNotBlank(uc.getCzybh())){
	    		wfProcInstService.doCompleteTask(evt.getTaskId(), uc.getCzybh(), "approval", evt.getComment(),evt.getProcessInstId(),formProperties);
	    		if(StringUtils.isNotBlank(evt.getWfProcInstNo())){
	    			wfProcInstService.doPushTaskToOperator("approval", evt.getWfProcInstNo());
	    		}
	    	}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("保存异常");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
	    	}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@RequestMapping("/doRejectTask")
	public void doRejectTask(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoCompleteWfProcInstEvt evt = new DoCompleteWfProcInstEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (DoCompleteWfProcInstEvt) JSONObject.toBean(json, DoCompleteWfProcInstEvt.class);
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
	    	UserContext uc = getUserContext(request);
	    	
	    	boolean isProcOperator = wfProcInstService.isProcOperator(evt.getTaskId(), uc.getCzybh());
			if(!isProcOperator){
		        respon.setReturnCode("400001");
				respon.setReturnInfo("保操作失败，您不是当前任务执行人异常");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
	    	
	    	Map<String, String[]> map = new HashMap<String, String[]>();
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				String[] values = new String[1];
				values[0] = json.getString(key);
				map.put(key, values);
			}
			Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(map);
			
			ProcessDefinitionEntity processDefinitionEntity = workflowService.findProcessDefinitionEntityByTaskId(evt.getTaskId());
			if("applyman".equals(evt.getReturnNode())){
				if(processDefinitionEntity.findActivity(evt.getReturnNode())==null){
					respon.setReturnCode("400001");
					respon.setReturnInfo("操作异常，该流程不支持退回到发起人");
					returnJson(respon, response, msg, respon, request,interfaceLog);
					return;
				}
			}
	    	if(uc != null && StringUtils.isNotBlank(uc.getCzybh())){
	    		wfProcInstService.doCompleteTask(evt.getTaskId(), uc.getCzybh(), evt.getReturnNode(), evt.getComment(),"",formProperties);
	    		if(StringUtils.isNotBlank(evt.getWfProcInstNo())){
	    			wfProcInstService.doPushTaskToOperator("reject", evt.getWfProcInstNo());
	    		}
	    	}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("保存异常");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
	    	}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@RequestMapping("/doSaveComment")
	public void doSaveComment(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoSaveWfProcCommentEvt evt = new DoSaveWfProcCommentEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			if("get".equals(request.getMethod().toLowerCase())){
				json = StringUtil.queryStringToJSONObject(request);
			}else{
				json = this.getJson(request,msg,json,respon);
			}
			evt = (DoSaveWfProcCommentEvt) JSONObject.toBean(json, DoSaveWfProcCommentEvt.class);
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
	    	UserContext uc = getUserContext(request);
	    	if(uc != null && StringUtils.isNotBlank(uc.getCzybh())){
	    		wfProcInstService.doSaveComment(evt.getWfProcInstNo(),evt.getComment(),uc.getCzybh());
	    	}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("保存异常");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
	    	}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/goJqGridByProcInstNo")
	public void goJqGridByProcInstNo(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GoJqGridByProcInstNoEvt evt = new GoJqGridByProcInstNoEvt();
		GoJqGridByProcInstNoResp respon = new GoJqGridByProcInstNoResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GoJqGridByProcInstNoEvt) JSONObject.toBean(json, GoJqGridByProcInstNoEvt.class);
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
			page.setPageNo(1);
			page.setPageSize(-1);
			if("4".equals(evt.getType())){
				wfProcInstService.doUpdateCopyStatus(getUserContext(request).getCzybh(),evt.getWfProcInstNo());
			}
			wfProcInstService.findByProcInstId(page, evt.getWfProcInstNo());
			if(StringUtils.isNotBlank(evt.getProcDefKey()) && StringUtils.isNotBlank(evt.getWfProcInstNo())){
				List<Map<String, Object>> hisList = page.getResult();
				List<Map<String, Object>> addList = wfProcInstService.getProcBranch(evt.getWfProcInstNo(), evt.getProcDefKey(),evt.getEl()) ;
				if(hisList != null && addList != null){
					hisList.addAll(addList);
					page.setResult(hisList);
				}
			}
			respon.setDatas(page.getResult());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadWfPhoto")
	public void uploadWfPhoto(HttpServletRequest request,
			HttpServletResponse response) {

		/*StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			String fileRealPath = "";//文件存放真实地址 
			String firstFileName = ""; 
			String PhotoPath="";
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 设置允许上传的最大文件大小 500k
//			upload.setSizeMax(500 * 1024);
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);
			String custCode="",prjItem="",lastUpdatedBy="",no="";
			List<String> fileRealPathList = new ArrayList<String>();
			List<String> PhotoPathList = new ArrayList<String>();
			List<String> fileNameList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				// 如果是普通 表单参数
				if (obit.isFormField()) { //普通域,获取页面参数
					String fieldName = obit.getFieldName();
					String fieldValue = obit.getString();
				}
				// 如果是多媒体
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					// 如果item是文件上传表单域
					// 获得文件名及路径
					String fileName = item.getName();
					if (fileName != null) {
						if(fileName.indexOf("?")!=-1){
							fileName=fileName.substring(0,fileName.indexOf("?"));
						}
						firstFileName = fileName.substring(
								fileName.lastIndexOf("\\") + 1);
						String formatName = firstFileName
								.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
						String fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
						String filePath = getWfProcPicPath(fileNameNew,"");
						PhotoPath=getWfProcPicDownloadPath(request,fileNameNew,"")+fileNameNew;
						FileUploadServerMgr.makeDir(filePath);
						fileRealPath = filePath + fileNameNew;// 文件存放真实地址
						BufferedInputStream in = new BufferedInputStream(
								item.getInputStream());// 获得文件输入流
						BufferedOutputStream outStream = new BufferedOutputStream(
								new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
						Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
						in.close();
						outStream.close();
						fileRealPathList.add(fileRealPath);
						PhotoPathList.add(PhotoPath);
						fileNameList.add(fileNameNew);
						
					}
				}
			}
			
			respon.setPhotoPathList(PhotoPathList);
			respon.setPhotoNameList(fileNameList);
			
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}*/
		
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			
			// 图片路径
			List<String> PhotoPathList = new ArrayList<String>();
			
			// 图片名称
			List<String> fileNameList = new ArrayList<String>();
			
			// 解析参数
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String wfProcInstNo=multipartFormData.getParameter("wfProcInstNo");
			
			FileItem fileItem = multipartFormData.getFileItem();
			
			// 项目资料管理文件命名规则
			WfProcPhotoUploadRule wfProcPhotoUploadRule = new WfProcPhotoUploadRule(fileItem.getName());

			// 调用上传工具将文件上传到服务 
			Result result = FileUploadUtils.upload(fileItem.getInputStream(), 
					wfProcPhotoUploadRule.getFileName(),wfProcPhotoUploadRule.getFilePath());
			
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("上传文件失败，原文件名:{}",fileItem.getName());
				ServletUtils.outPrintFail(request, response, "上传文件失败");
				return;
			}			
			
			PhotoPathList.add(FileUploadUtils.getFileUrl(wfProcPhotoUploadRule.getFullName()));
			fileNameList.add(wfProcPhotoUploadRule.getFullName());
			
			// 返回图片路径 用于预览
			respon.setPhotoPathList(PhotoPathList);
			
			// 返回图片名称 用于保存
			respon.setPhotoNameList(fileNameList);
			
			returnJson(respon,response,null,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, null, request,null);
		}
		
	}
	
	public static String getWfProcPicDownloadPath(HttpServletRequest request, String fileName,String wfProcInstNo){
		String path = getWfProcPicPath(fileName,wfProcInstNo);
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	public static String getWfProcPicPath(String fileName,String wfProcInstNo){
		String wfProcPicNameNew = SystemConfig.getProperty("wfProcPic", "", "photo");
		if (StringUtils.isBlank(wfProcPicNameNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			if(StringUtils.isNotBlank(wfProcInstNo)){
				return wfProcPicNameNew + wfProcInstNo + "/";
			}else {
				return wfProcPicNameNew+fileName.substring(0, 5)+"/";
			}
		}else{
			return SystemConfig.getProperty("wfProcPic", "", "photo");
		}
	}
						
	@RequestMapping("/doApprFinanceTask")
	public void doApprFinanceTask(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoCompleteWfProcInstEvt evt = new DoCompleteWfProcInstEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DoCompleteWfProcInstEvt) JSONObject.toBean(json, DoCompleteWfProcInstEvt.class);
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
			
	    	UserContext uc = getUserContext(request);
	    	Map<String, String[]> map = new HashMap<String, String[]>();
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				String[] values = new String[1];
				values[0] = json.getString(key);
				map.put(key, values);
			}
			Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(map);

			boolean error = false;
			String tableName = wfProcInstService.getMainTableName(formProperties.get("wfProcNo").toString());
			for(Entry<String, Object> entry : ((IdentityHashMap<String, Object>)formProperties.get("tables")).entrySet()){
				if(StringUtils.isNotBlank(tableName) && tableName.equals(entry.getKey())){
					Map<String, Object> tableMap = (Map<String, Object>) entry.getValue();
					if(tableMap.get("BefAmount") != null && tableMap.get("DeductionAmount") != null){
						Double aftAmount = Double.parseDouble(tableMap.get("BefAmount").toString());
						Double dedutionAmount = Double.parseDouble(tableMap.get("DeductionAmount").toString());
						if(aftAmount < dedutionAmount){
							error = true;
						}
					}
				}
			}
			if(error){
				respon.setReturnCode("400001");
				respon.setReturnInfo("审批失败,抵扣金额大于预支余额,导致抵扣后预支余额为负数！");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			
	    	if(uc != null && StringUtils.isNotBlank(uc.getCzybh())){
	    		//通过service类名名称和调用的方法名反射接口
	    		Method method= ReflectionUtils.findMethod(SpringContextHolder.getBean("wfProcInstService_"+formProperties.get("procKey").toString()+"Impl").getClass(), "doCompFinanceTask"
						,new Class[]{String.class, String.class, String.class, String.class, String.class, Map.class} );
		        ReflectionUtils.invokeMethod(method,SpringContextHolder.getBean("wfProcInstService_"+formProperties.get("procKey").toString()+"Impl"),
		        		evt.getTaskId(), uc.getCzybh(), "approval", evt.getComment(),evt.getProcessInstId(),formProperties);
	    		if(StringUtils.isNotBlank(evt.getWfProcInstNo())){
	    			wfProcInstService.doPushTaskToOperator("approval", evt.getWfProcInstNo());
	    		}
	    	}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("保存异常");
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
	    	}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getEmpCardList")
	public void getEmpCardList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		EmpCardEvt evt = new EmpCardEvt();
		BasePageQueryResp<GetEmpCardListResp> respon = new BasePageQueryResp<GetEmpCardListResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (EmpCardEvt) JSONObject.toBean(json, EmpCardEvt.class);
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
			String czybh = "";
			if(StringUtils.isBlank(evt.getCzybh())){
				czybh = "-1";
			}else {
				czybh = evt.getCzybh();
			}
			
			wfProcInstService.getEmpAccountJqGrid(page, czybh, evt.getActName());
			List<GetEmpCardListResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetEmpCardListResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getRcvActList")
	public void getRcvActList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RcvActEvt evt = new RcvActEvt();
		BasePageQueryResp<GetRcvActResp> respon = new BasePageQueryResp<GetRcvActResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (RcvActEvt) JSONObject.toBean(json, RcvActEvt.class);
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
			
			wfProcInstService.getRcvActByJqGrid(page, this.getUserContext(request).getCzybh(),evt.getActName());
			List<GetRcvActResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetRcvActResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getCheckPayInfo")
	public void getCheckPayInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		RcvActEvt evt = new RcvActEvt();
		BasePageQueryResp<GetRcvActResp> respon = new BasePageQueryResp<GetRcvActResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (RcvActEvt) JSONObject.toBean(json, RcvActEvt.class);
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
			
			CustPay custPay = new CustPay();
			custPay.setCustCode(evt.getCustCode());
			custPay.setPayActName(evt.getPayActName());
			custPay.setPayAmount(evt.getPayAmount());
			custPay.setReceiveActName(evt.getReceiveActName());
			custPay.setPayDate(evt.getPayDate());
			custPay.setFlag(evt.getFlag());
			
			List<Map<String , Object>> list = custPayService.getPayInfo(custPay);
			Long total = 0l;
			if(list != null && list.size() > 0){
				total = (long) list.size();
			}
			respon.setRecordSum(total);
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getExpenseaAdvanceList")
	public void getExpenseaAdvanceList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		ExpenseAdvanceEvt evt = new ExpenseAdvanceEvt();
		BasePageQueryResp<GetExpenseAdvanceResp> respon = new BasePageQueryResp<GetExpenseAdvanceResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ExpenseAdvanceEvt) JSONObject.toBean(json, ExpenseAdvanceEvt.class);
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
			WfProcInst wfProcInst = new WfProcInst();
			String czybh = "*";
			if(StringUtils.isNotBlank(evt.getWfProcInstNo())){
				wfProcInst = wfProcInstService.get(WfProcInst.class, evt.getWfProcInstNo());
				if(wfProcInst != null){
					czybh = wfProcInst.getStartUserId();
				}else {
					czybh = this.getUserContext(request).getCzybh();
				}
			}
			
			wfProcInstService.getAdvanceNoByJqgrid(page, czybh,evt.getActName());
			List<GetExpenseAdvanceResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetExpenseAdvanceResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon, response, msg, respon, request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getHtml")
	public void getHtml(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetWfProcInstPageHtmlEvt evt = new GetWfProcInstPageHtmlEvt();
		GetWfProcInstPageHtmlResp respon = new GetWfProcInstPageHtmlResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetWfProcInstPageHtmlEvt) JSONObject.toBean(json, GetWfProcInstPageHtmlEvt.class);
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
			String pageHtmlCode = "";

			//创建一个URL实例
			String basePath = getOaBasePath();
			URL url = null;

			if(StringUtils.isNotBlank(evt.getSrc())){
				url = new URL(basePath+evt.getSrc());
			} else {
				url = new URL(basePath+"oa/templates/pages/"+evt.getProcKey()+".html");
			}
			
			try {
				//通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
				InputStream is = url.openStream();
				InputStreamReader isr = new InputStreamReader(is,"utf-8");

				//为字符输入流添加缓冲
				BufferedReader br = new BufferedReader(isr);
				String data = br.readLine();//读取数据

				while (data!=null){//循环读取数据
					pageHtmlCode += data;
					data = br.readLine();
				}
				respon.setPageHtml(pageHtmlCode.replace("	", " "));
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCandidate")
	public void getCandidate(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		GetWfProcInstDataEvt evt = new GetWfProcInstDataEvt();
		GetWfProcInstCandidateResp respon = new GetWfProcInstCandidateResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (GetWfProcInstDataEvt) JSONObject.toBean(json, GetWfProcInstDataEvt.class);
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
			String operDescr = "";
			
			if(StringUtils.isNotBlank(evt.getProcessInstanceId())){
				List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(evt.getProcessInstanceId()).list();
				if(StringUtils.isNotBlank(taskQuery.get(0).getAssignee())){
					Czybm czybm = wfProcInstService.get(Czybm.class,taskQuery.get(0).getAssignee());
					operDescr = czybm.getZwxm();
				}else{
					List<IdentityLink> identityLisk= taskService.getIdentityLinksForTask(taskQuery.get(0).getId());
					for (IdentityLink identityLink : identityLisk) {
						if(StringUtils.isNotBlank(identityLink.getGroupId())){
							if(StringUtils.isBlank(operDescr)){
								operDescr = wfProcInstService.getActUser(identityLisk.get(0).getGroupId());
							} else {
								operDescr += "/" + wfProcInstService.getActUser(identityLisk.get(0).getGroupId());
							}
						} 
						if(StringUtils.isNotBlank(identityLink.getUserId())){
							Czybm czybm = wfProcInstService.get(Czybm.class,identityLink.getUserId());
							if(StringUtils.isBlank(operDescr)){
								operDescr = czybm==null?"":czybm.getZwxm();
							} else {
								operDescr += czybm == null?"":("/" + czybm.getZwxm());
							}
						}
					}
				}
				
				respon.setOperator(operDescr);
			} else {
				
				respon.setOperator("");
			}
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	/**
	 * 流程转交
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doTransfer")
	public void doTransfer(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		DoTransferEvt evt = new DoTransferEvt();
		BaseResponse respon = new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (DoTransferEvt) JSONObject.toBean(json, DoTransferEvt.class);
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
			
	    	WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(evt.getProcessInstId());

			List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(evt.getProcessInstId()).list();
			
			if(StringUtils.isNotBlank(taskQuery.get(0).getAssignee())){
				// 删除候选人
				taskService.deleteCandidateUser(taskQuery.get(0).getId(), taskQuery.get(0).getAssignee());
			} else {
				List<IdentityLink> identityLisk= taskService.getIdentityLinksForTask(taskQuery.get(0).getId());
				
				IdentityLinkEntity identityLinkEntity = (IdentityLinkEntity) identityLisk.get(0);
				
				// 删除群组链
				taskService.deleteGroupIdentityLink(identityLinkEntity.getTaskId(), identityLinkEntity.getId(),identityLinkEntity.getType());
			}
			UserContext uc = getUserContext(request);

			WfProcTrack wfProcTrack = new WfProcTrack();
			wfProcTrack.setActionLog("ADD");
			wfProcTrack.setExpired("F");
			wfProcTrack.setLastUpdate(new Date());
			wfProcTrack.setLastUpdatedBy(uc.getCzybh());
			wfProcTrack.setRemarks("流程转交");
			wfProcTrack.setWfProcInstNo(wfProcInst.getNo());
			wfProcTrack.setOperId(uc.getCzybh());
			wfProcTrack.setActionType("7");//操作类型  转交
			wfProcTrack.setFromActivityId(taskQuery.get(0).getId()); // 转交发送到本节点
			wfProcTrack.setFromActivityDescr(taskQuery.get(0).getName());
			wfProcTrack.setToActivityId(taskQuery.get(0).getId());
			wfProcTrack.setToActivityDescr(taskQuery.get(0).getName());
			wfProcInstService.save(wfProcTrack);
			Serializable serializable = wfProcInstService.save(wfProcTrack);
			
			//添加新的流程执行人
			taskService.setAssignee(taskQuery.get(0).getId(), evt.getNewOperator());
			
			if(wfProcInst != null && serializable !=null &&StringUtils.isNotBlank(wfProcInst.getNo())){
				
				wfProcInstService.doPushTaskToOperator("approval",wfProcInst.getNo());
			}
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getEmployeeAdvance")
	public void getEmployeeAdvance(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		ExpenseAdvanceEvt evt = new ExpenseAdvanceEvt();
		EmployeeDetailResp respon = new EmployeeDetailResp();

		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (ExpenseAdvanceEvt) JSONObject.toBean(json, ExpenseAdvanceEvt.class);
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
			
			Double amount = wfProcInstService.getAdvanceAmount(evt.getEmpCode());
			respon.setAmount(amount);
			
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
}
