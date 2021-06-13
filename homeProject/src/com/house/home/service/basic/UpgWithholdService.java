package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.UpgWithhold;

public interface UpgWithholdService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, UpgWithhold upgWithhold);
	
	public Page<Map<String,Object>> findDetailByCode(Page<Map<String,Object>> page, UpgWithhold upgWithhold);
	
	public UpgWithhold getByCode(String code);
	
	public Result doSave(UpgWithhold upgWithhold);
}
