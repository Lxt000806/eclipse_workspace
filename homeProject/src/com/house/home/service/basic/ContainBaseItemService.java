package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ContainBaseItem;

public interface ContainBaseItemService extends BaseService {

	/**ContainBaseItem分页信息
	 * @param page
	 * @param containBaseItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ContainBaseItem containBaseItem);
	
}
