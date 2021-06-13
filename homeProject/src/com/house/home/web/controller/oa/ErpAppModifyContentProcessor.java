package com.house.home.web.controller.oa;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.home.entity.oa.AppErp;
import com.house.home.service.oa.ErpAppService;

@Component
@Transactional
public class ErpAppModifyContentProcessor implements TaskListener{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	RuntimeService runtimeService;
	@Autowired
	ErpAppService erpAppService;
	
	public void notify(DelegateTask delegatetask) {
		
		String processInstanceId = delegatetask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		AppErp appErp= erpAppService.get(AppErp.class, Long.valueOf(processInstance.getBusinessKey()));
		appErp.setType(delegatetask.getVariable("type").toString());
		erpAppService.update(appErp);
	}

	
}
