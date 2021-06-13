package com.house.home.serviceImpl.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.AgainSignNotInTimeDao;
import com.house.home.dao.finance.WorkQltFeeDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;
import com.house.home.service.finance.AgainSignNotInTimeService;
import com.house.home.service.finance.WorkQltFeeService;

@SuppressWarnings("serial")
@Service
public class AgainSignNotInTimeServiceImpl extends BaseServiceImpl implements
		AgainSignNotInTimeService {

	@Autowired
	private AgainSignNotInTimeDao againSignNotInTimeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return againSignNotInTimeDao.findPageBySql(page, customer);
	}

}
