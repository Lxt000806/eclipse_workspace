package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface DispatchCenterKPIService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	public Page<Map<String,Object>> findPageBySql_picConfirmDetail(Page<Map<String, Object>> page, Customer customer);
	public Page<Map<String,Object>> findPageBySql_beginDetail(Page<Map<String, Object>> page,Customer customer);
	public  Page<Map<String,Object>> findPageBySql_checkDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_specItemReqDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_fixDutyDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_dispatchDetail(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findPageBySql_mainCheckDetail(Page<Map<String, Object>> page,Customer customer);
	
	
}
