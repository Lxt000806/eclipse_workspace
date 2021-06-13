package com.house.home.service.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustCon;

public interface CustCallRecordService extends BaseService {

	public Long getLastCustConByCZY(String czybh);
	
	public Result doSaveCallList(String callList, String czybh, Long maxConDate);
	
	public List<Map<String, Object>> getUnUploadCallRecordList(String czybh);
	
	public Page<Map<String, Object>> getCallListOneMonth(Page<Map<String, Object>> page, String czybh, Date date);
}
