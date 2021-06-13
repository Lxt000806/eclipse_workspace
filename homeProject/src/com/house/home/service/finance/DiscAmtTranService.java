package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.DiscAmtTran;

public interface DiscAmtTranService extends BaseService{
	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, DiscAmtTran discAmtTran);

	public void doSave(DiscAmtTran discAmtTran);
	
	public Page<Map<String, Object>> findDiscAmtTranPageBySql(Page<Map<String, Object>> page,
			DiscAmtTran discAmtTran);

	public double getLpExpense(String custCode);
	
}
