package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BasePlanTempDetail;

public interface BasePlanTempDetailService extends BaseService {

	/**BasePlanTempDetail分页信息
	 * @param page
	 * @param basePlanTempDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BasePlanTempDetail basePlanTempDetail);
	/**
	 * 根据no查找基础算量明细
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findDetailByNo(BasePlanTempDetail basePlanTempDetail) ;
}
