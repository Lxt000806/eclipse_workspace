package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface ColorDrawFeeProvideService extends BaseService {

	/**分页信息
	 * 图纸无变更日期在查询范围内
	 * @param page
	 * @param 
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer);
	
}
