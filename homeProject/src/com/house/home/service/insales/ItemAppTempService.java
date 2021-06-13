package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppTemp;
import com.house.home.entity.insales.ItemAppTempArea;

public interface ItemAppTempService extends BaseService {

	/**ItemAppTemp分页信息
	 * @param page
	 * @param itemAppTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTemp itemAppTemp);

	public Page<Map<String,Object>> getAppItemTypeBatch(Page<Map<String,Object>> page, ItemAppTemp itemAppTemp);

	/**
	 * @Description: TODO 领料申请模板分页查询
	 * @author	created by zb
	 * @date	2018-9-7--下午3:19:22
	 * @param page
	 * @param itemAppTemp
	 * @return
	 */
	public Page<Map<String,Object>> findItemAPPTempPageBySql(Page<Map<String, Object>> page,
			ItemAppTemp itemAppTemp);

	/**
	 * @Description: TODO 运行存储过程
	 * @author	created by zb
	 * @date	2018-9-7--下午5:13:26
	 * @param itemAppTemp
	 * @return
	 */
	public Result doSave(ItemAppTemp itemAppTemp);

	/**
	 * @Description: TODO 领料申请模板面积区间分页查询
	 * @author	created by zb
	 * @date	2018-9-9--上午9:58:11
	 * @param page
	 * @param itemAppTemp
	 * @return
	 */
	public Page<Map<String,Object>> findAreaPageBySql(Page<Map<String, Object>> page,
			ItemAppTemp itemAppTemp);
	
	/**
	 * @Description: TODO 领料申请明细分页查询 
	 * @author	created by zb
	 * @date	2018-9-9--上午11:09:45
	 * @param page
	 * @param itemAppTempArea
	 * @return
	 */
	public Page<Map<String,Object>> findAreaDetailPageBySql(Page<Map<String, Object>> page,
			ItemAppTempArea itemAppTempArea);
	
	/**
	 * @Description: TODO 根据材料编号获取材料信息（只可增加字段，不可删除）
	 * @author	created by zb
	 * @date	2018-9-10--上午11:20:42
	 * @param code 材料编号
	 * @return
	 */
	public Map<String, Object> getItemByCode(String code);
	
	/**
	 * @Description: TODO 领料申请面积区间存储过程
	 * @author	created by zb
	 * @date	2018-9-11--上午10:19:32
	 * @param itemAppTempArea
	 * @return
	 */
	public Result doAreaSave(ItemAppTempArea itemAppTempArea);

}
