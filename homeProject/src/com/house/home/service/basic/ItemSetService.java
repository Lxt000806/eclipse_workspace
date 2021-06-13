package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;


public interface ItemSetService extends BaseService {

	/**ItemSet分页信息
	 * @param page
	 * @param ItemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSet itemSet);
	public Page<Map<String,Object>> findPageBySqlDetail(Page<Map<String,Object>> page, ItemSetDetail itemSetDetail);
	public Page<Map<String,Object>> findPageBySqlDetailadd(Page<Map<String,Object>> page, ItemSetDetail itemSetDetail);
	public Result doItemSetSave(ItemSet itemSet);
	public Result deleteForProc(ItemSet itemSet);
	public ItemSet getByDescr(String descr);
	public ItemSet getByDescr1(String descr,String descr1);

	public List<Map<String, Object>> findItemSetNo(ItemSet itemSet);

}

