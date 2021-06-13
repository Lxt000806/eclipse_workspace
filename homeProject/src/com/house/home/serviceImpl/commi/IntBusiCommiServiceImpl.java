package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.IntBusiCommiDao;
import com.house.home.entity.commi.IntBusiCommi;
import com.house.home.service.commi.IntBusiCommiService;

@SuppressWarnings("serial")
@Service
public class IntBusiCommiServiceImpl extends BaseServiceImpl implements IntBusiCommiService {

	@Autowired
	private IntBusiCommiDao intBusiCommiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntBusiCommi intBusiCommi){
		return intBusiCommiDao.findPageBySql(page, intBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goBaseJqGrid(Page<Map<String, Object>> page, IntBusiCommi intBusiCommi) {
		return intBusiCommiDao.goBaseJqGrid(page, intBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goSumJqGrid(Page<Map<String, Object>> page, IntBusiCommi intBusiCommi) {
		return intBusiCommiDao.goSumJqGrid(page, intBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goHisJqGrid(Page<Map<String, Object>> page, IntBusiCommi intBusiCommi) {
		return intBusiCommiDao.goHisJqGrid(page, intBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goIndJqGrid(Page<Map<String, Object>> page, IntBusiCommi intBusiCommi) {
		return intBusiCommiDao.goIndJqGrid(page, intBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goStakeholderJqGrid(Page<Map<String, Object>> page, IntBusiCommi intBusiCommi) {
		return intBusiCommiDao.goStakeholderJqGrid(page, intBusiCommi);
	}

}
