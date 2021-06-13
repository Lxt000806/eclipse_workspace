package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemTransferHeader;

public interface ItemTransferHeaderService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemTransferHeader itemTransferHeader);
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, ItemTransferHeader itemTransferHeader);

	public Map<String , Object> getFromQty(String itCode,String fromWHCode);

	public String getToQty(String itCode,String toWHCode);

	public String getPostQty(String itCode,String fromWHCode);

	public Result doSaveItemTransfer(ItemTransferHeader itemTransferHeader);
	
	public Result doUpdateItemTransfer(ItemTransferHeader itemTransferHeader);

	public Result doSaveCheckItemTransfer(ItemTransferHeader itemTransferHeader);
	/**
	 * 根据供应商汇总amount
	 * @author	created by zb
	 * @date	2019-4-18--下午4:46:34
	 */
	public Page<Map<String,Object>> findOrderBySupplPageBySql(Page<Map<String, Object>> page,
			ItemTransferHeader itemTransferHeader);

}
