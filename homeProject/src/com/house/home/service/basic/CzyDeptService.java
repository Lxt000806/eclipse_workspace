package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzyDept;

public interface CzyDeptService extends BaseService {

	/**CzyDept分页信息
	 * @param page
	 * @param czyDept
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyDept czyDept);

	/**根据操作员编号查找CzyDept
	 * @param czybh
	 * @return
	 */
	public List<CzyDept> findByCzybh(String czybh);

	/**根据操作员编号删除操作员部门
	 * @param czybh
	 */
	public void deleteByCzybh(String czybh);
	
}
