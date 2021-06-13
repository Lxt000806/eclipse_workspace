package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

public interface LeaveEmpCustManageService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer Customer,UserContext uc);

	public Result doChgStakeholder(List<Map<String, Object>> list,String showType,String stakeholder,String lastUpdatedBy);
}
