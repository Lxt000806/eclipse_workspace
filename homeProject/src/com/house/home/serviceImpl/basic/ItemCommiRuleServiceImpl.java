package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemCommiRuleDao;
import com.house.home.entity.basic.ItemCommiRule;
import com.house.home.service.basic.ItemCommiRuleService;

@SuppressWarnings("serial")
@Service
public class ItemCommiRuleServiceImpl extends BaseServiceImpl implements ItemCommiRuleService {

	@Autowired
	private ItemCommiRuleDao itemCommiRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiRule itemCommiRule){
		return itemCommiRuleDao.findPageBySql(page, itemCommiRule);
	}

}
