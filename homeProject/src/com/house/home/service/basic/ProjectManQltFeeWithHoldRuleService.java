package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ProjectManQltFeeWithHoldRule;

public interface ProjectManQltFeeWithHoldRuleService extends BaseService {

	/**ProjectManQltFeeWithHoldRule分页信息
	 * @param page
	 * @param projectManQltFeeWithHoldRule 
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule);
	
}
