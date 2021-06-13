package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.AutoArrWorkerApp;

public interface AutoArrWorkerAppService extends BaseService {

	/**AutoArrWorkerApp分页信息
	 * @param page
	 * @param autoArrWorkerApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AutoArrWorkerApp autoArrWorkerApp);
	
}
