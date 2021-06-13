package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiCustomerDao;
import com.house.home.entity.commi.CommiCustomer;
import com.house.home.service.commi.CommiCustomerService;

@SuppressWarnings("serial")
@Service
public class CommiCustomerServiceImpl extends BaseServiceImpl implements CommiCustomerService {

	@Autowired
	private CommiCustomerDao commiCustomerDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustomer commiCustomer){
		return commiCustomerDao.findPageBySql(page, commiCustomer);
	}

}
