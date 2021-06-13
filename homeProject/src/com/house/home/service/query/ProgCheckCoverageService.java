package com.house.home.service.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;

public interface ProgCheckCoverageService extends BaseService  {

	public List<Map<String,Object>> goJqGrid(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String isCheckDept);

	public Page<Map<String,Object>> goJqGridView(Page<Map<String, Object>> page, String type, Date dateFrom, Date dateTo, String isCheckDept);
}
