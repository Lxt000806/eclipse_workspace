package com.house.home.service.driver;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemReturnDetail;

public interface ItemReturnDetailService extends BaseService {

	/**
	 * ItemReturnDetail分页信息
	 * 
	 * @param page
	 * @param itemReturnDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemReturnDetail itemReturnDetail);

	/**
	 * 根据退货申请编号查询退货详情
	 */
	 public Map<String,Object> getReturnDetailById(String No);
		/**获取退货材料明细
		 * @return
		 */
	public List<Map<String,Object>> getMaterialList(String No);
	/**获取待退货材料明细
	 * @return
	 */
	public List<Map<String,Object>> getReturnMaterial(String No);
	

	/**
	 * 根据no获取退货申请明细列表
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id);

	public Map<String, Object> getByPk(int pk);
	/**
	 * 退货详情
	 * 
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String, Object>> findReturnDetailByNo(
			Page<Map<String, Object>> page, ItemReturnDetail itemReturnDetail);
}
