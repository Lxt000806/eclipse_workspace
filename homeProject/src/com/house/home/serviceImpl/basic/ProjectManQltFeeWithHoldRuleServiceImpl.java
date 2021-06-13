package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ProjectManQltFeeWithHoldRuleDao;
import com.house.home.entity.basic.ProjectManQltFeeWithHoldRule;
import com.house.home.service.basic.ProjectManQltFeeWithHoldRuleService;

@SuppressWarnings("serial")
@Service
public class ProjectManQltFeeWithHoldRuleServiceImpl extends BaseServiceImpl implements ProjectManQltFeeWithHoldRuleService {
	@Autowired
	private ProjectManQltFeeWithHoldRuleDao projectManQltFeeWithHoldRuleDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule) {
		return projectManQltFeeWithHoldRuleDao.findPageBySql(page, projectManQltFeeWithHoldRule);
	}

}
