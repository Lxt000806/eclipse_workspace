package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActByteArray;

public interface ActByteArrayService extends BaseService {

	/**ActByteArray分页信息
	 * @param page
	 * @param actByteArray
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActByteArray actByteArray);
	
}
