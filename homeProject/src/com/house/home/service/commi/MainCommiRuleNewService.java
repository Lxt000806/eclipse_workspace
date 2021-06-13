package com.house.home.service.commi;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.MainCommiRuleNew;

public interface MainCommiRuleNewService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiRuleNew mainCommiRuleNew);

	public Page<Map<String,Object>> findItemDetailPageBySql(Page<Map<String,Object>> page, MainCommiRuleNew mainCommiRuleNew);

	public Result doSave(MainCommiRuleNew mainCommiRuleNew);
}
