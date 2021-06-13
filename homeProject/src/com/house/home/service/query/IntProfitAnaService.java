package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface IntProfitAnaService extends BaseService {

	/**intProfitAna分页信息
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer);
	/**
	 * 明细表头
	 * 
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> findDetailHead(String custCode);

}
