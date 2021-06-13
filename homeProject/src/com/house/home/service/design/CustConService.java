package com.house.home.service.design;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustCon;

public interface CustConService extends BaseService {

	/**CustCon分页信息
	 * @param page
	 * @param custCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustCon custCon,UserContext uc);
	/**
	 * CustCon分页信息for bs
	 * 
	 * @param page
	 * @param custCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_bs(Page<Map<String,Object>> page, CustCon custCon,UserContext uc);
	/**CustCon分页信息-客户信息查询
	 * @param page
	 * @param custCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, CustCon custCon);
	
}
