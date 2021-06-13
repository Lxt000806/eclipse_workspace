package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.BusinessCustFlowAnalyDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.BusinessCustFlowAnalyService;

@SuppressWarnings("serial")
@Service 
public class BusinessCustFlowAnalyServiceImpl extends BaseServiceImpl implements BusinessCustFlowAnalyService {
	@Autowired
	private  BusinessCustFlowAnalyDao businessCustFlowAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return businessCustFlowAnalyDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee, UserContext uc) {
		if ("CRT".equals(employee.getViewType())) {
			return this.businessCustFlowAnalyDao.findCrtDetailPageBySql(page, employee, uc);
		} else if ("SET".equals(employee.getViewType())) {
			return this.businessCustFlowAnalyDao.findSetDetailPageBySql(page, employee, uc);
		} else if ("SIGN".equals(employee.getViewType())) {
			return this.businessCustFlowAnalyDao.findSignDetailPageBySql(page, employee, uc);
		}
		return null;
	}

}
