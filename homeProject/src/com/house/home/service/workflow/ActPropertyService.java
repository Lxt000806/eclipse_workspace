package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActProperty;

public interface ActPropertyService extends BaseService {

	/**ActProperty分页信息
	 * @param page
	 * @param actProperty
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProperty actProperty);
	
}
