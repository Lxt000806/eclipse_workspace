package com.house.home.service.basic;

import com.house.framework.commons.orm.BaseService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ConfirmCustomFiled;

public interface ConfirmCustomFiledService extends BaseService{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfirmCustomFiled confirmCustomFiled);
	
	public List<Map<String, Object>> checkDescrExists(String descr, String code);	
}
