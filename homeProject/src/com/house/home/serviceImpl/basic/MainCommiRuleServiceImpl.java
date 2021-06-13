package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.MainCommiRuleDao;
import com.house.home.entity.basic.MainCommiRule;
import com.house.home.service.basic.MainCommiRuleService;

@SuppressWarnings("serial")
@Service
public class MainCommiRuleServiceImpl extends BaseServiceImpl implements MainCommiRuleService {

	@Autowired
	private MainCommiRuleDao mainCommiRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiRule mainCommiRule){
		return mainCommiRuleDao.findPageBySql(page, mainCommiRule);
	}

	@Override
	public Page<Map<String, Object>> findDetailByNo(
			Page<Map<String, Object>> page, MainCommiRule mainCommiRule) {
		return mainCommiRuleDao.findDetailByNo(page, mainCommiRule);
	}

	@Override
	public MainCommiRule getByNo(String no) {
		return mainCommiRuleDao.getByNo(no);
	}

	@Override
	public Result doSave(MainCommiRule mainCommiRule) {
		return mainCommiRuleDao.doSave(mainCommiRule);
	}

}
