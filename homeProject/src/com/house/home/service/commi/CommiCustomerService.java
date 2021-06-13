package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustomer;

public interface CommiCustomerService extends BaseService {

	/**CommiCustomer分页信息
	 * @param page
	 * @param commiCustomer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustomer commiCustomer);
	
}
