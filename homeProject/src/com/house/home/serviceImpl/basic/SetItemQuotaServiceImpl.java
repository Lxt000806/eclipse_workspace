package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.SetItemQuotaDao;
import com.house.home.entity.basic.SetItemQuota;
import com.house.home.service.basic.SetItemQuotaService;

@SuppressWarnings("serial")
@Service 
public class SetItemQuotaServiceImpl extends BaseServiceImpl implements SetItemQuotaService {
	@Autowired
	private  SetItemQuotaDao setItemQuotaDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SetItemQuota setItemQuota) {
		// TODO Auto-generated method stub
		return setItemQuotaDao.findPageBySql(page,setItemQuota);
	}
	
	@Override
	public Page<Map<String, Object>> findDetailByNo(
			Page<Map<String, Object>> page, SetItemQuota setItemQuota) {
		// TODO Auto-generated method stub
		return setItemQuotaDao.findDetailByNo(page,setItemQuota);
	}

	@Override
	public Result doSave(SetItemQuota setItemQuota) {
		// TODO Auto-generated method stub
		return setItemQuotaDao.doSave(setItemQuota);
	}

	public SetItemQuota getSupplierTimeByNo(String no) {
		return setItemQuotaDao.getSupplierTimeByNo(no);
	}

	@Override
	public Result updateSendTime(String deleteNo) {
		// TODO Auto-generated method stub
		return setItemQuotaDao.updateSendTime(deleteNo);
	}


}
