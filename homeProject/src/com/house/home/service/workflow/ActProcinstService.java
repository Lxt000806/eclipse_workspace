package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActProcinst;

public interface ActProcinstService extends BaseService {

	/**ActProcinst分页信息
	 * @param page
	 * @param actProcinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProcinst actProcinst);
	
}
