package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppTempAreaDetail;

public interface ItemAppTempAreaDetailService extends BaseService {

	/**ItemAppTempAreaDetail分页信息
	 * @param page
	 * @param itemAppTempAreaDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTempAreaDetail itemAppTempAreaDetail);
	/**
	 * 根据模板号和客户编号获取模板对应材料明细
	 * @param page
	 * @param iatNo
	 * @param custCode
	 * @return
	 */
		public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, String iatNo,String custCode);
		
		public Page<Map<String,Object>> findConfItemTypePageBySql(Page<Map<String,Object>> page, String batchCode,String custCode);

		public Page<Map<String,Object>> findConfItemCodePageBySql(Page<Map<String,Object>> page, 
					String confCode,String custCode,String flag,String m_umStatus);

		public Page<Map<String,Object>> findCheckAppItemAllPageBySql(Page<Map<String,Object>> page, String appItemCodes,String itemCode,String custCode,String isSetItem);

}
