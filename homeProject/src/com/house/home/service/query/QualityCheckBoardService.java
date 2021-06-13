package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustWorker;

public interface QualityCheckBoardService extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, CustWorker custWorker);
	
	public Page<Map<String,Object>> findDetailBySql(Page<Map<String,Object>> page, CustWorker custWorker);
}
