package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiStakeholder;

public interface CommiStakeholderService extends BaseService {

	/**CommiStakeholder分页信息
	 * @param page
	 * @param commiStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiStakeholder commiStakeholder);
	
}
