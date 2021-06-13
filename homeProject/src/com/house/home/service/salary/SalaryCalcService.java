package com.house.home.service.salary;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryDataAdjust;

public interface SalaryCalcService extends BaseService {

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, SalaryData salaryData);
	
	public Page<Map<String, Object>>  getPaymentDetail(Page<Map<String,Object>> page, SalaryData salaryData);
	
	public List<Map<String, Object>> getMainPageData(SalaryData salaryData);

	public List<Map<String, Object>> getPaymentDetailPageData(SalaryData salaryData);

	public Page<Map<String, Object>>  getSalaryChgByJqgrid(Page<Map<String,Object>> page, SalaryData salaryData);

	public Integer getCalcMon();

	public Integer getMaxCalcMon();

	public Result doDelSalaryChg(String pks);
	
	public Integer getSalaryScheme();

	public Result doCalc(SalaryData salaryData);

	public Result doCheck(SalaryData salaryData);
	
	public Result doCheckReturn(SalaryData salaryData);
	
	public Result doChgEmpSalary(SalaryDataAdjust salaryDataAdjust);

	public Result doChgEmpSalaryUpdate(SalaryDataAdjust salaryDataAdjust);

	public Result doSchemeEmpSave(SalaryData salaryData);
	
	public String getCmpEmpCount(Integer salaryScheme);

	public List<Map<String, Object>> getSalaryStatusCtrl(SalaryData salaryData);
	
	public List<Map<String, Object>> getPaymentSubreport(SalaryData salaryData);
	
	public Result doImportSalaryChg(SalaryData salaryData);
	
	public String getSalaryEmpByIdNum(String idNum);
	
	public String IsLastCheckedCtrl(SalaryData salaryData);

	public String getFirstCalcTime(Integer salaryMon);
	
	public String getSalaryStatus(SalaryData salaryData);


}

