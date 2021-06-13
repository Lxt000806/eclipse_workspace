package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActModelDao;
import com.house.home.entity.workflow.ActModel;
import com.house.home.service.workflow.ActModelService;

@SuppressWarnings("serial")
@Service
public class ActModelServiceImpl extends BaseServiceImpl implements ActModelService {

	@Autowired
	private ActModelDao actModelDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActModel actModel){
		return actModelDao.findPageBySql(page, actModel);
	}

}
