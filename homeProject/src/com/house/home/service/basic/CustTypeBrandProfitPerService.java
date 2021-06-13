package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustTypeBrandProfitPer;

public interface CustTypeBrandProfitPerService extends BaseService {

	/**CustTypeBrandProfitPer分页信息
	 * @param page
	 * @param custTypeBrandProfitPer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustTypeBrandProfitPer custTypeBrandProfitPer);
	
}
