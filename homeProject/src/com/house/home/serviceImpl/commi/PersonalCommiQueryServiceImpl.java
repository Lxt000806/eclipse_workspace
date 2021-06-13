package com.house.home.serviceImpl.commi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.commi.PersonalCommiQueryDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.commi.PersonalCommiQueryService;

@SuppressWarnings("serial")
@Service
public class PersonalCommiQueryServiceImpl extends BaseServiceImpl implements PersonalCommiQueryService{

	@Autowired
	private PersonalCommiQueryDao personalCommiQueryDao;
	
	@Override
	public List<Map<String, Object>> getMainPageData(Employee employee) {

		return personalCommiQueryDao.getMainPageData(employee);
	}

}
