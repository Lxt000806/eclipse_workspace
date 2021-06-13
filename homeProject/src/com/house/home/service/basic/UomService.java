/**
 * 
 */
package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.Uom;

/**
 * @author lenovo-l821
 *
 */
public interface UomService extends BaseService {
	/**
	 * 查询列表
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Uom uom);
	/**
	 * 验证编号是否重复
	 */
	public boolean valideUom(String id);

}
