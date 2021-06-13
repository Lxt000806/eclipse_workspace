package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BasePlanTemp;

public interface BasePlanTempService extends BaseService {

	/**BasePlanTemp分页信息
	 * @param page
	 * @param basePlanTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BasePlanTemp basePlanTemp);
	/**
	 * 保存
	 * @param basePlanTemp
	 * @return
	 */
	public Result doSaveProc(BasePlanTemp basePlanTemp);
	/**
	 * 名称和客户名是否已存在
	 * @param basePlanTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExist(BasePlanTemp basePlanTemp);
}
