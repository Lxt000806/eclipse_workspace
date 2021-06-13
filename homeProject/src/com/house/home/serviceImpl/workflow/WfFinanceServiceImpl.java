package com.house.home.serviceImpl.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.dao.workflow.WfProcInstDao;
import com.house.home.entity.finance.ExpenseAdvance;
import com.house.home.entity.finance.ExpenseAdvanceTran;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.service.workflow.WfFinanceService;
import com.house.home.service.workflow.WorkflowService;

@SuppressWarnings("serial")
@Service 
public class WfFinanceServiceImpl extends BaseServiceImpl implements WfFinanceService {

	@Autowired
	private  WfProcInstDao wfProcInstDao;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private WorkflowService workflowService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doCompleteProcTask(String taskId, String czybh, String status, String comment,String processInstId,Map<String, Object> formProperties){
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
				List<Map<String , Object>> list = wfProcInstDao.getProcTaskTableStru(taskId, formProperties.get("wfProcInstNo").toString());
				if (list != null) {
					Map<String, Object> map = (Map<String, Object>)formProperties.get("tables");
					for(String key : map.keySet()){
						for(Map<String , Object> tableStru : list){
							if("1".equals(tableStru.get("IsEdit")) && key.equals(tableStru.get("TableCode").toString())){
								Map<String, Object> table = (Map<String, Object>)map.get(key);
								for(String column : table.keySet()){
									if(column.equals(tableStru.get("StruCode").toString()) //允许修改的字段改动才修改值
											&& StringUtils.isNotBlank(table.get(column).toString())){//不允许修改为空
										if("1".equals(tableStru.get("mainTable"))){//主表可以根据主键：wfProcInstNo修改
											Map<String, Object> resultMap = new HashMap<String, Object>();
											resultMap.put("table", key);
											resultMap.put(column, table.get(column).toString());
											variables.put(column, table.get(column).toString());
											wfProcInstDao.doUpdateCust_Table(formProperties.get("wfProcInstNo").toString(),resultMap);
										}else{// 从表增加主键PK才能关联修改相关数据
											Map<String, Object> resultMap = new HashMap<String, Object>();
											resultMap.put("table", key);
											resultMap.put(column, table.get(column).toString());
											if(StringUtils.isNotBlank(table.get("Pk").toString())){//模板没有Pk的值 不执行update
												wfProcInstDao.doUpdateCust_TableByPk(table.get("Pk").toString(),resultMap);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			Object object = taskService.getVariable(taskId,"PROC_HI_OPERATOR");
			List<String> list = new ArrayList<String>();
			if(object != null){
				list = (List<String>) object;
			}
			
			list.add(czybh);
			variables.put("PROC_HI_STATUS", status);
			if("approval".equals(status)){
				variables.put("PROC_HI_OPERATOR", list);
			}
			if("applyman".equals(status)){
				variables.put("PROC_HI_OPERATOR", null);
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
	public void doCompExpenseAdvanceTask(String taskId, String czybh, String status,
			String comment, String processInstId,
			Map<String, Object> formProperties) {
		this.doCompleteProcTask(taskId, czybh, status, comment,processInstId,formProperties);
		String tableName = wfProcInstDao.getMainTableName(formProperties.get("wfProcNo").toString());
		List<Map<String , Object>> list = this.getExpenseAdvance(formProperties.get("wfProcInstNo").toString(),tableName);
		ExpenseAdvance expenseAdvance = new ExpenseAdvance();
		if(StringUtils.isNotBlank(list.get(0).get("EmpCode").toString())){
			expenseAdvance = this.get(ExpenseAdvance.class, list.get(0).get("EmpCode").toString());
			expenseAdvance.setAmount(Double.parseDouble(list.get(0).get("ChgAmount").toString()) + 
					Double.parseDouble(list.get(0).get("Amount").toString()));
			expenseAdvance.setLastUpdate(new Date());
			expenseAdvance.setLastUpdatedBy(czybh);
			this.update(expenseAdvance);
		} else {
			expenseAdvance.setEmpCode(list.get(0).get("ChgEmpCode").toString());
			expenseAdvance.setAmount(Double.parseDouble(list.get(0).get("ChgAmount").toString()));
			expenseAdvance.setLastUpdate(new Date());
			expenseAdvance.setLastUpdatedBy(czybh);
			expenseAdvance.setActionLog("ADD");
			expenseAdvance.setExpired("F");
			this.save(expenseAdvance);
		}
		doSaveExpenseAdvanceTran(list,czybh);
	}
	
	@Override
	public void doCompExpenseClaimTask(String taskId, String czybh, String status,
			String comment, String processInstId,
			Map<String, Object> formProperties) {
		this.doCompleteProcTask(taskId, czybh, status, comment,processInstId,formProperties);
		String tableName = wfProcInstDao.getMainTableName(formProperties.get("wfProcNo").toString());
		List<Map<String , Object>> list = this.getExpenseAdvance(formProperties.get("wfProcInstNo").toString(),tableName);
		ExpenseAdvance expenseAdvance = new ExpenseAdvance();
		if(StringUtils.isNotBlank(list.get(0).get("EmpCode").toString())){
			expenseAdvance = this.get(ExpenseAdvance.class, list.get(0).get("EmpCode").toString());
			expenseAdvance.setAmount(- Double.parseDouble(list.get(0).get("ChgAmount").toString()) +
					Double.parseDouble(list.get(0).get("Amount").toString()));
			expenseAdvance.setLastUpdate(new Date());
			expenseAdvance.setLastUpdatedBy(czybh);
			this.update(expenseAdvance);
		} else {
			expenseAdvance.setEmpCode(list.get(0).get("ChgEmpCode").toString());
			expenseAdvance.setAmount(-Double.parseDouble(list.get(0).get("ChgAmount").toString()));
			expenseAdvance.setLastUpdate(new Date());
			expenseAdvance.setLastUpdatedBy(czybh);
			expenseAdvance.setActionLog("ADD");
			expenseAdvance.setExpired("F");
			this.save(expenseAdvance);
		}
		doSaveExpenseAdvanceTran(list,czybh);
	}
	
	public WfProcInst getWfProcInstByActProcInstId(String actProcInstId){
		return wfProcInstDao.getWfProcInstByActProcInstId(actProcInstId);
	}
	
	public List<Map<String , Object>> getExpenseAdvance(String wfProcInstNo, String tableName){
		return wfProcInstDao.getExpenseAdvanceAmount(wfProcInstNo,tableName);
	}
	
	public void doSaveExpenseAdvanceTran(List<Map<String , Object>> list,String czybh){
		ExpenseAdvanceTran expenseAdvanceTran = new ExpenseAdvanceTran();
		expenseAdvanceTran.setEmpCode(list.get(0).get("ChgEmpCode").toString());
		expenseAdvanceTran.setChgAmount(-Double.parseDouble(list.get(0).get("ChgAmount").toString()));
		expenseAdvanceTran.setAftAmount(Double.parseDouble(list.get(0).get("Amount").toString()) 
				 - Double.parseDouble(list.get(0).get("ChgAmount").toString()));
		expenseAdvanceTran.setCardId(list.get(0).get("CardId").toString());
		expenseAdvanceTran.setActName(list.get(0).get("ActName").toString());
		expenseAdvanceTran.setBank(list.get(0).get("Bank").toString());
		expenseAdvanceTran.setActionLog("ADD");
		expenseAdvanceTran.setLastUpdate(new Date());
		expenseAdvanceTran.setLastUpdatedBy(czybh);
		expenseAdvanceTran.setExpired("F");
		expenseAdvanceTran.setAdvanceDate(new Date());
		this.save(expenseAdvanceTran);
	};
	
}























