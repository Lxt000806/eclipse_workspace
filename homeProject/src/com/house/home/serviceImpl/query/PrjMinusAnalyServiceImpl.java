package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjMinusAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjMinusAnalyService;

@SuppressWarnings("serial")
@Service
public class PrjMinusAnalyServiceImpl extends BaseServiceImpl implements PrjMinusAnalyService {
	@Autowired
	private PrjMinusAnalyDao prjMinusAnalyDao;
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjMinusAnalyDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjMinusAnalyDao.findDetailPageBySql(page, customer);
	}
	
}
