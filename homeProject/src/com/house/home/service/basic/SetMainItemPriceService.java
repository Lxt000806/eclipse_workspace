package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.SetMainItemPrice;

public interface SetMainItemPriceService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SetMainItemPrice setMainItemPrice);
	/**
	 * 检查面积是否重复
	 * @author	created by zb
	 * @date	2020-1-9--下午4:15:58
	 * @param custType
	 * @param area
	 * @return
	 */
	public List<Map<String, Object>> getIsRepeatArea(String custType, Double area);
	/**
	 * 新增
	 * @author	created by zb
	 * @date	2020-1-9--下午5:13:28
	 * @param setMainItemPrice
	 * @param userContext
	 */
	public void doSave(SetMainItemPrice setMainItemPrice,
			UserContext userContext);
	/**
	 * 编辑
	 * @author	created by zb
	 * @date	2020-1-10--上午9:34:05
	 * @param setMainItemPrice
	 * @param userContext
	 */
	public void doUpdate(SetMainItemPrice setMainItemPrice,
			UserContext userContext);
	/**
	 * 删除
	 * @author	created by zb
	 * @date	2020-1-10--上午10:53:08
	 * @param deleteIds
	 */
	public void doDelete(String deleteIds);
	
	/**
	 * Excel导入调过程
	 * @param setMainItemPrice
	 * @return
	 */
	public Result doSaveBatch (SetMainItemPrice setMainItemPrice);
}
