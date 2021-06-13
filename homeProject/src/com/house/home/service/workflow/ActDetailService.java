package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActDetail;

public interface ActDetailService extends BaseService {

	/**ActDetail分页信息
	 * @param page
	 * @param actDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActDetail actDetail);
	
}
