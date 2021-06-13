package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActVariable;

public interface ActVariableService extends BaseService {

	/**ActVariable分页信息
	 * @param page
	 * @param actVariable
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActVariable actVariable);
	
}
