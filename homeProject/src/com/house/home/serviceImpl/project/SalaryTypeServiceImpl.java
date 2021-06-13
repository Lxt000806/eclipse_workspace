package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.SalaryTypeDao;
import com.house.home.entity.project.SalaryType;
import com.house.home.service.project.SalaryTypeService;

@SuppressWarnings("serial")
@Service
public class SalaryTypeServiceImpl extends BaseServiceImpl implements SalaryTypeService {

	@Autowired
	private SalaryTypeDao salaryTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryType salaryType){
		return salaryTypeDao.findPageBySql(page, salaryType);
	}

	@Override
	public List<SalaryType> findByNoExpired() {
		return salaryTypeDao.findByNoExpired();
	}

	@Override
	public Map<String,Object> findByCode(String code) {
		return salaryTypeDao.findByCode(code);
	}

}
