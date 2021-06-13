package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActTaskinst;

public interface ActTaskinstService extends BaseService {

	/**ActTaskinst分页信息
	 * @param page
	 * @param actTaskinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActTaskinst actTaskinst);
	
	public Page<Map<String,Object>> findByProcInstId(Page<Map<String,Object>> page, String procInstId);
	
}
