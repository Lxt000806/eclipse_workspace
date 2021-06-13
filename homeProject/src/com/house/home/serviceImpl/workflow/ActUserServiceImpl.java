package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActUserDao;
import com.house.home.entity.workflow.ActUser;
import com.house.home.service.workflow.ActUserService;

@SuppressWarnings("serial")
@Service
public class ActUserServiceImpl extends BaseServiceImpl implements ActUserService {

	@Autowired
	private ActUserDao actUserDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActUser actUser){
		return actUserDao.findPageBySql(page, actUser);
	}

}
