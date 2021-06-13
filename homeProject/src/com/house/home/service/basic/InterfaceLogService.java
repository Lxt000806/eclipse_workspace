package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.InterfaceLog;

public interface InterfaceLogService extends BaseService {

	/**InterfaceLog分页信息
	 * @param page
	 * @param interfaceLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InterfaceLog interfaceLog);
	
}
