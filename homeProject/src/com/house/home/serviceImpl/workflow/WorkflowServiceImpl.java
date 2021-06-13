package com.house.home.serviceImpl.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.dao.workflow.WfProcInstDao;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.service.workflow.WorkflowService;

@SuppressWarnings("serial")
@Service
public class WorkflowServiceImpl extends BaseServiceImpl implements WorkflowService {

	@Autowired
	private TaskService taskService;
	@Autowired 
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private WfProcInstDao wfProcInstDao;
	@Autowired 
	private IdentityService identityService;
	
	/**
	 * 清空节点的所有流向
	 * @param activityImpl
	 * @return
	 */
	public List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		
		// 存储当前节点所有流向临时变量
		List<PvmTransition> originalPvmtransitionList = new ArrayList<PvmTransition>(); 
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition: pvmTransitionList) {
			originalPvmtransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();
		
		return originalPvmtransitionList;
	}
	
	/**
	 * 还原指定活动节点流向
	 * @param activityImpl
	 * @param originalPvmTransitionList
	 */
	public void restoreTransition(ActivityImpl activityImpl,
		List<PvmTransition> originalPvmTransitionList) {
		
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		
		// 还原以前流向
		for (PvmTransition pvmTransition: originalPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	
	/**
	 * 根据任务id查询任务
	 * @param taskId
	 * @return
	 */
	public Task findTaskById(String taskId) {
		Task task = taskService.createTaskQuery()
			.taskId(taskId).singleResult();
		if (task == null) {
			throw new RuntimeException("未找到任务实例：" + taskId);
		}
		
		return task;
	}
	
	/**
	 * 通过任务id查询流程实例
	 * @param taskId
	 * @return
	 */
	public ProcessInstance findProcessInstanceByTaskId(String taskId) {
		Task task = this.findTaskById(taskId);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			.processInstanceId(task.getProcessInstanceId())
			.singleResult();
		if (processInstance == null) {
			throw new RuntimeException("未找到流程实例：" + task.getProcessInstanceId());
		}
		
		return processInstance;
	}
	
	/**
	 * 通过任务id查询流程定义
	 * @param taskId
	 * @return
	 */
	public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) {
		Task task = this.findTaskById(taskId);
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
			.getDeployedProcessDefinition(task.getProcessDefinitionId());
		if (processDefinitionEntity == null) {
			throw new RuntimeException("为找到流程定义：" + task.getProcessDefinitionId());
		}
		
		return processDefinitionEntity;
	}
	
	/**
	 * 根据任务id和节点id获取活动节点
	 * @param taskId
	 * @param activityId
	 * @return
	 */
	public ActivityImpl findActivitiImpl(String taskId, String activityId) {
		
		// 取得流程定义
		ProcessDefinitionEntity processDefinitionEntity = this.findProcessDefinitionEntityByTaskId(taskId);
		
		// 获取当前活动节点id
		if (StringUtils.isBlank(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}
		
		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl: processDefinitionEntity.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl
					.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}
		
		return processDefinitionEntity.findActivity(activityId);
	}
	
	/**
	 * 流程转向操作
	 * @param taskId
	 * @param activityId
	 * @param variables
	 */
	public void turnTransition(String taskId, String activityId,
		Map<String, Object> variables) {
		
		// 当前节点
		ActivityImpl currentActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> originalPvmTransitionList = clearTransition(currentActivity);
		
		// 创建新流向
		TransitionImpl newTransition = currentActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = this.findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);
		
		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);
		// 还原以前流向
		restoreTransition(currentActivity, originalPvmTransitionList);
	}
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param variables
	 * @param activityId
	 * @throws Exception
	 */
	public void commitProcess(String taskId, Map<String, Object> variables, String activityId,String processInstId,String wfProcInstNo){
		
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isBlank(activityId)) {
			taskService.complete(taskId, variables);
			
			List<Task> taskQuery = taskService.createTaskQuery().processInstanceId(processInstId).list();
			if(taskQuery.size()>0){
				Object object = taskService.getVariable(taskQuery.get(0).getId(),"PROC_HI_OPERATOR");
				List<String> list = new ArrayList<String>();
				if(object!=null){
					list = (List<String>) object;
				}
				if(StringUtils.isNotBlank(taskQuery.get(0).getAssignee())){
					if(checkAssigneeSame(list,taskQuery.get(0).getAssignee())){
						WfProcTrack wfProcTrack = new WfProcTrack();
				        wfProcTrack.setWfProcInstNo(wfProcInstNo);
				        wfProcTrack.setActionType("3");
				        wfProcTrack.setOperId(taskQuery.get(0).getAssignee());
				        wfProcTrack.setRemarks("去重自动审批通过");
				        wfProcTrack.setLastUpdate(DateUtil.addSecond(new Date(), 10));
				        wfProcTrack.setLastUpdatedBy(taskQuery.get(0).getAssignee());
				        wfProcTrack.setActionLog("ADD");
				        wfProcTrack.setExpired("F");
				        wfProcTrack.setFromActivityId(taskQuery.get(0).getId());
				        wfProcTrack.setFromActivityDescr(taskQuery.get(0).getName());
				        this.save(wfProcTrack);
						commitProcess(taskQuery.get(0).getId(),null,activityId,processInstId,wfProcInstNo);
					}
				}else{
					List<IdentityLink> identityLisk= taskService.getIdentityLinksForTask(taskQuery.get(0).getId());
					if(identityLisk.size() == 0 ){
						return;
					}
					List<User> groupUserList= identityService.createUserQuery() .memberOfGroup(identityLisk.get(0).getGroupId()).list(); 
					for(User user : groupUserList){
						if(checkAssigneeSame(list,user.getId())){
							WfProcTrack wfProcTrack = new WfProcTrack();
					        wfProcTrack.setWfProcInstNo(wfProcInstNo);
					        wfProcTrack.setActionType("3");
					        wfProcTrack.setOperId(user.getId());
					        wfProcTrack.setRemarks("去重自动审批通过");
					        wfProcTrack.setLastUpdate(DateUtil.addSecond(new Date(), 10));
					        wfProcTrack.setLastUpdatedBy(user.getId());
					        wfProcTrack.setActionLog("ADD");
					        wfProcTrack.setExpired("F");
					        wfProcTrack.setFromActivityId(taskQuery.get(0).getId());
					        wfProcTrack.setFromActivityDescr(taskQuery.get(0).getName());
					        this.save(wfProcTrack);
							commitProcess(taskQuery.get(0).getId(),null,activityId,processInstId,wfProcInstNo);
							break;
						}
					}
				}
			}
		} else { // 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}
	
	/**
	 * 终止流程
	 * @param taskId
	 * @throws Exception
	 */
	public void endProcess(String taskId){
		ActivityImpl endActivity = findActivitiImpl(taskId, "end");
		commitProcess(taskId, null, endActivity.getId(),"","");
	}
	
	public boolean checkAssigneeSame(List<String> list,String user){
		boolean result = false;
		for(String value:list){
			if(StringUtils.isNotBlank(user)){
				if(user.trim().equals(value.trim())){
					result = true ;
					break;
				}
			}
		}
		
		return result;
	}
}
