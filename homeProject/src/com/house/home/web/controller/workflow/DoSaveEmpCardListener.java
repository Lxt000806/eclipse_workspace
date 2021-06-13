package com.house.home.web.controller.workflow;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 保存员工卡号监听器 
 * @author xzy
 */
@Component
public class DoSaveEmpCardListener implements TaskListener,ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskService taskService;
	
	public void notify(DelegateTask delegateTask) {
		System.out.println(new Date());
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		String startUserId= execuEntity.getIdentityLinks().get(0).getUserId();
		taskService.setAssignee(delegateTask.getId(),startUserId);
	}

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		/*Map<String , Object > map = delegateExecution.getVariables();
		for(Entry<String, Object> entry :map.entrySet()){
		}*/
		System.out.println("start listener！！！");
	}

}
