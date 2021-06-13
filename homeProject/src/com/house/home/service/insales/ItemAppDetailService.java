package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppDetail;

public interface ItemAppDetailService extends BaseService {

	/**ItemAppDetail分页信息
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail);

	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail);
	
	public Page<Map<String,Object>> findImportPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail);

	/**根据no查找ItemAppDetail分页信息
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page, String id);
	
	/**
	 * 已有项新增
	 * @param page
	 * @param param
	 * @return
	 */
	public Page<Map<String,Object>> findItemAppDetailExists(Page<Map<String,Object>> page,Map<String,Object> param);

	/**
	 * 快速新增
	 * @param page
	 * @param param
	 * @return
	 */
	public Page<Map<String,Object>> findItemAppDetailFast(Page<Map<String,Object>> page,Map<String,Object> param);

	/**
	 * 领料退回明细
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_return(Page<Map<String, Object>> page,
			ItemAppDetail itemAppDetail);

	/**
	 * 领料退回新增
	 * @param page
	 * @param param
	 * @return
	 */
	public Page<Map<String,Object>> findItemAppDetailExistsReturn(Page<Map<String, Object>> page,
			Map<String, Object> param);

/**
 * 审核领料数量明细
 * @param page
 * @param itemAppDetail
 * @return
 */
	public Page<Map<String,Object>> findCheckedPageBySql(Page<Map<String, Object>> page,
			ItemAppDetail itemAppDetail);
	/**
	 * 申请领料数量明细
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findAppliedPageBySql(Page<Map<String, Object>> page,
			ItemAppDetail itemAppDetail);

	/**根据no和客户编号查找ItemAppDetail分页信息
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo_khxxcx(Page<Map<String,Object>> page, String no, String custCode);

	/**查询客户的领料未发货材料
	 * @param page
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> findNotSendItemList(String custCode);
	/**
	 * 发货申请明细
	 * @author	created by zb
	 * @date	2019-4-10--下午5:34:28
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String, Object>> findSendDetailBySql(Page<Map<String, Object>> page,
			ItemAppDetail itemAppDetail);
}
