package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActPropertyDao;
import com.house.home.entity.workflow.ActProperty;
import com.house.home.service.workflow.ActPropertyService;

@SuppressWarnings("serial")
@Service
public class ActPropertyServiceImpl extends BaseServiceImpl implements ActPropertyService {

	@Autowired
	private ActPropertyDao actPropertyDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProperty actProperty){
		return actPropertyDao.findPageBySql(page, actProperty);
	}

}
