package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActVarinst;

public interface ActVarinstService extends BaseService {

	/**ActVarinst分页信息
	 * @param page
	 * @param actVarinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActVarinst actVarinst);
	
	public ActVarinst getByInstIdandName(String instId,String name);
}
