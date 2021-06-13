package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.IntMeasureProgDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntMeasureProgService;
@Service
@SuppressWarnings("serial")
public class IntMeasureProgServiceImpl extends BaseServiceImpl implements IntMeasureProgService {
	@Autowired
	private IntMeasureProgDao intMeasureProgDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return intMeasureProgDao.findPageBySql(page,customer);
	}
}
