package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActGroupDao;
import com.house.home.entity.workflow.ActGroup;
import com.house.home.service.workflow.ActGroupService;

@SuppressWarnings("serial")
@Service
public class ActGroupServiceImpl extends BaseServiceImpl implements ActGroupService {

	@Autowired
	private ActGroupDao actGroupDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActGroup actGroup){
		return actGroupDao.findPageBySql(page, actGroup);
	}

	@Override
	public Page<Map<String, Object>> findUserPageBySql(
			Page<Map<String, Object>> page, ActGroup actGroup) {
		return actGroupDao.findUserPageBySql(page, actGroup);
	}
	

	@Override
	public Page<Map<String, Object>> getRoleAuthorityJqGrid(
			Page<Map<String, Object>> page, ActGroup actGroup) {
		return actGroupDao.getRoleAuthorityJqGrid(page,actGroup);
	}

	@Override
	public Result doSave(ActGroup actGroup) {
		return actGroupDao.doSave(actGroup);
	}

	@Override
	public boolean existsAuth(ActGroup actGroup) {
		// TODO Auto-generated method stub
		return actGroupDao.existsAuth(actGroup);
	}
	

}
