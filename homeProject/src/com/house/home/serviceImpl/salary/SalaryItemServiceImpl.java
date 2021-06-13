package com.house.home.serviceImpl.salary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryItemDao;
import com.house.home.entity.salary.SalaryItem;
import com.house.home.service.salary.SalaryItemService;

@SuppressWarnings("serial")
@Service 
public class SalaryItemServiceImpl extends BaseServiceImpl implements SalaryItemService {
	@Autowired
	private  SalaryItemDao salaryItemDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryItem salaryItem) {
		return salaryItemDao.findPageBySql(page, salaryItem);
	}
	
	@Override
	public Page<Map<String, Object>> findCategoryDefindPageBySql(
			Page<Map<String, Object>> page, SalaryItem salaryItem) {
		return salaryItemDao.findCategoryDefindPageBySql(page, salaryItem);
	}
	
	@Override
	public List<Map<String, Object>>  findFormulaNodeBySql(
			Page<Map<String, Object>> page, SalaryItem salaryItem) {

		return salaryItemDao.findFormulaNodeBySql(salaryItem);
	}

	@Override
	public boolean checkSalaryItemDescr(SalaryItem salaryItem, String m_umStatus) {

		return salaryItemDao.checkSalaryItemDescr(salaryItem, m_umStatus);
	}

	@Override
	public Map<String, Object> getOperatorCfg() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = salaryItemDao.getOperatorCfg();
		
		if(list!= null & list.size()>0){
			for (Map<String, Object> operatorMap : list) {
				map.put(operatorMap.get("descr").toString(), operatorMap.get("code"));
			}
		}
		
		return map;
	}

	
	
}
