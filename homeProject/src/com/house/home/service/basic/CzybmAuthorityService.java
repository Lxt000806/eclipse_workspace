package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzybmAuthority;

public interface CzybmAuthorityService extends BaseService {

	/**CzybmAuthority分页信息
	 * @param page
	 * @param czybmAuthority
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzybmAuthority czybmAuthority);
	
}
