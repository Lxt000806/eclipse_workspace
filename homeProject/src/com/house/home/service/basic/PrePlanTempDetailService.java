package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrePlanTempDetail;

public interface PrePlanTempDetailService extends BaseService {

	/**PrePlanTempDetail分页信息
	 * @param page
	 * @param prePlanTempDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlanTempDetail prePlanTempDetail);
	/**
	 * 根据no查找快速预报价明细
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findDetailByNo(PrePlanTempDetail prePlanTempDetail);
	/**
	 * 根据切割类型匹配瓷砖尺寸
	 * 
	 * @param prePlanTempDetail
	 * @return
	 */
	public List<Map<String, Object>> getQtyByCutType(PrePlanTempDetail prePlanTempDetail);
}
