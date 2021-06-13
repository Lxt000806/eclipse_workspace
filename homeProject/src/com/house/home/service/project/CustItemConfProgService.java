package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfProg;

public interface CustItemConfProgService extends BaseService {

	/**CustItemConfProg分页信息
	 * @param page
	 * @param custItemConfProg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfProg custItemConfProg);
	
}
