package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.SoftInstall;

public interface SoftInstallService extends BaseService {

	/**SoftInstall分页信息
	 * @param page
	 * @param softInstall
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftInstall softInstall);
	
}
