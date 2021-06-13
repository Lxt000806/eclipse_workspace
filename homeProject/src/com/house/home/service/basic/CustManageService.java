package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Advert;
import com.house.home.entity.basic.CustAccount;

public interface CustManageService extends BaseService {
	
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustAccount custAccount);

	public List<Map<String, Object>> getCustomers(String phone);
	
	public Map<String, Object> doSave(CustAccount custAccount);
	
	public Page<Map<String, Object>> goJqGridCustCode(Page<Map<String, Object>> page, CustAccount custAccount);
	
	public Map<String, Object> doUpdate(CustAccount custAccount);
}
