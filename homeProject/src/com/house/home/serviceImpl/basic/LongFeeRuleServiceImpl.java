package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.LongFeeRuleDao;
import com.house.home.entity.basic.LongFeeRule;
import com.house.home.service.basic.LongFeeRuleService;

@SuppressWarnings("serial")
@Service
public class LongFeeRuleServiceImpl extends BaseServiceImpl implements LongFeeRuleService {

	@Autowired
	private LongFeeRuleDao longFeeRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, LongFeeRule longFeeRule){
		return longFeeRuleDao.findPageBySql(page, longFeeRule);
	}

	@Override
	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, LongFeeRule longFeeRule) {
		return longFeeRuleDao.goDetailJqGrid(page, longFeeRule);
	}
	
	@Override
	public Page<Map<String, Object>> goItemDetailJqGrid(Page<Map<String, Object>> page,
	        LongFeeRule longFeeRule) {

	    return longFeeRuleDao.goItemDetailJqGrid(page, longFeeRule);
	}

	@Override
	public Result doSaveProc(LongFeeRule longFeeRule) {
		return longFeeRuleDao.doSaveProc(longFeeRule);
	}

}
