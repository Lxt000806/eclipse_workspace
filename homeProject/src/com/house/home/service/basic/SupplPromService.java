package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SupplProm;

public interface SupplPromService extends BaseService{

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, SupplProm supplProm);
	/**
	 * 主页列表查询
	 * @author	created by zb
	 * @date	2019-7-11--下午4:02:12
	 * @param page
	 * @param supplProm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page,
			SupplProm supplProm);
	/**
	 * 查询材料信息
	 * @author	created by zb
	 * @date	2019-7-13--下午4:41:14
	 * @param page
	 * @param supplProm
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(Page<Map<String, Object>> page,
			SupplProm supplProm);
	/**
	 * 存储过程
	 * @author	created by zb
	 * @date	2019-7-15--下午4:05:33
	 * @param supplProm
	 * @return
	 */
	public Result doSave(SupplProm supplProm);

}
