package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.StaffingDao;
import com.house.home.entity.basic.Department3;
import com.house.home.service.basic.StaffingService;

@SuppressWarnings("serial")
@Service 
public class StaffingServiceImpl extends BaseServiceImpl implements StaffingService {
	@Autowired
	private  StaffingDao staffingDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, String departType,
			Department3 department3) {
		return staffingDao.findPageBySql(page, departType, department3);
	}

}
