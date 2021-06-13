package com.house.home.service.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;

public interface PrjTaskExecAnalyService extends BaseService{

	public Page<Map<String,Object>> goJqGrid(Page<Map<String, Object>> page,
			Date dateFrom, Date dateTo, String department1, String department2);

	public Page<Map<String,Object>> goJqGridView(Page<Map<String, Object>> page, Date dateFrom,
			Date dateTo, String rcvCZY);
	
	public Page<Map<String,Object>> goJqGrid_prjDelayNoTrrigerTask(Page<Map<String, Object>> page, Date dateFrom,
			Date dateTo, String rcvCZY);

}
