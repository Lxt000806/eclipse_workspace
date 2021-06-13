package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.GzsgqkfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.project.GzsgqkfxService;

@SuppressWarnings("serial")
@Service
public class GzsgqkfxServiceImpl extends BaseServiceImpl implements GzsgqkfxService {
	
	@Autowired
	private GzsgqkfxDao gzsgqkfxDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer,String isSign,String orderBy,String direction) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findPageBySql(page,customer,isSign,orderBy,direction);
	}

	@Override
	public Page<Map<String, Object>> findHasArrPageBySql(
			Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findHasArrPageBySql(page,workType12,customer,isSign);
	}

	@Override
	public Page<Map<String, Object>> findBuilderRepPageBySql(
			Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findBuilderRepPageBySql(page,workType12,customer,isSign);
	}

	@Override
	public Page<Map<String, Object>> findCompletePageBySql(
			Page<Map<String, Object>> page,String workType12,Customer customer,String isSign,String level,String onTimeCmp) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findCompletePageBySql(page,workType12,customer,isSign,level,onTimeCmp);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findDetailPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findOndoPageBySql(
			Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findOndoPageBySql(page,workType12,customer,isSign);
	}
	
	@Override
	public Page<Map<String, Object>> findHasConfPageBySql(
			Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.findHasConfPageBySql(page,workType12,customer,isSign);
	}

	@Override
	public Map<String, Object> findPrjRegionDescr(String Code) {
		return gzsgqkfxDao.findPrjRegionDescr(Code);
	}

	@Override
	public Page<Map<String, Object>> getWorkSignPicBySql(
			Page<Map<String, Object>> page, String no) {
		// TODO Auto-generated method stub
		return gzsgqkfxDao.getWorkSignPicBySql(page, no);
	}
	
	
	
	
}
