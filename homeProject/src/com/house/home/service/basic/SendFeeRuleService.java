package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SendFeeRule;

public interface SendFeeRuleService extends BaseService {

	/**SendFeeRule分页信息
	 * @param page
	 * @param sendFeeRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendFeeRule sendFeeRule);
	/**
	 * SendFeeRuleItem分页信息
	 * 
	 * @param page
	 * @param sendFeeRule
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(Page<Map<String, Object>> page, SendFeeRule sendFeeRule);
	/**
	 * 保存
	 * 
	 * @param sendFeeRule
	 * @return
	 */
	public Result doSaveProc(SendFeeRule sendFeeRule);
}
