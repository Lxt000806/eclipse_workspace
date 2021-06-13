package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActIdentityLinkHi;

public interface ActIdentityLinkHiService extends BaseService {

	/**ActIdentityLinkHi分页信息
	 * @param page
	 * @param actIdentityLinkHi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActIdentityLinkHi actIdentityLinkHi);
	
}
