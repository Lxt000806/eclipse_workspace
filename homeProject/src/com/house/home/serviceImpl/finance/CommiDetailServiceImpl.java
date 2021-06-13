package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.CommiDetailDao;
import com.house.home.entity.finance.CommiDetail;
import com.house.home.service.finance.CommiDetailService;

@SuppressWarnings("serial")
@Service
public class CommiDetailServiceImpl extends BaseServiceImpl implements CommiDetailService {

	@Autowired
	private CommiDetailDao commiDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiDetail commiDetail){
		return commiDetailDao.findPageBySql(page, commiDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CommiDetail commiDetail) {
		return commiDetailDao.findPageBySql_khxx(page,commiDetail);
	}

}
