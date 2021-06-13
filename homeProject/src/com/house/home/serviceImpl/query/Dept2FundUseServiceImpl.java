package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.Dept2FundUseDao;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.query.Dept2FundUseService;

@Service
@SuppressWarnings("serial")
public class Dept2FundUseServiceImpl extends BaseServiceImpl implements
        Dept2FundUseService {
	@Autowired 
	private  Dept2FundUseDao dept2FundUseDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Purchase purchase) {
		// TODO Auto-generated method stub
		return dept2FundUseDao.findPageBySql_pdept2FundUse(page,purchase);
	}
	@Override
	public Page<Map<String, Object>> findPrepayFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		return dept2FundUseDao.findPrepayFeePageBySql(page,purchase);
	}
	@Override
	public Page<Map<String, Object>> findLaborFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		// TODO Auto-generated method stub
		return dept2FundUseDao.findLaborFeePageBySql(page,purchase);
	}
	@Override
	public Page<Map<String, Object>> findOtherFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		// TODO Auto-generated method stub
		return dept2FundUseDao.findOtherFeePageBySql(page,purchase);
	}
	@Override
	public Page<Map<String, Object>> findPreAmountPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		// TODO Auto-generated method stub
		return dept2FundUseDao.findPreAmountPageBySql(page,purchase);
	}
	@Override
	public Page<Map<String, Object>> findPurArrFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		// TODO Auto-generated method stub
		return dept2FundUseDao.findPurArrFeePageBySql(page,purchase);
	}
	
	
}
