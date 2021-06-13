package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface MainBusiManWorkloadService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	public Page<Map<String,Object>> findPageBySql_beginDetail(Page<Map<String, Object>> page, Customer customer);
	public Page<Map<String,Object>> findPageBySql_buildingDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_completedDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_firstConfirmDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_secondConfirmDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_chgDetail(Page<Map<String, Object>> page,Customer customer);
}
