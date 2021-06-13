package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.IntReplenishDT;

public interface IntReplenishDTService extends BaseService {

	/**IntReplenishDT分页信息
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntReplenishDT intReplenishDT);
	/**
	 * IntReplenishDT分页信息
	 * 
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String,Object>> goCodeJqGrid(Page<Map<String,Object>> page, IntReplenishDT intReplenishDT) ;
	/**
	 * 根据No获取DT信息
	 * @author	created by zb
	 * @date	2019-11-20--上午11:56:13
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String,Object>> findNoPageBySql(Page<Map<String, Object>> page,
			IntReplenishDT intReplenishDT);
}
