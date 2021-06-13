package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MainCommiRule;
import com.house.home.entity.basic.UpgWithhold;

public interface MainCommiRuleService extends BaseService {

	/**MainCommiRule分页信息
	 * @param page
	 * @param mainCommiRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiRule mainCommiRule);
	
	public Page<Map<String,Object>> findDetailByNo(Page<Map<String,Object>> page, MainCommiRule mainCommiRule);
	
	public MainCommiRule getByNo(String no);
	
	public Result doSave(MainCommiRule mainCommiRule);
	
}
