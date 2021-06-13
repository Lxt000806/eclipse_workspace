package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemBalAdjHeader;

public interface ItemBalAdjHeaderService extends BaseService {

	/**ItemBalAdjHeaderService分页信息
	 * @param page
	 * @param signIn
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBalAdjHeader itemBalAdjHeader,UserContext uc);
	
	public Result doItemBalAdjHeaderSave(ItemBalAdjHeader itemBalAdjHeader);
	
	public Result doItemBalAdjHeaderUpdate(ItemBalAdjHeader itemBalAdjHeader);
	
	public Result doItemBalAdjHeaderVerify(ItemBalAdjHeader itemBalAdjHeader,String status);
}
