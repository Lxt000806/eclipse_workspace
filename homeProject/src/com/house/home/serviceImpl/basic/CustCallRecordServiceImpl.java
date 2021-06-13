package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustCallRecordDao;
import com.house.home.service.basic.CustCallRecordService;

@SuppressWarnings("serial")
@Service
public class CustCallRecordServiceImpl extends BaseServiceImpl implements CustCallRecordService {
	
	@Autowired
	private CustCallRecordDao custCallRecordDao;
	
	@Override
	public Long getLastCustConByCZY(String czybh){
		return this.custCallRecordDao.getLastCustConByCZY(czybh);
	}

	@Override
	public Result doSaveCallList(String callList, String czybh, Long maxConDate){
		return this.custCallRecordDao.doSaveCallList(callList, czybh, maxConDate);
	}
	
	@Override
	public List<Map<String, Object>> getUnUploadCallRecordList(String czybh){
		return this.custCallRecordDao.getUnUploadCallRecordList(czybh);
	}

	@Override
	public Page<Map<String, Object>> getCallListOneMonth(Page<Map<String, Object>> page, String czybh, Date date){
		return this.custCallRecordDao.getCallListOneMonth(page, czybh, date);
	}
}
