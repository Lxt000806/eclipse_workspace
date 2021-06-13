package com.house.home.service.query;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Department2;

public interface PrjDeptPerfService extends BaseService  {
	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String,Object>> page,Department2 department2,String orderBy,String direction,String statistcsMethod);
	
	public Page<Map<String, Object>> goConfBuildsJqGrid(Page<Map<String,Object>> page,String empCode,Date dateFrom,Date dateTo,String custType);
	
	public Page<Map<String, Object>> goCheckBuildsJqGrid(Page<Map<String,Object>> page,String empCode,Date checkDateFrom,Date checkDateTo,String custType,String department2);
	
	public Page<Map<String, Object>> goReOrderBuildsJqGrid(Page<Map<String,Object>> page,String empCode,Date dateFrom,Date dateTo,String custType,String department2);
	
	public Page<Map<String, Object>> goChangedPerformanceJqGrid(Page<Map<String, Object>> page, Map<String, String> postData);
	
	public Page<Map<String, Object>> goConfBuildsOutJqGrid(Page<Map<String, Object>> page, String dept2Code, Date dateFrom,Date dateTo, String custType) ;
	
	public Page<Map<String, Object>> goConfBuildsInJqGrid(Page<Map<String, Object>> page, String dept2Code, Date dateFrom,Date dateTo, String custType);

	public Page<Map<String, Object>> goCheckBuildsOutJqGrid(Page<Map<String, Object>> page, String dept2Code, Date dateFrom,Date dateTo, String custType); 

	public Page<Map<String, Object>> goCheckBuildsInJqGrid(Page<Map<String, Object>> page, String dept2Code, Date dateFrom,Date dateTo, String custType);

}
