package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkSignAlarm;

public interface WorkSignAlarmService extends BaseService {

	/**WorkSignAlarm分页信息
	 * @param page
	 * @param workSignAlarm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkSignAlarm workSignAlarm);
	
}
