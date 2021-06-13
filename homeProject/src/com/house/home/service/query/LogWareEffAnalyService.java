package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemWHBal;


public interface LogWareEffAnalyService extends BaseService {
	
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,ItemWHBal itemWHBal);
	public Page<Map<String,Object>> findPageBySql_detail(Page<Map<String,Object>> page,ItemWHBal itemWHBal);
	public Page<Map<String,Object>> findPageBySql_sendDetail(Page<Map<String,Object>> page,ItemWHBal itemWHBal);

}

