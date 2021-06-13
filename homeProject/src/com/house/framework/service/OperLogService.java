package com.house.framework.service;

import com.house.framework.commons.orm.Page;
import com.house.framework.log.LoggerObject;

public interface OperLogService {
	
	/**
	 * 日志列表，明细
	 * @param page 
	 * @param logObj 日志对象
	 * @param logId 日志ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 列表对象
	 */
	@SuppressWarnings("rawtypes")
	public Page findPage(Page page,LoggerObject logObj,String logId,String startTime,String endTime);
	
	/**
	 * Wap平台日志列表，明细
	 * @param page 
	 * @param logObj 日志对象
	 * @param logId 日志ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 列表对象
	 */
	@SuppressWarnings("rawtypes")
	public Page findPageWap(Page page,LoggerObject logObj,String logId,String startTime,String endTime);
	
}
