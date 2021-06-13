package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.ManualPerfCustDao;
import com.house.home.entity.finance.ManualPerfCust;
import com.house.home.service.finance.ManualPerfCustService;

@SuppressWarnings("serial")
@Service
public class ManualPerfCustServiceImpl extends BaseServiceImpl implements ManualPerfCustService {

	@Autowired
	private ManualPerfCustDao manualPerfCustDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ManualPerfCust manualPerfCust){
		return manualPerfCustDao.findPageBySql(page, manualPerfCust);
	}

}
