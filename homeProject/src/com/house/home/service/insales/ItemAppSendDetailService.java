package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppSendDetail;

public interface ItemAppSendDetailService extends BaseService {

	/**ItemAppSendDetail分页信息
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppSendDetail itemAppSendDetail);
	
	/**ItemAppSendDetail分页信息
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page, String no);
	/**
	 * 送货详情
	 * 
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String,Object>> findSendDetailByNo(Page<Map<String,Object>> page, ItemAppSendDetail itemAppSendDetail);
	/**
	 * 已发货详情——app
	 * @author	created by zb
	 * @date	2019-9-21--下午3:42:48
	 * @param page
	 * @param itemAppSendDetail
	 * @return
	 */
	public Page<Map<String,Object>> findDetailBySqlNoAPP(Page<Map<String,Object>> page, ItemAppSendDetail itemAppSendDetail);
}
