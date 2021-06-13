package com.house.framework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.Page;
import com.house.framework.dao.OperLogDao;
import com.house.framework.log.LoggerObject;
import com.house.framework.service.OperLogService;

@Service
public class OperLogServiceImpl implements OperLogService {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);

	@Autowired
	private OperLogDao operLogDao;

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
	public Page findPage(Page page, LoggerObject logObj, String logId, String startTime, String endTime) {
		return operLogDao.findPage(page, logObj, logId, startTime, endTime);
	}

	
	@SuppressWarnings("rawtypes")
	public Page findPageWap(Page page, LoggerObject logObj, String logId,
			String startTime, String endTime) {
		return operLogDao.findPageWap(page, logObj, logId, startTime, endTime);
	}
	

}
