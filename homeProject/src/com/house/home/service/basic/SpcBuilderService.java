package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.design.ResrCust;


public interface SpcBuilderService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SpcBuilder spcBuilder);
	
	public Page<Map<String,Object>> findDelivPageBySql(Page<Map<String,Object>> page, String code,String builderNums);

	public Result doSave(SpcBuilder spcBuilder);

	public Result doUpdate(SpcBuilder spcBuilder);

	public SpcBuilder getByDescr(String descr);
}
