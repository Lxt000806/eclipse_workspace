package com.house.home.serviceImpl.salary;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryCompareAnalyseDao;
import com.house.home.service.salary.SalaryCompareAnalyseService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.home.entity.salary.SalaryData;

@SuppressWarnings("serial")
@Service 
public class SalaryCompareAnalyseServiceImpl extends BaseServiceImpl implements SalaryCompareAnalyseService {
	@Autowired
	private  SalaryCompareAnalyseDao salaryCompareAnalyseDao;
	
	@Override	
	public List<Map<String, Object>> findPageBySql(SalaryData salaryData){
	
		return salaryCompareAnalyseDao.findPageBySql(salaryData);
	}
	@Override
	public Page<Map<String, Object>> findJoinEmpPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCompareAnalyseDao.findJoinEmpPageBySql(page, salaryData);
	}
	@Override
	public Page<Map<String, Object>> findLeaveEmpPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCompareAnalyseDao.findLeaveEmpPageBySql(page, salaryData);
	}
	@Override
	public Page<Map<String, Object>> findBaseSalaryCHgEmpPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCompareAnalyseDao.findBaseSalaryCHgEmpPageBySql(page, salaryData);
	}
	@Override
	public Page<Map<String, Object>> findRealPayPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCompareAnalyseDao.findRealPayPageBySql(page, salaryData);
	}
	@Override
	public Page<Map<String, Object>> findUnPaidEmpPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		
		return salaryCompareAnalyseDao.findUnPaidEmpPageBySql(page, salaryData);
	}
	
	
}
