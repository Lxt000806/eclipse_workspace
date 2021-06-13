package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ConDayCalcRuleDao;
import com.house.home.entity.basic.ConDayCalcRule;
import com.house.home.service.basic.ConDayCalcRuleService;

@SuppressWarnings("serial")
@Service
public class ConDayCalcRuleServiceImpl extends BaseServiceImpl implements ConDayCalcRuleService {

	@Autowired
	private ConDayCalcRuleDao conDayCalcRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConDayCalcRule conDayCalcRule){
		return conDayCalcRuleDao.findPageBySql(page, conDayCalcRule);
	}

	@Override
	public List<Map<String,Object>> isRepeated(ConDayCalcRule conDayCalcRule) {
		return conDayCalcRuleDao.isRepeated(conDayCalcRule);
	}

}
