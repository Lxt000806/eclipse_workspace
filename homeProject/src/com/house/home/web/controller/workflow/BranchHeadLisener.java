package com.house.home.web.controller.workflow;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Component;

import com.house.framework.commons.utils.StringUtils;

/**
 * 获取分公司负责人：福清长乐
 */
@Component
public class BranchHeadLisener implements TaskListener{
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		Map<String, Object> map = execuEntity.getVariables();
		String company = "";
		if(map.containsKey("Company")){
			company = map.get("Company").toString();
		}
		
		if(StringUtils.isNotBlank(company)){
			if("福清有家".equals(company)){
				delegateTask.addCandidateGroup("FQBranchHeader");
			} else if("长乐有家".equals(company)){
				delegateTask.addCandidateGroup("CLBranchHeader");
			} else {
				delegateTask.addCandidateGroup("Admin");
			}
		} else {
			delegateTask.addCandidateGroup("Admin");
		}
	}
}

