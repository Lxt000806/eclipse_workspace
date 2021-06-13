package com.house.home.serviceImpl.basic;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.DriverDao;
import com.house.home.entity.basic.Driver;
import com.house.home.service.basic.DriverService;

@SuppressWarnings("serial")
@Service
public class DriverServiceImpl extends BaseServiceImpl implements DriverService {
	
	@Autowired
	private DriverDao driverDao;
	
	@Override
	public Driver getByPhoneAndMm(String phone, String mm) {
		// TODO Auto-generated method stub
		return driverDao.getByPhoneAndMm(phone, mm);
	}
	
	@Override
	public Driver getByPhone(String phone) {
		// TODO Auto-generated method stub
		return driverDao.getByPhone(phone);
	}


	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Driver driver){
		return driverDao.findPageBySql(page,driver);
	}
	
	@Override
	public Driver getByName(String nameChi){
		return driverDao.getByName(nameChi);
	}
	
	@Override
	public List<Driver> findByNoExpired() {
		return driverDao.findByNoExpired();
	}

	@Override
	public Result doSave(Driver driver) {
		return driverDao.doSave(driver);
	}

	@Override
	public Boolean checkPhone(String phone) {
		return driverDao.checkPhone(phone);
	}
}
