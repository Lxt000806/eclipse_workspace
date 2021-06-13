package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryEmpAttendanceDao;
import com.house.home.entity.salary.SalaryEmpAttendance;
import com.house.home.service.salary.SalaryEmpAttendanceService;

@SuppressWarnings("serial")
@Service
public class SalaryEmpAttendanceServiceImpl extends BaseServiceImpl implements SalaryEmpAttendanceService {

	@Autowired
	private SalaryEmpAttendanceDao salaryEmpAttendanceDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpAttendance salaryEmpAttendance){
		return salaryEmpAttendanceDao.findPageBySql(page, salaryEmpAttendance);
	}

	@Override
	public Long doDeleteBatch(Integer salaryMon) {
		return salaryEmpAttendanceDao.doDeleteBatch(salaryMon);
	}
	
	@Override
	public List<Map<String, Object>> isExistsMon(SalaryEmpAttendance salaryEmpAttendance) {
		return salaryEmpAttendanceDao.isExistsMon(salaryEmpAttendance);
	}
	
	@Override
	public Result doSaveBatch(SalaryEmpAttendance salaryEmpAttendance) {
		return salaryEmpAttendanceDao.doSaveBatch(salaryEmpAttendance);
	}

	@Override
	public Integer getDefSalaryMon() {
		
		return salaryEmpAttendanceDao.getDefSalaryMon();
	}


}
