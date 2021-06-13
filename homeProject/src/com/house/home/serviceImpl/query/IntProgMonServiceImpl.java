package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.IntProgMonDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntProgMonService;

@SuppressWarnings("serial")
@Service
public class IntProgMonServiceImpl extends BaseServiceImpl implements IntProgMonService{
	@Autowired 
	private IntProgMonDao intProgMonDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return intProgMonDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findNotInstallPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return intProgMonDao.findNotInstallPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findNotDeliverPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return intProgMonDao.findNotDeliverPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findNotAppPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return intProgMonDao.findNotAppPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findNotSetWorkerPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return intProgMonDao.findNotSetWorkerPageBySql(page, customer);
	}
	
}
