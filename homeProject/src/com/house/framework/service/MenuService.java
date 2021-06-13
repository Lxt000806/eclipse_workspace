package com.house.framework.service;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Menu;

public interface MenuService {
	
	/**
	 * 保存菜单
	 * @param menu
	 */
	public void save(Menu menu);
	
	/**
	 * 更新菜单
	 * @param menu
	 */
	public void update(Menu menu);
	
	/**
	 * 删除菜单
	 * @param menuId
	 */
	public void delete(Long menuId);
	
	/**
	 * 获取菜单
	 * @param menuId
	 * @return
	 */
	public Menu get(Long menuId);
	
	/**
	 * 加载所有的菜单
	 * @return
	 */
	public List<Menu> getAll();
	
	/**
	 * 根据父菜单获取子菜单信息
	 * @param parentId 
	 * @return
	 */
	public List<Menu> getSubMenu(Long parentId);
	
	/**
	 * 根据菜单类型获取菜单列表
	 * @param menuType
	 * @return
	 */
	public List<Menu> getByMenuType(String menuType);
	
	/**
	 * 根据当前用户ID, 和标签菜单ID, 查询所有菜单结点
	 * @param userId
	 * @param menu_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getMenusByUser(String czybh, Long munuId);
	

	/**
	 * 根据当前用户ID, 查询所有菜单结点
	 * @param czybh
	 * @param menu_id
	 * @return
	 */
	public List<Map<String, Object>> getMenusByUser(String czybh);
	
	/**
	 * 取得标签菜单 
	 * @return
	 */
	public List<Map<String, Object>> getTabMenus();
	
	/**
	 * 
	 * @Title: getAllMenus 
	 * @Description: 返回所有菜单 
	 * @return List<Map<String,Object>>    
	 * @throws
	 */
	public List<Map<String, Object>> getAllMenus();
	
	/**
	 * 
	 * @Title: initMenus 
	 * @Description: 返回所有的菜单
	 * @return void    返回类型 
	 * @throws
	 */
	public Map<Long, Map<String, Object>> getAllMenusForMap();
	
	/**
	 * 由子菜单获取其根tab类型菜单
	 * @param menuId
	 * @return
	 */
	public Menu getTabMenuBySubMenu(Long menuId);
	
	/**
	 * 根据菜单名获取菜单对象
	 * @param menuName
	 * @return
	 */
	public Menu getByMenuName(String menuName);
	
	/**
	 * 获取下一个排序号
	 * @param parentId
	 * @return
	 */
	public int getNextNum(Long parentId);
	
	/**
	 * 获取可定制菜单
	 */
	public List<Menu> getDefinitionMenu(Long status);
	/**
	 * 根据菜单编码获取菜单
	 */
	public Menu getByCode(String code);
	
	/**
	 * 根据czybh查询菜单
	 * @param userId
	 * @return
	 */
	public List<Menu> getByCzybh(String czybh);

	/**
	 * 根据操作员类别查询菜单
	 * @param czylb
	 * @return
	 */
	public List<Menu> getBySysCode(String czylb);

	public Menu findByUrl(String id);

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Menu menu);
	
	public List<Menu> getAllMenus_czybh();
	
	public List<String> getForbidMenusUrlByCzybh(String czybh);
	
	public Map<String, Object> getMenuMsgByUrl(String url);

}
