package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.IntSplPerfPer;

public interface IntSplPerfPerService extends BaseService {

	/**IntSplPerfPer分页信息
	 * @param page
	 * @param intSplPerfPer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntSplPerfPer intSplPerfPer);
	
}
