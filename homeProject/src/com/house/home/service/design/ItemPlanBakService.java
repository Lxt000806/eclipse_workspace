package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ItemPlanBak;

public interface ItemPlanBakService extends BaseService {

	/**ItemPlanBak分页信息
	 * @param page
	 * @param itemPlanBak
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanBak itemPlanBak);
	
}
