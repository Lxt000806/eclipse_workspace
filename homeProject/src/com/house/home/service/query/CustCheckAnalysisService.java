package com.house.home.service.query;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface CustCheckAnalysisService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);

	public Page<Map<String,Object>> goJqGridCheckDetail(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String role,
														String statistcsMethod, String department1, String department2, String custtype, String constructType, String isContainSoft);

	public Page<Map<String,Object>> goJqGridReturnDetail(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String role, String statistcsMethod, String department1, 
													      String department2, String custtype, String constructType, String isContainSoft, int returnFlag);
}
