package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Brand;

public interface BrandService extends BaseService {

	/**Brand分页信息
	 * @param page
	 * @param brand
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Brand brand);
	
	public List<Map<String,Object>> findBrand(Map<String,Object> param);

	/**
	 * 品牌管理分页查询
	 * @author	created by zb
	 * @date	2018-12-24--上午10:41:53
	 * @param page
	 * @param brand
	 * @return
	 */
	public Page<Map<String, Object>> findBrandPageBySql(Page<Map<String, Object>> page, Brand brand);

	/**
	 * 校验一下商品类型2是否已经存在
	 * @author	created by zb
	 * @date	2018-12-24--下午3:26:05
	 * @param brand
	 * @return false:存在
	 */
	public Boolean checkDescr(Brand brand);

	
}
