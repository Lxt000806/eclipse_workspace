package com.house.home.serviceImpl.design;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.ResrCustLogDao;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustLog;
import com.house.home.service.design.ResrCustLogService;

@SuppressWarnings("serial")
@Service
public class ResrCustLogServiceImpl extends BaseServiceImpl implements ResrCustLogService{
	@Autowired
	private ResrCustLogDao resrCustLogDao;
	
	@Override
	public Page<Map<String, Object>> findResrLogPageBySql(
			Page<Map<String, Object>> page, ResrCustLog resrCustLog) {
		return resrCustLogDao.findResrLogPageBySql(page, resrCustLog);
	}
	
	
}
