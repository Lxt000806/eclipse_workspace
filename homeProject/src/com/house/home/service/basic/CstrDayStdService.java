package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CstrDayStd;

public interface CstrDayStdService extends BaseService {

	/**CstrDayStd分页信息
	 * @param page
	 * @param cstrDayStd
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CstrDayStd cstrDayStd);
	
}
