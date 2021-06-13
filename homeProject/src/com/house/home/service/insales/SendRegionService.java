package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.SendRegion;

public interface SendRegionService extends BaseService {

	/**SendRegion分页信息
	 * @param page
	 * @param sendRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendRegion sendRegion);

	/**
	 * @Description: TODO 配送区域分页查询
	 * @author	created by zb
	 * @date	2018-9-6--上午11:30:01
	 * @param page
	 * @param sendRegion
	 * @return
	 */
	public Page<Map<String,Object>> findSendRegionPageBySql(Page<Map<String, Object>> page,
			SendRegion sendRegion);
	
	/**
	 * @Description: TODO 根据传入的表名，和java类判断是否存在descr
	 * @author	created by zb
	 * @date	2018-9-6--下午12:22:09
	 * @param tableName 表名
	 * @param sendRegion java类
	 * @return true：存在
	 */
	public boolean hasDescr(String tableName, SendRegion sendRegion);

}
