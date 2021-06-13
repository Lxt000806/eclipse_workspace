package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.CljsfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.CljsfxService;

@Service
@SuppressWarnings("serial")
public class CljsfxServiceImpl extends BaseServiceImpl implements CljsfxService{

	@Autowired
	private CljsfxDao itemSettlementDao;

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return itemSettlementDao.findPageBySql(page, customer);
	}
	
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, Customer customer) {
		return itemSettlementDao.findPageBySqlDetail(page, customer);
	}
}
