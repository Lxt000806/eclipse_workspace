package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseAlgorithm;

public interface BaseAlgorithmService extends BaseService{

	/**
	 * @Description: TODO 基础项目算法管理——分页查询
	 * @author	created by zb
	 * @date	2018-8-28--下午12:15:29
	 * @param page
	 * @param baseAlgorithm
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
			BaseAlgorithm baseAlgorithm);
	
	/**
	 * @Description: TODO 走存储过程
	 * @author	created by zb
	 * @date	2018-8-28--下午6:03:02
	 * @param baseAlgorithm
	 * @return
	 */
	public Result doSave(BaseAlgorithm baseAlgorithm);

	/**
	 * @Description: TODO 根据基础项目算法编码查找算法适用类型
	 * @author	created by zb
	 * @date	2018-8-31--下午4:08:34
	 * @param page
	 * @param baseAlgorithm
	 * @return
	 */
	public Page<Map<String, Object>> findPrjTypePageBySql(Page<Map<String, Object>> page,
			BaseAlgorithm baseAlgorithm);
	
	public String getBaseAlgorithmByDescr(String descr);

	/**
	 * @Description: TODO 判断是否存在descr
	 * @author	created by zb
	 * @date	2018-9-4--上午9:09:23
	 * @param baseAlgorithm
	 * @return
	 */
	public boolean hasDescr(BaseAlgorithm baseAlgorithm);

}
