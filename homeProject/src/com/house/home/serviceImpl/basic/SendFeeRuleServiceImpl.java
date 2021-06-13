package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SendFeeRuleDao;
import com.house.home.entity.basic.SendFeeRule;
import com.house.home.service.basic.SendFeeRuleService;

@SuppressWarnings("serial")
@Service
public class SendFeeRuleServiceImpl extends BaseServiceImpl implements SendFeeRuleService {

	@Autowired
	private SendFeeRuleDao sendFeeRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendFeeRule sendFeeRule){
		return sendFeeRuleDao.findPageBySql(page, sendFeeRule);
	}

	@Override
	public Page<Map<String, Object>> findItemPageBySql(Page<Map<String, Object>> page, SendFeeRule sendFeeRule) {
		return sendFeeRuleDao.findItemPageBySql(page, sendFeeRule);
	}

	@Override
	public Result doSaveProc(SendFeeRule sendFeeRule) {
		return sendFeeRuleDao.doSaveProc(sendFeeRule);
	}

}
