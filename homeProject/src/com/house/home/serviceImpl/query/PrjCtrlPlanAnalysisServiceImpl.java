package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjCtrlPlanAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjCtrlPlanAnalysisService;

@SuppressWarnings("serial")
@Service
public class PrjCtrlPlanAnalysisServiceImpl extends BaseServiceImpl implements PrjCtrlPlanAnalysisService {

	@Autowired
	private PrjCtrlPlanAnalysisDao prjCtrlPlanAnalysisDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return prjCtrlPlanAnalysisDao.findPageBySql(page, customer);
	}

}
