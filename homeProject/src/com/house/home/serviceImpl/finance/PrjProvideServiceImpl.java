package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.PrjProvideDao;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.entity.finance.PrjProvide;
import com.house.home.service.finance.PrjProvideService;

@SuppressWarnings("serial")
@Service
public class PrjProvideServiceImpl extends BaseServiceImpl implements PrjProvideService {

	@Autowired
	private PrjProvideDao prjProvideDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProvide prjProvide) {
		// TODO Auto-generated method stub
		return prjProvideDao.findPageBySql(page, prjProvide);
	}

	@Override
	public Page<Map<String, Object>> goJqGrid_toPrjProCheck(
			Page<Map<String, Object>> page, PrjCheck prjCheck) {
		// TODO Auto-generated method stub
		return prjProvideDao.goJqGrid_toPrjProCheck(page, prjCheck);
	}

	@Override
	public Result doPrjProvideForProc(PrjProvide prjProvide) {
		// TODO Auto-generated method stub
		return prjProvideDao.doPrjProvideForProc(prjProvide);
	}

	@Override
	public Result doPrjProvideCheckForProc(PrjProvide prjProvide){
		// TODO Auto-generated method stub
		return prjProvideDao.doPrjProvideCheckForProc(prjProvide);
	}

	@Override
	public Page<Map<String, Object>> goJqGrid_toPrjProDetail(
			Page<Map<String, Object>> page, PrjCheck prjCheck) {
		// TODO Auto-generated method stub
		return prjProvideDao.goJqGrid_toPrjProDetail(page, prjCheck);
	}	
}
