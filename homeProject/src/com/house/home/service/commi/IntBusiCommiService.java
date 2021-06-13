package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.IntBusiCommi;

public interface IntBusiCommiService extends BaseService {

	/**IntBusiCommi分页信息
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi);
	
	/**
	 * 材料明细查询
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goBaseJqGrid(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi);
	
	/**
	 * 提成汇总查询
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goSumJqGrid(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi);
	
	/**
	 * 历史提成
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi);
	
	/**
	 * 独立销售
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goIndJqGrid(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi);
	
	/**
	 * 干系人
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goStakeholderJqGrid(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi);
}
