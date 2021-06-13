package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.BaseItemPlanBak;

public interface BaseItemPlanBakService extends BaseService {

	/**BaseItemPlanBak分页信息
	 * @param page
	 * @param baseItemPlanBak
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemPlanBak baseItemPlanBak);
	
}
