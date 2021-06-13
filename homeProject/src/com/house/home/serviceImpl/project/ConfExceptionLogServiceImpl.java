package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ConfExceptionLogDao;
import com.house.home.entity.project.ConfExceptionLog;
import com.house.home.service.project.ConfExceptionLogService;

@SuppressWarnings("serial")
@Service
public class ConfExceptionLogServiceImpl  extends BaseServiceImpl implements ConfExceptionLogService {

	@Autowired
	private ConfExceptionLogDao confExceptionLogDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, ConfExceptionLog confExceptionLog) {
		
		return confExceptionLogDao.findPageBySql(page, confExceptionLog);
	}

}
