package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Menu;
import com.house.home.entity.insales.BaseItemType1;

public interface BaseItemType1Service extends BaseService {

	/**BaseItemType1分页信息
	 * @param page
	 * @param baseItemType1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemType1 baseItemType1);
	/**
	 * 返回基装类型1菜单树
	 * @return
	 */
	public List<Menu> getTreeMenu();
	/**
	 * 三级联动
	 */
	public List<Map<String,Object>> findBaseItemType(int type,String pCode);
	/**
	 * 基础结算项目返回基装类型1菜单树
	 * @return
	 */
	public List<Menu> getCheckTreeMenu();
	/**
	 * 验证编号是否重复
	 */
	public boolean valideCode(String id);
	/**
	 * 获取基装类型1
	 * @author	created by zb
	 * @date	2019-3-4--下午4:30:50
	 * @param page
	 */
	public Page<Map<String,Object>> getBaseItemType1(Page<Map<String,Object>> page);
	
}
