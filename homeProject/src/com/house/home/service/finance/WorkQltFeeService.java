package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;

public interface WorkQltFeeService extends BaseService {

	/**Worker分页信息
	 * @param page
	 * @param worker
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Worker worker);
	
	/**
	 * WorkQltFee明细信息
	 * 
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String, Object>> findDetailBySql(
			Page<Map<String, Object>> page, WorkQltFeeTran workQltFeeTran);
	/**
	 * 存取
	 * @param workQltFeeTran
	 */
	public void doUpdate(WorkQltFeeTran workQltFeeTran);
}
