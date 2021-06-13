package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfProgDt;

public interface CustItemConfProgDtService extends BaseService {

	/**CustItemConfProgDt分页信息
	 * @param page
	 * @param custItemConfProgDt
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfProgDt custItemConfProgDt);
	
}
