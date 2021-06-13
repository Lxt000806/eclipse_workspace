package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustOrder;

public interface CustOrderService extends BaseService {
	
	public boolean existsCustOrder(String phone);
	
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustOrder custOrder);
}
