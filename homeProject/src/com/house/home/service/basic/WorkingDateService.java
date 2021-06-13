package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkingDate;

public interface WorkingDateService extends BaseService {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkingDate workingDate);

	/**
	 * 修改保存
	 * @author	created by zb
	 * @param workingDate
	 */
	public void doUpdate(WorkingDate workingDate);
	/**
	 * 日期类型初始化
	 * @author	created by zb
	 * @date	2020-3-3--上午11:56:33
	 * @param workingDate
	 */
	public void doDateInit(WorkingDate workingDate);
	/**
	 * 日期类型修改
	 * @author	created by zb
	 * @date	2020-3-3--下午4:43:57
	 * @param workingDate
	 */
	public void doUpdateHoliType(WorkingDate workingDate);
}
