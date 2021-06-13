package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.AppItemTypeBatch;

/**
 * @Description: TODO 下单材料service
 * @author created by zb
 * @date   2018-7-30--上午11:36:23
 */
public interface AppItemTypeBatchService extends BaseService{

	/**
	 * @Description: TODO 分页查询
	 * @author	created by zb
	 * @date	2018-7-30--上午11:56:43
	 * @param page
	 * @param appItemTypeBatch
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page,
			AppItemTypeBatch appItemTypeBatch);

	/**
	 * @Description: TODO 走存储过程
	 * @author	created by zb
	 * @date	2018-7-31--下午5:15:48
	 * @param appItemTypeBatch
	 * @return
	 */
	public Result doSave(AppItemTypeBatch appItemTypeBatch);

	/**
	 * @Description: TODO 明细查询
	 * @author	created by zb
	 * @date	2018-8-1--下午1:43:14
	 * @param page
	 * @param appItemTypeBatch
	 * @return
	 */
	public Page<Map<String,Object>> findDetailByCode(Page<Map<String, Object>> page,
			AppItemTypeBatch appItemTypeBatch);

}
