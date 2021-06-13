package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActProcdefDao;
import com.house.home.entity.workflow.ActProcdef;
import com.house.home.service.workflow.ActProcdefService;

@SuppressWarnings("serial")
@Service
public class ActProcdefServiceImpl extends BaseServiceImpl implements ActProcdefService {

	@Autowired
	private ActProcdefDao actProcdefDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProcdef actProcdef){
		return actProcdefDao.findPageBySql(page, actProcdef);
	}

	@Override
	public void updateProcVersion(String wfProcKey) {

		actProcdefDao.updateProcVersion(wfProcKey);
	}

	
}
