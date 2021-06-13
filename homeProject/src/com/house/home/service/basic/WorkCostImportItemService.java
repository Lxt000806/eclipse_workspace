package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkCostImportItem;

public interface WorkCostImportItemService extends BaseService {

	/**WorkCostImportItem分页信息
	 * @param page
	 * @param workCostImportItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCostImportItem workCostImportItem);
	
}
