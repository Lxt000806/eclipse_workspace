package com.house.home.service.design;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;

public interface CustSceneDesiService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(
			Page<Map<String,Object>> page,Customer customer);
	
	public String getDesignerCode(String code);

	public String getSceneDesignerCode(String code);
	
	public void doUpdateSceneDesi(String code ,String custSceneDesi,String lastUpdatedBy,String oldEmpCode);
	
	public void doSaveSceneDesi(String code ,String custSceneDesi,String lastUpdatedBy);
	
	public void doDeleteSceneDesi(String code ,String oldEmpCode,String lastUpdatedBy);

	public String getIsAddCustType();
	
	public Map<String, Object> getSceneDesiDepartment();
}
