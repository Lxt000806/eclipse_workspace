package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Gift;

public interface GiftService extends BaseService {

	/**Gift分页信息
	 * @param page
	 * @param gift
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Gift gift);
	/**
	 * GiftCustType分页信息
	 * 
	 * @param page
	 * @param gift
	 * @return
	 */
	public Page<Map<String,Object>> goCustTypeJqGrid(Page<Map<String,Object>> page, Gift gift);
	/**
	 * GiftItem分页信息
	 * 
	 * @param page
	 * @param gift
	 * @return
	 */
	public Page<Map<String,Object>> goItemJqGrid(Page<Map<String,Object>> page, Gift gift);
	/**
	 * 保存
	 * 
	 * @param gift
	 * @return
	 */
	public Result doSaveProc(Gift gift);
	
    public void doSetCustType(Map<String, String> params);
}
