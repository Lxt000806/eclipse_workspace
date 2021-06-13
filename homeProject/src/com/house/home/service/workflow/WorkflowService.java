package com.house.home.service.workflow;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseService;

@Service
public interface WorkflowService extends BaseService {
	
	public List<PvmTransition> clearTransition(ActivityImpl activityImpl);
	
	/**
	 * 还原指定活动节点流向
	 * @param activityImpl
	 * @param originalPvmTransitionList
	 */
	public void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> originalPvmTransitionList);
	
	/**
	 * 根据任务id查询任务
	 * @param taskId
	 * @return
	 */
	public Task findTaskById(String taskId);
	
	/**
	 * 通过任务id查询流程实例
	 * @param taskId
	 * @return
	 */
	public ProcessInstance findProcessInstanceByTaskId(String taskId);
	
	/**
	 * 通过任务id查询流程定义
	 * @param taskId
	 * @return
	 */
	public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId);
	
	/**
	 * 根据任务id和节点id获取活动节点
	 * @param taskId
	 * @param activityId
	 * @return
	 */
	public ActivityImpl findActivitiImpl(String taskId, String activityId);
	
	/**
	 * 流程转向操作
	 * @param taskId
	 * @param activityId
	 * @param variables
	 */
	public void turnTransition(String taskId, String activityId, Map<String, Object> variables);
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param variables
	 * @param activityId
	 * @throws Exception
	 */
	public void commitProcess(String taskId, Map<String, Object> variables, String activityId,String processInstId,String wfProcInstNo);
	
	/**
	 * 终止流程
	 * @param taskId
	 * @throws Exception
	 */
	public void endProcess(String taskId);
}
