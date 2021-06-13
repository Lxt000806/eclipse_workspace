package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ConDayCalcRule;

public interface ConDayCalcRuleService extends BaseService {

	/**ConDayCalcRule分页信息
	 * @param page
	 * @param conDayCalcRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConDayCalcRule conDayCalcRule);
	/**
	 * 是否存在重复工种分类12，工人分类
	 * @param code
	 * @return
	 */
	public List<Map<String,Object>> isRepeated(ConDayCalcRule conDayCalcRule);
}
