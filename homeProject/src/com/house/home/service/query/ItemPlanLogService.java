package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemPlanLog;

public interface ItemPlanLogService extends BaseService {

	/**customerDa分页信息
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanLog itemPlanLog);

}
