package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.IntProd;

public interface IntProdService extends BaseService {

	/**IntProd分页信息
	 * @param page
	 * @param intProd
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntProd intProd);
	/**
	 * 判断对应装修区域下是否已经存在同名的集成成品
	 * @param intProd
	 * @return
	 */
	public boolean isExisted(IntProd intProd);
}
