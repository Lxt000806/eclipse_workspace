package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.IntMeasureBrand;

public interface IntMeasureBrandService extends BaseService {

	/**IntMeasureBrand分页信息
	 * @param page
	 * @param intMeasureBrand
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntMeasureBrand intMeasureBrand);

	public List<Map<String, Object>> getByType(String type);
	
}
