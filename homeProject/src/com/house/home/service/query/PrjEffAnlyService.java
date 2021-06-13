package com.house.home.service.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;

public interface PrjEffAnlyService extends BaseService {
	
	public List<Map<String, Object>> goJqGrid(Date dateFrom, Date dateTo, String department2s, String custTypes, String sType, String builderCode);
	
	public Page<Map<String, Object>> goJqGridView(Page<Map<String, Object>> page,Date dateFrom, Date dateTo, 
			String department2s, String custTypes, String dept2Code, String custType, String constructType, 
			String builderCode);
}
