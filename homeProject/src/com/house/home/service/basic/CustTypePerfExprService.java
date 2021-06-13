package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustTypePerfExpr;

public interface CustTypePerfExprService extends BaseService {

	/**CustTypePerfExpr分页信息
	 * @param page
	 * @param custTypePerfExpr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustTypePerfExpr custTypePerfExpr);
	
}
