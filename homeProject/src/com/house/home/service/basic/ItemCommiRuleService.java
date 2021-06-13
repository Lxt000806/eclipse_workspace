package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemCommiRule;

public interface ItemCommiRuleService extends BaseService {

	/**ItemCommiRule分页信息
	 * @param page
	 * @param itemCommiRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiRule itemCommiRule);
	
}
