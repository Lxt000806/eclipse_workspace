package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.AppServerUrl;

public interface AppServerUrlService extends BaseService {

	/**AppServerUrl分页信息
	 * @param page
	 * @param appServerUrl
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AppServerUrl appServerUrl);
	
}
