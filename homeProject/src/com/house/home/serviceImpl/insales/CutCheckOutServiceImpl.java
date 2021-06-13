package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.CutCheckOutDao;
import com.house.home.entity.insales.CutCheckOut;
import com.house.home.service.insales.CutCheckOutService;

@SuppressWarnings("serial")
@Service
public class CutCheckOutServiceImpl extends BaseServiceImpl implements CutCheckOutService {

	@Autowired
	private CutCheckOutDao cutCheckOutDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CutCheckOut cutCheckOut){
		return cutCheckOutDao.findPageBySql(page, cutCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goAddDetailJqGrid(
			Page<Map<String, Object>> page, CutCheckOut cutCheckOut) {
		return cutCheckOutDao.goAddDetailJqGrid(page, cutCheckOut);
	}

	@Override
	public List<Map<String, Object>> getCutTypeBySize(CutCheckOut cutCheckOut) {
		return cutCheckOutDao.getCutTypeBySize(cutCheckOut);
	}

	@Override
	public Result doSaveProc(CutCheckOut cutCheckOut) {
		return cutCheckOutDao.doSaveProc(cutCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goDetailJqGrid(
			Page<Map<String, Object>> page, CutCheckOut cutCheckOut) {
		return cutCheckOutDao.goDetailJqGrid(page, cutCheckOut);
	}

	@Override
	public Result doCheckIn(CutCheckOut cutCheckOut) {
		return cutCheckOutDao.doCheckIn(cutCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goJqGridCheckIn(
			Page<Map<String, Object>> page, CutCheckOut cutCheckOut) {
		return cutCheckOutDao.goJqGridCheckIn(page, cutCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goJqGridCheckInDtl(
			Page<Map<String, Object>> page, CutCheckOut cutCheckOut) {
		return cutCheckOutDao.goJqGridCheckInDtl(page, cutCheckOut);
	}

}
