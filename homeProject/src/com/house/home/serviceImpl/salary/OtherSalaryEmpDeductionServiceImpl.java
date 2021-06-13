package com.house.home.serviceImpl.salary;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.OtherSalaryEmpDeductionDao;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.service.salary.OtherSalaryEmpDeductionService;

@SuppressWarnings("serial")
@Service
public class OtherSalaryEmpDeductionServiceImpl extends BaseServiceImpl implements OtherSalaryEmpDeductionService {

	@Autowired
	private OtherSalaryEmpDeductionDao otherSalaryEmpDeductionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpDeduction otherSalaryEmpDeduction){
		return otherSalaryEmpDeductionDao.findPageBySql(page, otherSalaryEmpDeduction);
	}

}
