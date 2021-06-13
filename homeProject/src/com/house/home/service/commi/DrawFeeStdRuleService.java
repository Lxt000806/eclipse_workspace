package com.house.home.service.commi;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.DrawFeeStdRule;

public interface DrawFeeStdRuleService extends BaseService {

	/**DrawFeeStdRule分页信息
	 * @param page
	 * @param drawFeeStdRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DrawFeeStdRule drawFeeStdRule);
	
	/**
	 * 保存
	 * 
	 * @param drawFeeStdRule
	 * @return
	 */
	public Result doSaveProc(DrawFeeStdRule drawFeeStdRule);
	
	/**
	 * goDetailJqGrid分页信息
	 * 
	 * @param page
	 * @param drawFeeStdRule
	 * @return
	 */
	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, DrawFeeStdRule drawFeeStdRule);
	
}
