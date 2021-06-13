package com.house.home.service.workflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.ExpenseAdvance;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcess;

public interface WfProcInstService extends BaseService {

	/**wfProcInst分页信息
	 * @param page
	 * @param wfProcInst
	 * @return
	 */
	public Page<Map<String,Object>> findWfProcInstPageBySql(Page<Map<String,Object>> page, WfProcInst wfProcInst);

	/**
	 * 获取流程列表
	 * @param page
	 * @param wfProcInst
	 * @return
	 */
	public Page<Map<String,Object>> getApplyListByJqgrid(Page<Map<String,Object>> page, WfProcInst wfProcInst);
	/**
	 * 查询流程数据
	 * @param page
	 * @param wfProcInst
	 * @return
	 */
	public Page<Map<String,Object>> getAllListByJqgrid(Page<Map<String,Object>> page, WfProcInst wfProcInst);
	
	/**
	 * 获取审批单历史记录 
	 * @param page
	 * @param procInstId
	 * @return
	 */
	public Page<Map<String,Object>> findByProcInstId(Page<Map<String,Object>> page, String wfProcInstNo);

	public void doStartProcInst(Map<String, Object> formProperties, String processDefinitionId, String czybh,String detailJson,final Map<String, String> pushWfProcInstNo);

	public WfProcess getWfProcessByProcKey(String procKey);
	
	public List<Map<String, Object>> getTables(String wfProcNo);
	
	public WfProcInst getWfProcInstByActProcInstId(String actProcInstId);
	
	public List<Map<String, Object>> getTableInfo(String tableName, String wfProcInstNo);
	
	public void doCompleteTask(String taskId, String czybh, String status, String comment,String processInstId, Map<String, Object> formProperties);
	
	public String getProcKeyByNo(String no );
	
	public Page<Map<String, Object>> getWfProcTrack(Page<Map<String, Object>> page, String wfProcInstNo);
	
	public Page<Map<String, Object>> getAllListDetailByJqgrid(Page<Map<String, Object>> page,WfProcInst wfProcInst);
	
	public Page<Map<String, Object>> findWfProcInstPic(Page<Map<String, Object>> page,String wfProcInstNo,String photoPK);
	
	/**
	 * 抄送人,修改状态，
	 * @param czybh
	 * @param wfProcInstNo
	 */
	public void doUpdateCopyStatus(String czybh,String wfProcInstNo);
	
	/**
	 * 干系人调整，选取执行人。
	 * @param wfProcInstNo
	 * @param taskKey
	 * @return
	 */
	public String getOptionAssignee(String wfProcInstNo,String taskKey);
	
	/**
	 * 直接部门领导
	 * @param departmentCode
	 * @return
	 */
	public String getDeptLeader(String departmentCode);

	/**
	 * 二级部门领导
	 * @param departmentCode
	 * @return
	 */
	public String getDeptLeaderTow(String departmentCode);
	
	/**
	 * 获取当钱操作员有几个归属不部门 包括虚拟部门
	 * @param czybh
	 * @return
	 */
	public List<Map<String, Object>> getDeptListByCzybh(String czybh);

	/**
	 * 获取流程节点执行人
	 * @param groupId
	 * @param wfProcNo
	 * @param taskKey
	 * @return
	 */
	public String getActUser(String wfProcNo,String taskKey,String wfProcInstNo);

	public String getActUser(String groupId);

	/**
	 * 获取明细谁数量
	 * @param tableName
	 * @param wfProcInstNo
	 * @return
	 */
	public int getDetailNum(String tableName, String wfProcInstNo);
	
	/**
	 * 干系人调整明细
	 * @param tableName
	 * @param wfProcInstNo
	 * @return
	 */
	public List<Map<String, Object>> getDetails(String tableName, String wfProcInstNo);

