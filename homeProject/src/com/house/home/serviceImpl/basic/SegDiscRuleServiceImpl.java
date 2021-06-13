package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SegDiscRuleDao;
import com.house.home.entity.basic.SegDiscRule;
import com.house.home.service.basic.SegDiscRuleService;

@SuppressWarnings("serial")
@Service
public class SegDiscRuleServiceImpl extends BaseServiceImpl implements
		SegDiscRuleService {
	@Autowired
	private SegDiscRuleDao segDiscRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SegDiscRule segDiscRule) {
		return segDiscRuleDao.findPageBySql(page, segDiscRule);
	}
}
