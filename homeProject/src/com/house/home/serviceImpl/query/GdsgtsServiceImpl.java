package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.GdsgtsDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.GdsgtsService;

@Service
@SuppressWarnings("serial")
public class GdsgtsServiceImpl extends BaseServiceImpl implements GdsgtsService{

	@Autowired
	private GdsgtsDao gdsgtsDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return gdsgtsDao.findPageBySql(page, customer);
	}
	
	
}
