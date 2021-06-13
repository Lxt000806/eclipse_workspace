package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjPlanPerfProfitDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjPlanPerfProfitService;

@SuppressWarnings("serial")
@Service 
public class PrjPlanPerfProfitServiceImpl extends BaseServiceImpl implements PrjPlanPerfProfitService {
	@Autowired
	private  PrjPlanPerfProfitDao prjPlanPerfProfitDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjPlanPerfProfitDao.findPageBySql(page, customer);
	}

}
