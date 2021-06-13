package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActVariableDao;
import com.house.home.entity.workflow.ActVariable;
import com.house.home.service.workflow.ActVariableService;

@SuppressWarnings("serial")
@Service
public class ActVariableServiceImpl extends BaseServiceImpl implements ActVariableService {

	@Autowired
	private ActVariableDao actVariableDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActVariable actVariable){
		return actVariableDao.findPageBySql(page, actVariable);
	}

}
