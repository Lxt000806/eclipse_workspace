package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemPic;

public interface ItemPicService extends BaseService {

	/**ItemPic分页信息
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPic itemPic);
	
	
	public int getCountNum(String itemCode); 

}
