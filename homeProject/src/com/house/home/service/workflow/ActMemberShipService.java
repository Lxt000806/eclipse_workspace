package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActMemberShip;

public interface ActMemberShipService extends BaseService {

	/**ActMemberShip分页信息
	 * @param page
	 * @param actMemberShip
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActMemberShip actMemberShip);
	
}
