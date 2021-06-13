package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActJob;

public interface ActJobService extends BaseService {

	/**ActJob分页信息
	 * @param page
	 * @param actJob
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActJob actJob);
	
}
