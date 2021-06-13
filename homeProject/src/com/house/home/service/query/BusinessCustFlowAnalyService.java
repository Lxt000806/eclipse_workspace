package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;

public interface BusinessCustFlowAnalyService extends BaseService{
	/**
	 * 主表查询
	 * @author	created by zb
	 * @date	2019-8-27--下午4:20:46
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 明细查询
	 * @author	created by zb
	 * @date	2019-9-9--下午2:36:03
	 * @param page
	 * @param employee
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			Employee employee, UserContext uc);

}
