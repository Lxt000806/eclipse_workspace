package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface IntSetAnalyService extends BaseService{

	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 领料明细查看
	 * @author	created by zb
	 * @date	2019-10-30--上午11:45:41
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findIaDetailBySql(Page<Map<String, Object>> page,
			Customer customer);

}
