package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.MainCommiPercIndeSaleDao;
import com.house.home.entity.basic.MainCommiPercIndeSale;
import com.house.home.service.basic.MainCommiPercIndeSaleService;

@SuppressWarnings("serial")
@Service
public class MainCommiPercIndeSaleServiceImpl extends BaseServiceImpl implements MainCommiPercIndeSaleService {

	@Autowired
	private MainCommiPercIndeSaleDao mainCommiPercIndeSaleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiPercIndeSale mainCommiPercIndeSale){
		return mainCommiPercIndeSaleDao.findPageBySql(page, mainCommiPercIndeSale);
	}

	@Override
	public boolean checkExistMainCommiPercIndeSale(MainCommiPercIndeSale mainCommiPercIndeSale) {
		return mainCommiPercIndeSaleDao.checkExistMainCommiPercIndeSale(mainCommiPercIndeSale);
	}

}
