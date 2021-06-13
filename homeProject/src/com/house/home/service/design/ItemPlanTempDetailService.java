package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ItemPlanTempDetail;

public interface ItemPlanTempDetailService extends BaseService {

	/**ItemPlanTempDetail分页信息
	 * @param page
	 * @param itemPlanTempDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanTempDetail itemPlanTempDetail);
	
}
