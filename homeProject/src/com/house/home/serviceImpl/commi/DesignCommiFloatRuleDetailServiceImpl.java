package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.DesignCommiFloatRuleDetailDao;
import com.house.home.entity.commi.DesignCommiFloatRuleDetail;
import com.house.home.service.commi.DesignCommiFloatRuleDetailService;

@SuppressWarnings("serial")
@Service 
public class DesignCommiFloatRuleDetailServiceImpl extends BaseServiceImpl implements DesignCommiFloatRuleDetailService {
	@Autowired
	private  DesignCommiFloatRuleDetailDao designCommiFloatRuleDetailDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			DesignCommiFloatRuleDetail designCommiFloatRuleDetail) {
		
		return designCommiFloatRuleDetailDao.findPageBySql(page, designCommiFloatRuleDetail);
	}

	
}
