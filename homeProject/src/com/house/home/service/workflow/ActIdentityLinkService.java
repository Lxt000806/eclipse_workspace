package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActIdentityLink;

public interface ActIdentityLinkService extends BaseService {

	/**ActIdentityLink分页信息
	 * @param page
	 * @param actIdentityLink
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActIdentityLink actIdentityLink);
	
}
