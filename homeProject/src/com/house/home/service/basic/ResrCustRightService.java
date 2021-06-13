package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ResrCustRight;

public interface ResrCustRightService extends BaseService {
	/**
	 * ResrCustService分页信息
	 * @param page
	 * @param resrCustRight
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ResrCustRight resrCustRight);
	/**
	 * 通过pk获取ResrCustService记录
	 * @param pk
	 * @return
	 */
	public Map<String,Object> getByPk(Integer pk);
	/**
	 * 通过项目编号和部门编号查询记录
	 * @param builderCode
	 * @param department2
	 * @return
	 */
	public boolean getByBuildandDept(String builderCode,String department2);
}
