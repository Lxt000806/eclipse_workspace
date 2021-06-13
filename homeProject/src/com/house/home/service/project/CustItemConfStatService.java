package com.house.home.service.project;

import java.util.Date;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;

public interface CustItemConfStatService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Date dateFrom, Date dateTo ,
			Date sdDateFrom ,Date sdDateTo ,Date nsDateFrom ,Date nsDateTo,String mainBusinessMan, String custType);
}
