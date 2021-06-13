package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.UpgWithholdDao;
import com.house.home.entity.basic.UpgWithhold;
import com.house.home.service.basic.UpgWithholdService;

@SuppressWarnings("serial")
@Service 
public class UpgWithholdServiceImpl extends BaseServiceImpl implements UpgWithholdService {
	@Autowired
	private  UpgWithholdDao upgWithholdDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, UpgWithhold upgWithhold) {
		return upgWithholdDao.findPageBySql(page,upgWithhold);
	}
	
	@Override
	public Page<Map<String, Object>> findDetailByCode(
			Page<Map<String, Object>> page, UpgWithhold upgWithhold) {
		return upgWithholdDao.findDetailByCode(page,upgWithhold);
	}
	
	public UpgWithhold getByCode(String code){
		return upgWithholdDao.getByCode(code);
	}

	@Override
	public Result doSave(UpgWithhold upgWithhold) {
		return upgWithholdDao.doSave(upgWithhold);
	}

}
