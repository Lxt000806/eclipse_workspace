package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustTypeBrandProfitPerDao;
import com.house.home.entity.basic.CustTypeBrandProfitPer;
import com.house.home.service.basic.CustTypeBrandProfitPerService;

@SuppressWarnings("serial")
@Service
public class CustTypeBrandProfitPerServiceImpl extends BaseServiceImpl implements CustTypeBrandProfitPerService {

	@Autowired
	private CustTypeBrandProfitPerDao custTypeBrandProfitPerDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustTypeBrandProfitPer custTypeBrandProfitPer){
		return custTypeBrandProfitPerDao.findPageBySql(page, custTypeBrandProfitPer);
	}

}
