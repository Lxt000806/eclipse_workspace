package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SysLog;

public interface SysLogService extends BaseService {

	/**SysLog分页信息
	 * @param page
	 * @param sysLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SysLog sysLog);
	
	public Page<Map<String,Object>> findAppPageBySql(Page<Map<String,Object>> page, SysLog sysLog);
	
	
	public void saveLog(SysLog log, Object handler, Exception ex);

	
}
