package com.house.home.service.oa;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.home.entity.oa.AppErp;
import com.house.home.entity.oa.OaAll;
import com.house.home.service.basic.EmployeeService;

@Component
@Transactional
public class ErpAppWorkflowService {
	
	private static Logger logger = LoggerFactory.getLogger(LeaveWorkflowService.class);
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private OaAllService oaAllService;
	@Autowired
	private ErpAppService erpAppService;
	
	public ProcessInstance startWorkflow(AppErp entity,
			Map<String, Object> variables) {
		employeeService.save(entity);
		logger.debug("save entity: {}", entity);
		String businessKey = entity.getId().toString();

		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(entity.getUserId());
		
		//AppErp appErp= erpAppService.get(AppErp.class, Long.valueOf(businessKey));
		if (StringUtils.isNotBlank(entity.getErpCzy())){
			variables.put("erpCzys", Arrays.asList(entity.getErpCzy()));
		}else{
			List<String> list = employeeService.getErpCzyList();
			variables.put("erpCzys", list);
		}
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("erpApp", businessKey, variables);
		String processInstanceId = processInstance.getId();
		entity.setProcessInstanceId(processInstanceId);
		OaAll oa = new OaAll();
		oa.setProcInstId(processInstanceId);
		oa.setApplyTime(entity.getApplyTime());
		oa.setUserId(entity.getUserId());
		oa.setStatus("1");
		oaAllService.save(oa);
		logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}",
				new Object[] { "appErp", businessKey, processInstanceId, variables });
		return processInstance;
	}
	
}
