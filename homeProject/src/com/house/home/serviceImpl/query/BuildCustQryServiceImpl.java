package com.house.home.serviceImpl.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.query.BuildCustQryDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.BuildCustQryService;

@SuppressWarnings("serial")
@Service 
public class BuildCustQryServiceImpl extends BaseServiceImpl implements BuildCustQryService {
	@Autowired
	private  BuildCustQryDao conCustQueryDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		// TODO Auto-generated method stub
		return conCustQueryDao.findPageBySql(page,customer,uc);
	}

	
}
