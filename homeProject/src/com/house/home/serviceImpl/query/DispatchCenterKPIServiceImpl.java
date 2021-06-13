package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.DispatchCenterKPIDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DispatchCenterKPIService;
@Service
@SuppressWarnings("serial")
public class DispatchCenterKPIServiceImpl extends BaseServiceImpl implements
		DispatchCenterKPIService {
	@Autowired
	private DispatchCenterKPIDao dispatchCenterKPIDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_picConfirmDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_picConfirmDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_beginDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_beginDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_checkDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_checkDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>>  findPageBySql_specItemReqDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_specItemReqDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_fixDutyDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_fixDutyDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_dispatchDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_dispatchDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_mainCheckDetail(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return dispatchCenterKPIDao.findPageBySql_mainCheckDetail(page,customer);
	}


}
