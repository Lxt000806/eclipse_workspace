package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjCtrlCheckAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjCtrlCheckAnalysisService;

@SuppressWarnings("serial")
@Service
public class PrjCtrlCheckAnalysisServiceImpl extends BaseServiceImpl implements PrjCtrlCheckAnalysisService {

	@Autowired
	private PrjCtrlCheckAnalysisDao prjCtrlCheckAnalysisDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return prjCtrlCheckAnalysisDao.findPageBySql(page, customer);
	}

}
