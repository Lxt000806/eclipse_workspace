package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.CommiCalDao;
import com.house.home.entity.finance.CommiCal;
import com.house.home.service.finance.CommiCalService;

@SuppressWarnings("serial")
@Service
public class CommiCalServiceImpl extends BaseServiceImpl implements CommiCalService {

	@Autowired
	private CommiCalDao commiCalDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCal commiCal){
		return commiCalDao.findPageBySql(page, commiCal);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CommiCal commiCal) {
		return commiCalDao.findPageBySql_khxx(page,commiCal);
	}

}
