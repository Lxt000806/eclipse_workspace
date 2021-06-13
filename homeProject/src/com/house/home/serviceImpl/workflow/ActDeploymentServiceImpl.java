package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActDeploymentDao;
import com.house.home.entity.workflow.ActDeployment;
import com.house.home.service.workflow.ActDeploymentService;

@SuppressWarnings("serial")
@Service
public class ActDeploymentServiceImpl extends BaseServiceImpl implements ActDeploymentService {

	@Autowired
	private ActDeploymentDao actDeploymentDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActDeployment actDeployment){
		return actDeploymentDao.findPageBySql(page, actDeployment);
	}

}
