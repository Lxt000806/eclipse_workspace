package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.home.dao.basic.PayRuleDao;import com.house.home.entity.basic.PayRule;
import com.house.home.service.basic.PayRuleService;

@SuppressWarnings("serial")
@Service 
public class PayRuleServiceImpl extends BaseServiceImpl implements PayRuleService {
	@Autowired
	private  PayRuleDao payRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PayRule payRule) {
		// TODO Auto-generated method stub
		return payRuleDao.findPageBySql(page, payRule);
	}
	
	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, PayRule payRule) {
		// TODO Auto-generated method stub
		return payRuleDao.findDetailPageBySql(page, payRule);
	}

	@Override
	public Result doSave(PayRule payRule) {
		// TODO Auto-generated method stub
		return payRuleDao.doSave(payRule);
	}

	
}
