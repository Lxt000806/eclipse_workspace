package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.PrePlan;

public interface PrePlanService extends BaseService {

	/**PrePlan分页信息
	 * @param page
	 * @param prePlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlan prePlan);
	
}
