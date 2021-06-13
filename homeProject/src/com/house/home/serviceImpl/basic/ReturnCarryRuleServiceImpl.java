package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ReturnCarryRuleDao;
import com.house.home.entity.basic.ReturnCarryRule;
import com.house.home.service.basic.ReturnCarryRuleService;

@SuppressWarnings("serial")
@Service
public class ReturnCarryRuleServiceImpl extends BaseServiceImpl implements ReturnCarryRuleService {

	@Autowired
	private ReturnCarryRuleDao returnCarryRuleDao;


	@Override
	public Result deleteForProc(ReturnCarryRule returnCarryRule) {
		// TODO Auto-generated method stub
		return returnCarryRuleDao.doReturnCarryRuleReturnCheckOut(returnCarryRule);
	}


	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ReturnCarryRule returnCarryRule) {
		// TODO Auto-generated method stub
		return returnCarryRuleDao.findPageBySql(page, returnCarryRule);
	}
	@Override
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, String no) {
		// TODO Auto-generated method stub
		return returnCarryRuleDao.findItemPageBySql(page, no);
	}
	@Override
	public ReturnCarryRule getByNo(String No,String No1) {
		// TODO Auto-generated method stub
		return returnCarryRuleDao.getByNo(No,No1);
	}

	@Override
	public Result doReturnCarryRuleSave(ReturnCarryRule returnCarryRule) {
		// TODO Auto-generated method stub
		return returnCarryRuleDao.doReturnCarryRuleReturnCheckOut(returnCarryRule);
	}
	
}
 
