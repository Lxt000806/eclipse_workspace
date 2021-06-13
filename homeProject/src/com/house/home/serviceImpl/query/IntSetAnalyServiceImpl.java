package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.IntSetAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntSetAnalyService;

@SuppressWarnings("serial")
@Service 
public class IntSetAnalyServiceImpl extends BaseServiceImpl implements IntSetAnalyService {
	@Autowired
	private IntSetAnalyDao intSetAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return intSetAnalyDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findIaDetailBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return intSetAnalyDao.findIaDetailBySql(page, customer);
	}

}
