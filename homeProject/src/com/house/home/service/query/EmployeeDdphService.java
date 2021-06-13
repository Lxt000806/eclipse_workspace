package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;

public interface EmployeeDdphService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	public Page<Map<String,Object>> findSignPageBySql(Page<Map<String, Object>> page, Customer customer);
	public Page<Map<String,Object>> findSignSetPageBySql(Page<Map<String, Object>> page,Customer customer);
	public  Page<Map<String,Object>> findNowSignPageBySql(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findCrtPageBySql(Page<Map<String, Object>> page,Customer customer);
	public Page<Map<String,Object>> findSetPageBySql(Page<Map<String, Object>> page,Customer customer);
	public boolean hasViewRight(Czybm czybm, Employee employee);
	public Page<Map<String, Object>> findQdyjPageBySql(Page<Map<String, Object>> page, Customer customer,UserContext uc);
	public Page<Map<String, Object>> findZjyjPageBySql(Page<Map<String, Object>> page, Customer customer,UserContext uc);
}
