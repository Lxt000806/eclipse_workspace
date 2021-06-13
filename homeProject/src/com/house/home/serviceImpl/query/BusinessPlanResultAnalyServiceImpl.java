package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.BusinessPlanResultAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.BusinessPlanResultAnalyService;

@Service
@SuppressWarnings("serial")
public class BusinessPlanResultAnalyServiceImpl extends BaseServiceImpl implements BusinessPlanResultAnalyService {
	@Autowired
	private BusinessPlanResultAnalyDao businessPlanResultAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		return businessPlanResultAnalyDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findBusinessStatistics(
			Page<Map<String, Object>> page, Customer customer) {
		return businessPlanResultAnalyDao.findBusinessStatistics(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findBusinessDetail(Page<Map<String, Object>> page, Customer customer) {
		return businessPlanResultAnalyDao.findBusinessDetail(page, customer);
	}



}
