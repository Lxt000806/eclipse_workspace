package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Czrz;

public interface CzrzService extends BaseService {

	/**Czrz分页信息
	 * @param page
	 * @param czrz
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Czrz czrz);
	
}
