package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ItemPlanTemp;

public interface ItemPlanTempService extends BaseService {

	/**ItemPlanTemp分页信息
	 * @param page
	 * @param itemPlanTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp);
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp);

	public Result doSave(ItemPlanTemp itemPlanTemp);

	public Result doUpdate(ItemPlanTemp itemPlanTemp);
}
