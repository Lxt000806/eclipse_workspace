package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.AutoArrDao;
import com.house.home.entity.project.AutoArr;
import com.house.home.service.project.AutoArrService;

@SuppressWarnings("serial")
@Service
public class AutoArrServiceImpl extends BaseServiceImpl implements AutoArrService{

	@Autowired
	private AutoArrDao autoArrDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, AutoArr autoArr) {
		
		return autoArrDao.findPageBySql(page,autoArr);
	}
	
	
	
}
