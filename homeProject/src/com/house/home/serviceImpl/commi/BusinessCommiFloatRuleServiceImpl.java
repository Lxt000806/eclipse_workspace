package com.house.home.serviceImpl.commi;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.BusinessCommiFloatRuleDao;
import com.house.home.entity.commi.BusinessCommiFloatRule;
import com.house.home.service.commi.BusinessCommiFloatRuleService;

@SuppressWarnings("serial")
@Service 
public class BusinessCommiFloatRuleServiceImpl extends BaseServiceImpl implements BusinessCommiFloatRuleService {
	@Autowired
	private  BusinessCommiFloatRuleDao businessCommiFloatRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BusinessCommiFloatRule businessCommiFloatRule) {
		return businessCommiFloatRuleDao.findPageBySql(page, businessCommiFloatRule);
	}

	@Override
	public Result doSave(BusinessCommiFloatRule businessCommiFloatRule) {

		return businessCommiFloatRuleDao.doSave(businessCommiFloatRule);
	}

	@Override
	public List<Map<String, Object>> getFloatRuleSelection() {
		
		return businessCommiFloatRuleDao.getFloatRuleSelection();
	}
	
	
}
