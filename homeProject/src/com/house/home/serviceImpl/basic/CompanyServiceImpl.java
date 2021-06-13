package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CompanyDao;
import com.house.home.entity.basic.Company;
import com.house.home.service.basic.CompanyService;

@SuppressWarnings("serial")
@Service
public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Company company){
		return companyDao.findPageBySql(page, company);
	}
	
	public Company getByDesc2(String desc2) {
		return companyDao.getByDesc2(desc2);
	}

	@Override
	public Page<Map<String, Object>> findSignPlacePageBySql(
			Page<Map<String, Object>> page, Company company) {
		return companyDao.findSignPlacePageBySql(page, company);
	}

	@Override
	public Page<Map<String, Object>> findCmpListOrderDistanceBySql(
			Page<Map<String, Object>> page, Company company) {
		return companyDao.findCmpListOrderDistanceBySql(page,company);
	}

	@Override
	public Page<Map<String, Object>> findSignPlaceOrderDistancePageBySql(
			Page<Map<String, Object>> page, Company company) {
		return companyDao.findSignPlaceOrderDistancePageBySql(page,company);
	}

}
