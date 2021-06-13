package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PersonMessage;


public interface prjMsgService extends BaseService {

	/**PersonMessage分页信息
	 * @param page
	 * @param PersonMessage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PersonMessage personMessage,boolean hasSelfRight,boolean hasDeptRight,boolean hasAllRight);
	
	public Page<Map<String,Object>> goJqGridByWorkType12(Page<Map<String,Object>> page, PersonMessage personMessage);
	
	public Result doSave(PersonMessage personMessage);
	/**PersonMessage分页信息
	 * @param page
	 * @param PersonMessage
	 * @return   address:楼盘地址，deparment2 二级部门
	 */
	public Map<String,Object> getAddress(int PK); 
	public Map<String, Object> getMaxAuthId(String czybh);
	/**
	 * 延误信息
	 * @param page
	 * @param personMessage
	 * @return
	 */
	public Page<Map<String,Object>> goReasonJqGrid(Page<Map<String,Object>> page, PersonMessage personMessage);
}

