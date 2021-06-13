package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.SoftPerfAnyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.SoftPerfAnyService;
@Service
@SuppressWarnings("serial")
public class SoftPerfAnyServiceImpl extends BaseServiceImpl implements SoftPerfAnyService {
	@Autowired
	private SoftPerfAnyDao softPerfAnyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return softPerfAnyDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return softPerfAnyDao.findPageBySql_detail(page,customer);
	}

    @Override
    public Page<Map<String, Object>> findCustomerPageBySql(Page<Map<String, Object>> page, Customer customer) {
        return softPerfAnyDao.findCustomerPageBySql(page, customer);
    }
	
}
