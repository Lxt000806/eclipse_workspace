package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjTaskExecAnalyDao;
import com.house.home.service.query.PrjTaskExecAnalyService;

@SuppressWarnings("serial")
@Service
public class PrjTaskExecAnalyServiceImpl extends BaseServiceImpl implements PrjTaskExecAnalyService {
	@Autowired
	private PrjTaskExecAnalyDao prjTaskExecAnalyDao;
	
	@Override
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String department1, String department2){
		return prjTaskExecAnalyDao.goJqGrid(page, dateFrom, dateTo, department1, department2);
	}

	@Override
	public Page<Map<String, Object>> goJqGridView(Page<Map<String, Object>> page, Date dateFrom, Date dateTo,String rcvCZY) {
		return prjTaskExecAnalyDao.goJqGridView(page, dateFrom, dateTo, rcvCZY);
	}

	@Override
	public Page<Map<String, Object>> goJqGrid_prjDelayNoTrrigerTask(
			Page<Map<String, Object>> page, Date dateFrom, Date dateTo,
			String rcvCZY) {
		return prjTaskExecAnalyDao.goJqGrid_prjDelayNoTrrigerTask(page, dateFrom, dateTo, rcvCZY);
	}
	
	
}
