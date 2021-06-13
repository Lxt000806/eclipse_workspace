package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemSignCountDao;
import com.house.home.dao.query.itemCheckAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemSignCountService;
import com.house.home.service.query.itemCheckAnalysisService;
@Service
@SuppressWarnings("serial")
public class itemCheckAnalysisServiceImpl extends BaseServiceImpl implements itemCheckAnalysisService {
	@Autowired
	private itemCheckAnalysisDao itemCheckAnalysisDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return itemCheckAnalysisDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return itemCheckAnalysisDao.findPageBySql_detail(page,customer);
	}
	
}
