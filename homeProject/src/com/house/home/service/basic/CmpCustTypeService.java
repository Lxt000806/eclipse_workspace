package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CmpCustType;

public interface CmpCustTypeService extends BaseService {

	/**CmpCustType分页信息
	 * @param page
	 * @param cmpCustType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CmpCustType cmpCustType);
	/**
	 * 客户类型重复判断
	 * @param cmpCustType
	 * @return
	 */
	public List<Map<String, Object>> checkExist(CmpCustType cmpCustType);
	
	/**
	 * 根据客户编号获取公司logo
	 * @param custCode
	 * @return
	 */
	public String getLogoFile(String custCode);
	
	
}
