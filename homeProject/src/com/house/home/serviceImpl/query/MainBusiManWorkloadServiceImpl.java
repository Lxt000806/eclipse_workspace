package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.EmployeeDdphDao;
import com.house.home.dao.query.MainBusiManWorkloadDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.EmployeeDdphService;
import com.house.home.service.query.MainBusiManWorkloadService;
@Service
@SuppressWarnings("serial")
public class MainBusiManWorkloadServiceImpl extends BaseServiceImpl implements
		MainBusiManWorkloadService {
	@Autowired
	private MainBusiManWorkloadDao mainBusiManWorkloadDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_beginDetail(
			Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql_beginDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_buildingDetail(
			Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql_buildingDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_completedDetail(
			Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql_completedDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_firstConfirmDetail(
			Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql_firstConfirmDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_secondConfirmDetail(
			Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql_secondConfirmDetail(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_chgDetail(
			Page<Map<String, Object>> page, Customer customer) {
		 
		return mainBusiManWorkloadDao.findPageBySql_chgDetail(page,customer);
	}

}
