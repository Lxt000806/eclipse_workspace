package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholderProvide;

public interface CommiCustStakeholderProvideService extends BaseService {

	/**CommiCustStakeholderProvide分页信息
	 * @param page
	 * @param commiCustStakeholderProvide
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholderProvide commiCustStakeholderProvide);
	
}
