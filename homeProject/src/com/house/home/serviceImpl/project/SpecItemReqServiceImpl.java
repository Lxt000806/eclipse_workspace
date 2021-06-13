package com.house.home.serviceImpl.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.project.SpecItemReqDao;
import com.house.home.entity.project.SpecItemReq;
import com.house.home.service.project.SpecItemReqService;

@SuppressWarnings("serial")
@Service 
public class SpecItemReqServiceImpl extends BaseServiceImpl implements SpecItemReqService {
	@Autowired
	private  SpecItemReqDao specItemReqDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SpecItemReq specItemReq) {
		return specItemReqDao.findPageBySql(page, specItemReq);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, String custCode, String iscupboard) {
		return specItemReqDao.findDetailPageBySql(page, custCode, iscupboard);
	}

	@Override
	public Map<String, Object> getIntProd(String custCode, String productName) {
		return specItemReqDao.getIntProd(custCode, productName);
	}

	@Override
	public Map<String, Object> getAppQty(String custCode, String itemCode) {
		return specItemReqDao.getAppQty(custCode, itemCode);
	}

	@Override
	public Result doSave(SpecItemReq specItemReq) {
		return specItemReqDao.doSave(specItemReq);
	}

	@Override
	public Page<Map<String, Object>> goDetailQuery(Page<Map<String, Object>> page, SpecItemReq specItemReq) {
		if("2".equals(specItemReq.getStatistcsMethod())){
			return specItemReqDao.goDetailQuery_itmeType3Total(page, specItemReq);
		}else{
			return specItemReqDao.goDetailQuery(page, specItemReq);
		}
	}

	@Override
	public Map<String, Object> getStakeholderInfo(String custCode) {
		return specItemReqDao.getStakeholderInfo(custCode);
	}

}
