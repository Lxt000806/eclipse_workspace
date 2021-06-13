package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CzyGnqxDao;
import com.house.home.entity.basic.CzyGnqx;
import com.house.home.service.basic.CzyGnqxService;

@SuppressWarnings("serial")
@Service
public class CzyGnqxServiceImpl extends BaseServiceImpl implements CzyGnqxService {

	@Autowired
	private CzyGnqxDao czyGnqxDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyGnqx czyGnqx){
		return czyGnqxDao.findPageBySql(page, czyGnqx);
	}

	@Override
	public CzyGnqx getCzyGnqx(String mkdm, String gnmc, String czybh) {
		return czyGnqxDao.getCzyGnqx(mkdm, gnmc, czybh);
	}

}
