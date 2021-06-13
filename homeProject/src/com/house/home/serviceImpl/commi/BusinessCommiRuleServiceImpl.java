package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.BusinessCommiRuleDao;
import com.house.home.entity.commi.BusinessCommiRule;
import com.house.home.service.commi.BusinessCommiRuleService;

@SuppressWarnings("serial")
@Service 
public class BusinessCommiRuleServiceImpl extends BaseServiceImpl implements BusinessCommiRuleService {
	@Autowired
	private  BusinessCommiRuleDao businessCommiRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BusinessCommiRule businessCommiRule) {

		return businessCommiRuleDao.findPageBySql(page, businessCommiRule);
	}

	
}
