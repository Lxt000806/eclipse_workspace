package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustType;

public interface CustTypeService extends BaseService {

	/**CustType分页信息
	 * @param page
	 * @param custType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustType custType);
	public List<CustType> findByDefaultStatic();
	public List<CustType> findByIsAddAllInfo();
	/**
	 * 检查编号是否已存在
	 * @param custType
	 * @return
	 */
	public List<Map<String, Object>> checkExist(CustType custType);
	
	public boolean isFdlCust(String custType);
	
	public List<CustType> findByAllCustType();
	/**
	 * 根据条件获得code
	 * @author	created by zb
	 * @date	2019-4-20--下午3:12:57
	 * @param custType
	 * @return
	 */
	public String getNeedCode(CustType custType);
	
	public String getSelectCustType(CustType custType);
	
}
