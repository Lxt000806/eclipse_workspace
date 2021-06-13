package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActModel;

public interface ActModelService extends BaseService {

	/**ActModel分页信息
	 * @param page
	 * @param actModel
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActModel actModel);
	
}
