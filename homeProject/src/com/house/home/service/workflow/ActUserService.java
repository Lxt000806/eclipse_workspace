package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActUser;

public interface ActUserService extends BaseService {

	/**ActUser分页信息
	 * @param page
	 * @param actUser
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActUser actUser);
	
}
