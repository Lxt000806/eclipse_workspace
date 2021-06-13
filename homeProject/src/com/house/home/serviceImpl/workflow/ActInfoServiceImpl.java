package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActInfoDao;
import com.house.home.entity.workflow.ActInfo;
import com.house.home.service.workflow.ActInfoService;

@SuppressWarnings("serial")
@Service
public class ActInfoServiceImpl extends BaseServiceImpl implements ActInfoService {

	@Autowired
	private ActInfoDao actInfoDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActInfo actInfo){
		return actInfoDao.findPageBySql(page, actInfo);
	}

}
