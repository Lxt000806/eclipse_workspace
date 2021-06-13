package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActDeployment;

public interface ActDeploymentService extends BaseService {

	/**ActDeployment分页信息
	 * @param page
	 * @param actDeployment
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActDeployment actDeployment);
	
}
