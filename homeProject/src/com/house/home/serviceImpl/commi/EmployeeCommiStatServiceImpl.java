package com.house.home.serviceImpl.commi;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.EmployeeCommiStatDao;
import com.house.home.entity.basic.Employee;
import com.house.home.service.commi.EmployeeCommiStatService;
@Service
@SuppressWarnings("serial")
public class EmployeeCommiStatServiceImpl extends BaseServiceImpl implements EmployeeCommiStatService{
	
	@Autowired
	private EmployeeCommiStatDao employeeCommiStatDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Employee employee) {
		
		return employeeCommiStatDao.findPageBySql(page, employee);
	}
}
