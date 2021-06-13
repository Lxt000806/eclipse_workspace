package com.house.home.service.salary;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.InjuryInsurParam;

public interface InjuryInsurParamService extends BaseService {

	/**InjuryInsurParam分页信息
	 * @param page
	 * @param injuryInsurParam
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InjuryInsurParam injuryInsurParam);
	
}
