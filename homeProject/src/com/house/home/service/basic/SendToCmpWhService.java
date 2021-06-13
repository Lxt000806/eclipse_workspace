package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SendToCmpWh;

public interface SendToCmpWhService extends BaseService {

	/**SendToCmpWh分页信息
	 * @param page
	 * @param sendToCmpWh
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendToCmpWh sendToCmpWh);
	
}
