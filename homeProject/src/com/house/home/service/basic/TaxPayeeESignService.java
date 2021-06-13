package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.TaxPayeeESign;

public interface TaxPayeeESignService extends BaseService {

	/**TaxPayeeESign分页信息
	 * @param page
	 * @param taxPayeeESign
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, TaxPayeeESign taxPayeeESign);
	
	/**
	 * 印章是否启用
	 * 
	 * @param page
	 * @param sealId
	 * @return
	 */
	public List<Map<String,Object>> isEnableSeal(String sealId);
	
	/**
	 * 机构是否启用
	 * 
	 * @param page
	 * @param orgId
	 * @return
	 */
	public List<Map<String,Object>> isEnableOrg(String orgId);
}
