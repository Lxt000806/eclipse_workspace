package com.house.home.serviceImpl.workflow.financeServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.workflow.WfProcInstDao;
import com.house.home.service.workflow.WfProcInstService;
import com.house.home.service.workflow.financeService.WfProcInstService_expenseAdvance;
import com.house.home.serviceImpl.workflow.WfProcInstServiceImpl;

@SuppressWarnings("serial")
@Service
public class WfProcInstService_expenseAdvanceImpl extends BaseServiceImpl implements WfProcInstService_expenseAdvance{

	@Autowired
	private WfProcInstDao wfProcInstDao;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	@Override
	public void doCompFinanceTask(String taskId, String czybh, String status,
			String comment, String processInstId,
			Map<String, Object> formProperties) {
		wfProcInstService.doCompleteTask(taskId, czybh, status, comment,processInstId,formProperties);
		wfProcInstService.doCompExpenseAdvanceTask(taskId, czybh, status, comment, processInstId, formProperties);
	}
	

	
}
