package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.MainBusiCommiDao;
import com.house.home.entity.commi.MainBusiCommi;
import com.house.home.service.commi.MainBusiCommiService;

@SuppressWarnings("serial")
@Service
public class MainBusiCommiServiceImpl extends BaseServiceImpl implements MainBusiCommiService {

	@Autowired
	private MainBusiCommiDao mainBusiCommiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi){
		return mainBusiCommiDao.findPageBySql(page, mainBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goBaseJqGrid(Page<Map<String, Object>> page, MainBusiCommi mainBusiCommi) {
		return mainBusiCommiDao.goBaseJqGrid(page, mainBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goSumJqGrid(Page<Map<String, Object>> page, MainBusiCommi mainBusiCommi) {
		return mainBusiCommiDao.goSumJqGrid(page, mainBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goHisJqGrid(Page<Map<String, Object>> page, MainBusiCommi mainBusiCommi) {
		return mainBusiCommiDao.goHisJqGrid(page, mainBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goIndJqGrid(Page<Map<String, Object>> page, MainBusiCommi mainBusiCommi) {
		return mainBusiCommiDao.goIndJqGrid(page, mainBusiCommi);
	}

	@Override
	public Page<Map<String, Object>> goStakeholderJqGrid(Page<Map<String, Object>> page, MainBusiCommi mainBusiCommi) {
		return mainBusiCommiDao.goStakeholderJqGrid(page, mainBusiCommi);
	}

}
