package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SendTime;

public interface SendTimeService extends BaseService {

	/**SendTime分页信息
	 * @param page
	 * @param sendTime
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendTime sendTime);
	/**
	 * SendTimeDetail分页信息
	 * 
	 * @param page
	 * @param sendTime
	 * @return
	 */
	public Page<Map<String, Object>> goDetailGrid(Page<Map<String, Object>> page, SendTime sendTime);
	/**
	 * 
	 * 供应商是否引用发货时限
	 * @param sendTime
	 * @return
	 */
	public List<Map<String, Object>> isSupplierTime(SendTime sendTime);
	/**
	 * 保存
	 * 
	 * @param sendTime
	 * @return
	 */
	public Result doSaveProc(SendTime sendTime);
}
