package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface DdzhfxService extends BaseService {

	/**Ddzhfx分页信息
	 * 订单转化分析
	 * @param page
	 * @param 
	 * @return
	 */

	public Page<Map<String,Object>> findPageBySqlTJFS(Page<Map<String,Object>> page,  Customer customer,String orderBy,String direction);
	public Map<String,Object> getbmqx(String czy);
	
}
