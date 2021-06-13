package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActVarinstDao;
import com.house.home.entity.workflow.ActVarinst;
import com.house.home.service.workflow.ActVarinstService;

@SuppressWarnings("serial")
@Service
public class ActVarinstServiceImpl extends BaseServiceImpl implements ActVarinstService {

	@Autowired
	private ActVarinstDao actVarinstDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActVarinst actVarinst){
		return actVarinstDao.findPageBySql(page, actVarinst);
	}

	@Override
	public ActVarinst getByInstIdandName(String instId, String name) {
		return actVarinstDao.getByInstIdandName(instId,name);
	}

}
