package com.house.home.serviceImpl.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionTaskListener;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task; 
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.workflow.WfProcInstDao; 
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.ExpenseAdvance;
import com.house.home.entity.finance.ExpenseAdvanceTran;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.workflow.WfProcComment;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcPhoto;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.workflow.WfProcInstService;
import com.house.home.service.workflow.WorkflowService; 

@SuppressWarnings("serial")
@Service 
public class WfProcInstServiceImpl extends BaseServiceImpl implements WfProcInstService {
	@Autowired
	private  WfProcInstDao wfProcInstDao;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public Page<Map<String, Object>>  findWfProcInstPageBySql(
			Page<Map<String, Object>> page, WfProcInst wfProcInst) {
		return wfProcInstDao.findWfProcInstPageBySql(page,wfProcInst);
	}

	@Override
	public Page<Map<String, Object>> getApplyListByJqgrid(
			Page<Map<String, Object>> page, WfProcInst wfProcInst) {
		
		return wfProcInstDao.getApplyListByJqgrid(page,wfProcInst);
	}
	
	@Override
	public Page<Map<String, Object>> getAllListByJqgrid(
			Page<Map<String, Object>> page, WfProcInst wfProcInst) {
		
		return wfProcInstDao.getAllListByJqgrid(page,wfProcInst);
	}
	
	@Override
	public Page<Map<String, Object>> findByProcInstId(Page<Map<String,Object>> page, String wfProcInstNo) {
		return wfProcInstDao.findByProcInstId(page, wfProcInstNo);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void doStartProcInst(Map<String, Object> formProperties, String processDefinitionId, String czybh,String detailJson,final Map<String, String> pushWfProcInstNo){
		 try {
		
			//??????????????????
			String department ="";
			if(formProperties.containsKey("department")){
				department = formProperties.get("department").toString();
			} 
			boolean nullValue = true; 
		 	//????????????????????????
	        Map<String, Object> seqNoMap = this.wfProcInstDao.getSeqNoBySql("tWfProcInst");//this.getSeqNo("tWfProcInst");
	        String wfProcInstNo = seqNoMap.get("no").toString();
	        pushWfProcInstNo.put("pushWfProcInstNo", wfProcInstNo);
	        //??????????????????
	        List<Map<String, Object>> tables = this.wfProcInstDao.getTables(formProperties.get("wfProcNo").toString(), "1", null);

	        //????????????
	        StringBuilder mainSummary = new StringBuilder();
	        
	        //???????????????
	        StringBuilder subSummary = new StringBuilder();
	        
	        //????????????map
			Map<String, Object> activityMap = new LinkedHashMap<String, Object>();
			//?????????????????????????????????
			//??????:IdentityHashMap?????????Map??????,??????????????????(???:String str = "123" ??? String str1 = str ????????????;String str = "123" ??? String str1 = "123" ????????????)
			//???????????????????????????????????????????????????
			for(Entry<String, Object> entry : ((IdentityHashMap<String, Object>)formProperties.get("tables")).entrySet()){
				
				if(tables == null || tables.size() == 0){
					break;
				}
				//???????????????
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				
				nullValue = true;
				for(Object value :map.values()){
					if(!"".equals(value.toString())){
						nullValue=false;
					}
				}
				
				//????????????????????????????????????
				Map<String, Object> columnsMap = null;
				//????????????,??????false???????????????
				boolean mainFlag = true;
				
				//??????????????????,??????????????????,????????????"???-?????????"???Map????????????Map??????
				for(int i = 0; i < tables.size(); i++){
					//Code??????entry.getKey()?????????
					if(tables.get(i).get("Code").toString().equals(entry.getKey())){
						//????????????"???-??????"???Map??????
						columnsMap = StringUtils.convertMapStr(tables.get(i).get("columns").toString().toLowerCase());
						//??????????????????,???????????????????????????????????????
						if(!tables.get(0).get("Code").toString().equals(entry.getKey())){
							mainFlag = false;
							if(nullValue){
								break;
							}
							subSummary.append(tables.get(i).get("Descr").toString()+"[<none/>");
							break;
						}
					}
				}
				if(columnsMap == null){
					continue;
				}
				//????????????,????????????????????????
				if(mainFlag){
					List<Map<String, Object>> mainTables = this.wfProcInstDao.getTables(formProperties.get("wfProcNo").toString(), null, "0");
					for(String key : StringUtils.convertMapStr(mainTables.get(0).get("columns").toString()).keySet()){
						activityMap.put(key.toLowerCase(), "");
					}
				}
				//?????????????????????????????????,????????????<br/>
				boolean appendFlag = false;
				boolean continueFlag =true; 
				//???????????????
				for(Entry<String, Object> entMap : columnsMap.entrySet()){
					boolean hasDetail =false;
					for(Entry<String, Object> param : map.entrySet()){
						//?????????????????????
						if(mainFlag){
							activityMap.put(param.getKey(), param.getValue().toString().trim());
						}
						//??????????????????
						if(param.getValue() != null && StringUtils.isNotBlank(param.getValue().toString()) 
								&& columnsMap.containsKey(param.getKey().toLowerCase())){
							//????????????????????????????????????
							if(mainFlag && entMap.getKey().toLowerCase().equals(param.getKey().toLowerCase())){
								if(appendFlag && mainSummary.length()>0){
									mainSummary.append("<br/>");
								}
								mainSummary.append(columnsMap.get(param.getKey().toLowerCase())+":"+param.getValue());
							}else{
								if(!mainFlag && continueFlag && !nullValue){
									hasDetail =true;
									if(appendFlag){
										subSummary.append("<br/>");
									}
									subSummary.append(columnsMap.get(param.getKey().toLowerCase())+":"+param.getValue());
								}
							}
							appendFlag = true;
						}
					}
					if(hasDetail){
						continueFlag = false;
					}
				}
				
				continueFlag =true;
				//???????????????????????????
				if(!mainFlag && !nullValue){
					subSummary.append("<none/>]<br/>");
				}
				
				//??????????????????,?????????????????????????????????
				map.put("wfProcInstNo", wfProcInstNo);
				
				// ?????????????????????????????????
				//formatSummary(formProperties, entry, mainSummary, subSummary);
				
				//???????????????
				this.wfProcInstDao.autoSaveSql(entry.getKey(), map);
			}
	        identityService.setAuthenticatedUserId(czybh);
	        if(StringUtils.isNotBlank(department)){
	        	activityMap.put("department", department);
	        }
	        List<Object> list =JSON.parseArray(detailJson);
	        if(StringUtils.isNotBlank(detailJson)){
	        	for (Object object : list){
	        		Map<String, Object> objMap = (Map<String, Object>) object;
	        		if(StringUtils.isNotBlank(objMap.get("assignee").toString()) && StringUtils.isNotBlank(objMap.get("taskKey").toString())){
	                    activityMap.put("PROC_OPTION_"+objMap.get("taskKey").toString(), objMap.get("assignee").toString());
	        		}
	        	}
	        }
	        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, activityMap);
	        String summary =mainSummary.toString()+"<br/>"+subSummary.toString();
	        if(summary.length()>990){
	        	summary = summary.substring(0, 950)+"...";
	        }
			List<Task> taskQueryList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
	        
	        WfProcInst wfProcInst = new WfProcInst();
	        wfProcInst.setNo(wfProcInstNo);
	        wfProcInst.setWfProcNo(formProperties.get("wfProcNo").toString());
	        wfProcInst.setStatus("1");
	        wfProcInst.setActProcInstId(processInstance.getProcessInstanceId());
	        wfProcInst.setActProcDefId(processInstance.getProcessDefinitionId());
	        wfProcInst.setStartUserId(czybh);
	        wfProcInst.setStartTime(new Date());
	        wfProcInst.setSummary(summary);
	        wfProcInst.setLastUpdate(new Date());
	        wfProcInst.setLastUpdatedBy(czybh);
	        wfProcInst.setExpired("F");
	        wfProcInst.setActionLog("ADD");
	        wfProcInst.setDepartment(department);
	        wfProcInst.setPrintTimes(0);
	        this.save(wfProcInst);
	        
	        
	        WfProcTrack wfProcTrack = new WfProcTrack();
	        if(taskQueryList.size()>0){
	        	wfProcTrack.setToActivityDescr(taskQueryList.get(0).getName());
	        	wfProcTrack.setToActivityId(taskQueryList.get(0).getId());
	        }
	        wfProcTrack.setWfProcInstNo(wfProcInstNo);
	        wfProcTrack.setActionType("1");
	        wfProcTrack.setOperId(czybh);
	        wfProcTrack.setRemarks("");
	        wfProcTrack.setLastUpdate(new Date());
	        wfProcTrack.setLastUpdatedBy(czybh);
	        wfProcTrack.setActionLog("ADD");
	        wfProcTrack.setExpired("F");
	        this.save(wfProcTrack);
	        
	        if(StringUtils.isNotBlank(detailJson)){
	        	WfProcInst wf = new WfProcInst();
	        	wf.setDetailJson(detailJson);
	        	wf.setLastUpdatedBy(czybh);
		        wf.setNo(wfProcInstNo);
	        	wfProcInstDao.doSaveWfProcInstOption(wf);
	        }
	        if(formProperties.containsKey("photoPK")){
				String photoPK = formProperties.get("photoPK").toString();
				wfProcInstDao.doUpdatePhotoNo(wfProcInstNo,photoPK);
			}
	        if(formProperties.containsKey("photoUrlList")){
	        	if("null".equals(formProperties.get("photoUrlList").toString())){
	        		return;
	        	}
				String[] photoList = formProperties.get("photoUrlList").toString().split(",");
				if(photoList.length>0){
					for(int i=0;i<photoList.length;i++){
						if(StringUtils.isNotBlank(photoList[i]) && !"[object Object]".equals(photoList[i])){
							WfProcPhoto wfProcPhoto= new WfProcPhoto();
							wfProcPhoto.setWfProcInstNo(wfProcInstNo);
							wfProcPhoto.setDescr(photoList[i]);
							wfProcPhoto.setActionLog("ADD");
							wfProcPhoto.setExpired("F");
							wfProcPhoto.setLastUpdate(new Date());
							wfProcPhoto.setLastUpdatedBy(czybh);
							this.save(wfProcPhoto);
						}
					}
				}
			}
	        //wfProcInstDao.doPushTaskToOperator(wfProcInstNo);
	    } finally {
	        identityService.setAuthenticatedUserId(null);
	    }
	}

