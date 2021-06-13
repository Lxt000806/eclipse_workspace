package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiCustStakeholderRuleDao;
import com.house.home.entity.commi.CommiCustStakeholderRule;
import com.house.home.service.commi.CommiCustStakeholderRuleService;

@SuppressWarnings("serial")
@Service
public class CommiCustStakeholderRuleServiceImpl extends BaseServiceImpl implements CommiCustStakeholderRuleService {

	@Autowired
	private CommiCustStakeholderRuleDao commiCustStakeholderRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholderRule commiCustStakeholderRule){
		return commiCustStakeholderRuleDao.findPageBySql(page, commiCustStakeholderRule);
	}

	@Override
	public Page<Map<String, Object>> goRuleJqGrid(Page<Map<String, Object>> page,CommiCustStakeholderRule commiCustStakeholderRule) {
		return commiCustStakeholderRuleDao.goRuleJqGrid(page, commiCustStakeholderRule);
	}

}
