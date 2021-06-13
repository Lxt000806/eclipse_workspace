package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryEmpPensionDao;
import com.house.home.entity.salary.SalaryEmpPension;
import com.house.home.service.salary.SalaryEmpPensionService;

@SuppressWarnings("serial")
@Service
public class SalaryEmpPensionServiceImpl extends BaseServiceImpl implements SalaryEmpPensionService {

	@Autowired
	private SalaryEmpPensionDao salaryEmpPensionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpPension salaryEmpPension){
		return salaryEmpPensionDao.findPageBySql(page, salaryEmpPension);
	}

	@Override
	public List<Map<String, Object>> isExistsMon(SalaryEmpPension salaryEmpPension) {
		return salaryEmpPensionDao.isExistsMon(salaryEmpPension);
	}

	@Override
	public Result doImportSalaryEmpPension(SalaryEmpPension salaryEmpPension) {

		return salaryEmpPensionDao.doImportSalaryEmpPension(salaryEmpPension);
	}

	@Override
	public List<Map<String, Object>> isCheckMon(
			SalaryEmpPension salaryEmpPension) {
		return salaryEmpPensionDao.isCheckMon(salaryEmpPension);
	}
	
	
}
