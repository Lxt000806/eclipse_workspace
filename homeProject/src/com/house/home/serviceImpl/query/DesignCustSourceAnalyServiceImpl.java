package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.DesignCustSourceAnalyDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DesignCustSourceAnalyService;

@SuppressWarnings("serial")
@Service
public class DesignCustSourceAnalyServiceImpl extends BaseServiceImpl implements DesignCustSourceAnalyService {

	@Autowired
	private DesignCustSourceAnalyDao designCustSourceAnalyDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer,UserContext uc){
		return designCustSourceAnalyDao.findPageBySql(page, customer, uc);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee, UserContext uc) {
		if ("CRT".equals(employee.getViewType())) {
			return this.designCustSourceAnalyDao.findCrtDetailPageBySql(page, employee, uc);
		} else if ("SET".equals(employee.getViewType())) {
			return this.designCustSourceAnalyDao.findSetDetailPageBySql(page, employee, uc);
		} else if ("SIGN".equals(employee.getViewType())) {
			return this.designCustSourceAnalyDao.findSignDetailPageBySql(page, employee, uc);
		}
		return null;
	}

}
