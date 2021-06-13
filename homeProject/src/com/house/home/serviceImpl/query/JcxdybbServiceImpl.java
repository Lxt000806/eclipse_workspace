package com.house.home.serviceImpl.query;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.JcxdybbDao;
import com.house.home.dao.query.SoftCostQueryDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.JcxdybbService;
import com.house.home.service.query.SoftCostQueryService;

@SuppressWarnings("serial")
@Service
public class JcxdybbServiceImpl extends BaseServiceImpl implements JcxdybbService {

	@Autowired
	private JcxdybbDao jcxdybbDao;
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return jcxdybbDao.findPageBySql(page, customer);
	}

	public Page<Map<String, Object>> llmx_findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return jcxdybbDao.llmx_findPageBySql(page, customer);
	}
	
	public Page<Map<String, Object>> zjmx_findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return jcxdybbDao.zjmx_findPageBySql(page, customer);
	}
}
