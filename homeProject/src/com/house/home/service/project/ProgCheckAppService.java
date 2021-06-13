package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.ProgCheckApp;

public interface ProgCheckAppService extends BaseService {

	/**ProgCheckApp分页信息
	 * @param page
	 * @param progCheckApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, 
			ProgCheckApp progCheckApp, UserContext uc);
	
	public Map<String,Object> getByPk(Integer pk);
	
}
