package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.SoftBusiCommiDao;
import com.house.home.entity.commi.SoftBusiCommi;
import com.house.home.service.commi.SoftBusiCommiService;

@SuppressWarnings("serial")
@Service
public class SoftBusiCommiServiceImpl extends BaseServiceImpl implements SoftBusiCommiService {

	@Autowired
	private SoftBusiCommiDao softBusiCommiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi){
		return softBusiCommiDao.findPageBySql(page, softBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goBaseJqGrid(Page<Map<String, Object>> page, SoftBusiCommi softBusiCommi) {
		return softBusiCommiDao.goBaseJqGrid(page, softBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goSumJqGrid(Page<Map<String, Object>> page, SoftBusiCommi softBusiCommi) {
		return softBusiCommiDao.goSumJqGrid(page, softBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goHisJqGrid(Page<Map<String, Object>> page, SoftBusiCommi softBusiCommi) {
		return softBusiCommiDao.goHisJqGrid(page, softBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goIndJqGrid(Page<Map<String, Object>> page, SoftBusiCommi softBusiCommi) {
		return softBusiCommiDao.goIndJqGrid(page, softBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goStakeholderJqGrid(Page<Map<String, Object>> page, SoftBusiCommi softBusiCommi) {
		return softBusiCommiDao.goStakeholderJqGrid(page, softBusiCommi);
	}

}
