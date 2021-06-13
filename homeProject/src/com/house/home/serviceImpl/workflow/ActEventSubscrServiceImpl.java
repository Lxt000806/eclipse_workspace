package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActEventSubscrDao;
import com.house.home.entity.workflow.ActEventSubscr;
import com.house.home.service.workflow.ActEventSubscrService;

@SuppressWarnings("serial")
@Service
public class ActEventSubscrServiceImpl extends BaseServiceImpl implements ActEventSubscrService {

	@Autowired
	private ActEventSubscrDao actEventSubscrDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActEventSubscr actEventSubscr){
		return actEventSubscrDao.findPageBySql(page, actEventSubscr);
	}

}
