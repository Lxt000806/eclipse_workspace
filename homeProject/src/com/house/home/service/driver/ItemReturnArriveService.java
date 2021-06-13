package com.house.home.service.driver;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemReturnArrive;

public interface ItemReturnArriveService extends BaseService {

	/**ItemReturnArrive分页信息
	 * @param page
	 * @param itemReturnArrive
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReturnArrive itemReturnArrive);
	/**
	 * 查询退货单号下的所有退货到达列表
	 */
	public List<Map<String,Object>> findByReturnNo(String returnNo);
	/**
	 * 查询退货单号下的所有退货到达列表jqgrid
	 */
	public Page<Map<String,Object>> findArriveByNo(Page<Map<String,Object>> page, ItemReturnArrive itemReturnArrive);
}
