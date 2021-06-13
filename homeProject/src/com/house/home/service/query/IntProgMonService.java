package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface IntProgMonService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> findNotInstallPageBySql(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> findNotDeliverPageBySql(Page<Map<String,Object>> page, Customer customer);

	/**
	 * @Description: 未下单明细分页查询
	 * @author	created by zb
	 * @date	2018-10-20--下午6:04:45
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findNotAppPageBySql(Page<Map<String, Object>> page,
			Customer customer);
	/**
	 * 未安排工人明细
	 * @author	created by zb
	 * @date	2019-9-6--下午6:10:56
	 * @param page
	 * @param customer
	 */
	public Page<Map<String,Object>> findNotSetWorkerPageBySql(Page<Map<String, Object>> page,
			Customer customer);

}
