package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustContractTemp;

public interface CustContractTempService extends BaseService {

	/**CustContractTemp分页信息
	 * @param page
	 * @param custContractTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContractTemp custContractTemp);

	public Result doSaveProc(CustContractTemp custContractTemp);

	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, CustContractTemp custContractTemp);

	public Map<String, Object> getCustContractTempFileName(Integer pk);
	
}
