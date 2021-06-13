package com.house.home.service.insales;

import java.util.Map;
import java.util.List;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemBatchDetail;
public interface ItemBatchDetailService extends BaseService {

	/**ItemBatchDetail分页信息
	 * @param page
	 * @param itemBatchDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchDetail itemBatchDetail);
	/**
	 * 查询材料批次明细
	 */
	public Page<Map<String,Object>> findPageByIbdNo(Page<Map<String,Object>> page, String ibdNo);
	/**
	 * 查询材料批次明细列表
	 */
	public List<ItemBatchDetail>  getItemBatchDetailByIbdNo(String ibdNo);
	public Page<Map<String,Object>> getItemBatchDetailByIbdNo(Page<Map<String, Object>> page,
			ItemBatchDetail itemBatchDetail);
	
	public Page<Map<String,Object>> getItemBatchDetailJqGrid(Page<Map<String, Object>> page,
			ItemBatchDetail itemBatchDetail);
	
    public Page<Map<String,Object>> getItemChgImportingJqGrid(Page<Map<String, Object>> page,
            ItemBatchDetail itemBatchDetail);
	
	
}
