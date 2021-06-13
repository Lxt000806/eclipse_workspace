package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.FinSalaryEmpDeductionDao;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.service.salary.FinSalaryEmpDeductionService;

@SuppressWarnings("serial")
@Service
public class FinSalaryEmpDeductionServiceImpl extends BaseServiceImpl implements FinSalaryEmpDeductionService {

	@Autowired
	private FinSalaryEmpDeductionDao finSalaryEmpDeductionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpDeduction finSalaryEmpDeduction){
		return finSalaryEmpDeductionDao.findPageBySql(page, finSalaryEmpDeduction);
	}

	@Override
	public List<Map<String, Object>> getDeductType2(String deductType2) {
		return finSalaryEmpDeductionDao.getDeductType2(deductType2);
	}

	@Override
	public Result doSaveBatch(SalaryEmpDeduction finSalaryEmpDeduction) {
		return finSalaryEmpDeductionDao.doSaveBatch(finSalaryEmpDeduction);
	}

}
