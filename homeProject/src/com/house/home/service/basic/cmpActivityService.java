package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.basic.cmpActivityGift;

public interface cmpActivityService extends BaseService {

	/**cmpActivity分页信息
	 * @param page
	 * @param cmpActivity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, cmpActivity cmpactivity);
	
	public Page<Map<String,Object>> findPageBySqlDetail(Page<Map<String,Object>> page, cmpActivityGift cmpactivitygift);	
	
	public Page<Map<String,Object>> findSupplierPageBySql(Page<Map<String,Object>> page, cmpActivityGift cmpactivitygift);	
	
	public Result docmpActivitySave(cmpActivity cmpactivity);
	
	public cmpActivity getByDescr(String descr);
	
	public cmpActivity getByDescrUpdate(String descr,String descr1);
	
	public void doSaveSuppl(String no,String code,String type,String czybm);

	public void doDelSuppl(Integer pk);
	
	public boolean existSuppl(String no,String code);
	
}
