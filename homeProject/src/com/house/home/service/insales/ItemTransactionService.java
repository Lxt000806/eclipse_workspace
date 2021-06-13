package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemTransaction;
public interface ItemTransactionService extends BaseService {
	/**ItemTransaction分页信息
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemTransaction itemTransaction,UserContext uc);
}
