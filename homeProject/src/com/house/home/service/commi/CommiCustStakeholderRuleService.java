package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholderRule;

public interface CommiCustStakeholderRuleService extends BaseService {

	/**CommiCustStakeholderRule分页信息
	 * @param page
	 * @param commiCustStakeholderRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholderRule commiCustStakeholderRule);
	
	/**
	 * 结算浮动规则明细
	 * 
	 * @param page
	 * @param commiCustStakeholderRule
	 * @return
	 */
	public Page<Map<String,Object>> goRuleJqGrid(Page<Map<String,Object>> page, CommiCustStakeholderRule commiCustStakeholderRule);
}
