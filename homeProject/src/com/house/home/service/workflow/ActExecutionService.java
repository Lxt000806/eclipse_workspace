package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActExecution;

public interface ActExecutionService extends BaseService {

	/**ActExecution分页信息
	 * @param page
	 * @param actExecution
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActExecution actExecution);
	
}
