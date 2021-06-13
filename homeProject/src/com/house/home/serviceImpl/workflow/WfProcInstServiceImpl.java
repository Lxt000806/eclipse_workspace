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
		
			//设置发起部门
			String department ="";
			if(formProperties.containsKey("department")){
				department = formProperties.get("department").toString();
			} 
			boolean nullValue = true; 
		 	//获取流程定义编号
	        Map<String, Object> seqNoMap = this.wfProcInstDao.getSeqNoBySql("tWfProcInst");//this.getSeqNo("tWfProcInst");
	        String wfProcInstNo = seqNoMap.get("no").toString();
	        pushWfProcInstNo.put("pushWfProcInstNo", wfProcInstNo);
	        //获取摘要字段
	        List<Map<String, Object>> tables = this.wfProcInstDao.getTables(formProperties.get("wfProcNo").toString(), "1", null);

	        //主表摘要
	        StringBuilder mainSummary = new StringBuilder();
	        
	        //明细表摘要
	        StringBuilder subSummary = new StringBuilder();
	        
	        //流程变量map
			Map<String, Object> activityMap = new LinkedHashMap<String, Object>();
			//遍历需要保存所有表记录
			//其中:IdentityHashMap特殊的Map结构,键以对象区分(例:String str = "123" 与 String str1 = str 对象相同;String str = "123" 与 String str1 = "123" 对象不同)
			//因为考虑到明细表记录不止一条而采用
			for(Entry<String, Object> entry : ((IdentityHashMap<String, Object>)formProperties.get("tables")).entrySet()){
				
				if(tables == null || tables.size() == 0){
					break;
				}
				//获取表记录
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				
				nullValue = true;
				for(Object value :map.values()){
					if(!"".equals(value.toString())){
						nullValue=false;
					}
				}
				
				//相应表格的所有列及其名称
				Map<String, Object> columnsMap = null;
				//主表标记,若为false则为明细表
				boolean mainFlag = true;
				
				//遍历所有表格,区分是否主表,并且转换"列-列名称"的Map字符串为Map数据
				for(int i = 0; i < tables.size(); i++){
					//Code以及entry.getKey()为表名
					if(tables.get(i).get("Code").toString().equals(entry.getKey())){
						//获取表格"列-列名"的Map数据
						columnsMap = StringUtils.convertMapStr(tables.get(i).get("columns").toString().toLowerCase());
						//判断是否主表,若为明细表添加相应摘要信息
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
				//若为主表,获取流程变量字段
				if(mainFlag){
					List<Map<String, Object>> mainTables = this.wfProcInstDao.getTables(formProperties.get("wfProcNo").toString(), null, "0");
					for(String key : StringUtils.convertMapStr(mainTables.get(0).get("columns").toString()).keySet()){
						activityMap.put(key.toLowerCase(), "");
					}
				}
				//对于当行记录是否添加过,方便添加<br/>
				boolean appendFlag = false;
				boolean continueFlag =true; 
				//遍历表记录
				for(Entry<String, Object> entMap : columnsMap.entrySet()){
					boolean hasDetail =false;
					for(Entry<String, Object> param : map.entrySet()){
						//保存流程变量值
						if(mainFlag){
							activityMap.put(param.getKey(), param.getValue().toString().trim());
						}
						//拼接摘要信息
						if(param.getValue() != null && StringUtils.isNotBlank(param.getValue().toString()) 
								&& columnsMap.containsKey(param.getKey().toLowerCase())){
							//主表和明细表摘要信息拼接
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
				//明细表摘要结尾部分
				if(!mainFlag && !nullValue){
					subSummary.append("<none/>]<br/>");
				}
				
				//增加流程编号,并且保存到相应的业务表
				map.put("wfProcInstNo", wfProcInstNo);
				
				// 重构上面获取概要的内容
				//formatSummary(formProperties, entry, mainSummary, subSummary);
				
				//保存明细表
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
            	//审批通过时 更新数据
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
            
            // 设置流程变量
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
            if (historicProcessInstance.getEndTime() != null) { // 流程结束，须更新业务流程表(tWorkflowProcInst)的状态
            	if (StringUtils.equals(status, "cancel")) {
            		wfProcInst.setStatus("3"); // 撤销
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
           		wfProcTrack.setActionType("3"); // 审批通过
        	} else if (StringUtils.equals(status, "reject")) {
        		wfProcTrack.setActionType("4"); // 审批拒绝
        	} else if (StringUtils.equals(status, "cancel")) {
        		wfProcTrack.setActionType("5"); // 撤销
        	} else {
        		wfProcTrack.setActionType("6"); // 退回
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
			//tablecode 关联去取申请事由   	 no 取流程过程明细
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
		for(int i =0 ; i<processDefinitionEntity.getActivities().size();i++){//遍历对应支线  列出所有任务及任务的执行人
			String actName=processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString();
			
			if(pvmId.equals(processDefinitionEntity.getActivities().get(i).getId())){
				if(processDefinitionEntity.getActivities().get(i).getOutgoingTransitions().size()>0){
					if(pvmId.split("copyTask").length > 1){
						czyList.add(actName+"__"+getActUser(wfProcNo,processDefinitionEntity.getActivities().get(i).getId(),""));
						czyList.add(pvmId);
						operateList.add(czyList);
					}
					//任务定义
					TaskDefinition taskDefinition = ((TaskDefinition)processDefinitionEntity.getActivities().get(i).getProperties().get("taskDefinition"));
					//pvmTransition连接线，获取该任务的出口连接线
					PvmTransition pvmTransition  = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions().get(0);
					
					if(taskDefinition!=null){
						pdId.add(pvmTransition.getId());
						if(taskDefinition.getTaskListeners().get("create").get(0) != null){
							Set<Expression> expressions = taskDefinition.getCandidateGroupIdExpressions();
							TaskListener taskListener  =  taskDefinition.getTaskListeners().get("create").get(0);
							//判断是否存在监听器  存在的话辨别监听器的类型 获取对应的执行人 ，当 taskListener 为null 时  ，类型就不是 DelegateExpressionTaskListener
							if("org.activiti.engine.impl.bpmn.listener.DelegateExpressionTaskListener".equals(taskListener.getClass().getName())){
								String expressionText = ((DelegateExpressionTaskListener)taskDefinition.getTaskListeners().get("create").get(0)).getExpressionText();
								if("${wfOptionListener}".equals(expressionText)){//当存在选择任务执行人的监听器 ，
									//czyList:1.节点的名称，2.流程的编码 taskKey ，taskKey用来判断是预选人是全公司还是按角色 配置在tWfProcOption
									czyList.add(processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString());
									czyList.add(processDefinitionEntity.getActivities().get(i).getId().toString());
									operateList.add(czyList);
								} else if ("${deptLeaderListener}".equals(expressionText)){ //存在直接领导监听器 获取直接领导 
									leaderCode =this.getDeptLeader(department);
									if(StringUtils.isNotBlank(leaderCode)){
										String errorText = "";
										if(leaderCode.equals(czybh.trim()) && !"017".equals(wfProcNo) && !"007".equals(wfProcNo) && !"008".equals(wfProcNo)  
												&& !"018".equals(wfProcNo) && !"023".equals(wfProcNo) && !"024".equals(wfProcNo) && !"012".equals(wfProcNo)
												&& !"010".equals(wfProcNo) && !"011".equals(wfProcNo) && !"013".equals(wfProcNo) && !"014".equals(wfProcNo) 
												&& !"001".equals(wfProcNo) && !"005".equals(wfProcNo)
												&& !"003".equals(wfProcNo) && !"004".equals(wfProcNo) && !"006".equals(wfProcNo) && !"009".equals(wfProcNo) 
										){
											errorText="(选错部门,直接主管不能为自己)";
										}
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? leaderCode:employee.getNameChi())+errorText);//如果存在编号，员工表里面没有对对应员工 则显示编号
									}else {
										operateList.add(actName+"__(系统管理员)"+this.getActUser("Admin"));//不存在执行人 则显示流程名称,这里还没找到自动通过的方法
									}
								} else if ("${deptLeaderTowListener}".equals(expressionText)){//存在二级领导监听器 获取二级领导
									leaderCode =this.getDeptLeaderTow(department);
									if(StringUtils.isNotBlank(leaderCode)){
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? leaderCode:employee.getNameChi()));
									}else {
										operateList.add(actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
									}	
								} else if ("${deptManageLisener}".equals(expressionText)){
									leaderCode =this.getDeptManager(department);
									if(StringUtils.isNotBlank(leaderCode)){
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? leaderCode:employee.getNameChi()));
									}else {
										operateList.add(actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
									}
								} else if ("${deptLeaderPrjLeaderListener}".equals(expressionText)){
									leaderCode =this.getDeptLeader(department);
									String prjDeptLeader = getActUser("EngineerManager");
									if(StringUtils.isNotBlank(leaderCode)){
										employee=this.get(Employee.class, leaderCode);
										operateList.add(actName+"__"+(employee == null ? "":employee.getNameChi())+(StringUtils.isNotBlank(prjDeptLeader)?"/"+prjDeptLeader:""));
									} else {
										operateList.add(actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
									}
								}else if ("${branchHeadLisener}".equals(expressionText)){
									if(jsonObject.get("Company") != null && StringUtils.isNotBlank(jsonObject.get("Company").toString())){
										if("福清有家".equals(jsonObject.get("Company").toString())){
											operateList.add(actName+"__"+this.getActUser("FQBranchHeader"));
										} else if ("长乐有家".equals(jsonObject.get("Company").toString())){
											operateList.add(actName+"__"+this.getActUser("CLBranchHeader"));
										} else {
											operateList.add(actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
										}
									} else {
										operateList.add(actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
									}
								} else {
									operateList.add(processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString()+"__无");
								}
							}else if(expressions.size()>0){
								//当执行人为表达式值得时候 获取Activiti成员关系表的对应组的对应成员  ，未考虑 一个节点执行人有多个表达式的情况 列[infoOfficer,cfo]
								String expressString = expressions.toString().substring(1, expressions.toString().length()-1);
								if(StringUtils.isNotBlank(expressString)){
									operateList.add(actName+"__"+getActUser(expressString));
								}
							}else {
								operateList.add(processDefinitionEntity.getActivities().get(i).getProperties().get("name").toString()+"__无");
							}
						}
					} else {
						List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
						if(pvmTransitions.size()>1){
							for(int p = 0 ;p<pvmTransitions.size();p++){//取得所选值对应的流程支线
								if(this.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
									pvmTransition = pvmTransitions.get(p);
									break;
								}
							}
						}
						pdId.add(pvmTransition.getId());
					}
					//当不存在任务定义的时候 根据连接线获取下一个节点的id，进入下个节点
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
        				for(int p = 0 ;p<exclusiveTransitions.size();p++){//取得所选值对应的流程支线
        					if(this.checkFormDataByRuleEl(exclusiveTransitions.get(p).getProperty("conditionText").toString(),jsonObject)){
        						PvmActivity pvmActivity =null; //活动节点
        						pvmActivity = exclusiveTransitions.get(p).getDestination();
        						activityImpl = (ActivityImpl) pvmActivity;
        						actName = activityImpl.getProperties().get("name").toString();
        						break;
        					}
        				}
        			} else {
        				List<PvmTransition> applyManTransitions = activityImpl.getOutgoingTransitions();
        				PvmActivity pvmActivity =null; //活动节点
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
						if("${wfTaskContinueListener}".equals(expressionText)){//当存在选择任务执行人的监听器 ，
							String express = expressions.toString().substring(1, expressions.toString().length()-1);
							map.put("wfprocdescr", actName);
							map.put("namechidescr", getActUser(express));
						} else if("${wfOptionListener}".equals(expressionText)){//当存在选择任务执行人的监听器 ，
							map.put("wfprocdescr", actName);
							if(StringUtils.isNotBlank(getOptionAssignee(wfProcNo,taskDefinition.getKey()))){
								employee = this.get(Employee.class, getOptionAssignee(wfProcNo,taskDefinition.getKey()));
								map.put("namechidescr", employee.getNameChi());
							}
						} else if("${deptLeaderListener}".equals(expressionText)){ //存在直接领导监听器 获取直接领导 
							leaderCode =this.getDeptLeader(wfProcInst.getDepartment());
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",employee == null ? leaderCode:employee.getNameChi());//如果存在编号，员工表里面没有对对应员工 则显示编号
							}else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr",this.getActUser("Admin"));//如果不存在执行人 则判为系统管理员
							}
						} else if("${deptLeaderTowListener}".equals(expressionText)){//存在二级领导监听器 获取二级领导
							leaderCode =this.getDeptLeaderTow(wfProcInst.getDepartment());
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",employee == null ? leaderCode:employee.getNameChi());
							}else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr","未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
							}
						} else if("${deptManageLisener}".equals(expressionText)){
							leaderCode =this.getDeptManager(wfProcInst.getDepartment());
							if(StringUtils.isNotBlank(leaderCode)){
								employee=this.get(Employee.class, leaderCode);
								map.put("wfprocdescr", actName);
								map.put("namechidescr",employee == null ? leaderCode:employee.getNameChi());
							}else {
								map.put("wfprocdescr", actName);
								map.put("namechidescr","未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
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
								map.put("namechidescr","未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
							}
						} else if ("${branchHeadLisener}".equals(expressionText)){
							map.put("wfprocdescr", "分公司负责人");
							if(jsonObject.get("Company") != null && StringUtils.isNotBlank(jsonObject.get("Company").toString())){
								if("福清有家".equals(jsonObject.get("Company").toString())){
									map.put("namechidescr",this.getActUser("FQBranchHeader"));
								} else if ("长乐有家".equals(jsonObject.get("Company").toString())){
									map.put("namechidescr",this.getActUser("CLBranchHeader"));
								} else {
									map.put("namechidescr",actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
								}
							} else {
								map.put("namechidescr",actName+"__"+"未找到审批人，将自动通过");//未找到审批人 同钉钉 自动通过
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
						starTaskKey = activityImpl.getOutgoingTransitions().get(i).getDestination().getId();//下一节点的TaskKey,循环
					} else {
						if(activityImpl.getOutgoingTransitions().size()>0){
							List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
							if(pvmTransitions.size() == 1){
							
								starTaskKey = activityImpl.getOutgoingTransitions().get(0).getDestination().getId();//下一节点的TaskKey,循环
							} else {
								
								for(int p = 0 ;p<pvmTransitions.size();p++){//取得所选值对应的流程支线
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
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
	
	/**
	 * 判断El表达式
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
	 * OA页面选择条件后，获取流程执行人
	 */
	public List<Object> getOperator(JSONObject jsonObject,String pdID,String department,String wfProcNo,
			String czybh){
		boolean hasGetWay = false;
		PvmActivity pvmActivity =null; //活动节点
		ProcessDefinitionEntity processDefinitionEntity = null; //流程定义实体
		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(pdID); //根据流程定义id获取已部署的流程定义实体 
		ActivityImpl activityImpl= processDefinitionEntity.getActivities().get(0);
		for (int i=0;i<processDefinitionEntity.getActivities().size();i++){
			//exclusivegateway1 //获取 el表达式
			if("exclusivegateway1".equals(processDefinitionEntity.getActivities().get(i).getId())){
				hasGetWay = true;
				List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
				for(int p = 0 ;p<pvmTransitions.size();p++){//取得所选值对应的流程支线
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
			// 修改之前审批的推送消息为已执行
			wfProcInstDao.doUpdatePustRcvStatus(wfProcInstNo);
			// 推送给下个节点的执行人
			if("approval".equals(status)){
				wfProcInstDao.doPushTaskToOperator(wfProcInstNo);
			}
			// 推送消息给发起人
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
			for(int p = 0 ;p<applyManTransitions.size();p++){//取得所选值对应的流程支线
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
	 * 预支金额变动
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
	 * 报销余额变动保存
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
		//有审批金额 变更金额为审批金额  没审批金额 变更金额为申请额
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
	 * 余额明细保存
	 * @param list
	 * @param czybh
	 * @param confAmount 最终审批通过的变更金额
	 * @param diff  预支为1，报销为 -1
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
		//不存在表单数据 返回
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
						if(column.equals(tableStru.get("StruCode").toString()) //允许修改的字段改动才修改值
								&& StringUtils.isNotBlank(table.get(column).toString())){//不允许修改为空
							
							if("1".equals(tableStru.get("mainTable"))){ //主表可以根据主键：wfProcInstNo修改
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
								
							}else{ //从表增加主键PK才能关联修改相关数据
								Map<String, Object> resultMap = new HashMap<String, Object>();
								resultMap.put("table", key);
								resultMap.put(column, table.get(column).toString());
								if( table.get("PK") != null){//模板没有Pk的值 不执行update
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
		for(int i =0 ; i<processDefinitionEntity.getActivities().size();i++){//遍历对应支线  列出所有任务及任务的执行人
			if(pvmActivityId.equals(processDefinitionEntity.getActivities().get(i).getId())){
				if(processDefinitionEntity.getActivities().get(i).getOutgoingTransitions().size()>0){
					//任务定义
					//pvmTransition连接线，获取该任务的出口连接线
					PvmTransition pvmTransition = null;
						
					List<PvmTransition> pvmTransitions = processDefinitionEntity.getActivities().get(i).getOutgoingTransitions();
					if(pvmTransitions.size() == 1){
						pvmTransition = pvmTransitions.get(0);
					} else {
						for(int p = 0 ;p<pvmTransitions.size();p++){//取得所选值对应的流程支线
							if(this.checkFormDataByRuleEl(pvmTransitions.get(p).getProperty("conditionText").toString(), jsonObject)){
								pvmTransition = pvmTransitions.get(p);
								break;
							}
						}
					}
					pdId.add(pvmTransition.getId());
					//当不存在任务定义的时候 根据连接线获取下一个节点的id，进入下个节点
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
		//遍历所有表格,区分是否主表,并且转换"列-列名称"的Map字符串为Map数据
		for(int i = 0; i < tables.size(); i++){
			//Code以及entry.getKey()为表名
			if(tables.get(i).get("Code").toString().equals(entry.getKey())){
				//获取表格"列-列名"的Map数据
				columnsMap = StringUtils.convertMapStr(tables.get(i).get("columns").toString().toLowerCase());
				//判断是否主表,若为明细表添加相应摘要信息
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
				//拼接摘要信息
				if(param.getValue() != null && StringUtils.isNotBlank(param.getValue().toString()) 
						&& columnsMap.containsKey(param.getKey().toLowerCase())){
					//主表和明细表摘要信息拼接
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
		//明细表摘要结尾部分
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
 
