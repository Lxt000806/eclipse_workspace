package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemBatchHeader;

public interface ItemBatchHeaderService extends BaseService {

	/**ItemBatchHeader分页信息
	 * @param page
	 * @param itemBatchHeader
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchHeader itemBatchHeader);
	/**
	 * 保存
	 * 
	 * @param itemBatchHeader
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(ItemBatchHeader itemBatchHeader) ;
	/**
	 * 根据itcode找itemtype2
	 * @param itCode
	 * @return
	 */
	public List<Map<String, Object>> getItemType2(String itCode);
	
	public boolean hasChgItemBatch(String no);
}
