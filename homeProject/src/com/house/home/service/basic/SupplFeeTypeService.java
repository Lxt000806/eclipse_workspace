package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SupplFeeType;

public interface SupplFeeTypeService extends BaseService {

	/**SupplFeeType分页信息
	 * @param page
	 * @param supplFeeType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplFeeType supplFeeType);
	
}
