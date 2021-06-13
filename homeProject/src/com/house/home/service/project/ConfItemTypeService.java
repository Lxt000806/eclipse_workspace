package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ConfItemType;

public interface ConfItemTypeService extends BaseService {

	/**ConfItemType分页信息
	 * @param page
	 * @param confItemType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemType confItemType);

}
