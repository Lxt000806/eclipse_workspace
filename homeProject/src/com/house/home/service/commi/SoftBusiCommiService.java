package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.SoftBusiCommi;

public interface SoftBusiCommiService extends BaseService {

	/**SoftBusiCommi分页信息
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi);
	
	/**
	 * 查询基础数据
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goBaseJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi);
	
	/**
	 * 查看汇总数据
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goSumJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi);
	
	/**
	 * 历史提成
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi);
	
	/**
	 * 独立销售
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goIndJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi);
	
	/**
	 * 干系人
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goStakeholderJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi);
}
