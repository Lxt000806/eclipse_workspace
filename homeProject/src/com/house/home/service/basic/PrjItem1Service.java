package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrjItem1;

public interface PrjItem1Service extends BaseService {

	/**PrjItem1分页信息
	 * @param page
	 * @param prjItem1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjItem1 prjItem1);
	
}
