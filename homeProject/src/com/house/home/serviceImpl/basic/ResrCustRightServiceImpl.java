package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ResrCustRightDao;
import com.house.home.entity.basic.ResrCustRight;
import com.house.home.service.basic.ResrCustRightService;

@SuppressWarnings("serial")
@Service
public class ResrCustRightServiceImpl extends BaseServiceImpl implements ResrCustRightService {
	@Autowired
	private ResrCustRightDao resrCustRightDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ResrCustRight resrCustRight) {
		return resrCustRightDao.findPageBySql(page, resrCustRight);
	}
	
	@Override
	public Map<String,Object> getByPk(Integer pk){
		return resrCustRightDao.getByPk(pk);
	}
	
	@Override
	public boolean getByBuildandDept(String builderCode,String department2){
		return resrCustRightDao.getByBuildandDept(builderCode,department2);
	}
}
