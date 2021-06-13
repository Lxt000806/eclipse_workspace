package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.cmpActivityDao;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.basic.cmpActivityGift;
import com.house.home.service.basic.cmpActivityService;

@SuppressWarnings("serial")
@Service
public class cmpActivityServiceImpl extends BaseServiceImpl implements cmpActivityService {

	@Autowired
	private cmpActivityDao cmpactivityDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, cmpActivity cmpactivity) {
		return cmpactivityDao.findPageBySql(page, cmpactivity);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, cmpActivityGift cmpactivitygift) {
		return cmpactivityDao.findPageBySqlDetail(page, cmpactivitygift);
	}

	@Override
	public Result docmpActivitySave(cmpActivity cmpactivity) {
		return cmpactivityDao.docmpActivitySave(cmpactivity);
	}

	@Override
	public cmpActivity getByDescr(String descr) {
		return  cmpactivityDao.getByDescr(descr);
	}


	@Override
	public cmpActivity getByDescrUpdate(String descr,String descr1) {
		return cmpactivityDao.getByDescrUpdate(descr,descr1);
	}

	@Override
	public Page<Map<String, Object>> findSupplierPageBySql(
			Page<Map<String, Object>> page, cmpActivityGift cmpactivitygift) {
		// TODO Auto-generated method stub
		return cmpactivityDao.findSupplierPageBySql(page, cmpactivitygift);
	}

	@Override
	public void doSaveSuppl(String no, String code, String type, String czybm) {
		// TODO Auto-generated method stub
		cmpactivityDao.doSaveSuppl(no,code,type,czybm);
	}
	
	@Override
	public boolean existSuppl(String no, String code) {
		// TODO Auto-generated method stub
		return cmpactivityDao.existSuppl(no, code);
	}

	@Override
	public void doDelSuppl(Integer pk) {
		// TODO Auto-generated method stub
		cmpactivityDao.doDelSuppl(pk);
	}
	
	
	
	
	
	

}
 
