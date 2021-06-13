package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface DesignManRankingService extends BaseService{

	/**
	 * 设计师排名查询
	 * @author	created by zb
	 * @date	2018-12-11--下午12:01:26
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);

}
