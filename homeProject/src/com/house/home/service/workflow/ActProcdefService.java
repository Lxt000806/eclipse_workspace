package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActProcdef;

public interface ActProcdefService extends BaseService {

	/**ActProcdef分页信息
	 * @param page
	 * @param actProcdef
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProcdef actProcdef);
	
	public void updateProcVersion(String wfProcKey);
	
}
