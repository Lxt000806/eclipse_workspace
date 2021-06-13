package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActTaskDao;
import com.house.home.entity.workflow.ActTask;
import com.house.home.service.workflow.ActTaskService;

@SuppressWarnings("serial")
@Service
public class ActTaskServiceImpl extends BaseServiceImpl implements ActTaskService {

	@Autowired
	private ActTaskDao actTaskDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActTask actTask){
		return actTaskDao.findPageBySql(page, actTask);
	}

	@Override
	public ActTask getByProcInstId(String procInstId) {
		return actTaskDao.getByProcInstId(procInstId);
	}

	@Override
	public Page<Map<String, Object>> findTodoTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		return actTaskDao.findTodoTasks(page, actTask);
	}

	@Override
	public Page<Map<String, Object>> findMyTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		return actTaskDao.findMyTasks(page, actTask);
	}

	@Override
	public Page<Map<String, Object>> findFinishedTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		return actTaskDao.findFinishedTasks(page, actTask);
	}

	@Override
	public Page<Map<String, Object>> findAllTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		return actTaskDao.findAllTasks(page, actTask);
	}

}
