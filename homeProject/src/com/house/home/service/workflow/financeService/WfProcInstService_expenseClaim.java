package com.house.home.service.workflow.financeService;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;


public interface WfProcInstService_expenseClaim extends BaseService{

	public void doCompFinanceTask(String taskId, String czybh, String status, String comment,String processInstId, Map<String, Object> formProperties);

}
