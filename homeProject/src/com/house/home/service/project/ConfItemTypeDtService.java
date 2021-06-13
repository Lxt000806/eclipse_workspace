package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ConfItemTypeDt;

public interface ConfItemTypeDtService extends BaseService {

	/**ConfItemTypeDt分页信息
	 * @param page
	 * @param confItemTypeDt
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemTypeDt confItemTypeDt);
	
}
