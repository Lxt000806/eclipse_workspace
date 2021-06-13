package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.CommiCal;

public interface CommiCalService extends BaseService {

	/**CommiCal分页信息
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCal commiCal);
	
	/**CommiCal分页信息
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, CommiCal commiCal);
	
}
