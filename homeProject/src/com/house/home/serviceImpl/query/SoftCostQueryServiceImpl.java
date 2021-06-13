package com.house.home.serviceImpl.query;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.SoftCostQueryDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.SoftCostQueryService;

@SuppressWarnings("serial")
@Service
public class SoftCostQueryServiceImpl extends BaseServiceImpl implements SoftCostQueryService {

	@Autowired
	private SoftCostQueryDao softCostQueryDao;
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return softCostQueryDao.findPageBySql(page, customer);
	}
	@Override
	public List<Map<String, Object>> getItemType12Descr() {
		return softCostQueryDao.getItemType12Descr();
	}
}
