package com.house.home.service.design;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustLog;

public interface ResrCustLogService extends BaseService{

	public Page<Map<String,Object>> findResrLogPageBySql(Page<Map<String,Object>> page, ResrCustLog resrCustLog);

}
