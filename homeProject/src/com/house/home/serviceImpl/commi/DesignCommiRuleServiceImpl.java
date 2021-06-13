package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.DesignCommiRuleDao;
import com.house.home.entity.commi.DesignCommiRule;
import com.house.home.service.commi.DesignCommiRuleService;

@SuppressWarnings("serial")
@Service 
public class DesignCommiRuleServiceImpl extends BaseServiceImpl implements DesignCommiRuleService {
	@Autowired
	private  DesignCommiRuleDao designCommiRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DesignCommiRule designCommiRule) {
	
		return designCommiRuleDao.findPageBySql(page, designCommiRule);
	}

	
	
}
