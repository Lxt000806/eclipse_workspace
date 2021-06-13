package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActJobDao;
import com.house.home.entity.workflow.ActJob;
import com.house.home.service.workflow.ActJobService;

@SuppressWarnings("serial")
@Service
public class ActJobServiceImpl extends BaseServiceImpl implements ActJobService {

	@Autowired
	private ActJobDao actJobDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActJob actJob){
		return actJobDao.findPageBySql(page, actJob);
	}

}
