package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.IntProfitAnaDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntProfitAnaService;

@SuppressWarnings("serial")
@Service
public class IntProfitAnaServiceImpl extends BaseServiceImpl implements IntProfitAnaService {

	@Autowired
	private IntProfitAnaDao intProfitAnaDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return intProfitAnaDao.findPageBySql(page, customer);
	}

	@Override
	public Map<String, Object> findDetailHead(String custCode) {
		return intProfitAnaDao.findDetailHead(custCode);
	}

}
