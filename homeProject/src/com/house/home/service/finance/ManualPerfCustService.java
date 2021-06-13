package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.ManualPerfCust;

public interface ManualPerfCustService extends BaseService {

	/**ManualPerfCust分页信息
	 * @param page
	 * @param manualPerfCust
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ManualPerfCust manualPerfCust);
	
}
