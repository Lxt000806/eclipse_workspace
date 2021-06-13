package com.house.home.service.workflow;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;

public interface WfFinanceService extends BaseService {
	
	public void doCompExpenseAdvanceTask(String taskId, String czybh, String status, String comment,String processInstId, Map<String, Object> formProperties);
	
	public void doCompExpenseClaimTask(String taskId, String czybh, String status, String comment,String processInstId, Map<String, Object> formProperties);

	public void doCompleteProcTask(String taskId, String czybh, String status, String comment, String processInstId,
			Map<String, Object> formProperties);
	
	
}
