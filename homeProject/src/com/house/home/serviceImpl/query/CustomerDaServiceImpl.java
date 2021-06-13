package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.dao.RoleUserDao;
import com.house.framework.entity.RoleUser;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.CustomerDaDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.CustomerDaService;

@SuppressWarnings("serial")
@Service
public class CustomerDaServiceImpl extends BaseServiceImpl implements CustomerDaService {

	@Autowired
	private CustomerDaDao customerDaDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return customerDaDao.findPageBySql(page, customer);
	}

}
