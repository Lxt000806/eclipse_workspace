package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.project.ProgCheckPlan;
import com.house.home.entity.project.ProgCheckPlanDetail;

public interface XjrwapService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProgCheckPlan progCheckPlan);
	
	public Page<Map<String,Object>> findPrjPageBySql(Page<Map<String,Object>> page);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, ProgCheckPlanDetail progCheckPlanDetail);
	
	public Page<Map<String,Object>> findAddPageBySql(Page<Map<String,Object>> page, 
			Customer customer,String arr,String checkType,String auto,String longitude,String latitude,String isCheckDept,String importancePrj);

	public Page<Map<String,Object>> findFroAddPageBySql(Page<Map<String,Object>> page, ProgCheckPlan progCheckPlan,String arr);

	public Result doSave(ProgCheckPlan progCheckPlan);

	public Result doUpdate(ProgCheckPlan progCheckPlan);

	public Integer  getFroNum();
	
	public void doSavePrjMan(String projectMan,String lastUpdatedBy);
	
	public void doDelPrjMan(Integer pk);
	
	public String getIsCheckDept(String czybh);
	
	public boolean getPrjManByCode(String prjMan);
	
}
