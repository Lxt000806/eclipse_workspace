package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PurchArrDao;
import com.house.home.entity.insales.PurchArr;
import com.house.home.service.insales.PurchArrService;

@SuppressWarnings("serial")
@Service
public class PurchArrServiceImpl extends BaseServiceImpl implements PurchArrService {

	@Autowired
	private PurchArrDao purchArrDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchArr purchArr){
		return purchArrDao.findPageBySql(page, purchArr);
	}
	
	public Page<Map<String,Object>> findPurchArrPageBySql(Page<Map<String,Object>> page, PurchArr purchArr){
		return purchArrDao.findPurchArrPageBySql(page, purchArr);
	}

	@Override
	public Page<Map<String, Object>> findPageByPuno(
			Page<Map<String, Object>> page, String puno) {
		return purchArrDao.findPageByPuno(page,puno);
	}

}
