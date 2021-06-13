package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.TaxPayeeESignDao;
import com.house.home.entity.basic.TaxPayeeESign;
import com.house.home.service.basic.TaxPayeeESignService;

@SuppressWarnings("serial")
@Service
public class TaxPayeeESignServiceImpl extends BaseServiceImpl implements TaxPayeeESignService {

	@Autowired
	private TaxPayeeESignDao taxPayeeESignDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, TaxPayeeESign taxPayeeESign){
		return taxPayeeESignDao.findPageBySql(page, taxPayeeESign);
	}

	@Override
	public List<Map<String, Object>> isEnableSeal(String sealId) {
		return taxPayeeESignDao.isEnableSeal(sealId);
	}

	@Override
	public List<Map<String, Object>> isEnableOrg(String orgId) {
		return taxPayeeESignDao.isEnableOrg(orgId);
	}

}
