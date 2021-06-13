package com.house.home.serviceImpl.salary;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.InjuryInsurParamDao;
import com.house.home.entity.salary.InjuryInsurParam;
import com.house.home.service.salary.InjuryInsurParamService;

@SuppressWarnings("serial")
@Service
public class InjuryInsurParamServiceImpl extends BaseServiceImpl implements InjuryInsurParamService {

	@Autowired
	private InjuryInsurParamDao injuryInsurParamDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InjuryInsurParam injuryInsurParam){
		return injuryInsurParamDao.findPageBySql(page, injuryInsurParam);
	}

}
