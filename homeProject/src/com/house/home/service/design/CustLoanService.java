package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustLoan;

public interface CustLoanService extends BaseService {

	/**CustLoan分页信息
	 * @param page
	 * @param custLoan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustLoan custLoan);
	
	/**CustLoan批量导入
	 * @param page
	 * @param custLoan
	 * @return
	 */
	public Result doSaveBatch(CustLoan custLoan);
	
	/**CustLoan判断客户号是否重复
	 * @param page
	 * @param custLoan
	 * @return
	 */
	public boolean isExistCustCode(CustLoan custLoan);

}
