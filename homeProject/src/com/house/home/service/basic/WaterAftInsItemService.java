package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.WaterAftInsItemEvt;
import com.house.home.entity.basic.WaterAftInsItem;

public interface WaterAftInsItemService extends BaseService {

	/**ItemSendNode分页信息
	 * @param page
	 * @param itemSendNode
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WaterAftInsItem waterAftInsItem);
	
	public Page<Map<String,Object>> findPageBySqlForDetail(Page<Map<String,Object>> page, WaterAftInsItem waterAftInsItem);
	
	public Result saveForProc(WaterAftInsItem waterAftInsItem, String xml);
	
	public Page<Map<String,Object>> getWaterAftInsItemType2List(Page<Map<String,Object>> page);
	
	public Page<Map<String,Object>> getWaterAftInsItemDetailList(Page<Map<String,Object>> page, WaterAftInsItemEvt evt);
	
	public Result saveWaterAftInsItemAppForProc(String custCode, String workerCode, String xml);
	
	public Page<Map<String,Object>> getWaterAftInsItemList(Page<Map<String,Object>> page, WaterAftInsItemEvt evt);
	
}
