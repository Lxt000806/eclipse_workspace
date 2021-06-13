package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface NetCustAnalyService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Customer customer);

	public String getDefaultCustType();

	public Page<Map<String,Object>> findManPageBySql_month(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String,Object>> findNetChanelPageBySql_month(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String,Object>> findManAndChanelPageBySql(Page<Map<String, Object>> page, Customer customer);

	public Page<Map<String,Object>> findManPageBySql_history(Page<Map<String, Object>> page, Customer customer);

	public Page<Map<String,Object>> findChanelPageBySql_history(Page<Map<String, Object>> page, Customer customer);
}
