package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;


public interface DesignCustSourceAnalyService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Customer customer, UserContext uc);
	/**
	 * 明细查看
	 * @author	created by zb
	 * @date	2019-9-9--下午5:51:18
	 * @param page
	 * @param employee
	 * @param uc
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			Employee employee, UserContext uc);	
		
}

