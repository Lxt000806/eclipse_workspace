package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.CommiExprRuleDao;
import com.house.home.entity.commi.CommiExprRule;
import com.house.home.service.commi.CommiExprRuleService;

@SuppressWarnings("serial")
@Service 
public class CommiExprRuleServiceImpl extends BaseServiceImpl implements CommiExprRuleService {
	@Autowired
	private  CommiExprRuleDao commiExprRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CommiExprRule commiExprRule) {

		return commiExprRuleDao.findPageBySql(page, commiExprRule);
	}

	
}
