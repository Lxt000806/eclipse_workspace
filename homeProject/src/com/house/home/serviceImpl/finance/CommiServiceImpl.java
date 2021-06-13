package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.CommiDao;
import com.house.home.entity.finance.Commi;
import com.house.home.service.finance.CommiService;

@SuppressWarnings("serial")
@Service
public class CommiServiceImpl extends BaseServiceImpl implements CommiService {

	@Autowired
	private CommiDao commiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Commi commi){
		return commiDao.findPageBySql(page, commi);
	}

	@Override
	public void doSaveCountBack(String no) {
		commiDao.doSaveCountBack(no);
	}

	@Override
	public String checkStatus(String no) {
		return commiDao.checkStatus(no);
	}

	@Override
	public void doSaveCount(String no) {
		commiDao.doSaveCount(no);
	}

	@Override
	public String isExistsPeriod(String no, String beginDate) {
		return commiDao.isExistsPeriod(no, beginDate);
	}

	@Override
	public Page<Map<String, Object>> findCustBySql(Page<Map<String, Object>> page, Commi commi) {
		return commiDao.findCustBySql(page, commi);
	}

	@Override
	public Page<Map<String, Object>> findItemBySql(Page<Map<String, Object>> page, Commi commi) {
		return commiDao.findItemBySql(page, commi);
	}

	@Override
	public Map<String, Object> doCount(String no, String lastUpdatedBy, String isRegenCommiPerc) {
		return commiDao.doCount(no, lastUpdatedBy, isRegenCommiPerc);
	}

	@Override
	public Map<String, Object> findCustMap(Page<Map<String, Object>> page,String pk) {
		return commiDao.findCustMap(page,pk);
	}

	@Override
	public Map<String, Object> findItemMap(Page<Map<String, Object>> page,String pk) {
		return commiDao.findItemMap(page,pk);
	}

	@Override
	public Page<Map<String, Object>> findItemReqBySql(Page<Map<String, Object>> page, Commi commi) {
		return commiDao.findItemReqBySql(page, commi);
	}

	@Override
	public void doUpdateBatch(Commi commi) {
		commiDao.doUpdateBatch(commi);
	}
	
}
