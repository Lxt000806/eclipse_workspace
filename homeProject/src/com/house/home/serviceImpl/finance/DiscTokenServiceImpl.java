package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.DiscTokenDao;
import com.house.home.entity.finance.DiscToken;
import com.house.home.service.finance.DiscTokenService;

@SuppressWarnings("serial")
@Service
public class DiscTokenServiceImpl extends BaseServiceImpl implements DiscTokenService {

	@Autowired
	private DiscTokenDao discTokenDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DiscToken discToken){
		return discTokenDao.findPageBySql(page, discToken);
	}


	@Override
	public Page<Map<String, Object>> findHasSelectPageBySql(
			Page<Map<String, Object>> page, DiscToken discToken) {
		return discTokenDao.findHasSelectPageBySql(page, discToken);
	}

	@Override
	public double getDiscTokenAmountTotal(DiscToken discToken) {
		return discTokenDao.getDiscTokenAmountTotal(discToken);
	}

	@Override
	public String getDiscTokenNo(DiscToken discToken) {
		return discTokenDao.getDiscTokenNo(discToken);
	}

}