	@Override
	public WfProcess getWfProcessByProcKey(String procKey){
		return wfProcInstDao.getWfProcessByProcKey(procKey);
	}
	
	@Override
	public List<Map<String, Object>> getTables(String wfProcNo){
		return wfProcInstDao.getTables(wfProcNo, null,"0");
	}
	
	@Override
	public WfProcInst getWfProcInstByActProcInstId(String actProcInstId){
		return wfProcInstDao.getWfProcInstByActProcInstId(actProcInstId);
	}
	
	@Override
	public List<Map<String, Object>> getTableInfo(String tableName, String wfProcInstNo){
		return wfProcInstDao.getTableInfo(tableName, wfProcInstNo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doCompleteTask(String taskId, String czybh, String status, String comment,String processInstId,Map<String, Object> formProperties){
		try {
			
        	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            Map<String, Object> variables = new HashMap<String, Object>();
        	//Task toTask = taskService.createTaskQuery().taskDefinitionKey(status).singleResult();
        	if (task.getAssignee() == null) {
        		taskService.claim(taskId, czybh);
        	}

            identityService.setAuthenticatedUserId(czybh);
            
            String activityId = "";
            if ("reject".equals(status) || "cancel".equals(status)) {
            	activityId = "end";
            }else {
            	if(!("approval".equals(status))){
            		activityId = status;
            	}
            	//??????????????? ????????????
            	if(StringUtils.isNumeric(taskId) && formProperties.get("wfProcInstNo") != null && formProperties.get("activityId") != null){
            		List<Map<String , Object>> list = 
            				wfProcInstDao.getProcTaskTableStru(formProperties.get("wfProcNo").toString(), 
            						formProperties.get("activityId").toString());
            		if (list != null) {
            			doUpdateWfProcInstData(list, formProperties, variables);
            		}
            	}
			}
            
            Object object = taskService.getVariable(taskId,"PROC_HI_OPERATOR");
            List<String> list = new ArrayList<String>();
            if(object != null){
            	list = (List<String>) object;
            }
            
            // ??????????????????
            variables.put("LAST_COMMENT", comment);
            if(formProperties !=null && formProperties.get("nextOperator") != null && StringUtils.isNotBlank(formProperties.get("nextOperator").toString())){
            	variables.put("NextOperator", formProperties.get("nextOperator").toString());
            }
            if(formProperties !=null && formProperties.get("TaskContinue") != null && StringUtils.isNotBlank(formProperties.get("TaskContinue").toString())){
            	variables.put("SKIP_NEXTTASK", formProperties.get("TaskContinue").toString());
            }
            list.add(czybh);
            variables.put("PROC_HI_STATUS", status);
            variables.put("PROC_HI_OPERATOR", list);
            variables.put("PROC_LASTCZY", czybh);
            if("approval".equals(status)){
            	variables.put("PROC_HI_OPERATOR", list);
			}else{
				list = new ArrayList<String>();
				variables.put("PROC_HI_OPERATOR", list);
			}
            
        	WfProcInst wfProcInst = this.getWfProcInstByActProcInstId(task.getProcessInstanceId());
        	if(StringUtils.isBlank(processInstId)){
        		processInstId = wfProcInst.getActProcInstId();
        	}
        	this.workflowService.commitProcess(taskId, variables, activityId,processInstId,wfProcInst.getNo());
        	
			List<Task> taskQueryList = taskService.createTaskQuery().processInstanceId(processInstId).list();
        	
        	HistoricProcessInstance historicProcessInstance = historyService
        			.createHistoricProcessInstanceQuery()
        			.processInstanceId(task.getProcessInstanceId())
        			.singleResult();
            if (historicProcessInstance.getEndTime() != null) { // ???????????????????????????????????????(tWorkflowProcInst)?????????
            	if (StringUtils.equals(status, "cancel")) {
            		wfProcInst.setStatus("3"); // ??????
            	}else{
            		wfProcInst.setStatus("2");
            		if(StringUtils.equals(status, "approval")){
            			wfProcInst.setIsPass("1");
            		}else{
            			wfProcInst.setIsPass("0");
            		}
            	}

            	wfProcInst.setEndTime(historicProcessInstance.getEndTime());
            	wfProcInst.setLastUpdate(new Date());
            	wfProcInst.setLastUpdatedBy(czybh);
            	wfProcInst.setActionLog("EDIT");
            	this.saveOrUpdate(wfProcInst);
            }
            
            WfProcTrack wfProcTrack = new WfProcTrack();
            wfProcTrack.setWfProcInstNo(wfProcInst.getNo());
            wfProcTrack.setOperId(czybh);
            wfProcTrack.setFromActivityId(task.getId());
            wfProcTrack.setFromActivityDescr(task.getName());
            if(taskQueryList.size()>0){
            	wfProcTrack.setToActivityId(taskQueryList.get(0).getId());
            	wfProcTrack.setToActivityDescr(taskQueryList.get(0).getName());
            }
           	if (StringUtils.equals(status, "approval")) {
           		wfProcTrack.setActionType("3"); // ????????????
        	} else if (StringUtils.equals(status, "reject")) {
        		wfProcTrack.setActionType("4"); // ????????????
        	} else if (StringUtils.equals(status, "cancel")) {
        		wfProcTrack.setActionType("5"); // ??????
        	} else {
        		wfProcTrack.setActionType("6"); // ??????
        	}
        	wfProcTrack.setRemarks(comment);
        	wfProcTrack.setLastUpdate(new Date());
        	wfProcTrack.setLastUpdatedBy(czybh);
        	wfProcTrack.setExpired("F");
        	wfProcTrack.setActionLog("ADD");
        	this.saveOrUpdate(wfProcTrack);
		} finally {
            identityService.setAuthenticatedUserId(null);
        }		
	}

	@Override
	public String getProcKeyByNo(String no) {
		return wfProcInstDao.getProcKeyByNo(no);
	}

	public Page<Map<String, Object>> getWfProcTrack(Page<Map<String, Object>> page, String wfProcInstNo){
		return this.wfProcInstDao.getWfProcTrack(page, wfProcInstNo);
	}

	@Override
	public Page<Map<String, Object>> getAllListDetailByJqgrid(
			Page<Map<String, Object>> page,WfProcInst wfProcInst) {
		
		getAllListByJqgrid(page,wfProcInst);
		
		for(Map<String, Object> map:page.getResult()){
			//tablecode ????????????????????????   	 no ?????????????????????
			map.put("applypedescr", wfProcInstDao.getDetailFromWfCustTable(map.get("tablecode").toString(),map.get("no").toString()));
			map.put("apptruck", wfProcInstDao.getDetailFromWfProcTruck(map.get("no").toString()));
		}
		
		return page;
	}
	
	@Override
	public Page<Map<String, Object>> findWfProcInstPic(
			Page<Map<String, Object>> page, String wfProcInstNo,String photoPK) {
		
		return wfProcInstDao.findWfProcInstPic(page, wfProcInstNo,photoPK);
	}

	@Override
	public void doUpdateCopyStatus(String czybh, String wfProcInstNo) {
		wfProcInstDao.doUpdateCopyStatus(czybh,wfProcInstNo);
	}

	@Override
	public String getOptionAssignee(String wfProcInstNo, String taskKey) {
		return wfProcInstDao.getOptionAssignee(wfProcInstNo,taskKey);
	}
	
	@Override
	public String getDeptLeader(String departmentCode) {
		return wfProcInstDao.getDeptLeader(departmentCode);
	}
	
	@Override
	public String getDeptLeaderTow(String departmentCode) {
		String deptLeaderDeptCode = wfProcInstDao.getDeptLeaderDeptCode(departmentCode);
		return wfProcInstDao.getDeptLeaderTow(deptLeaderDeptCode);
	}

	@Override
	public List<Map<String, Object>> getDeptListByCzybh(String czybh) {
		return wfProcInstDao.getDeptListByCzybh(czybh);
	}

	@Override
	public String getActUser(String wfProcNo,String taskKey,String wfProcInstNo) {
		return wfProcInstDao.getActUser(wfProcNo,taskKey,wfProcInstNo);
	}
	
	@Override
	public String getActUser(String groupId) {
		return wfProcInstDao.getActUser(groupId);
	}

	@Override
	public int getDetailNum(String tableName, String wfProcInstNo) {
		return wfProcInstDao.getDetailNum(tableName,wfProcInstNo);
	}
	
	@Override
	public List<Map<String, Object>> getDetails(String tableName, String wfProcInstNo) {
		return wfProcInstDao.getDetails(tableName,wfProcInstNo);
	}

	@Override
	public void getAllEntity(String pvmId, ProcessDefinitionEntity processDefinitionEntity, String department,
		String wfProcNo,String czybh,List<Object> operateList, List<String> pdId, JSONObject jsonObject) {
		List<String> czyList = new ArrayList<String>(); 
		
		String leaderCode="";
		Employee employee = new Employee();
		for(int i =0 ; i<processDefinitionEntity.getActivities().size();i++){//??????????????????  ???????????????????????????????????????
			String actName=processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString();
			
			if(pvmId.equals(processDefinitionEntity.getActivities().get(i).getId())){
				if(processDefinitionEntity.getActivities().get(i).getOutgoingTransitions().size()>0){
					if(pvmId.split("copyTask").length > 1){
						czyList.add(actName+"__"+getActUser(wfProcNo,processDefinitionEntity.getActivities().get(i).getId(),""));
						czyList.add(pvmId);
						operateList.add(czyList);
					}
					//????????????
					TaskDefinition taskDefinition = ((TaskDefinition)processDefinitionEntity.getActivities().get(i).getProperties().get("taskDefinition"));
					//pvmTransition?????????????????????????????????????????????
					PvmTransition pvmTransition  = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions().get(0);
					
					if(taskDefinition!=null){
						pdId.add(pvmTransition.getId());
						if(taskDefinition.getTaskListeners().get("create").get(0) != null){
							Set<Expression> expressions = taskDefinition.getCandidateGroupIdExpressions();
							TaskListener taskListener  =  taskDefinition.getTaskListeners().get("create").get(0);
							//???????????????????????????  ???????????????????????????????????? ???????????????????????? ?????? taskListener ???null ???  ?????????????????? DelegateExpressionTaskListener
							if("org.activiti.engine.impl.bpmn.listener.DelegateExpressionTaskListener".equals(taskListener.getClass().getName())){
								String expressionText = ((DelegateExpressionTaskListener)taskDefinition.getTaskListeners().get("create").get(0)).getExpressionText();
								if("${wfOptionListener}".equals(expressionText)){//?????????????????????????????????????????? ???
									//czyList:1.??????????????????2.??????????????? taskKey ???taskKey??????????????????????????????????????????????????? ?????????tWfProcOption
									czyList.add(processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString());
									czyList.add(processDefinitionEntity.getActivities().get(i).getId().toString());
									operateList.add(czyList);
								} else if ("${deptLeaderListener}".equals(expressionText)){ //??????????????????????????? ?????????????????? 
									leaderCode =this.getDeptLeader(department);
									if(StringUtils.isNotBlank(leaderCode)){
										String errorText = "";
										if(leaderCode.equals(czybh.trim()) && !"017".equals(wfProcNo) && !"007".equals(wfProcNo) && !"008".equals(wfProcNo)  
												&& !"018".equals(wfProcNo) && !"023".equals(wfProcNo) && !"024".equals(wfProcNo) && !"012".equals(wfProcNo)
												&& !"010".equals(wfProcNo) && !"011".equals(wfProcNo) && !"013".equals(wfProcNo) && !"014".equals(wfProcNo) 
												&& !"001".equals(wfProcNo) && !"005".equals(wfProcNo)
												&& !"003".equals(wfProcNo) && !"004".equals(wfProcNo) && !"006".equals(wfProcNo) && !"009".equals(wfProcNo) 
										){
											errorText="(????????????,???????????????????????????)";
										}
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? leaderCode:employee.getNameChi())+errorText);//????????????????????????????????????????????????????????? ???????????????
									}else {
										operateList.add(actName+"__(???????????????)"+this.getActUser("Admin"));//?????????????????? ?????????????????????,???????????????????????????????????????
									}
								} else if ("${deptLeaderTowListener}".equals(expressionText)){//??????????????????????????? ??????????????????
									leaderCode =this.getDeptLeaderTow(department);
									if(StringUtils.isNotBlank(leaderCode)){
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? leaderCode:employee.getNameChi()));
									}else {
										operateList.add(actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
									}	
								} else if ("${deptManageLisener}".equals(expressionText)){
									leaderCode =this.getDeptManager(department);
									if(StringUtils.isNotBlank(leaderCode)){
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? leaderCode:employee.getNameChi()));
									}else {
										operateList.add(actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
									}
								} else if ("${deptLeaderPrjLeaderListener}".equals(expressionText)){
									leaderCode =this.getDeptLeader(department);
									String prjDeptLeader = getActUser("EngineerManager");
									if(StringUtils.isNotBlank(leaderCode)){
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? "":employee.getNameChi())+(StringUtils.isNotBlank(prjDeptLeader)?"/"+prjDeptLeader:""));
									} else {
										operateList.add(actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
									}
								}else if ("${branchHeadLisener}".equals(expressionText)){
									if(jsonObject.get("Company") != null && StringUtils.isNotBlank(jsonObject.get("Company").toString())){
										if("????????????".equals(jsonObject.get("Company").toString())){
											operateList.add(actName+"__"+this.getActUser("FQBranchHeader"));
										} else if ("????????????".equals(jsonObject.get("Company").toString())){
											operateList.add(actName+"__"+this.getActUser("CLBranchHeader"));
										} else {
											operateList.add(actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
										}
									} else {
										operateList.add(actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
									}
								} else {
									operateList.add(processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString()+"__???");
								}
							}else if(expressions.size()>0){
								//???????????????????????????????????? ??????Activiti??????????????????????????????????????????  ???????????? ???????????????????????????????????????????????? ???[infoOfficer,cfo]
								String expressString = expressions.toString().substring(1, expressions.toString().length()-1);
								if(StringUtils.isNotBlank(expressString)){
									operateList.add(actName+"__"+getActUser(expressString));
								}
							}else {
								operateList.add(processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString()+"__???");
							}
						}
					} else {
						List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
						if(pvmTransitions.size()>1){
							for(int p = 0 ;p<pvmTransitions.size();p++){//????????????????????????????????????
								if(this.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
									pvmTransition = pvmTransitions.get(p);
									break;
								}
							}
						}
						pdId.add(pvmTransition.getId());
					}
					//????????????????????????????????? ???????????????????????????????????????id?????????????????????
					getAllEntity(pvmTransition.getDestination().getId(), processDefinitionEntity,department,wfProcNo,czybh, operateList,pdId, jsonObject);
				}
			}
		}
	}
	
	public List<Map<String, Object>> getProcBranch(String wfProcNo,String procDefId,JSONObject jsonObject){
		List<Map<String, Object> > resultList= new ArrayList<Map<String, Object>>();
		String starTaskKey = wfProcInstDao.getStartTaskKey(wfProcNo);
		String leaderCode="";
		Employee employee = new Employee();
		WfProcInst wfProcInst = this.get(WfProcInst.class, wfProcNo);
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
        while (StringUtils.isNotBlank(starTaskKey)) {//&& !wfProcInstDao.IsHisTask(wfProcNo, starTaskKey)
        	Map<String , Object> map = new HashMap<String, Object>();
        	ActivityImpl activityImpl = definitionEntity.findActivity(starTaskKey);
        	String actName = activityImpl.getProperties().get("name").toString();
        	
			if(activityImpl.getOutgoingTransitions().size()>0){
        		if("applyman".equals(activityImpl.getId())){
        			map.put("wfprocdescr", actName);
        			employee = this.get(Employee.class, wfProcInst.getStartUserId());
					map.put("namechidescr",employee.getNameChi());
        			if("exclusivegateway1".equals(activityImpl.getOutgoingTransitions().get(0).getDestination().getId())){
        				List<PvmTransition> applyManTransitions = activityImpl.getOutgoingTransitions();
        				List<PvmTransition> exclusiveTransitions = applyManTransitions.get(0).getDestination().getOutgoingTransitions();
        				List<HistoricVariableInstance> hisDataList=historyService.createHistoricVariableInstanceQuery()
        						.processInstanceId(wfProcInst.getActProcInstId()).list();
        				Map<String, Object> hisDataMap = new HashMap<String, Object>();
        				if(hisDataList.size()>0){
        					for(HistoricVariableInstance historicVariableInstance:hisDataList){
        						if(!"PROC_HI_OPERATOR".equals(historicVariableInstance.getVariableName())){
	        						if(historicVariableInstance.getValue() != null){
	        							if(StringUtils.isNotBlank(historicVariableInstance.getValue().toString())){
	        								hisDataMap.put("\""+historicVariableInstance.getVariableName()+"\"", 
	        										"\""+historicVariableInstance.getValue()+"\"");
	        							}
	        						}
        						}	
        					}
        				}
        				if(jsonObject == null){
        					jsonObject =JSONObject.parseObject(hisDataMap.toString().replace("=", ":"));
        				}
        				for(int p = 0 ;p<exclusiveTransitions.size();p++){//????????????????????????????????????
        					if(this.checkFormDataByRuleEl(exclusiveTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
        						PvmActivity pvmActivity =null; //????????????
        						pvmActivity = exclusiveTransitions.get(p).getDestination();
        						activityImpl = (ActivityImpl) pvmActivity;
        						actName = activityImpl.getProperties().get("name").toString();
        						break;
        					}
        				}
        			} else {
        				List<PvmTransition> applyManTransitions = activityImpl.getOutgoingTransitions();
        				PvmActivity pvmActivity =null; //????????????
        				pvmActivity = applyManTransitions.get(0).getDestination();
        				activityImpl = (ActivityImpl) pvmActivity;
						actName = activityImpl.getProperties().get("name").toString();
	        		}
        			if(map.size()>0){
    					resultList.add(map);
    					map = new HashMap<String, Object>();
    				}
        		}
        		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        		list = wfProcInstDao.getPresentOperater(wfProcInst.getActProcInstId(),activityImpl.getId());
        		if(list != null){
        			String assigneeNames = "";
        			map.put("wfprocdescr", actName);
        			if("zwxm".equals(list.get(0).get("role").toString())){
            			if(list.size() > 0){
            				for (int i = 0; i < list.size(); i++) {
            					if(StringUtils.isNotBlank(assigneeNames)){
            						assigneeNames += "/" + list.get(i).get("assignee").toString();
            					}else {
            						assigneeNames = list.get(i).get("assignee").toString();
								}
    						}
            			}

        				map.put("namechidescr",assigneeNames);
        			} else {
        				map.put("namechidescr",getActUser(list.get(0).get("assignee").toString()));
        			}
					resultList.add(map);
					map = new HashMap<String, Object>();
					activityImpl = (ActivityImpl) activityImpl.getOutgoingTransitions().get(0).getDestination();
					if(activityImpl != null){
						actName = activityImpl.getProperties().get("name").toString();
					}
        		}
        		if(activityImpl != null && activityImpl.getId().split("copyTask").length > 1){
					map.put("wfprocdescr", actName);
					map.put("namechidescr",getActUser(wfProcInst.getWfProcNo(),activityImpl.getId(),wfProcNo));
				}
        		TaskDefinition taskDefinition = null;
        		if (activityImpl != null) {
        			taskDefinition = ((TaskDefinition)activityImpl.getProperties().get("taskDefinition"));
				}
				if(taskDefinition != null){
					Set<Expression> expressions = taskDefinition.getCandidateGroupIdExpressions();
					TaskListener taskListener  =  taskDefinition.getTaskListeners().get("create").get(0);
					if("org.activiti.engine.impl.bpmn.listener.DelegateExpressionTaskListener".equals(taskListener.getClass().getName())){
						String expressionText = ((DelegateExpressionTaskListener)taskDefinition.getTaskListeners().get("create").get(0)).getExpressionText();
						if("${wfTaskContinueListener}".equals(expressionText)){//?????????????????????????????????????????? ???
							String express = expressions.toString().substring(1, expressions.toString().length()-1);
							map.put("wfprocdescr", actName);
							map.put("namechidescr", getActUser(express));
						} else if("${wfOptionListener}".equals(expressionText)){//?????????????????????????????????????????? ???
							map.put("wfprocdescr", actName);
							if(StringUtils.isNotBlank(getOptionAssignee(wfProcNo,taskDefinition.getKey()))){
								employee = this.get(Employee.class, getOptionAssignee(wfProcNo,taskDefinition.getKey()));
								map.put("namechidescr", employee.getNameChi());
							}
						} else if("${deptLeaderListener}".equals(expressionText)){ //??????????????????????????? ?????????????????? 
							leaderCode =this.getDeptLeader(wfProcInst.getDepartment());
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",employee == null ? leaderCode:employee.getNameChi());//????????????????????????????????????????????????????????? ???????????????
							}else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr",this.getActUser("Admin"));//???????????????????????? ????????????????????????
							}
						} else if("${deptLeaderTowListener}".equals(expressionText)){//??????????????????????????? ??????????????????
							leaderCode =this.getDeptLeaderTow(wfProcInst.getDepartment());
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",employee == null ? leaderCode:employee.getNameChi());
							}else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr","????????????????????????????????????");//?????????????????? ????????? ????????????
							}
						} else if("${deptManageLisener}".equals(expressionText)){
							leaderCode =this.getDeptManager(wfProcInst.getDepartment());
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",employee == null ? leaderCode:employee.getNameChi());
							}else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr","????????????????????????????????????");//?????????????????? ????????? ????????????
							}
						} else if ("${deptLeaderPrjLeaderListener}".equals(expressionText)){
							leaderCode =this.getDeptManager(wfProcInst.getDepartment());
							String prjDeptLeader = getActUser("EngineerManager");
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",actName+"__"+(employee == null ? leaderCode:employee.getNameChi())
										+(StringUtils.isNotBlank(prjDeptLeader)?"/"+prjDeptLeader:""));
							} else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr","????????????????????????????????????");//?????????????????? ????????? ????????????
							}
						} else if ("${branchHeadLisener}".equals(expressionText)){
							map.put("wfprocdescr", "??????????????????");
							if(jsonObject.get("Company") != null && StringUtils.isNotBlank(jsonObject.get("Company").toString())){
								if("????????????".equals(jsonObject.get("Company").toString())){
									map.put("namechidescr",this.getActUser("FQBranchHeader"));
								} else if ("????????????".equals(jsonObject.get("Company").toString())){
									map.put("namechidescr",this.getActUser("CLBranchHeader"));
								} else {
									map.put("namechidescr",actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
								}
							} else {
								map.put("namechidescr",actName+"__"+"????????????????????????????????????");//?????????????????? ????????? ????????????
							}
						}
					}else if(expressions.size()>0){
						String expressString = expressions.toString().substring(1, expressions.toString().length()-1);
						if(StringUtils.isNotBlank(expressString)){
							map.put("wfprocdescr", actName);
							map.put("namechidescr", getActUser(expressString));
						}
					}
				}
				if(map.size()>0){
					resultList.add(map);
				}
				if(activityImpl != null){
					if("exclusivegateway1".equals(activityImpl.getId())){
						int i = getLine(activityImpl, wfProcInst, jsonObject, actName, resultList);
						starTaskKey = activityImpl.getOutgoingTransitions().get(i).getDestination().getId();//???????????????TaskKey,??????
					} else {
						if(activityImpl.getOutgoingTransitions().size()>0){
							List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
							if(pvmTransitions.size() == 1){
							
								starTaskKey = activityImpl.getOutgoingTransitions().get(0).getDestination().getId();//???????????????TaskKey,??????
							} else {
								
								for(int p = 0 ;p<pvmTransitions.size();p++){//????????????????????????????????????
									if(this.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(), jsonObject)){
										starTaskKey = pvmTransitions.get(p).getDestination().getId();
										break;
									}
								}
								
							}
						} else{
							starTaskKey = null;
						
						}
					}
				}else{
					break;
				}
            }else{
            	starTaskKey=null;
            }
		}
		return resultList;
	}

	@Override
	public Map<String, Object> getCustStakeholder(String roll,String custCode) {
		
		return wfProcInstDao.getCustStakeholder(roll,custCode);
	}
	
	@Override
	public List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// ????????????????????????flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// ?????????????????????????????????
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// ?????????????????????????????????
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// ?????????????????????????????????????????????
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // ????????????????????????????????????????????????????????????
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// ?????????????????????
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// ?????????????????????
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // ???????????????????????????????????????????????????????????????
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // ????????????????????????
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// ?????????????????????????????????
            for (PvmTransition pvmTransition : pvmTransitions) {
                // ???????????????????????????
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // ?????????????????????????????????????????????????????????????????????????????????id?????????????????????
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
	
	/**
	 * ??????El?????????
	 * @param el
	 * @param formData
	 * @return
	 */
	public Boolean checkFormDataByRuleEl(String el, Map<String, Object> formData) {
		ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (Object k : formData.keySet()) {
            if (formData.get(k) != null) {
                context.setVariable(k.toString(), factory.createValueExpression(formData.get(k), formData.get(k).getClass()));
            }
        }
        ValueExpression e = factory.createValueExpression(context, el, Boolean.class);
        return (Boolean) e.getValue(context);
    }
	
	/**
	 * OA?????????????????????????????????????????????
	 */
	public List<Object> getOperator(JSONObject jsonObject,String pdID,String department,String wfProcNo,
			String czybh){
		boolean hasGetWay = false;
		PvmActivity pvmActivity =null; //????????????
		ProcessDefinitionEntity processDefinitionEntity = null; //??????????????????
		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(pdID); //??????????????????id???????????????????????????????????? 
		ActivityImpl activityImpl= processDefinitionEntity.getActivities().get(0);
		for (int i=0;i<processDefinitionEntity.getActivities().size();i++){
			//exclusivegateway1 //?????? el?????????
			if("exclusivegateway1".equals(processDefinitionEntity.getActivities().get(i).getId())){
				hasGetWay = true;
				List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
				for(int p = 0 ;p<pvmTransitions.size();p++){//????????????????????????????????????
					if(this.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
						pvmActivity = pvmTransitions.get(p).getDestination();
						break;
					}
				}
				if(pvmActivity != null){
					break;
				}
			}
		}
		List<Object> operateList = new ArrayList<Object>();
		List<String> pdId=new ArrayList<String>();
		if(!hasGetWay){
			
			pdId.add(activityImpl.getIncomingTransitions().get(0).getId());
			this.getAllEntity(activityImpl.getId(),processDefinitionEntity,department
					,wfProcNo,czybh,operateList, pdId,jsonObject);
			return operateList; 
		}
		
		if(pvmActivity != null){
			operateList = new ArrayList<Object>();
			pdId=new ArrayList<String>();
			pdId.add(pvmActivity.getIncomingTransitions().get(0).getId());
			this.getAllEntity(pvmActivity.getId(),processDefinitionEntity,department
					,wfProcNo,czybh,operateList, pdId, jsonObject);
			return operateList;  
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getProcTaskTableStru(String wfProcNo, String taskDefkey) {

		return wfProcInstDao.getProcTaskTableStru(wfProcNo, taskDefkey);
	}

	public void updateCopyDate(String wfProcInNo,String startUserId){
		wfProcInstDao.updateCopyDate(wfProcInNo,startUserId);
	}

	@Override
	public String getCzybhByEmpNum(String empNum) {
		return wfProcInstDao.getCzybhByEmpNum(empNum);
	}

	@Override
	public void doSaveComment(String wfProcInstNo, String comment,
			String lastUpdatedBy) {
		WfProcComment wfProcComment = new WfProcComment();
		wfProcComment.setWfProcInstNo(wfProcInstNo);
		wfProcComment.setComment(comment);
		wfProcComment.setEmpCode(lastUpdatedBy);
		wfProcComment.setLastUpdate(new Date());
		wfProcComment.setLastUpdatedBy(lastUpdatedBy);
		wfProcComment.setExpired("F");
		wfProcComment.setActionLog("ADD");
		this.save(wfProcComment);
		wfProcInstDao.doPushCommentToOperator(wfProcInstNo);
	}

	@Override
	public void doPushTaskToOperator(String status ,String wfProcInstNo) {
		if(StringUtils.isNotBlank(status)){
			// ?????????????????????????????????????????????
			wfProcInstDao.doUpdatePustRcvStatus(wfProcInstNo);
			// ?????????????????????????????????
			if("approval".equals(status)){
				wfProcInstDao.doPushTaskToOperator(wfProcInstNo);
			}
			// ????????????????????????
			wfProcInstDao.doPushToAppMan(wfProcInstNo); 
		}else{
			wfProcInstDao.doPushTaskToOperator(wfProcInstNo);
		}
		
	}

	@Override
	public Map<String , Object> getNextOperatorIdByNo(String no) {
		return wfProcInstDao.getNextOperatorIdByNo(no);	
	}
	
	@Override
	public Map<String, Object> getSeqNoBySql(String tableCode){
		return this.wfProcInstDao.getSeqNoBySql(tableCode);
	}

	@Override
	public List<String> getHisOperatorByNo(String wfProcInstNo) {
		return wfProcInstDao.getHisOperatorByNo(wfProcInstNo);
	}

	@Override
	public void doDelPhoto(String pks) {
		wfProcInstDao.doDelPhoto(pks);
	}

	@Override
	public List<Map<String, Object>> findWfProcInstPicByNo(String wfProcInstNo) {
		
		return wfProcInstDao.findWfProcInstPicByNo(wfProcInstNo);
	}
	
	@Override
	public String getDeptManager(String department){
		
		return wfProcInstDao.getDeptManager(department);
	}
	
	@Override
	public List<Map<String, Object>> findTodoTaskGroupByUserId() {
		return wfProcInstDao.findTodoTaskGroupByUserId();
	}
	
	public int getLine (ActivityImpl activityImpl, WfProcInst wfProcInst,JSONObject jsonObject,String actName,final List<Map<String, Object>> resultList){
		int result = 0;
		if("exclusivegateway1".equals(activityImpl.getId())){
			List<PvmTransition> applyManTransitions = activityImpl.getOutgoingTransitions();
			List<HistoricVariableInstance> hisDataList=historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(wfProcInst.getActProcInstId()).list();
			Map<String, Object> hisDataMap = new HashMap<String, Object>();
			if(hisDataList.size()>0){
				for(HistoricVariableInstance historicVariableInstance:hisDataList){
					if(!"PROC_HI_OPERATOR".equals(historicVariableInstance.getVariableName())){
						if(historicVariableInstance.getValue() != null){
							if(StringUtils.isNotBlank(historicVariableInstance.getValue().toString())){
								hisDataMap.put("\""+historicVariableInstance.getVariableName()+"\"", 
										"\""+historicVariableInstance.getValue()+"\"");
							}
						}
					}
				}
			}
			if(jsonObject == null){
				jsonObject =JSONObject.parseObject(hisDataMap.toString().replace("=", ":"));
			}
			for(int p = 0 ;p<applyManTransitions.size();p++){//????????????????????????????????????
				if(this.checkFormDataByRuleEl(applyManTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
					result = p;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public Page<Map<String, Object>> getEmpAccountJqGrid(
			Page<Map<String, Object>> page, String czybh, String actName) {
		return wfProcInstDao.getEmpAccountJqGrid(page,czybh ,actName);
	}
	
	
	@Override
	public Page<Map<String, Object>> getRcvActByJqGrid(
			Page<Map<String, Object>> page, String czybh, String descr) {

		return wfProcInstDao.getRcvActByJqGridPage(page, czybh, descr) ;
	}

	@Override
	public Page<Map<String, Object>> getAdvanceNoByJqgrid(
			Page<Map<String, Object>> page, String czybh, String searchData) {
		return wfProcInstDao.getAdvanceNoByJqgrid(page,czybh ,searchData);
	}
	
	@Override
	public Page<Map<String, Object>> getExpenseAdvanceJqGrid(
			Page<Map<String, Object>> page, Employee employee) {

		return wfProcInstDao.getExpenseAdvanceJqGrid(page, employee);
	}
	
	@Override
	public Page<Map<String, Object>> getExpenseAdvanceTran(
			Page<Map<String, Object>> page, Employee employee) {

		return wfProcInstDao.getExpenseAdvanceTran(page, employee);
	}

	@Override
	public Double getAdvanceAmount(String czybh) {
		return wfProcInstDao.getAdvanceAmount(czybh);
	}

	@Override
    public void doSaveAccount(String actName, String bank, String cardId,String subBranch,
			String czybh) {
		
		wfProcInstDao.doSaveAccount(actName,bank,cardId,subBranch,czybh);
	}
	
	public List<Map<String , Object>> getExpenseAdvance(String wfProcInstNo, String tableName){
		return wfProcInstDao.getExpenseAdvanceAmount(wfProcInstNo,tableName);
	}
	
	/**
	 * ??????????????????
	 * @param taskId
	 * @param czybh
	 * @param status
	 * @param comment
	 * @param processInstId
	 * @param formProperties
	 */
	@Override
	public void doCompExpenseAdvanceTask(String taskId, String czybh, String status,
			String comment, String processInstId,
			Map<String, Object> formProperties) {
		Double confAmount = 0.0;
		String tableName = wfProcInstDao.getMainTableName(formProperties.get("wfProcNo").toString());
		List<Map<String , Object>> list = this.getExpenseAdvance(formProperties.get("wfProcInstNo").toString(),tableName);
		if(formProperties.get("confAmount") != null ){
			confAmount = Double.parseDouble(formProperties.get("confAmount").toString());
		}else{
			confAmount = Double.parseDouble(list.get(0).get("ChgAmount").toString());
		}		
		ExpenseAdvance expenseAdvance = new ExpenseAdvance();
		if(StringUtils.isNotBlank(list.get(0).get("EmpCode").toString())){
			expenseAdvance = this.get(ExpenseAdvance.class, list.get(0).get("EmpCode").toString());
			expenseAdvance.setAmount(confAmount + Double.parseDouble(list.get(0).get("Amount").toString()));
			expenseAdvance.setLastUpdate(new Date());
			expenseAdvance.setLastUpdatedBy(czybh);
			this.update(expenseAdvance);
		} else {
			expenseAdvance.setEmpCode(list.get(0).get("ChgEmpCode").toString());
			expenseAdvance.setAmount(confAmount);
			expenseAdvance.setLastUpdate(new Date());
			expenseAdvance.setLastUpdatedBy(czybh);
			expenseAdvance.setActionLog("ADD");
			expenseAdvance.setExpired("F");
			this.save(expenseAdvance);
		}
		doSaveExpenseAdvanceTran(list,czybh,confAmount,1,formProperties.get("wfProcInstNo").toString(),tableName);
	}
	
	/**
	 * ????????????????????????
	 * @param taskId
	 * @param czybh
	 * @param status
	 * @param comment
	 * @param processInstId
	 * @param formProperties
	 */
	@Override
	public void doCompExpenseClaimTask(String taskId, String czybh, String status,
			String comment, String processInstId, Map<String, Object> formProperties) {
		Double confAmount= 0.0;
		String tableName = wfProcInstDao.getMainTableName(formProperties.get("wfProcNo").toString());
		List<Map<String , Object>> list = this.getExpenseAdvance(formProperties.get("wfProcInstNo").toString(),tableName);
		//??????????????? ???????????????????????????  ??????????????? ????????????????????????
		if(formProperties.get("confAmount") != null ){
			if(StringUtils.isBlank(formProperties.get("confAmount").toString().trim())){
				confAmount=0.0;
			}else{
				confAmount = Double.parseDouble(formProperties.get("confAmount").toString());
			}
		}else{
			confAmount = 0.0;
		}
		ExpenseAdvance expenseAdvance = new ExpenseAdvance();
		 
		if(confAmount > 0){
			if(list != null && StringUtils.isNotBlank(list.get(0).get("EmpCode").toString())){
				expenseAdvance = this.get(ExpenseAdvance.class, list.get(0).get("EmpCode").toString());
				expenseAdvance.setAmount(-1 * confAmount + Double.parseDouble(list.get(0).get("Amount").toString()));
				expenseAdvance.setLastUpdate(new Date());
				expenseAdvance.setLastUpdatedBy(czybh);
				this.update(expenseAdvance);
			} else {
				expenseAdvance.setEmpCode(list.get(0).get("ChgEmpCode").toString());
				expenseAdvance.setAmount(-1 * confAmount);
				expenseAdvance.setLastUpdate(new Date());
				expenseAdvance.setLastUpdatedBy(czybh);
				expenseAdvance.setActionLog("ADD");
				expenseAdvance.setExpired("F");
				this.save(expenseAdvance);
			}
			doSaveExpenseAdvanceTran(list,czybh,confAmount,-1,formProperties.get("wfProcInstNo").toString(),tableName);
		}
	}
	
	/**
	 * ??????????????????
	 * @param list
	 * @param czybh
	 * @param confAmount ?????????????????????????????????
	 * @param diff  ?????????1???????????? -1
	 */
	public void doSaveExpenseAdvanceTran(List<Map<String , Object>> list,String czybh,Double confAmount,int diff,
			String wfProcInstNo,String tableName){
		
		ExpenseAdvanceTran expenseAdvanceTran = new ExpenseAdvanceTran();
		expenseAdvanceTran.setEmpCode(list.get(0).get("ChgEmpCode").toString());
		expenseAdvanceTran.setChgAmount(diff * confAmount);
		expenseAdvanceTran.setAftAmount(Double.parseDouble(list.get(0).get("Amount").toString()) + diff * confAmount);
		
		if(list != null && list.get(0).get("StruPk") != null){

			Map<String, Object> cardMap = wfProcInstDao.getEmpCardData(wfProcInstNo,tableName);
			if (cardMap != null) {
				expenseAdvanceTran.setCardId(cardMap.get("CardId").toString());
				expenseAdvanceTran.setActName(cardMap.get("ActName").toString());
				expenseAdvanceTran.setBank(cardMap.get("Bank").toString());
			}
		}
		
		expenseAdvanceTran.setActionLog("ADD");
		expenseAdvanceTran.setLastUpdate(new Date());
		expenseAdvanceTran.setLastUpdatedBy(czybh);
		expenseAdvanceTran.setExpired("F");
		expenseAdvanceTran.setAdvanceDate(new Date());
		expenseAdvanceTran.setWfProcInstNo(wfProcInstNo);
		this.save(expenseAdvanceTran);
		
	};
	
	@SuppressWarnings("unchecked")
	public void doUpdateWfProcInstData(List<Map<String , Object>> list, Map<String, Object> formProperties,final Map<String, Object> variables) {
		Map<String, Object> map = (Map<String, Object>)formProperties.get("tables");
		//????????????????????? ??????
		if(!(map.size()>0)){
			return;
		}
		
		WfProcInst wfProcInst = new WfProcInst();
		
		if(StringUtils.isNotBlank(formProperties.get("wfProcInstNo").toString())){
			wfProcInst = this.get(WfProcInst.class,formProperties.get("wfProcInstNo").toString());
		}
		for(String key : map.keySet()){
			
			for(Map<String , Object> tableStru : list){
				boolean saveFlag=false;
				if("1".equals(tableStru.get("IsEdit")) && key.equals(tableStru.get("TableCode").toString())){
					Map<String, Object> table = (Map<String, Object>)map.get(key);
					
					for(String column : table.keySet()){
						if(column.equals(tableStru.get("StruCode").toString()) //???????????????????????????????????????
								&& StringUtils.isNotBlank(table.get(column).toString())){//?????????????????????
							
							if("1".equals(tableStru.get("mainTable"))){ //???????????????????????????wfProcInstNo??????
								Map<String, Object> resultMap = new HashMap<String, Object>();
								resultMap.put("table", key);
								resultMap.put(column, table.get(column).toString());
								variables.put(column, table.get(column).toString());
								wfProcInstDao.doUpdateCust_Table(formProperties.get("wfProcInstNo").toString(),resultMap);
								if("DocumentNo".equals(column)){
									StringBuilder mainSummary = new StringBuilder();
							        StringBuilder subSummary = new StringBuilder();
									for(Entry<String, Object> entry : ((IdentityHashMap<String, Object>)formProperties.get("tables")).entrySet()){
										formatSummary(formProperties, entry, mainSummary, subSummary);
									}
									
							        String summary =mainSummary.toString()+"<br/>"+subSummary.toString();
							        if(summary.length()>1000){
							        	summary = summary.substring(0, 950)+"...";
							        }
									wfProcInst.setSummary(summary);
									wfProcInst.setLastUpdate(new Date());
									this.update(wfProcInst);
								}
								
							}else{ //??????????????????PK??????????????????????????????
								Map<String, Object> resultMap = new HashMap<String, Object>();
								resultMap.put("table", key);
								resultMap.put(column, table.get(column).toString());
								if( table.get("PK") != null){//????????????Pk?????? ?????????update
									if(StringUtils.isBlank(table.get("PK").toString()) ){
										table.put("WfProcInstNo", formProperties.get("wfProcInstNo").toString());
										this.wfProcInstDao.autoSaveSql(key, table);
										saveFlag = true;
										break;
									} else {
										if(!"PK".equals(column)){
											wfProcInstDao.doUpdateCust_TableByPk(table.get("PK").toString(),resultMap);
										}
									}
								}
							}
						}
					}
					if(table.get("PK")==null && !"1".equals(tableStru.get("mainTable"))){
						table.put("WfProcInstNo", formProperties.get("wfProcInstNo").toString());
						this.wfProcInstDao.autoSaveSql(key, table);
						saveFlag = true;
						break;
					}
				}
				if(saveFlag){
					break;
				}
			}
		}
	}

	@Override
	public String getMainTableName(String wfProcNo) {
		return wfProcInstDao.getMainTableName(wfProcNo);
	}

	@Override
	public List<Map<String, Object>> getAssigneesByGroupId(String groupId) {
		return wfProcInstDao.getAssigneesByGroupId(groupId);
	}
	
	@Override 
	public void getHighLightedFlows(String pvmActivityId, ProcessDefinitionEntity processDefinitionEntity,List<String> pdId, JSONObject jsonObject){
		for(int i =0 ; i<processDefinitionEntity.getActivities().size();i++){//??????????????????  ???????????????????????????????????????
			if(pvmActivityId.equals(processDefinitionEntity.getActivities().get(i).getId())){
				if(processDefinitionEntity.getActivities().get(i).getOutgoingTransitions().size()>0){
					//????????????
					//pvmTransition?????????????????????????????????????????????
					PvmTransition pvmTransition = null;
						
					List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
					if(pvmTransitions.size() == 1){
						pvmTransition = pvmTransitions.get(0);
					} else {
						for(int p = 0 ;p<pvmTransitions.size();p++){//????????????????????????????????????
							if(this.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(), jsonObject)){
								pvmTransition = pvmTransitions.get(p);
								break;
							}
						}
					}
					pdId.add(pvmTransition.getId());
					//????????????????????????????????? ???????????????????????????????????????id?????????????????????
					getHighLightedFlows(pvmTransition.getDestination().getId(), processDefinitionEntity,pdId, jsonObject);
				}
			} 
		}
	}

	@Override
	public Map<String, Object> getEmpCompany(String department) {
		return wfProcInstDao.getEmpCompany(department);
	}
	
	
	@SuppressWarnings("unchecked")
	public void formatSummary(Map<String, Object> formProperties,Entry<String, Object> entry,
			final StringBuilder mainSummary, final StringBuilder subSummary){
        List<Map<String, Object>> tables = this.wfProcInstDao.getTables(formProperties.get("wfProcNo").toString(), "1", null);
		Map<String, Object> columnsMap = null;
		boolean mainFlag = true;
		boolean nullValue = true;
		boolean continueFlag = true;
		boolean appendFlag = false;

		Map<String, Object> map = (Map<String, Object>) entry.getValue();
		for(Object value :map.values()){
			if(!"".equals(value.toString())){
				nullValue=false;
			}
		}
		//??????????????????,??????????????????,????????????"???-?????????"???Map????????????Map??????
		for(int i = 0; i < tables.size(); i++){
			//Code??????entry.getKey()?????????
			if(tables.get(i).get("Code").toString().equals(entry.getKey())){
				//????????????"???-??????"???Map??????
				columnsMap = StringUtils.convertMapStr(tables.get(i).get("columns").toString().toLowerCase());
				//??????????????????,???????????????????????????????????????
				if(!tables.get(0).get("Code").toString().equals(entry.getKey())){
					mainFlag = false;
					if(nullValue){
						break;
					}
					subSummary.append(tables.get(i).get("Descr").toString()+"[<none/>");
					break;
				}
			}
		}
		for(Entry<String, Object> entMap : columnsMap.entrySet()){
			boolean hasDetail =false;
			for(Entry<String, Object> param : map.entrySet()){
				//??????????????????
				if(param.getValue() != null && StringUtils.isNotBlank(param.getValue().toString()) 
						&& columnsMap.containsKey(param.getKey().toLowerCase())){
					//????????????????????????????????????
					if(mainFlag && entMap.getKey().toLowerCase().equals(param.getKey().toLowerCase())){
						if(appendFlag && mainSummary.length()>0){
							mainSummary.append("<br/>");
						}
						mainSummary.append(columnsMap.get(param.getKey().toLowerCase())+":"+param.getValue());
					}else{
						if(!mainFlag && continueFlag && !nullValue){
							hasDetail =true;
							if(appendFlag){
								subSummary.append("<br/>");
							}
							subSummary.append(columnsMap.get(param.getKey().toLowerCase())+":"+param.getValue());
						}
					}
					appendFlag = true;
				}
			}
			if(hasDetail){
				continueFlag = false;
			}
		}
		continueFlag =true;
		//???????????????????????????
		if(!mainFlag && !nullValue){
			subSummary.append("<none/>]<br/>");
		}
	}

	@Override 
	public void doSaveRefund(ExpenseAdvance expenseAdvance) {
		
		expenseAdvance.setActionLog("ADD");
		expenseAdvance.setLastUpdate(new Date());
		expenseAdvance.setExpired("F");
		saveOrUpdate(expenseAdvance);
	
		ExpenseAdvanceTran expenseAdvanceTran = new ExpenseAdvanceTran();
		expenseAdvanceTran.setEmpCode(expenseAdvance.getEmpCode());
		expenseAdvanceTran.setChgAmount(-1 * expenseAdvance.getChgAmount());
		expenseAdvanceTran.setWfProcInstNo(expenseAdvance.getExpenseAdvanceNo());
		expenseAdvanceTran.setAftAmount(expenseAdvance.getAmount());
		expenseAdvanceTran.setAdvanceDate(new Date());
		expenseAdvanceTran.setLastUpdate(new Date());
		expenseAdvanceTran.setLastUpdatedBy(expenseAdvance.getLastUpdatedBy());
		expenseAdvanceTran.setExpired("F");
		expenseAdvanceTran.setActionLog("ADD");
		expenseAdvanceTran.setRemarks(expenseAdvance.getRemarks());
		
		this.save(expenseAdvanceTran);
	}

	@Override
	public boolean isProcOperator(String taskId, String czybh) {
		return wfProcInstDao.isProcOperator(taskId, czybh);
	}
	
	@Override
	public void saveMarketClaimMessage(String wfProcInstNo) {
		
		wfProcInstDao.saveMarketClaimMessage(wfProcInstNo);
		
	}

	@Override
	public Page<Map<String, Object>> getSupplAccountJqGrid(Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		return wfProcInstDao.getSupplAccountJqGrid(page, splCheckOut);
	}
	
	public String getWfPrjCardInfo(String wfProcNo, String wfProcInstNo){
		
		return wfProcInstDao.getWfPrjCardInfo(wfProcNo, wfProcInstNo);
	}

	@Override
	public String getTaskCommntByPIIDTaskName(String procInstId, String taskName) {
	
		return wfProcInstDao.getTaskCommntByPIIDTaskName(procInstId, taskName);
	}

	@Override
	public Serializable doDelEmpCard(String actName, String cardId) {

		return wfProcInstDao.doDelEmpCard(actName, cardId);
	}

	
}
 
