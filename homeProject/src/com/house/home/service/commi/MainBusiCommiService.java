package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.MainBusiCommi;

public interface MainBusiCommiService extends BaseService {

	/**MainBusiCommi分页信息
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi);
	
	/**
	 * 查询基础数据
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goBaseJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi);
	
	/**
	 * 查看汇总数据
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goSumJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi);
	
	/**
	 * 历史提成
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi);
	
	/**
	 * 独立销售
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goIndJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi);
	
	/**
	 * 干系人
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goStakeholderJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi);
}
