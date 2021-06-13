package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.ItemAppConfRuleDao;
import com.house.home.entity.basic.ItemAppConfRule;
import com.house.home.entity.basic.ItemAppConfRuleDetail;
import com.house.home.service.basic.ItemAppConfRuleService;

@Service
public class ItemAppConfRuleServiceImpl extends BaseServiceImpl implements ItemAppConfRuleService{
	@Autowired 
	private ItemAppConfRuleDao itemAppConfRuleDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemAppConfRule itemAppConfRule,UserContext uc) {
		// TODO Auto-generated method stub
		return itemAppConfRuleDao.findPageBySql(page, itemAppConfRule,uc);
	}

	@Override
	public Result doItemAppConfRuleForProc(ItemAppConfRule itemAppConfRule) {
		// TODO Auto-generated method stub
		return itemAppConfRuleDao.doItemAppConfRuleForProc(itemAppConfRule);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page,
			ItemAppConfRuleDetail itemAppConfRuleDetail) {
		// TODO Auto-generated method stub
		return itemAppConfRuleDao.findDetailPageBySql(page, itemAppConfRuleDetail);
	}
}
