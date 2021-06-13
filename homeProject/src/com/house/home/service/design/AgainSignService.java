package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.AgainSign;

public interface AgainSignService extends BaseService {

	/**AgainSign分页信息
	 * @param page
	 * @param againSign
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AgainSign againSign);
	/**AgainSign分页信息-客户信息查询
	 * @param page
	 * @param againSign
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, AgainSign againSign);
	
}
