package com.house.home.service.project;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustCheck;

public interface CheckSalaryConfirmService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustCheck custCheck);
	
	public Page<Map<String,Object>> findPageSalaryDetailBySql(Page<Map<String,Object>> page, CustCheck custCheck);


}
