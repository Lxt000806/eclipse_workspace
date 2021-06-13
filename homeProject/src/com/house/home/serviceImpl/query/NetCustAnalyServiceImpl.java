package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.NetCustAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.NetCustAnalyService;

@Service
@SuppressWarnings("serial")
public class NetCustAnalyServiceImpl extends BaseServiceImpl implements NetCustAnalyService{

	@Autowired
	private NetCustAnalyDao netCustAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		return netCustAnalyDao.findPageBySql(page,customer);
	
	}

	@Override
	public String getDefaultCustType() {
		return netCustAnalyDao.getDefaultCustType();
	}

	@Override
	public Page<Map<String, Object>> findManPageBySql_month(
			Page<Map<String, Object>> page, Customer customer) {
		return netCustAnalyDao.findManPageBySql_month(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findNetChanelPageBySql_month(
			Page<Map<String, Object>> page, Customer customer) {
		return netCustAnalyDao.findNetChanelPageBySql_month(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findManAndChanelPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return netCustAnalyDao.findManAndChanelPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findManPageBySql_history(
			Page<Map<String, Object>> page, Customer customer) {
		return netCustAnalyDao.findManPageBySql_History(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findChanelPageBySql_history(
			Page<Map<String, Object>> page, Customer customer) {
		return netCustAnalyDao.findChanelPageBySql_history(page,customer);
	}
	
}
