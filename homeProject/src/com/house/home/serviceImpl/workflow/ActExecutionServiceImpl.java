package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActExecutionDao;
import com.house.home.entity.workflow.ActExecution;
import com.house.home.service.workflow.ActExecutionService;

@SuppressWarnings("serial")
@Service
public class ActExecutionServiceImpl extends BaseServiceImpl implements ActExecutionService {

	@Autowired
	private ActExecutionDao actExecutionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActExecution actExecution){
		return actExecutionDao.findPageBySql(page, actExecution);
	}

}
