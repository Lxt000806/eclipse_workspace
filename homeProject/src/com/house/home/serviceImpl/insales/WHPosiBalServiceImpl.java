package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.WHPosiBalDao;
import com.house.home.entity.insales.WHPosiBal;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.insales.WHPosiBalService;

@SuppressWarnings("serial")
@Service
public class WHPosiBalServiceImpl extends BaseServiceImpl implements WHPosiBalService {

	@Autowired
	private WHPosiBalDao wHPosiBalDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHPosiBal wHPosiBal){
		return wHPosiBalDao.findPageBySql(page, wHPosiBal);
	}

	@Override
	public Page<Map<String, Object>> findPageByInnerSql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		return wHPosiBalDao.findPageByInnerSql(page, wareHouse);
	}

	@Override
	public Page<Map<String, Object>> findDataBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		return wHPosiBalDao.findDataBySql(page,wareHouse);
	}

	@Override
	public Result doSave(WareHouse wareHouse) {
		return wHPosiBalDao.doSave(wareHouse);
	}

	@Override
	public Page<Map<String, Object>> findMovePageBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		return wHPosiBalDao.findMovePageBySql(page,wareHouse);
	}

	@Override
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		return wHPosiBalDao.findItemPageBySql(page,wareHouse);
	}

	@Override
	public Boolean hasItem(String code, String itCode) {
		return wHPosiBalDao.hasItem(code, itCode);
	}

	@Override
	public boolean checkPk(String code, Integer pk) {
		return wHPosiBalDao.checkPk(code,pk);
	}

}
