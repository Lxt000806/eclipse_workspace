package com.house.home.service.workflow;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActGroup;

public interface ActGroupService extends BaseService {

	/**ActGroup分页信息
	 * @param page
	 * @param actGroup
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActGroup actGroup);

	/**
	 * 查询子表
	 * @author	created by zb
	 * @date	2019-3-30--上午10:12:32
	 * @param page
	 * @param actGroup
	 * @return 
	 */
	public Page<Map<String,Object>> findUserPageBySql(Page<Map<String, Object>> page,
			ActGroup actGroup);
	
	public Page<Map<String,Object>> getRoleAuthorityJqGrid(Page<Map<String, Object>> page,
			ActGroup actGroup);

	/**
	 * 保存操作员信息过程
	 * @author	created by zb
	 * @date	2019-3-30--下午4:06:43
	 * @param actGroup
	 * @return
	 */
	public Result doSave(ActGroup actGroup);
	
	public boolean existsAuth(ActGroup actGroup);
	
	
}
