package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.DrawFeeStdRuleDao;
import com.house.home.entity.commi.DrawFeeStdRule;
import com.house.home.service.commi.DrawFeeStdRuleService;

@SuppressWarnings("serial")
@Service
public class DrawFeeStdRuleServiceImpl extends BaseServiceImpl implements DrawFeeStdRuleService {

	@Autowired
	private DrawFeeStdRuleDao drawFeeStdRuleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DrawFeeStdRule drawFeeStdRule){
		return drawFeeStdRuleDao.findPageBySql(page, drawFeeStdRule);
	}

	@Override
	public Result doSaveProc(DrawFeeStdRule drawFeeStdRule) {
		return drawFeeStdRuleDao.doSaveProc(drawFeeStdRule);
	}

	@Override
	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, DrawFeeStdRule drawFeeStdRule) {
		return drawFeeStdRuleDao.goDetailJqGrid(page, drawFeeStdRule);
	}

}
