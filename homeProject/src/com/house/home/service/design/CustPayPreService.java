package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.bean.design.CustPaypreSaveBean;
import com.house.home.entity.design.CustPayPre;

public interface CustPayPreService extends BaseService {

	/**CustPayPre分页信息
	 * @param page
	 * @param custPayPre
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustPayPre custPayPre);
	public CustPayPre getCustPayPre(String custCode,String payType);
	public Result doCustPaypreForProc(CustPaypreSaveBean custPaypreSaveBean);
	
}
