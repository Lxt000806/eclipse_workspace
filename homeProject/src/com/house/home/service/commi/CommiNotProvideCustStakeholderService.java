package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiNotProvideCustStakeholder;

public interface CommiNotProvideCustStakeholderService extends BaseService {

	/**CommiNotProvideCustStakeholder分页信息
	 * @param page
	 * @param commiNotProvideCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder);
	
}
