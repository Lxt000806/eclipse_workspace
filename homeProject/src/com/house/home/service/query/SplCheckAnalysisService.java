package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.SplCheckOut;

public interface SplCheckAnalysisService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, SplCheckOut splCheckOut);

	public Page<Map<String,Object>> goJqGridDetail(Page<Map<String, Object>> page, SplCheckOut splCheckOut);
}
