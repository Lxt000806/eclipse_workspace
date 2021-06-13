package com.house.home.serviceImpl.commi;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CustCommiProvideAnalysisDao;
import com.house.home.entity.commi.CommiCustStakeholder;
import com.house.home.service.commi.CustCommiProvideAnalysisService;

@SuppressWarnings("serial")
@Service
public class CustCommiProvideAnalysisServiceImpl extends BaseServiceImpl implements CustCommiProvideAnalysisService {

	@Autowired
	private CustCommiProvideAnalysisDao custCommiProvideAnalysisDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder){
		return custCommiProvideAnalysisDao.findPageBySql(page, commiCustStakeholder);
	}

}
