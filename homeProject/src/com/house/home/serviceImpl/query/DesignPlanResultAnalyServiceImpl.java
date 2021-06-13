package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.DesignPlanResultAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DesignPlanResultAnalyService;

@Service
@SuppressWarnings("serial")
public class DesignPlanResultAnalyServiceImpl extends BaseServiceImpl implements DesignPlanResultAnalyService {
	@Autowired
	private DesignPlanResultAnalyDao designPlanResultAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		return designPlanResultAnalyDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findDesignStatistics(
			Page<Map<String, Object>> page, Customer customer) {
		return designPlanResultAnalyDao.findDesignStatistics(page, customer);
	}
}
