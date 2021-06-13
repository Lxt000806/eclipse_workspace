package com.house.home.serviceImpl.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.project.CheckSalaryConfirmDao;
import com.house.home.entity.project.CustCheck;
import com.house.home.service.project.CheckSalaryConfirmService;

@SuppressWarnings("serial")
@Service 
public class CheckSalaryConfirmServiceImpl extends BaseServiceImpl implements CheckSalaryConfirmService {
	
	@Autowired
	CheckSalaryConfirmDao checkSalaryConfirmDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustCheck custCheck) {
		return checkSalaryConfirmDao.findPageBySql(page, custCheck);
	}

	@Override
	public Page<Map<String, Object>> findPageSalaryDetailBySql(
			Page<Map<String, Object>> page, CustCheck custCheck) {
		return checkSalaryConfirmDao.findPageSalaryDetailBySql(page, custCheck);
	}

}
