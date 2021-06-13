package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;

public interface PrjManRankService extends BaseService{
	/**
	 * 主表查询
	 * @author	created by zb
	 * @date	2019-5-17--上午10:35:29
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 详细查看
	 * @author	created by zb
	 * @date	2019-5-17--上午10:35:40
	 * @param page
	 * @param employee
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			Employee employee);

}
