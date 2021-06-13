package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemSendNode;

public interface ItemSendNodeService extends BaseService {

	/**ItemSendNode分页信息
	 * @param page
	 * @param itemSendNode
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSendNode itemSendNode);
	
	public Page<Map<String,Object>> findPageBySqlForDetail(Page<Map<String,Object>> page, ItemSendNode itemSendNode);
	
	public Result saveForProc(ItemSendNode itemSendNode, String xml);
	
}
