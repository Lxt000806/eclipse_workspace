package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.BusinessCommiFloatRuleDetailDao;
import com.house.home.entity.commi.BusinessCommiFloatRuleDetail;
import com.house.home.service.commi.BusinessCommiFloatRuleDetailService;

@SuppressWarnings("serial")
@Service 
public class BusinessCommiFloatRuleDetailServiceImpl extends BaseServiceImpl implements BusinessCommiFloatRuleDetailService {
	@Autowired
	private  BusinessCommiFloatRuleDetailDao businessCommiFloatRuleDetailDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BusinessCommiFloatRuleDetail businessCommiFloatRuleDetail) {

		return businessCommiFloatRuleDetailDao.findPageBySql(page, businessCommiFloatRuleDetail);
	}

	
}
