package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemSendBatch;

public interface ItemSendBatchService extends BaseService {
	
	public Page<Map<String,Object>> getJqGrid(Page<Map<String,Object>> page,ItemSendBatch itemSendBatch);
	
	public Page<Map<String,Object>> goReturnDetailJqGrid(Page<Map<String,Object>> page,ItemSendBatch itemSendBatch);
	/**
	 * 司机是否有两天前的配送任务未完成
	 * 
	 * @param itemSendBatch
	 * @return
	 */
	public List<Map<String, Object>> checkDriver(ItemSendBatch itemSendBatch);
	/**
	 * 保存
	 * 
	 * @param itemSendBatch
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(ItemSendBatch itemSendBatch);
}
