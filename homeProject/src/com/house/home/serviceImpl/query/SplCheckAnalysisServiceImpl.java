package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.SplCheckAnalysisDao;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.service.query.SplCheckAnalysisService;
@Service
@SuppressWarnings("serial")
public class SplCheckAnalysisServiceImpl extends BaseServiceImpl implements SplCheckAnalysisService {
	@Autowired
	private SplCheckAnalysisDao splCheckAnalysisDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		// TODO Auto-generated method stub
		return splCheckAnalysisDao.findPageBySql(page, splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goJqGridDetail(Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		// TODO Auto-generated method stub
		return splCheckAnalysisDao.goJqGridDetail(page, splCheckOut);
	}

}
