package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface AgainSignNotInTimeService extends BaseService {

	/**Customer分页信息
	 * @param page
	 * @param worker
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer);
}
