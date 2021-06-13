package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustStakeholderHis;

public interface CustStakeholderHisService extends BaseService {

	/**CustStakeholderHis分页信息
	 * @param page
	 * @param custStakeholderHis
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustStakeholderHis custStakeholderHis);
	/**CustStakeholderHis分页信息-客户信息查询
	 * @param page
	 * @param custStakeholderHis
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, CustStakeholderHis custStakeholderHis);
	
}
