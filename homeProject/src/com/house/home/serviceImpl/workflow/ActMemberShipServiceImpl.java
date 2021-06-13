package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActMemberShipDao;
import com.house.home.entity.workflow.ActMemberShip;
import com.house.home.service.workflow.ActMemberShipService;

@SuppressWarnings("serial")
@Service
public class ActMemberShipServiceImpl extends BaseServiceImpl implements ActMemberShipService {

	@Autowired
	private ActMemberShipDao actMemberShipDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActMemberShip actMemberShip){
		return actMemberShipDao.findPageBySql(page, actMemberShip);
	}

}
