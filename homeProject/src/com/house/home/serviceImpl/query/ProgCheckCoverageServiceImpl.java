package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ProgCheckCoverageDao;
import com.house.home.service.query.ProgCheckCoverageService;

@Service
@SuppressWarnings("serial")
public class ProgCheckCoverageServiceImpl extends BaseServiceImpl implements ProgCheckCoverageService  {
	@Autowired
	private ProgCheckCoverageDao progCheckCoverageDao;

	@Override
	public List<Map<String,Object>> goJqGrid(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String isCheckDept){
		return progCheckCoverageDao.goJqGrid(page, dateFrom, dateTo, isCheckDept);
	}

	@Override
	public Page<Map<String,Object>> goJqGridView(Page<Map<String, Object>> page, String type, Date dateFrom, Date dateTo, String isCheckDept){
		return progCheckCoverageDao.goJqGridView(page, type, dateFrom, dateTo, isCheckDept);
	}
}
