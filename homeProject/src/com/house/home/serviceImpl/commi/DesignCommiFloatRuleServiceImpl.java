package com.house.home.serviceImpl.commi;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.DesignCommiFloatRuleDao;
import com.house.home.entity.commi.DesignCommiFloatRule;
import com.house.home.service.commi.DesignCommiFloatRuleService;

@SuppressWarnings("serial")
@Service 
public class DesignCommiFloatRuleServiceImpl extends BaseServiceImpl implements DesignCommiFloatRuleService {
	@Autowired
	private  DesignCommiFloatRuleDao designCommiFloatRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			DesignCommiFloatRule designCommiFloatRule) {
		
		return designCommiFloatRuleDao.findPageBySql(page, designCommiFloatRule);
	}

	@Override
	public Result doSave(DesignCommiFloatRule designCommiFloatRule) {
		
		return designCommiFloatRuleDao.doSave(designCommiFloatRule);
	}

	@Override
	public List<Map<String, Object>> getFloatRuleSelection() {
		
		return designCommiFloatRuleDao.getFloatRuleSelection();
	}
}
