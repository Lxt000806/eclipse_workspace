package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.SoftPerf;

public interface SoftPerfService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,SoftPerf softPerf);
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page,SoftPerf softPerf);

	public Page<Map<String,Object>> findEmpInfoPageBySql(
			Page<Map<String,Object>> page,Employee employee);
	
	public Page<Map<String,Object>> findReportPageBySql(
			Page<Map<String,Object>> page,SoftPerf softPerf);
	
	public Page<Map<String,Object>> findReportDetailPageBySql(
			Page<Map<String,Object>> page,SoftPerf softPerf);
	
	public Page<Map<String,Object>> findCountSoftPerPageBySql(
			Page<Map<String,Object>> page,SoftPerf softPerf);

	public String isExistsPeriod(String no,String beginDate) ;
	
	public void doSaveCount(String no) ;
	
	public void doGetSoftPerDetail(String no,String lastUpdatedBy) ;
	
	public void doSaveCountBack(String no) ;
	
	public String checkStatus(String no) ;
	
	public List<Map<String, Object>> checkMonExists(String no, Integer mon);	
	
	public Map<String, Object> getSoftPerfDetail(Integer pk);
	
	public Page<Map<String,Object>> findCountSoftPerIndPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf);
	
	public String getSoftPerfNoByMon(Integer mon);
}
