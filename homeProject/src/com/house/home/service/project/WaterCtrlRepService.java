package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustWorker;

public interface WaterCtrlRepService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,CustWorker custWorker );

	
}
