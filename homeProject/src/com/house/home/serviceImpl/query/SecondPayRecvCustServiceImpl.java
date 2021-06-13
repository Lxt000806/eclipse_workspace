package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ColorDrawFeeProvideDao;
import com.house.home.dao.query.DdzhfxDao;
import com.house.home.dao.query.SecondPayRecvCustDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ColorDrawFeeProvideService;
import com.house.home.service.query.DdzhfxService;
import com.house.home.service.query.SecondPayRecvCustService;

@SuppressWarnings("serial")
@Service
public class SecondPayRecvCustServiceImpl extends BaseServiceImpl implements SecondPayRecvCustService {

	@Autowired
	private SecondPayRecvCustDao secondPayRecvCustDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return secondPayRecvCustDao.findPageBySql(page, customer);
	}
	

}
