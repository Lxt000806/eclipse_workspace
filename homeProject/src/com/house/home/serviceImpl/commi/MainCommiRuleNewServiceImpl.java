package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.MainCommiRuleNewDao;
import com.house.home.entity.commi.MainCommiRuleNew;
import com.house.home.service.commi.MainCommiRuleNewService;

@SuppressWarnings("serial")
@Service 
public class MainCommiRuleNewServiceImpl extends BaseServiceImpl implements MainCommiRuleNewService {
	@Autowired
	private  MainCommiRuleNewDao mainCommiRuleNewDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, MainCommiRuleNew mainCommiRuleNew) {

		return mainCommiRuleNewDao.findPageBySql(page, mainCommiRuleNew);
	}
	
	@Override
	public Page<Map<String, Object>> findItemDetailPageBySql(
			Page<Map<String, Object>> page, MainCommiRuleNew mainCommiRuleNew) {

		return mainCommiRuleNewDao.findItemDetailPageBySql(page, mainCommiRuleNew);
	}

	@Override
	public Result doSave(MainCommiRuleNew mainCommiRuleNew) {

		return mainCommiRuleNewDao.doSave(mainCommiRuleNew);
	}

	
}
