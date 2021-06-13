package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfDate;

public interface CustItemConfDateService extends BaseService {

	/**CustItemConfDate分页信息
	 * @param page
	 * @param custItemConfDate
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfDate custItemConfDate);
	public CustItemConfDate getCustItemConfDate(String custCode,String itemTimeCode);
}
