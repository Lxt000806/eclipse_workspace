package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.BaseItemType2;

public interface BaseItemType2Service extends BaseService {

	/**BaseItemType2分页信息
	 * @param page
	 * @param baseItemType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemType2 baseItemType2);
	/**
	 * 验证编号是否重复
	 * */
	public boolean valideBaseItemType(String code);
	/**
	 * 根据类型1的code获取类型2_PrjAPP
	 * @author	created by zb
	 * @date	2019-3-4--下午4:40:45
	 * @param page
	 * @param code
	 */
	public Page<Map<String,Object>> getBaseItemType2(Page<Map<String,Object>> page, String code);
	
}
