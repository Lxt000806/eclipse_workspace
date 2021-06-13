package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.ProfitPerf;

public interface ProfitPerfService extends BaseService {

	/**ProfitPerf分页信息
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProfitPerf profitPerf);

}
