package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.CommiDetail;

public interface CommiDetailService extends BaseService {

	/**CommiDetail分页信息
	 * @param page
	 * @param commiDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiDetail commiDetail);
	/**CommiDetail分页信息
	 * @param page
	 * @param commiDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, CommiDetail commiDetail);
	
}
