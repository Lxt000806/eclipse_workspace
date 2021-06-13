package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActEventSubscr;

public interface ActEventSubscrService extends BaseService {

	/**ActEventSubscr分页信息
	 * @param page
	 * @param actEventSubscr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActEventSubscr actEventSubscr);
	
}
