package com.house.home.web.controller.workflow;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.WfProcPhotoUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.workflow.WorkflowUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Position;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.finance.ExpenseAdvance;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.workflow.Department;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcPhoto;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.workflow.WfProcInstService;
import com.house.home.service.workflow.WorkflowService;
import com.house.home.service.workflow.WorkflowTraceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ?????????????????????
 * 
 */
@Controller
@RequestMapping("/admin/wfProcInst")
public class WfProcInstController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected FormService formService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected WorkflowTraceService traceService;
	@Autowired
	ProcessEngineFactoryBean processEngine;
	
	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private FormTokenManagerImpl formTokenManagerImpl;
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param wfProcInst
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getApplyListByJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getApplyListByJqgrid(HttpServletRequest request,
			HttpServletResponse response,WfProcInst  wfProcInst) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInstService.getApplyListByJqgrid(page, wfProcInst);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllListByJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getAllListByJqgrid(HttpServletRequest request,
			HttpServletResponse response,WfProcInst  wfProcInst) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInst.setCzybh(this.getUserContext(request).getCzybh());
		wfProcInstService.getAllListDetailByJqgrid(page, wfProcInst);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ?????????????????????
	 * @param request
	 * @param response
	 * @param actTask
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridByProcInstNo")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridByProcInstNo(HttpServletRequest request,
			HttpServletResponse response, String wfProcInstNo,String procDefKey) throws Exception {
		JSONObject jsonObject = JSONObject.parseObject(request.getParameter("el"));
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if("4".equals(request.getParameter("type"))){//type:4  ?????????????????????
			wfProcInstService.doUpdateCopyStatus(this.getUserContext(request).getCzybh(),wfProcInstNo);
		}
		wfProcInstService.findByProcInstId(page, wfProcInstNo);
		if(StringUtils.isNotBlank(procDefKey) && StringUtils.isNotBlank(wfProcInstNo)){
			List<Map<String, Object>> hisList = page.getResult();
			List<Map<String, Object>> addList = wfProcInstService.getProcBranch(wfProcInstNo,procDefKey,jsonObject) ;
			if(hisList != null && addList != null){
				hisList.addAll(addList);
				page.setResult(hisList);
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getEmpAccountJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getEmpAccountJqGrid(HttpServletRequest request,
			HttpServletResponse response,String czybh,String actName) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInstService.getEmpAccountJqGrid(page, czybh, actName);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getSupplAccountJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSupplAccountJqGrid(HttpServletRequest request,
			HttpServletResponse response,SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInstService.getSupplAccountJqGrid(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getAdvanceNoByJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getAdvanceNoByJqgrid(HttpServletRequest request,
			HttpServletResponse response,String czybh,String serchData) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInstService.getAdvanceNoByJqgrid(page, czybh, serchData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getExpenseAdvanceJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getExpenseAdvanceJqGrid(HttpServletRequest request,
			HttpServletResponse response,Employee employee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInstService.getExpenseAdvanceJqGrid(page, employee);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getExpenseAdvanceTran")
	@ResponseBody
	public WebPage<Map<String, Object>> getExpenseAdvanceTran(HttpServletRequest request ,
			HttpServletResponse response, Employee employee) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		wfProcInstService.getExpenseAdvanceTran(page, employee);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param type 1.?????????????????? 2.??????????????????3.????????????
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Integer type, String m_umState) {
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_list")
			.addObject("type", type);
	}
	
	/**
	 * '??????????????????
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping("/goAllList")
	public ModelAndView goAllList(HttpServletRequest request,
			HttpServletResponse response) {
		String czybh = this.getUserContext(request).getCzybh();
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_allList").addObject("czybh", czybh);
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping("/goApplyList")
	public ModelAndView goApplyList(HttpServletRequest request,
			HttpServletResponse response) {
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_applyList");
	}
	 
    /**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param wfProcInst.type 1.?????????????????? 2.??????????????????3.????????????
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/findWfProcInst")
    @ResponseBody
    public WebPage<Map<String,Object>> findMyTodoTask(HttpServletRequest request, 
    		HttpServletResponse response,
    		WfProcInst wfProcInst) throws Exception {
    	
    	UserContext uc = UserContextHolder.getUserContext();    	
    	
    	if (uc == null || StringUtils.isBlank(uc.getCzybh())) {
    		logger.error("????????????????????????????????????????????????????????????");
    		return null;
    	}
    	wfProcInst.setUserId(uc.getCzybh()); 
    	Page<Map<String,Object>> page = this.newPageForJqGrid(request);
    	wfProcInstService.findWfProcInstPageBySql(page, wfProcInst);
    	return new WebPage<Map<String,Object>>(page);
    }
    
    /**
     * ????????????
     * @param request
     * @param response
     * @param key
     * @return
     */
    @RequestMapping("/goApply")
	public ModelAndView goApply(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key) {
		// ?????????????????????????????????????????????????????????
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey(key)
			.latestVersion()
			.singleResult();
		String url = FileUploadUtils.DOWNLOAD_URL;
		// ??????????????????ID??????????????????
		//Object formData = formService.getRenderedStartForm(processDefinition.getId());
        WfProcess wfProcess = this.wfProcInstService.getWfProcessByProcKey(processDefinition.getKey());
		String wfProcNo = "";
		if(wfProcess != null){
			wfProcNo = wfProcess.getNo();
			wfProcess.setRemarks((wfProcess.getRemarks()==null?"":wfProcess.getRemarks()).replace("\r\n", "<br/>"));
		}
		Map<String , Object> detailMap = new HashMap<String, Object>();
		List<Map<String, Object>> tables = this.wfProcInstService.getTables(wfProcNo);
		for(int i = 0; i < tables.size(); i++){
			detailMap.put(tables.get(i).get("Code").toString(), 0);
		}
		
		String startMan = this.getUserContext(request).getCzybh();
		String detailJson = "{}";
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		Employee employee = wfProcInstService.get(Employee.class, this.getUserContext(request).getEmnum());
		Department department = new Department();
		if(employee != null && StringUtils.isNotBlank(employee.getDepartment2())){
			department = wfProcInstService.get(Department.class, employee.getDepartment());
			if(department != null){
				employee.setDepartmentDescr(department.getDesc2());
			}
		}
		
		getEmpData(request, employee);
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("processDefinition", processDefinition)
			.addObject("processDefinitionKey", processDefinition.getId())
			.addObject("wfProcNo",wfProcNo)
			.addObject("applyPage", processDefinition.getKey()+".jsp")
			.addObject("m_umState", "A")
			.addObject("detailJson", detailJson)
			.addObject("processInstanceId", "processInstanceId")
			.addObject("processInstanceId", "processInstanceId")
			.addObject("detailList",JSONObject.toJSONString(detailMap))
			.addObject("startMan", startMan)
			.addObject("wfProcess", wfProcess)
			.addObject("url", url)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("employee", employee).addObject("activityId", "startevent")
			.addObject("conSignCmp","")
			//.addObject("wfProcInstNo",wfProcInstNo)
			;
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param processDefinitionId
	 */
	@RequestMapping("/doApply")
	public void doApply(HttpServletRequest request, HttpServletResponse response, @RequestParam("processDefinitionId") String processDefinitionId) {
		try{
			//??????????????????,????????????????????????????????????,???????????????????????????
			Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(request.getParameterMap());
	
			UserContext uc = UserContextHolder.getUserContext();
	    	
	    	if (uc == null || StringUtils.isBlank(uc.getCzybh())) {
	    		ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????????????????");
	    		logger.error("????????????????????????????????????????????????????????????");
	    		return;
	    	}
			String detailJson = request.getParameter("detailJson");
	    	
			// ????????????????????????????????????
			Map<String, String> pushWfProcInstNo = new HashMap<String, String>();
	    	wfProcInstService.doStartProcInst(formProperties, processDefinitionId, uc.getCzybh(),detailJson,pushWfProcInstNo);
	    	
	    	// ???????????????????????????????????????????????????????????????
	    	if(StringUtils.isNotBlank(pushWfProcInstNo.get("pushWfProcInstNo"))){
	    		wfProcInstService.doPushTaskToOperator("",pushWfProcInstNo.get("pushWfProcInstNo"));
	    	}
	       
	    	ServletUtils.outPrintSuccess(request, response, "????????????");
		}catch (Exception e) {
    		ServletUtils.outPrintFail(request, response, "???????????????");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/goPhotoView")
	public ModelAndView goPhotoView(HttpServletRequest request,
			HttpServletResponse response,String url) {
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_photoView")
		.addObject("url", url);
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param processDefinitionId
	 * @param resourceType
	 * @throws Exception
	 */
	@RequestMapping("/getTraceImage")
	public void getTraceImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("processInstanceId") String processInstanceId) throws Exception {
		
		InputStream inputStream = null;
		
		HistoricProcessInstance historicProcessInstance = historyService
			.createHistoricProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		
		if (historicProcessInstance.getEndTime() != null){ // ???????????????????????????????????????????????????
			inputStream = repositoryService.getProcessDiagram(historicProcessInstance.getProcessDefinitionId());
		} else {
			BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
			List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
			Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
			//inputStream = ProcessDiagramGenerator.generateDiagram(
			//		bpmnModel, "png", activeActivityIds);
	        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId());
	        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
	        List<String> highLightedFlows = wfProcInstService.getHighLightedFlows(definitionEntity,highLightedActivitList);
	        inputStream = ProcessDiagramGenerator.generateDiagram(
					bpmnModel, "png", activeActivityIds,highLightedFlows);
		} 
		IOUtils.copy(inputStream, response.getOutputStream());
	}
	
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param processDefinitionId
	 * @param resourceType
	 * @throws Exception
	 */
	@RequestMapping("/getTraceImageApply")
	public void getTraceImageApply(HttpServletRequest request, HttpServletResponse response,
			String el,String pdID) throws Exception {
		
		List<String> hightLight = new ArrayList<String>();
		InputStream inputStream = null;
		boolean hasGetway = false ;
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("el"));
		PvmActivity pvmActivity =null;
		ProcessDefinitionEntity processDefinitionEntity = null;
		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(pdID);  
		for (int i=0;i<processDefinitionEntity.getActivities().size();i++){
			//exclusivegateway1 //?????? el?????????
			if("exclusivegateway1".equals(processDefinitionEntity.getActivities().get(i).getId())){
				hasGetway = true;
				List<PvmTransition> inComingTransitions = processDefinitionEntity.getActivities().get(i).getIncomingTransitions();
				hightLight.add(inComingTransitions.get(0).getId());
				List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
				for(int p = 0 ;p<pvmTransitions.size();p++){//????????????????????????????????????
					if(wfProcInstService.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
						pvmActivity = pvmTransitions.get(p).getDestination();
						break;
					}
				}
				if(pvmActivity != null){
					hightLight.add(pvmActivity.getIncomingTransitions().get(0).getId());
					break;
				}
			}
		}
		if(pvmActivity==null && hasGetway){
			return;
		}else {
			BpmnModel bpmnModel = repositoryService.getBpmnModel(pdID);
			if(pvmActivity != null){
				wfProcInstService.getHighLightedFlows(pvmActivity.getId(),processDefinitionEntity,hightLight, jsonObject);
			}
			List<String> activeActivityIds = new ArrayList<String>();
			Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
	        inputStream = ProcessDiagramGenerator.generateDiagram(
					bpmnModel, "png", activeActivityIds,hightLight);
			IOUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
	/**
	 * ?????????????????????????????????????????????xml????????????????????????
	 * @param request
	 * @param response
	 * @param processDefinitionId
	 * @param resourceType
	 * @throws Exception
	 */
	@RequestMapping("/getResource")
	public void getProcDefResource(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("processDefinitionId") String processDefinitionId,
			@RequestParam("resourceType") String resourceType) throws Exception {
		
		if (!resourceType.equals("image") && !resourceType.equals("xml")) {
			ServletUtils.outPrintFail(request, response, "?????????????????????");
			return;
		}
		
		InputStream inputStream = null;
		if (resourceType.equals("image")) {
			inputStream = repositoryService.getProcessDiagram(processDefinitionId);
		} else if (resourceType.equals("xml")) {
			inputStream = repositoryService.getProcessModel(processDefinitionId);
			
		}
		IOUtils.copy(inputStream, response.getOutputStream());
	}
	
	/**
     * ??????????????????
     * @param request
     * @param response
     * @param key
     * @return
     */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, @RequestParam("type") Integer type,
								@RequestParam("taskId") String taskId,
								@RequestParam("processInstanceId") String processInstanceId, String m_umState){
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		
		WfProcInst wfProcInst = this.wfProcInstService.getWfProcInstByActProcInstId(processInstanceId);
	
		String procKey = wfProcInstService.getProcKeyByNo(wfProcInst.getWfProcNo());
		WfProcess wfProcess = this.wfProcInstService.get(WfProcess.class, wfProcInst.getWfProcNo());

		List<Map<String, Object>> tables = this.wfProcInstService.getTables(wfProcInst.getWfProcNo());
		
		Map<String, Object> datas = new HashMap<String, Object>();
		Map<String , Object> detailMap = new HashMap<String, Object>();
		for(int i = 0; i < tables.size(); i++){
			List<Map<String, Object>> rows = this.wfProcInstService.getTableInfo(tables.get(i).get("Code").toString(), wfProcInst.getNo());
			int detailNum= this.wfProcInstService.getDetailNum(tables.get(i).get("Code").toString(), wfProcInst.getNo()); 
			detailMap.put(tables.get(i).get("Code").toString(), detailNum);
			for(int j = 0; j < rows.size(); j++){
				for(Entry<String, Object> entry : rows.get(j).entrySet()){
					datas.put("fp__"+tables.get(i).get("Code")+"__"+j+"__"+entry.getKey(), entry.getValue());
				}
			}
		}
		
		/*int detailNum =0;
		if(tables.size()>1){
			detailNum = this.wfProcInstService.getDetailNum(tables.get(1).get("Code").toString(), wfProcInst.getNo()); 
		}	*/	
		boolean isEnd = false;
		if(wfProcInst.getEndTime()!= null){
			isEnd = true;
		}
		String activityId ="";
		String url = FileUploadUtils.DOWNLOAD_URL;

		ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId()); 
		ProcessInstance pi=runtimeService.createProcessInstanceQuery() // ??????????????????id??????????????????
                .processInstanceId(processInstanceId)
                .singleResult();
		if(pi != null){
			ActivityImpl activityImpl=processDefinitionEntity.findActivity(pi.getActivityId());
			if(activityImpl != null){
				activityId = activityImpl.getId();
			}
		}
		String taskName = "";
    	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task != null && 1 ==type ){
			taskName = task.getName();
		}
    	
		String printMan = this.getUserContext(request).getZwxm();
		String detailJson= JSONObject.toJSONString(datas);
		String detailList= JSONObject.toJSONString(detailMap);

		Map<String, FormToken> formsInSession = null;  
        HttpSession session = request.getSession();  
        
        formsInSession = (Map<String, FormToken>) session.getAttribute("_forms_in_session");  
        if (formsInSession == null) {  
            formsInSession = Maps.newLinkedHashMap();
            session.setAttribute("_forms_in_session", formsInSession);  
        } 
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		
		String empNum = this.getUserContext(request).getEmnum();
		Employee employee = wfProcInstService.get(Employee.class, empNum);
		String conSignCmp = "";
		if(employee != null){
			conSignCmp = employee.getConSignCmp();
		}
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("wfProcInstNo", wfProcInst.getNo())
			.addObject("wfProcNo", wfProcInst.getWfProcNo())
			.addObject("piId", historicProcessInstance.getId())
			.addObject("processDefinitionKey", historicProcessInstance.getProcessDefinitionId())
			.addObject("applyPage", procKey+".jsp")
			.addObject("datas", datas)
			.addObject("m_umState", m_umState)
			.addObject("taskId", taskId)
			.addObject("taskName", taskName)
			.addObject("type", type)
			.addObject("detailJson",detailJson)
			.addObject("detailList", detailList)
			.addObject("isEnd", isEnd)
			.addObject("printMan", printMan)
			.addObject("wfProcess", wfProcess).addObject("url", url)
			.addObject("activityId", activityId)
			.addObject("procKey",procKey)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("formsInSession",formsInSession)
			.addObject("conSignCmp", conSignCmp);
	}
	
	/**
	 * ?????????????????????
	 * @param request
     * @param response
     * @param taskId
     * @param processInstanceId
	 */
	@RequestMapping("/goTransfer")
	public ModelAndView goTransfer(HttpServletRequest request, HttpServletResponse response,
							@RequestParam("taskId") String taskId,
							@RequestParam("processInstanceId") String processInstanceId,
							@RequestParam("title") String title,
							@RequestParam("operDescr") String operDescr){
		operDescr = "";
		List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		if(StringUtils.isNotBlank(taskQuery.get(0).getAssignee())){
			Czybm czybm = wfProcInstService.get(Czybm.class,taskQuery.get(0).getAssignee());
			if(czybm != null){
				operDescr = czybm.getZwxm();
			} else {
				Employee employee = new Employee();
				employee = wfProcInstService.get(Employee.class, taskQuery.get(0).getAssignee());
				if(employee != null){
					operDescr = employee.getNameChi();
				}
			}
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
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_transfer")
		.addObject("processInstanceId", processInstanceId)
			.addObject("title", title)
			.addObject("operDescr", operDescr)
			.addObject("taskId", taskId);
	}
	
	/**
	 * ?????????????????????
	 * @param request
     * @param response
     * @param taskId
     * @param processInstanceId
	 */
	@RequestMapping("/goTransferInst")
	public ModelAndView goTransferInst(HttpServletRequest request, HttpServletResponse response,
							@RequestParam("taskId") String taskId,
							@RequestParam("processInstanceId") String processInstanceId,
							@RequestParam("title") String title){
		title = "";
		String operDescr = "";
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(processInstanceId);
		Czybm applyCzy= new Czybm();
		WfProcess wfProcess = new WfProcess();
		
		if (wfProcInst != null) {
			applyCzy = wfProcInstService.get(Czybm.class, wfProcInst.getStartUserId());
			wfProcess = wfProcInstService.get(WfProcess.class, wfProcInst.getWfProcNo());
			if(applyCzy != null && wfProcess != null){
				title = applyCzy.getZwxm()+"?????????"+wfProcess.getDescr();
			}
		}
		
		
		List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
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
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_transfer")
		.addObject("processInstanceId", processInstanceId)
			.addObject("title", title)
			.addObject("operDescr", operDescr)
			.addObject("taskId", taskId);
	}
    /**
     * ????????????
     * @param request
     * @param response
     * @param taskId
     * @param comment
     */
    @RequestMapping("/doApprovalTask")
	public void doApprovalTask(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("taskId") String taskId,
			@RequestParam(value="comment", required=false) String comment,
			@RequestParam(value = "wfProcInstNo",required = false) String wfProcInstNo,
			@RequestParam(value = "processInstId",required = false) String processInstId) {
		Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(request.getParameterMap());
		
		boolean isProcOperator = wfProcInstService.isProcOperator(taskId, this.getUserContext(request).getCzybh());
		if(!isProcOperator){
	        ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
	        return;
		}
		
		doCompleteTask(request, response, taskId, "approval", comment,wfProcInstNo,processInstId,formProperties);
    }
    
    /**
     * ????????????
     * @param request
     * @param response
     * @param taskId
     * @param comment
     */
    @RequestMapping("/doRejectTask")
	public void doRejectTask(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("taskId") String taskId,
			@RequestParam(value="comment", required=false) String comment,
		@RequestParam(value = "wfProcInstNo",required = false) String wfProcInstNo,
		@RequestParam(value = "returnNode",required = true) String returnNode) {
		Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(request.getParameterMap());
		
		boolean isProcOperator = wfProcInstService.isProcOperator(taskId, this.getUserContext(request).getCzybh());
		if(!isProcOperator){
	        ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
	        return;
		}
		
		doCompleteTask(request, response, taskId, returnNode, comment,wfProcInstNo,"",formProperties);
	}

    /**
     * ??????
     * @param request
     * @param response
     * @param taskId
     */
    @RequestMapping("/doCancelProcInst")
	public void doCancelProcInst(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("taskId") String taskId,
			@RequestParam(value="comment", required=false) String comment,
			@RequestParam(value = "wfProcInstNo",required = false) String wfProcInstNo) {

        Object object = taskService.getVariable(taskId, "CanCancel");
        if(object != null && "0".equals(object)){
	        ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????");
        	return;
        }
    	
    	doCompleteTask(request, response, taskId, "cancel", comment,wfProcInstNo,"",null);
	}
    
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param taskId
	 * @param approval
	 * @param comment
	 */
	@RequestMapping("/doCompleteTask")
	public void doCompleteTask(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("taskId") String taskId,
			@RequestParam(value="status", required=false) String status,
			@RequestParam(value="comment", required=false) String comment,
			@RequestParam(value = "wfProcInstNo",required = false) String wfProcInstNo,
			@RequestParam(value = "processInstId",required = false) String processInstId,
			@RequestParam(value = "formProperties",required = false) Map<String, Object> formProperties) {
		try{
			UserContext uc = UserContextHolder.getUserContext();
			ProcessDefinitionEntity processDefinitionEntity = workflowService.findProcessDefinitionEntityByTaskId(taskId);
			
			if("applyman".equals(status)){
				if(processDefinitionEntity.findActivity(status)==null){
					ServletUtils.outPrintFail(request, response, "????????????????????????????????????");
					return;
				}
			}
			
			wfProcInstService.doCompleteTask(taskId, uc.getCzybh(), status, comment,processInstId,formProperties);
			if(StringUtils.isNotBlank(wfProcInstNo)){
				wfProcInstService.doPushTaskToOperator(status,wfProcInstNo);
			}
			
	        ServletUtils.outPrintSuccess(request, response, "???????????????");
		}catch (Exception e) {
			
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getWfProcTrack")
	@ResponseBody
	public WebPage<Map<String,Object>> getWfProcTrack(HttpServletRequest request, HttpServletResponse response, String wfProcInstNo){

    	UserContext uc = UserContextHolder.getUserContext();
    	
    	if (uc == null || StringUtils.isBlank(uc.getCzybh())) {
    		logger.error("????????????????????????????????????????????????????????????");
    		return null;
    	}
    	Page<Map<String,Object>> page = this.newPageForJqGrid(request);
    	page.setPageSize(-1);
		this.wfProcInstService.getWfProcTrack(page, wfProcInstNo);
    	return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doAllWfProcExcel")
	public void doAllWfProcExcel(HttpServletRequest request ,HttpServletResponse response,
			WfProcInst wfProcInst){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		wfProcInst.setCzybh(this.getUserContext(request).getCzybh());
		wfProcInstService.getAllListDetailByJqgrid(page, wfProcInst);
		getExcelList(request);
		columnList.add("startuserid");
		columnList.add("startuserdescr");
		columnList.add("department2descr");
		columnList.add("apptruck");
		columnList.add("applypedescr");
		titleList.add("???????????????");
		titleList.add("???????????????");
		titleList.add("???????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doCopyExcel")
	public void doCopyExcel(HttpServletRequest request ,HttpServletResponse response,
			WfProcInst wfProcInst){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = this.getUserContext(request);
    	wfProcInst.setUserId(uc.getCzybh()); 

		wfProcInstService.findWfProcInstPageBySql(page, wfProcInst);
		getExcelList(request);
		
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"???????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doComment")
	public void doComment(HttpServletRequest request, HttpServletResponse response,
			WfProcInst wfProcInst) {
		try{
			UserContext uc = UserContextHolder.getUserContext();
			if(StringUtils.isNotBlank(wfProcInst.getNo()) && StringUtils.isNotBlank(wfProcInst.getEmpComment())){
				wfProcInstService.doSaveComment(wfProcInst.getNo(), wfProcInst.getEmpComment(), uc.getCzybh());
				ServletUtils.outPrintSuccess(request, response, "???????????????");
			}else{
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????");
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "???????????????");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getOperator")
	@ResponseBody
	public List<Object> getOperator(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> el,String pdID,String department,String wfProcNo) throws Exception{
		logger.debug("ajax????????????");   
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("el"));
		List<Object> list = wfProcInstService.getOperator(jsonObject,pdID,department,wfProcNo,this.getUserContext(request).getCzybh());
		if(list == null){
			ServletUtils.outPrintFail(request, response,"?????????????????????????????????,??????????????????");
		}
		return list;
	}
	
	@RequestMapping("/getProcTaskTableStru")
	@ResponseBody
	public List<Map<String, Object>> getProcTaskTableStru(HttpServletRequest request,HttpServletResponse response,
			String wfProcNo, String taskDefkey) throws Exception{
		logger.debug("ajax????????????");   
		List<Map<String, Object>> list = wfProcInstService.getProcTaskTableStru(wfProcNo, taskDefkey);
		
		return list;
	}
	
	@RequestMapping("/getDeptCode")
	@ResponseBody
	public List<Map<String, Object>> getDeptCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("ajax????????????");  
		List<Map<String, Object>>deptList = new ArrayList<Map<String, Object>>();
		deptList = wfProcInstService.getDeptListByCzybh(this.getUserContext(request).getCzybh());
		
        return deptList;
	}
	
	@RequestMapping("/getCustStakeholder")
	@ResponseBody
	public Map<String, Object> getCustStakeholder(HttpServletRequest request,HttpServletResponse response,
			String roll,String custCode) throws Exception{
		logger.debug("ajax????????????");  
		
        return wfProcInstService.getCustStakeholder(roll,custCode);
	}
	
	@RequestMapping("/getDetails")
	@ResponseBody
	public List<Map<String, Object>> getDetails(HttpServletRequest request,HttpServletResponse response,
			String tableCode,String wfProcInstNo) throws Exception{
		logger.debug("ajax????????????");  
		List<Map<String, Object>>deptList = new ArrayList<Map<String, Object>>();
		deptList = wfProcInstService.getDetails(tableCode,wfProcInstNo);
		
        return deptList;
	}
	
	@RequestMapping("/goUpdateCopyCzy")
	public ModelAndView goUpdaetCopyCzy(HttpServletRequest request,
			HttpServletResponse response,String nameChi,String oldNameChi,String assignee) {
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_updateCopyCzy")
		.addObject("nameChi", nameChi).addObject("oldNameChi", oldNameChi)
		.addObject("assignee",assignee);
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes", "null" })
	@RequestMapping("/uploadWfProcPic")
	public void uploadWfProcPic(HttpServletRequest request,
			HttpServletResponse response) {
//		UploadPhotoResp respon = new UploadPhotoResp();
//		DesignDemo designDemo =new DesignDemo();
//		try {
//			String fileRealPath = "";//???????????????????????? 
//			String firstFileName = ""; 
//			String PhotoPath="";
//			DiskFileItemFactory fac = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(fac);
//			upload.setHeaderEncoding("UTF-8");
//			// ??????????????????????????????????????? 500k
//			//upload.setSizeMax(500 * 1024);
//			// ????????????????????????
//			List fileList = upload.parseRequest(request);
//			Iterator it = fileList.iterator();
//			String wfProcInstNo=request.getParameter("wfProcInstNo");
//					
//			List<String> fileRealPathList = new ArrayList<String>();
//			List<String> PhotoPathList = new ArrayList<String>();
//			List<String> fileNameList = new ArrayList<String>();
//			Serializable serializable = null;
//			// ??????????????????????????????
//			while (it.hasNext()) {
//				FileItem obit = (FileItem) it.next();
//				// ??????????????? ????????????
//				if (obit.isFormField()) { //?????????,??????????????????
//					String fieldName = obit.getFieldName();
//					String fieldValue = obit.getString("UTF-8");
//					if ("wfProcInstNo".equals(fieldName)){
//						wfProcInstNo = fieldValue;
//					}
//				}
//				// ??????????????????
//				if (obit instanceof DiskFileItem) {
//					DiskFileItem item = (DiskFileItem) obit;
//					// ??????item????????????????????????
//					// ????????????????????????
//					String fileName = item.getName();
//					if (fileName != null) {
//						if(fileName.indexOf("?")!=-1){
//							fileName=fileName.substring(0,fileName.indexOf("?"));
//							
//						}
//						firstFileName = fileName.substring(//??????????????????
//								fileName.lastIndexOf("\\") + 1);
//						String formatName = firstFileName
//								.substring(firstFileName.lastIndexOf("."));//?????????????????????
//						String fileNameNew = System.currentTimeMillis()+formatName;
//						//saveCustDocName=fileNameNew;
//						String filePath = getWfProcPicPath(fileNameNew,"");
//						PhotoPath=getWfProcPicDownloadPath(request,fileNameNew,"")+fileNameNew;
//						//savePath=PhotoPath;
//						FileUploadServerMgr.makeDir(filePath);
//						fileRealPath = filePath+fileNameNew;// ????????????????????????
//						BufferedInputStream in = new BufferedInputStream(
//								item.getInputStream());// ????????????????????? 
//						
//						if("jpeg".equals(fileRealPath.split("\\.")[1].toLowerCase()) || "png".equals(fileRealPath.split("\\.")[1].toLowerCase()) ||
//								"jpg".equals("jpeg".equals(fileRealPath.split("\\.")[1].toLowerCase()))||"gif".equals(fileRealPath.split("\\.")[1].toLowerCase())){
//							ServletUtils.compressImage(in, fileRealPath, 300, 1.0f);
//						}
//						
//						BufferedOutputStream outStream = new BufferedOutputStream(
//								new FileOutputStream(new File(fileRealPath)));// ?????????????????????
//						Streams.copy(in, outStream, true);// ????????????????????????????????????????????????
//				        //??????????????????
//				        serializable = doSavePicDetail(wfProcInstNo, fileNameNew, this.getUserContext(request).getCzybh());
//				        //????????????????????????
//				        fileRealPathList.add(fileRealPath);
//						PhotoPathList.add(PhotoPath);
//						fileNameList.add(fileNameNew);
//					}
//				}
//			}
//			
//			respon.setPhotoPathList(PhotoPathList);
//			respon.setPhotoNameList(PhotoPathList);
//			ServletUtils.outPrintSuccess(request, response,serializable.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			ServletUtils.outPrintFail(request, response, "????????????");
//		}
		
		try {

			// ????????????
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String wfProcInstNo=multipartFormData.getParameter("wfProcInstNo");

			FileItem fileItem = multipartFormData.getFileItem();
			
			// ????????????????????????????????????
			WfProcPhotoUploadRule wfProcPhotoUploadRule = new WfProcPhotoUploadRule(fileItem.getName());

			// ?????????????????????????????????????????? 
			Result result = FileUploadUtils.upload(fileItem.getInputStream(), 
					wfProcPhotoUploadRule.getFileName(),wfProcPhotoUploadRule.getFilePath());
			
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("?????????????????????????????????:{}",fileItem.getName());
				ServletUtils.outPrintFail(request, response, "??????????????????");
				return;
			}

			Serializable serializable = null;
	        serializable = doSavePicDetail(wfProcInstNo, wfProcPhotoUploadRule.getFullName(), this.getUserContext(request).getCzybh());

			ServletUtils.outPrintSuccess(request, response,serializable.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
		
	}
	
	public Serializable doSavePicDetail(String wfProcInstNo,String photoName,String lastUpdatedBy){
		
		WfProcPhoto wfProcPhoto= new WfProcPhoto();
		wfProcPhoto.setDescr(photoName);
		wfProcPhoto.setWfProcInstNo(wfProcInstNo);
		wfProcPhoto.setLastUpdate(new Date());
		wfProcPhoto.setLastUpdatedBy(lastUpdatedBy);
		wfProcPhoto.setExpired("F");
		wfProcPhoto.setActionLog("ADD");
		
		Serializable saSerializable = wfProcInstService.save(wfProcPhoto);
		return saSerializable;
	}
	
	@RequestMapping("/findWfProcInstPic")
	@ResponseBody
	public WebPage<Map<String,Object>> findDesignPic(HttpServletRequest request,
			HttpServletResponse response, String wfProcInstNo,String photoPK) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wfProcInstService.findWfProcInstPic(page, wfProcInstNo,photoPK);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goUpload")
	public ModelAndView goUpload(HttpServletRequest request,
			HttpServletResponse response,String wfProcInstNo) {
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_upload")
		.addObject("wfProcInstNo", wfProcInstNo);
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response,String docNameArr){
		try{ 
			
			String zip = "????????????_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss")+".zip";
			String[] docName = docNameArr.split(",");
			StringBuilder[] files = null;
			files = new StringBuilder[docName.length];
			
			for(int i=0;i<docName.length;i++){
				files[i] = new StringBuilder();
				files[i].append(FileUploadUtils.DOWNLOAD_URL + docName[i]);
			}
			
			ServletUtils.downLoadFiles(request,response,files,zip,true);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doDelPhoto")
	public void doDelPhoto(HttpServletRequest request,HttpServletResponse response,String pks){
		try{ 
			wfProcInstService.doDelPhoto(pks);
			
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getWfProcPicPath(String fileName,String wfProcInstNo){
		String wfProcPicNameNew = WfProcPhotoUploadRule.FIRST_LEVEL_PATH;
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
			return wfProcPicNameNew;
		}
	}
	
	public static String getWfProcPicDownloadPath(HttpServletRequest request, String fileName,String wfProcInstNo){
		String path = getWfProcPicPath(fileName,wfProcInstNo);
		return FileUploadUtils.DOWNLOAD_URL+path;
	}
    
	@RequestMapping("/goEmpAccount")
	public ModelAndView goEmpAccount(HttpServletRequest request,
			HttpServletResponse response,String czybh) {
		if(StringUtils.isBlank(czybh)){
			czybh = this.getUserContext(request).getCzybh();
		}
		return new ModelAndView("admin/workflow/utilPage/empAccount")
			.addObject("czybh", czybh);
	}
	
	@RequestMapping("/goSupplAccount")
	public ModelAndView goSupplAccount(HttpServletRequest request,
			HttpServletResponse response,SplCheckOut splCheckOut) {
		return new ModelAndView("admin/workflow/utilPage/supplAccount")
			.addObject("splCheckOut",splCheckOut);
	}
	
	@RequestMapping("/getAdvanceNo")
	public ModelAndView getAdvanceNo(HttpServletRequest request,
			HttpServletResponse response,String czybh) {
		if(StringUtils.isBlank(czybh)){
			czybh = this.getUserContext(request).getCzybh();
		}
		return new ModelAndView("admin/workflow/utilPage/advanceNo")
			.addObject("czybh", czybh);
	}
	
	@RequestMapping("/getEmployeeMult")
	public ModelAndView getEmployeeMult(HttpServletRequest request,
			HttpServletResponse response,String czybh) {
		if(StringUtils.isBlank(czybh)){
			czybh = this.getUserContext(request).getCzybh();
		}
		return new ModelAndView("admin/workflow/utilPage/employee_multCode")
			.addObject("czybh", czybh);
	}
	
	@RequestMapping("/doSaveAccount")
	public void doSaveAccount(HttpServletRequest request,HttpServletResponse response, String actName, String bank, String cardId,String subBranch) {
		try{
			wfProcInstService.doSaveAccount(actName,bank,cardId,subBranch,this.getUserContext(request).getCzybh());
	        ServletUtils.outPrintSuccess(request, response, "????????????");
		}catch (Exception e) {
    		ServletUtils.outPrintFail(request, response, "???????????????");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getAdvanceAmount")
	@ResponseBody
	public Double getAdvanceAmount(HttpServletRequest request,HttpServletResponse response,String empCode) throws Exception{
		logger.debug("ajax????????????");   
		Double advanceAmount = wfProcInstService.getAdvanceAmount(empCode);
		
		return advanceAmount;
	}
	
	/**
     * ??????????????????
     * @param request
     * @param response
     * @param taskId
     * @param comment
     */
    @RequestMapping("/doApprFinanceTask")
	public void doApprFinanceTask(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("taskId") String taskId,
			@RequestParam(value="comment", required=false) String comment,
			@RequestParam(value = "wfProcInstNo",required = false) String wfProcInstNo,
			@RequestParam(value = "processInstId",required = false) String processInstId) {
		Map<String, Object> formProperties = WorkflowUtils.convertParamMapToFormProp(request.getParameterMap());
		
		boolean error = false;
		String tableName = wfProcInstService.getMainTableName(formProperties.get("wfProcNo").toString());
		for(Entry<String, Object> entry : ((IdentityHashMap<String, Object>)formProperties.get("tables")).entrySet()){
			if(StringUtils.isNotBlank(tableName) && tableName.equals(entry.getKey())){
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				if(map.get("BefAmount") != null && map.get("DeductionAmount") != null){
					Double aftAmount = Double.parseDouble(map.get("BefAmount").toString());
					Double dedutionAmount = Double.parseDouble(map.get("DeductionAmount").toString());
					if(aftAmount < dedutionAmount){
						error = true;
					}
				}
			}
		}
		if(error){
			ServletUtils.outPrintFail(request, response, "????????????,??????????????????????????????,??????????????????????????????");
			return;
		}else {
			doCompFinanceTask(request, response, taskId, "approval", comment,wfProcInstNo,processInstId,formProperties);
		}
		
    }
    
    @RequestMapping("/doCompFinanceTask")
	public void doCompFinanceTask(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("taskId") String taskId,
			@RequestParam(value="status", required=false) String status,
			@RequestParam(value="comment", required=false) String comment,
			@RequestParam(value = "wfProcInstNo",required = false) String wfProcInstNo,
			@RequestParam(value = "processInstId",required = false) String processInstId,
			@RequestParam(value = "formProperties",required = false) Map<String, Object> formProperties) {
		try{
			UserContext uc = UserContextHolder.getUserContext();
			//?????????????????????serviceImpl???????????????  serviceImpl??????: WfProcInstService_+procKey+Impl
			Method method= ReflectionUtils.findMethod(SpringContextHolder.getBean("wfProcInstService_"+formProperties.get("procKey").toString()+"Impl").getClass(), "doCompFinanceTask"
					,new Class[]{String.class, String.class, String.class, String.class, String.class, Map.class} );
	        ReflectionUtils.invokeMethod(method,SpringContextHolder.getBean("wfProcInstService_"+formProperties.get("procKey").toString()+"Impl"),taskId, uc.getCzybh(), status, comment,processInstId,formProperties);
			
	        if(StringUtils.isNotBlank(wfProcInstNo)){
				wfProcInstService.doPushTaskToOperator(status,wfProcInstNo);
			}
			
	        ServletUtils.outPrintSuccess(request, response, "???????????????");
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "???????????????");
			e.printStackTrace();
		}
	}
    
    @RequestMapping("/doUpdateOperator")
    public void doUpdateOperator(HttpServletRequest request, HttpServletResponse response,String taskId,String newOperator,
    		String processInstanceId){
    	try{
	    	List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	    	WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(processInstanceId);
	    	
	    	
			if(StringUtils.isNotBlank(taskQuery.get(0).getAssignee())){
				taskService.deleteCandidateUser(taskQuery.get(0).getId(), taskQuery.get(0).getAssignee());
			}else{
				List<IdentityLink> identityLisk= taskService.getIdentityLinksForTask(taskQuery.get(0).getId());
				IdentityLinkEntity identityLinkEntity = (IdentityLinkEntity) identityLisk.get(0);
				taskService.deleteGroupIdentityLink(identityLinkEntity.getTaskId(), identityLinkEntity.getId(),identityLinkEntity.getType());
				//taskService.deleteCandidateGroup(taskQuery.get(0).getId(), identityLisk.get(0).get.getGroupId());
			}
			taskService.setAssignee(taskQuery.get(0).getId(), newOperator);
			
			// ??????????????????
			WfProcTrack wfProcTrack = new WfProcTrack();
			wfProcTrack.setActionLog("ADD");
			wfProcTrack.setExpired("F");
			wfProcTrack.setLastUpdate(new Date());
			wfProcTrack.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			wfProcTrack.setRemarks("????????????");
			wfProcTrack.setWfProcInstNo(wfProcInst.getNo());
			wfProcTrack.setOperId(this.getUserContext(request).getCzybh());
			wfProcTrack.setActionType("7");//????????????  ??????
			wfProcTrack.setFromActivityId(taskQuery.get(0).getId()); // ????????????????????????
			wfProcTrack.setFromActivityDescr(taskQuery.get(0).getName());
			wfProcTrack.setToActivityId(taskQuery.get(0).getId());
			wfProcTrack.setToActivityDescr(taskQuery.get(0).getName());
			Serializable serializable = wfProcInstService.save(wfProcTrack);
			
			if(wfProcInst != null && serializable !=null &&StringUtils.isNotBlank(wfProcInst.getNo())){
				
	    		wfProcInstService.doPushTaskToOperator("approval",wfProcInst.getNo());
	    	}
			
	        ServletUtils.outPrintSuccess(request, response, "???????????????");
    	} catch (Exception e) {
	    	ServletUtils.outPrintFail(request, response, "???????????????");
			e.printStackTrace();
		}
    }
    
    public void getEmpData(HttpServletRequest request, final Employee employee){
    	
    	if(employee != null){
    		Double advanceAmount = wfProcInstService.getAdvanceAmount(this.getUserContext(request).getEmnum());
    		if(advanceAmount != null){
    			employee.setAdvanceAmount(advanceAmount);
    		}
    		employee.setLeaveDate(DateUtil.startOfTheDay(new Date()));
    		
    		if(StringUtils.isNotBlank(employee.getDepartment())){
    			Map<String, Object> cmpData = wfProcInstService.getEmpCompany(employee.getDepartment());
    			if(cmpData != null){
    				employee.setCmpCode(cmpData.get("Code").toString());
    				employee.setCmpDescr(cmpData.get("CmpDescr").toString());
    			}
    			
    			Department department = new Department();
    			department = wfProcInstService.get(Department.class, employee.getDepartment());
    			if(department != null){
    				employee.setDepartmentDescr(department.getDesc2());
    			}
    			
    		}
    		
    		Department2 department2 = wfProcInstService.get(Department2.class, employee.getDepartment2()==null ? "":employee.getDepartment2());
    		if(department2 != null ){
    			employee.setDepartment2Descr(department2.getDesc2());
    			if("1".equals(department2.getDepType()) || "2".equals(department2.getDepType()) || "0".equals(department2.getDepType())){
    				employee.setDepType("????????????");
    			}else {
    				employee.setDepType("????????????");
    			}
    			if("1".equals(employee.getIsLead())){
    				employee.setType("????????????");
    			}else {
    				if("1".equals(department2.getDepType()) || "2".equals(department2.getDepType()) || "0".equals(department2.getDepType())){
    					employee.setType("????????????");
    				}else {
    					employee.setType("????????????");
    				}
    			}
    		}else {
    			Department1 department1 = wfProcInstService.get(Department1.class, employee.getDepartment1() == null ? "":employee.getDepartment1());
    			if(department1 != null ){
    				employee.setDepartment2Descr(department1.getDesc2());
    				if("1".equals(department1.getDepType()) || "2".equals(department1.getDepType()) || "0".equals(department1.getDepType()) ){
    					employee.setDepType("????????????");
    				}else {
    					employee.setDepType("????????????");
    				}
    				if("1".equals(employee.getIsLead())){
    					employee.setType("????????????");
    				}else {
    					if("1".equals(department1.getDepType()) || "2".equals(department1.getDepType()) || "0".equals(department1.getDepType())){
    						employee.setType("????????????");
    					}else {
    						employee.setType("????????????");
    					}
    				}
    			}
    		}
    		
    		Position position = wfProcInstService.get(Position.class, employee.getPosition()==null ?"":employee.getPosition());
    		if(position != null ){
    			employee.setPositionDescr(position.getDesc2());
    		}
    		if("1".equals(employee.getIsLead())){
    			employee.setType("????????????");
    		}
    	}
    }
    
    /**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param nos
	 */
	@RequestMapping("doUpdatePrint")
	public void doUpdatePrint(HttpServletRequest request, HttpServletResponse response, String no){
		try{
			if(StringUtils.isNotBlank(no)){
				WfProcInst wfProcInst = wfProcInstService.get(WfProcInst.class, no);
					if(StringUtils.isNotBlank(wfProcInst.getNo())){
						if(wfProcInst.getPrintTimes()==null)
							wfProcInst.setPrintTimes(0);
						wfProcInst.setPrintTimes(wfProcInst.getPrintTimes()+1);
						wfProcInstService.update(wfProcInst);
					}
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "????????????????????????");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/goExpenseAdvance")
	public ModelAndView goExpenseAdvance(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_expenseAdvance");
	}
	
	@RequestMapping("/goRefund")
	public ModelAndView goRefund(HttpServletRequest request, HttpServletResponse response,Employee employee){
		
		Double advanceAmount = employee.getAdvanceAmount();  
		
		if(StringUtils.isNotBlank(employee.getNumber())){
			employee = wfProcInstService.get(Employee.class, employee.getNumber());
		}
		
		employee.setAdvanceAmount(advanceAmount);
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_refund").addObject("employee", employee);
	}
	
	@RequestMapping("/goExpenseAdvanceTran")
	public ModelAndView goExpenseAdvanceTran(HttpServletRequest request, HttpServletResponse response,Employee employee){
		
		if(StringUtils.isNotBlank(employee.getNumber())){
			employee = wfProcInstService.get(Employee.class, employee.getNumber());
		}
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_expenseAdvanceTran").addObject("employee", employee);
	}
	
	@RequestMapping("/doSaveRefund")
    public void doSaveRefund(HttpServletRequest request, HttpServletResponse response,ExpenseAdvance expenseAdvance){
    	try{
    		
    		expenseAdvance.setLastUpdatedBy(this.getUserContext(request).getCzybh());
    		wfProcInstService.doSaveRefund(expenseAdvance);
			
	        ServletUtils.outPrintSuccess(request, response, "???????????????");
    	} catch (Exception e) {
	    	ServletUtils.outPrintFail(request, response, "???????????????");
			e.printStackTrace();
		}
    }
	
	@RequestMapping("/doDelEmpCard")
	public void doDelEmpCard(HttpServletRequest request ,
			HttpServletResponse response ,String actName, String cardId){
		logger.debug("??????????????????");
		
		try{
			Serializable serializable = wfProcInstService.doDelEmpCard(actName, cardId);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
     * ??????????????????
     * @param request
     * @param response
     * @param key
     * @return
     */
	@RequestMapping("/goViewByWfProcInstNo")
	public ModelAndView goViewByWfProcInstNo(HttpServletRequest request, HttpServletResponse response, String wfProcInstNo){
		
		WfProcInst wfProcInst = new WfProcInst();
		wfProcInst = wfProcInstService.get(WfProcInst.class, wfProcInstNo);
		String processInstanceId = wfProcInst.getActProcInstId();
		
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		
		String procKey = wfProcInstService.getProcKeyByNo(wfProcInst.getWfProcNo());
		WfProcess wfProcess = this.wfProcInstService.get(WfProcess.class, wfProcInst.getWfProcNo());

		List<Map<String, Object>> tables = this.wfProcInstService.getTables(wfProcInst.getWfProcNo());
		
		Map<String, Object> datas = new HashMap<String, Object>();
		Map<String , Object> detailMap = new HashMap<String, Object>();
		for(int i = 0; i < tables.size(); i++){
			List<Map<String, Object>> rows = this.wfProcInstService.getTableInfo(tables.get(i).get("Code").toString(), wfProcInst.getNo());
			int detailNum= this.wfProcInstService.getDetailNum(tables.get(i).get("Code").toString(), wfProcInst.getNo()); 
			detailMap.put(tables.get(i).get("Code").toString(), detailNum);
			for(int j = 0; j < rows.size(); j++){
				for(Entry<String, Object> entry : rows.get(j).entrySet()){
					datas.put("fp__"+tables.get(i).get("Code")+"__"+j+"__"+entry.getKey(), entry.getValue());
				}
			}
		}
		
		/*int detailNum =0;
		if(tables.size()>1){
			detailNum = this.wfProcInstService.getDetailNum(tables.get(1).get("Code").toString(), wfProcInst.getNo()); 
		}	*/	
		boolean isEnd = false;
		if(wfProcInst.getEndTime()!= null){
			isEnd = true;
		}
		String activityId ="";
		String url = FileUploadUtils.DOWNLOAD_URL;

		ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId()); 
		ProcessInstance pi=runtimeService.createProcessInstanceQuery() // ??????????????????id??????????????????
                .processInstanceId(processInstanceId)
                .singleResult();
		if(pi != null){
			ActivityImpl activityImpl=processDefinitionEntity.findActivity(pi.getActivityId());
			if(activityImpl != null){
				activityId = activityImpl.getId();
			}
		}
		String taskName = "";
    	
		String printMan = this.getUserContext(request).getZwxm();
		String detailJson= JSONObject.toJSONString(datas);
		String detailList= JSONObject.toJSONString(detailMap);

		Map<String, FormToken> formsInSession = null;  
        HttpSession session = request.getSession();  
        
        formsInSession = (Map<String, FormToken>) session.getAttribute("_forms_in_session");  
        if (formsInSession == null) {  
            formsInSession = Maps.newLinkedHashMap();
            session.setAttribute("_forms_in_session", formsInSession);  
        } 
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		
		String empNum = this.getUserContext(request).getEmnum();
		Employee employee = wfProcInstService.get(Employee.class, empNum);
		String conSignCmp = "";
		if(employee != null){
			conSignCmp = employee.getConSignCmp();
		}
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("wfProcInstNo", wfProcInst.getNo())
			.addObject("wfProcNo", wfProcInst.getWfProcNo())
			.addObject("piId", historicProcessInstance.getId())
			.addObject("processDefinitionKey", historicProcessInstance.getProcessDefinitionId())
			.addObject("applyPage", procKey+".jsp")
			.addObject("datas", datas)
			.addObject("m_umState", "V")
			.addObject("taskId", "")
			.addObject("taskName", taskName)
			.addObject("type", "2")
			.addObject("detailJson",detailJson)
			.addObject("detailList", detailList)
			.addObject("isEnd", isEnd)
			.addObject("printMan", printMan)
			.addObject("wfProcess", wfProcess).addObject("url", url)
			.addObject("activityId", activityId)
			.addObject("procKey",procKey)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("formsInSession",formsInSession)
			.addObject("conSignCmp", conSignCmp);
	}
}


