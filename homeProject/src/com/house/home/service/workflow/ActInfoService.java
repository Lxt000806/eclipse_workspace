package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActInfo;

public interface ActInfoService extends BaseService {

	/**ActInfo分页信息
	 * @param page
	 * @param actInfo
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActInfo actInfo);
	
}
