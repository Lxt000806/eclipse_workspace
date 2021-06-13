package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.LongFeeRule;

public interface LongFeeRuleService extends BaseService {

	/**LongFeeRule分页信息
	 * @param page
	 * @param longFeeRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, LongFeeRule longFeeRule);
	/**
	 * LongFeeRuleDetail分页信息
	 * 
	 * @param page
	 * @param longFeeRule
	 * @return
	 */
	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, LongFeeRule longFeeRule);
	/**
	 * 保存
	 * 
	 * @param longFeeRule
	 * @return
	 */
	public Result doSaveProc(LongFeeRule longFeeRule);
	
    public Page<Map<String,Object>> goItemDetailJqGrid(Page<Map<String, Object>> page, LongFeeRule longFeeRule);
}
