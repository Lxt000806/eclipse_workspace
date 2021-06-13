package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustContractTempMapper;

public interface CustContractTempMapperService extends BaseService {

	/**CustContractTempMapper分页信息
	 * @param page
	 * @param custContractTempMapper
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContractTempMapper custContractTempMapper);
	
}
