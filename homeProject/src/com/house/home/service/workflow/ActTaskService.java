package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActTask;

public interface ActTaskService extends BaseService {

	/**ActTask分页信息
	 * @param page
	 * @param actTask
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActTask actTask);
	
	public ActTask getByProcInstId(String procInstId);
	
	public Page<Map<String,Object>> findTodoTasks(Page<Map<String,Object>> page, ActTask actTask);
	
	public Page<Map<String,Object>> findMyTasks(Page<Map<String,Object>> page, ActTask actTask);
	
	/**
	 * 我已审批的
	 * @param page
	 * @param actTask
	 * @return
	 */
	public Page<Map<String,Object>> findFinishedTasks(Page<Map<String,Object>> page, ActTask actTask);
	
	public Page<Map<String,Object>> findAllTasks(Page<Map<String,Object>> page, ActTask actTask);
	
}
