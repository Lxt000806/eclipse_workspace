package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Roll;

public interface RollService extends BaseService {

	/**Roll分页信息
	 * @param page
	 * @param roll
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Roll roll);
	
	public Map<String,Object> getByCode(String code);
	
}
