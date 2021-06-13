package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.PrjRegion;

public interface PrjRegionService extends BaseService {

	/**PrjRegion分页信息
	 * @param page
	 * @param prjRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjRegion prjRegion);
	
	/**PrjRegion修改
	 * @param prjRegion
	 * @return
	 */
	public void doUpdate(PrjRegion prjRegion);
	
	/**检测是否已存在大区名称
	 * @param prjRegion
	 * @return
	 */
	public boolean checkExsist(PrjRegion prjRegion);
}
