package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ItemConfirmInform;

public interface ItemConfirmInformService extends BaseService {

	/**ItemConfirmInform分页信息
	 * @param page
	 * @param itemConfirmInform
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemConfirmInform itemConfirmInform);
	
}
