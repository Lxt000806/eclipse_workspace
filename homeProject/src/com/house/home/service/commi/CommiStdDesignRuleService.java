package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiBasePersonal;
import com.house.home.entity.commi.CommiStdDesignRule;

public interface CommiStdDesignRuleService extends BaseService {

	/**CommiStdDesignRule分页信息
	 * @param page
	 * @param commiStdDesignRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiStdDesignRule commiStdDesignRule);
	
	public boolean checkExistDescr(CommiStdDesignRule commiStdDesignRule);
	
	public boolean checkExistDrawFeeStdRuleByCommiStdDesignRulePK(Integer commiStdDesignRulePK);
}
