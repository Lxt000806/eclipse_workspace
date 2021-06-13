package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface PrjMinusAnalyService extends BaseService{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 明细查看
	 * @author	created by zb
	 * @date	2019-9-11--上午11:14:02
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			Customer customer);
}
