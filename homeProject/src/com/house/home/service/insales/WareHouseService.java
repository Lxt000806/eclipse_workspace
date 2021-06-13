package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.insales.WareHouse;

public interface WareHouseService extends BaseService {

	/**WareHouse分页信息
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHouse wareHouse);

	public Page<Map<String,Object>> findLimitItemType2PageBySql(Page<Map<String, Object>> page, WareHouse wareHouse);
	
	public Page<Map<String,Object>> findLimitRegionPageBySql(Page<Map<String, Object>> page, WareHouse wareHouse);
	
	public Result saveForProc(WareHouse wareHouse);
	
	public List<WareHouse> findByNoExpired();

	public Page<Map<String,Object>> findPageBySqlCode(Page<Map<String, Object>> page,
			WareHouse wareHouse);
	
	public boolean hasWhRight(Czybm czybm, WareHouse wareHouse);
	/**
	 * 根据领料单号获取仓库材料数量
	 * @author	created by zb
	 * @date	2019-11-1--上午10:07:32
	 * @param page2
	 * @param iaNo
	 * @return
	 */
	public Page<Map<String,Object>> getWHItemDetail(Page<Map<String,Object>> page2, String iaNo);

}