	/**
	 * 获取一条流程支线的全部流程定义
	 * 判断是否与需要用到 wfOptionListener 监听器
	 * @param pvmId 下一个活动节点的ID
	 * @param processDefinitionEntity
	 */
	public void getAllEntity(String  pvmId,ProcessDefinitionEntity processDefinitionEntity,
			String department,String wfProcNo,String czybh, final List<Object> operateList,final List<String> pdId, JSONObject jsonObject);
	
	/**
	 * 获取未执行的流程
	 */
	public List<Map<String, Object>> getProcBranch(String wfProcNo,String procDefId,JSONObject jsonObject);
	
	public Map<String, Object> getCustStakeholder(String roll, String custCode);
	
	/**
	 * 获取高亮流程线
	 * @param processDefinitionEntity
	 * @param historicActivityInstances
	 * @return
	 */
	public List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) ;
	
	/**
	 * 判断El表达式
	 * @param el
	 * @param formData
	 * @return Boolean
	 */
	public Boolean checkFormDataByRuleEl(String el, Map<String, Object> formData) ;
	
	public List<Object> getOperator(JSONObject jsonObject,String pdID,String department,
			String wfProcNo,String czybh);
	
	public List<Map<String, Object>> getProcTaskTableStru(String wfProcNo, String taskDefkey);
	
	public void updateCopyDate(String wfProcInNo,String startUserId);
	
	public String getCzybhByEmpNum(String empNum);
	
	public void doSaveComment(String wfProcInstNo,String comment,String lastUpdatedBy);
	
	public void doPushTaskToOperator(String status, String wfProcInstNo);
	
	public Map<String , Object> getNextOperatorIdByNo(String no);
	
	public Map<String, Object> getSeqNoBySql(String tableCode);
	
	public List<String> getHisOperatorByNo(String wfProcInstNo);
	
	public void doDelPhoto(String pks);
	
	public List<Map<String , Object>> findWfProcInstPicByNo(String wfProcInstNo);
	
	public String getDeptManager(String department);
	
	public List<Map<String, Object>> findTodoTaskGroupByUserId();
	
	public Page<Map<String , Object>> getEmpAccountJqGrid(Page<Map<String , Object>> page,String czybh,String actName);

	public Page<Map<String , Object>> getRcvActByJqGrid(Page<Map<String , Object>> page,String czybh,String descr);

	public Page<Map<String , Object>> getAdvanceNoByJqgrid(Page<Map<String , Object>> page,String czybh,String searchData);
	
	public Page<Map<String , Object>> getExpenseAdvanceJqGrid(Page<Map<String , Object>> page,Employee employee);

	public Page<Map<String , Object>> getExpenseAdvanceTran(Page<Map<String , Object>> page,Employee employee);

	public Double getAdvanceAmount(String czybh);
	
	public void doSaveAccount(String actName, String bank, String cardId,String subBranch,String czybh);
	
	public void doCompExpenseClaimTask(String taskId, String czybh, String status,
			String comment, String processInstId, Map<String, Object> formProperties);
	
	public void doCompExpenseAdvanceTask(String taskId, String czybh, String status,
			String comment, String processInstId, Map<String, Object> formProperties);

	public String getMainTableName(String wfProcNo);
	
	public List<Map<String, Object>> getAssigneesByGroupId(String groupId);
	
	public void getHighLightedFlows(String pvmActivityId, ProcessDefinitionEntity processDefinitionEntity,
			final List<String> pdId, JSONObject jsonObject);
	
	public Map<String, Object> getEmpCompany(String department);
	
	public void doSaveRefund(ExpenseAdvance expenseAdvance);
	
	public boolean isProcOperator(String taskId, String czybh);
	
	public void saveMarketClaimMessage(String wfProcInstNo);
	
	public Page<Map<String, Object>> getSupplAccountJqGrid(Page<Map<String,Object>> page, SplCheckOut splCheckOut);
	
	public String getWfPrjCardInfo(String wfProcNo, String wfProcInstNo);
	
	public String getTaskCommntByPIIDTaskName(String procInstId, String taskName);
	
	public Serializable doDelEmpCard(String actName, String cardId);
	
}

