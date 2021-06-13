package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.CustStakeholderHisDao;
import com.house.home.entity.design.CustStakeholderHis;
import com.house.home.service.design.CustStakeholderHisService;

@SuppressWarnings("serial")
@Service
public class CustStakeholderHisServiceImpl extends BaseServiceImpl implements CustStakeholderHisService {

	@Autowired
	private CustStakeholderHisDao custStakeholderHisDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustStakeholderHis custStakeholderHis){
		return custStakeholderHisDao.findPageBySql(page, custStakeholderHis);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page,
			CustStakeholderHis custStakeholderHis) {
		return custStakeholderHisDao.findPageBySql_khxx(page,custStakeholderHis);
	}

}
