package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.DesignFeeSd;

public interface DesignFeeSdService extends BaseService{
	
	/**
	 * 设计费标准设置分页查询
	 * @author	created by zb
	 * @date	2018-12-21--下午3:21:32
	 * @param page
	 * @param designFeeSd
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, DesignFeeSd designFeeSd);
	
	/**
	 * 设计费标准根据设计费获取
	 * @param page
	 * @param designFeeSd
	 * @return
	 */
	public DesignFeeSd getDesignFeeSdByDesignFee(Double DesignFee);
	/**
	 * 根据PK获取设计费标准
	 * @author	created by zb
	 * @date	2020-4-1--上午11:35:11
	 */
	public DesignFeeSd getDesignFByPositCustT(DesignFeeSd designFeeSd);

}
