package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Activity;

public interface ActivityService extends BaseService {

	/**Activity分页信息
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Activity activity);
	/**
	 * 根据名称查询活动
	 * @param page
	 * @param actName
	 * @return
	 */
	public Page<Map<String,Object>> findActByName(Page<Map<String,Object>> page, String actName);
	
	public String getCurrActivity();
	/**
	 * 检查是否有效
	 * @author	created by zb
	 * @date	2019-8-26--下午4:57:59
	 * @param activity
	 * @return
	 */
	public Boolean checkActivity(Activity activity);
}
