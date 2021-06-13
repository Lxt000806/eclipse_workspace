package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SaleCust;;

public interface SaleCustService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>>page,SaleCust saleCust);
	
	public List<SaleCust> findByNoExpired();

	public SaleCust getByDesc1(String desc1);
	
	public List<Map<String, Object>> getDBcol(String dbName) ;
 }
