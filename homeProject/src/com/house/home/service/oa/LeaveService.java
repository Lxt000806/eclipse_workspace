package com.house.home.service.oa;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.oa.Leave;

public interface LeaveService extends BaseService {

	/**Leave分页信息
	 * @param page
	 * @param leave
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Leave leave);
	
	public Leave getByProcessInstanceId(String id);
	
}
