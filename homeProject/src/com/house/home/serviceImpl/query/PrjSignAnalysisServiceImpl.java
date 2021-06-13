package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjSignAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjSignAnalysisService;
@Service
@SuppressWarnings("serial")
public class PrjSignAnalysisServiceImpl extends BaseServiceImpl implements PrjSignAnalysisService {
	@Autowired
	private PrjSignAnalysisDao prjSignAnalysisDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjSignAnalysisDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return prjSignAnalysisDao.findPageBySql_detail(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return prjSignAnalysisDao.findPageBySql_detail_detail(page, customer);
	}
	
}
