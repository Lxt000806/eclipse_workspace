package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ReturnCarryRule;


public interface ReturnCarryRuleService extends BaseService {

	/**ItemSet分页信息
	 * @param page
	 * @param ItemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ReturnCarryRule returnCarryRule);
	
	public Page<Map<String,Object>> findItemPageBySql(Page<Map<String,Object>> page, String no);
	
	public ReturnCarryRule getByNo(String No,String No1);

	public Result doReturnCarryRuleSave(ReturnCarryRule returnCarryRule);

	public Result deleteForProc(ReturnCarryRule returnCarryRule);

	
}

