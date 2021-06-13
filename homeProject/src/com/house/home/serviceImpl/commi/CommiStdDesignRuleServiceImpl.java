package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiStdDesignRuleDao;
import com.house.home.entity.commi.CommiStdDesignRule;
import com.house.home.service.commi.CommiStdDesignRuleService;

@SuppressWarnings("serial")
@Service
public class CommiStdDesignRuleServiceImpl extends BaseServiceImpl implements CommiStdDesignRuleService {

	@Autowired
	private CommiStdDesignRuleDao commiStdDesignRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiStdDesignRule commiStdDesignRule){
		return commiStdDesignRuleDao.findPageBySql(page, commiStdDesignRule);
	}

	@Override
	public boolean checkExistDescr(CommiStdDesignRule commiStdDesignRule) {
		return commiStdDesignRuleDao.checkExistDescr(commiStdDesignRule);
	}

	@Override
	public boolean checkExistDrawFeeStdRuleByCommiStdDesignRulePK(
			Integer commiStdDesignRulePK) {
		return commiStdDesignRuleDao.checkExistDrawFeeStdRuleByCommiStdDesignRulePK(commiStdDesignRulePK);
	}

}
