package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiBasePersonal;

public interface CommiBasePersonalService extends BaseService {

	/**CommiBasePersonal分页信息
	 * @param page
	 * @param commiBasePersonal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiBasePersonal commiBasePersonal);
	
	public boolean checkCommiBasePersonalExist(CommiBasePersonal commiBasePersonal);
	
}
