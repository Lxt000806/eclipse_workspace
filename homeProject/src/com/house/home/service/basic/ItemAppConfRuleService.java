package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemAppConfRule;
import com.house.home.entity.basic.ItemAppConfRuleDetail;

public interface ItemAppConfRuleService extends BaseService{

	public Page<Map<String , Object>> findPageBySql(Page<Map<String, Object>> page,ItemAppConfRule itemAppConfRule,UserContext uc);
	//增加修改存储过程 
	public Result doItemAppConfRuleForProc(ItemAppConfRule itemAppConfRule);
    //明细表查询
	public Page<Map<String , Object>> findDetailPageBySql(Page<Map<String, Object>> page,ItemAppConfRuleDetail itemAppConfRuleDetail);

}
