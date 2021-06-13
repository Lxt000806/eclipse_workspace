package com.house.home.service.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryBankCardTypeCfg;

public interface SalaryBankCardTypeCfgService extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, SalaryBankCardTypeCfg salaryBankCardTypeCfg);

	public boolean checkSalaryBankCardTypeCfg(SalaryBankCardTypeCfg salaryBankCardTypeCfg, String m_umState);
}
