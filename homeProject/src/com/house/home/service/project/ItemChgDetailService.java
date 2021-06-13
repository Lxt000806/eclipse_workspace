package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ItemChgDetail;

public interface ItemChgDetailService extends BaseService {

	/**ItemChgDetail分页信息
	 * @param page
	 * @param itemChgDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChgDetail itemChgDetail);

	/**根据no查找ItemChgDetail分页信息
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page,String id);
	
	public Page<Map<String,Object>> findPageByNo_khxx(Page<Map<String,Object>> page,String no, String costRight);
	/**
	 * 调用导入存储过程补齐数据
	 * @param data
	 */
	public void importDetail(Map<String, Object> data);
}
