package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SetItemQuota;

public interface SetItemQuotaService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SetItemQuota setItemQuota);
	
	public Page<Map<String,Object>> findDetailByNo(Page<Map<String,Object>> page, SetItemQuota setItemQuota);
	
	public Result doSave(SetItemQuota setItemQuota);

	public SetItemQuota getSupplierTimeByNo(String no);

	public Result updateSendTime(String deleteNo);

}
