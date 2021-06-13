package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjWithHold;

public interface PrjWithHoldService extends BaseService {

	/**PrjWithHold分页信息
	 * @param page
	 * @param prjWithHold
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjWithHold prjWithHold);
	
	public Page<Map<String,Object>> findPageBySql_ykd(Page<Map<String,Object>> page, PrjWithHold prjWithHold);

	public Map<String, Object> findByPk(int pk);
	
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, PrjWithHold prjWithHold);
}
