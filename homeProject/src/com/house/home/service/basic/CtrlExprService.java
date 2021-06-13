package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CtrlExpr;

public interface CtrlExprService extends BaseService {

	/**CtrlExpr分页信息
	 * @param page
	 * @param ctrlExpr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CtrlExpr ctrlExpr);
	
}
