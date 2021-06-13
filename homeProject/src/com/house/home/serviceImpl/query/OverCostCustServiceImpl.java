package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.OverCostCustDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.OverCostCustService;

@Service
@SuppressWarnings("serial")
public class OverCostCustServiceImpl extends BaseServiceImpl implements OverCostCustService {
	@Autowired
	private OverCostCustDao overCostCustDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		return overCostCustDao.findPageBySql(page,customer);
	}



}
