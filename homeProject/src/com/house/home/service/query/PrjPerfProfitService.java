package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface PrjPerfProfitService extends BaseService{

	/**
	 * 工地结算利润分析分页查询
	 * @author	created by zb
	 * @date	2019-1-10--下午4:29:15
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);

}
