package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrePlanTemp;

public interface PrePlanTempService extends BaseService {

	/**PrePlanTemp分页信息
	 * @param page
	 * @param prePlanTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlanTemp prePlanTemp);
	/**
	 * 保存
	 * @param prePlanTemp
	 * @return
	 */
	public Result doSaveProc(PrePlanTemp prePlanTemp);
}
