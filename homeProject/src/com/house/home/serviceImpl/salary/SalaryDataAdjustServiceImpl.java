package com.house.home.serviceImpl.salary;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryDataAdjustDao;
import com.house.home.service.salary.SalaryDataAdjustService;

@SuppressWarnings("serial")
@Service 
public class SalaryDataAdjustServiceImpl extends BaseServiceImpl implements SalaryDataAdjustService {
	@Autowired
	private  SalaryDataAdjustDao salaryDataAdjustDao;

}
