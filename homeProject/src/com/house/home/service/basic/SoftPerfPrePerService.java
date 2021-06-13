package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SoftPerfPrePer;

public interface SoftPerfPrePerService extends BaseService {

	/**SoftPerfPrePer分页信息
	 * @param page
	 * @param softPerfPrePer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftPerfPrePer softPerfPrePer);
	
	public boolean hasDepartment1(SoftPerfPrePer softPerfPrePer);
	
}
