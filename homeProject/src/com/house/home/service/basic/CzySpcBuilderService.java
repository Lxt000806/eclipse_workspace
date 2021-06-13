package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzySpcBuilder;

public interface CzySpcBuilderService extends BaseService {

	/**CzySpcBuilder分页信息
	 * @param page
	 * @param czySpcBuilder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzySpcBuilder czySpcBuilder);

	public CzySpcBuilder getByCzybhAndSpcBuilder(String czybh, String spcBuilder);
	
}
