package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.EmployeeDdphDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.EmployeeDdphService;
@Service
@SuppressWarnings("serial")
public class EmployeeDdphServiceImpl extends BaseServiceImpl implements
		EmployeeDdphService {
	@Autowired
	private EmployeeDdphDao employeeDdphDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return employeeDdphDao.findPageBySql_pDdtj_ph(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findSignPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return employeeDdphDao.findSignPageBySql(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findSignSetPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return employeeDdphDao.findSignSetPageBySql(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findNowSignPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return employeeDdphDao.findNowSignPageBySql(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findCrtPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return employeeDdphDao.findCrtPageBySql(page,customer);
	}
	@Override
	public Page<Map<String, Object>> findSetPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return employeeDdphDao.findSetPageBySql(page,customer);
	}
	@Override
	public boolean hasViewRight(Czybm czybm,   Employee employee) {
		return employeeDdphDao.hasViewRight(czybm, employee);
	}
	@Override
	public Page<Map<String, Object>> findQdyjPageBySql(
			Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		return employeeDdphDao.findQdyjPageBySql(page, customer,uc);
	}
	@Override
	public Page<Map<String, Object>> findZjyjPageBySql(
			Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		return employeeDdphDao.findZjyjPageBySql(page, customer, uc);
	}


}
