package com.house.home.service.project;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.Gcxxgl;

public interface GcxxglService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer,UserContext uc);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, Customer customer,UserContext uc);

	public Page<Map<String,Object>> findDelayDetailPageBySql(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> findTotalDelayPageBySql(Page<Map<String,Object>> page, Customer customer);

	public void doChengeCheckMan(String custCode,String chengeCheckMan,Date lastUpdate,String lastUpdatedBy);
	
	public void updatePreloftsMan(String code,String preloftsMan,String preloftsManDescr,String lastUpdatedBy,String oldPreloftsMan);

	public void updateCustomer(String custCode,String projectMan,String oldProjectMan,String lastUpdatedBy);

	public String getConstDay();
	
	public Integer getCustStakeholderPk(String custCode,String role);
}
