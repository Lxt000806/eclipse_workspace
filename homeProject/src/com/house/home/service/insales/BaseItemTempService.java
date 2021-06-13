package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.BaseItemTemp;

public interface BaseItemTempService extends BaseService {

	/**BaseItemTemp分页信息
	 * @param page
	 * @param baseItemTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemTemp baseItemTemp);
	/**
	 * 主页查询
	 * @author	created by zb
	 * @date	2019-5-22--下午5:46:37
	 * @param page
	 * @param baseItemTemp
	 * @return
	 */
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String, Object>> page,
			BaseItemTemp baseItemTemp);
	/**
	 * 明细查询
	 * @author	created by zb
	 * @date	2019-5-23--上午10:31:10
	 * @param page
	 * @param baseItemTemp
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			BaseItemTemp baseItemTemp);
	/**
	 * 存储过程保存
	 * @author	created by zb
	 * @date	2019-5-23--下午5:38:00
	 * @param baseItemTemp
	 * @return
	 */
	public Result doSave(BaseItemTemp baseItemTemp);
	
}
