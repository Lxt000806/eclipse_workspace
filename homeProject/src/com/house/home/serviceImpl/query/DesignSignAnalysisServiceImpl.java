package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.DesignSignAnalysisDao;
import com.house.home.dao.query.PrjSignAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DesignSignAnalysisService;

@Service
@SuppressWarnings("serial")
public class DesignSignAnalysisServiceImpl extends BaseServiceImpl implements DesignSignAnalysisService{
	
	@Autowired
	private DesignSignAnalysisDao designSignAnalysisDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return designSignAnalysisDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return designSignAnalysisDao.findPageBySql_detail(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return designSignAnalysisDao.findPageBySql_detail_detail(page, customer);
	}

	
}
