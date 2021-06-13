package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.TaxPayeeDao;
import com.house.home.entity.basic.TaxPayee;
import com.house.home.service.basic.TaxPayeeService;

@SuppressWarnings("serial")
@Service 
public class TaxPayeeServiceImpl extends BaseServiceImpl implements TaxPayeeService {
	@Autowired
	private  TaxPayeeDao taxPayeeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, TaxPayee taxPayee) {
		return taxPayeeDao.findPageBySql(page,taxPayee);
	}

	@Override
	public List<Map<String, Object>> getTaxPayeeList() {

		return taxPayeeDao.getTaxPayeeList();
	}

	
}
