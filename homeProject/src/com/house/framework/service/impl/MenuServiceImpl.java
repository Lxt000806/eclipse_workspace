package com.house.framework.service.impl;

import java.util.Date;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.house.framework.commons.cache.MenuCacheManager;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.dao.MenuDao;
import com.house.framework.entity.Menu;
import com.house.framework.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private MenuCacheManager menuCacheManager;
	
	//@LoggerAnnotation(okLog="添加 ID=$args[0].menuId menuName=$args[0].menuName 成功", errLog="添加 menuName=$args[0].menuName 失败", modelCode=LogModuleConstant.MENU_MODULE)
	public void save(Menu menu) {
		Assert.notNull(menu,"菜单不能为空");
		menu.setGenTime(new Date());
		
		//如果新增菜单不是tab类型菜单，由其父ID向上追溯其所属tab 类型菜单ID
		if(!DictConstant.DICT_MENU_TYPE_TAB.equals(menu.getMenuType()) && menu.getParentId() != null && menu.getParentId().longValue() != 0L ){
			Menu tabMenu = this.getTabMenuBySubMenu(menu.getParentId());
			menu.setTabMenuId(tabMenu.getMenuId());
		}
		if(DictConstant.DICT_MENU_TYPE_TAB.equals(menu.getMenuType()) && menu.getParentId() == null){
			menu.setParentId(0L);
		}
		if(menu.getOrderNo() == null)
			menu.setOrderNo(0L);
		
		Reflections.trim(menu);
		
		this.menuDao.save(menu);
		//为tab菜单，则将自己的id作为tabID
		if(DictConstant.DICT_MENU_TYPE_TAB.equals(menu.getMenuType())){
			menu.setTabMenuId(menu.getMenuId());
			this.menuDao.update(menu);
		}
	}
	
	//@LoggerAnnotation(okLog="更新 ID=$args[0].menuId menuName=$args[0].menuName 成功", errLog="更新 ID=$args[0].menuId menuName=$args[0].menuName 失败", modelCode=LogModuleConstant.MENU_MODULE)
	public void update(Menu menu) {
		Assert.notNull(menu,"菜单不能为空");
		if(DictConstant.DICT_MENU_TYPE_TAB.equals(menu.getMenuType())){
			menu.setTabMenuId(menu.getMenuId());
		}else{
			Menu tabMenu = this.getTabMenuBySubMenu(menu.getMenuId());
			menu.setTabMenuId(tabMenu.getMenuId());
		}
		if(DictConstant.DICT_MENU_TYPE_TAB.equals(menu.getMenuType()) && menu.getParentId() == null){
			menu.setParentId(0L);
		}
		if(menu.getOrderNo() == null)
			menu.setOrderNo(0L);
		
		menu.setUpdateTime(new Date());
		Reflections.trim(menu);
		this.menuDao.update(menu);
	}
	
	//@LoggerAnnotation(okLog="删除 ID=$args[0] 成功", errLog="删除 ID=$args[0] 失败", modelCode=LogModuleConstant.MENU_MODULE)
	public void delete(Long menuId) {
		Menu menu = this.get(menuId);
		if(menu == null)
			return ;
		this.menuDao.delete(menu);
	}

	public Menu get(Long menuId) {
		if(menuId == null)
			return null;
		return this.menuDao.get(Menu.class, menuId);
	}
	
	@SuppressWarnings(value="unchecked")
	public List<Menu> getAll(){
		String hql = "from Menu m order by m.parentId, m.orderNo, m.menuId";
		return this.menuDao.find(hql);
	}

	@SuppressWarnings(value="unchecked")
	public List<Menu> getSubMenu(Long parentId){
		if(null == parentId)
			parentId = 0L;
		String hql = "from Menu d where d.parentId = ? order by d.orderNo";
		return this.menuDao.find(hql, new Object[]{parentId});
	}

	public List<Menu> getByMenuType(String menuType) {
		return this.menuDao.getByMenuType(menuType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getMenusByUser(String czybh, Long tabMenuId) {
		logger.debug("获取用户 ID={} 菜单",czybh);
		//取得所有子菜单包括
		//Map<Long, Map<String, Object>> menuMaps = (Map<Long, Map<String, Object>>)menuEhCacheCache.get(CommonConstant.CACHE_MENUS_KEY).get();
		
		//取得所有叶子节点
		List<Map<String, Object>> leafs = menuDao.getLeafMenusByUser(czybh, tabMenuId);
		
		Map<Long, Map<String, Object>> ownsMap = new HashMap<Long, Map<String, Object>>(); 
		
		
		for(Map<String, Object> leaf : leafs){
			Long leafMenuId = ((BigDecimal)leaf.get("MENU_ID")).longValue();
			//this.findParentMenu(leafMenuId, ownsMap, menuMaps);
			this.findParentMenu(leafMenuId, ownsMap);
		}
		ownsMap.remove(tabMenuId); //移除自身结点
		
		List ownsLst = new ArrayList(ownsMap.values());
		
		//对菜单进行排序
		Collections.sort(ownsLst, new Comparator<Map<String, Object>>(){

			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Long parentId1 = ((BigDecimal)o1.get("PARENT_ID")).longValue();
				Long parentId2 = ((BigDecimal)o2.get("PARENT_ID")).longValue();
				
				Long orderNo1 = ((BigDecimal)o1.get("ORDER_NO")).longValue();
				Long orderNo2 = ((BigDecimal)o2.get("ORDER_NO")).longValue();
				
				Long menuId1 = ((BigDecimal)o1.get("MENU_ID")).longValue();
				Long menuId2 = ((BigDecimal)o2.get("MENU_ID")).longValue();
				
				if(parentId1.equals(parentId2)){
					int temp1 = orderNo1.compareTo(orderNo2);
					
					if(temp1 == 0){
						menuId1.compareTo(menuId2);
					}
					
					return temp1;
				}
				
				return menuId1.compareTo(menuId2);
				
			}});
		
		logger.debug(ownsLst.toString());
		return ownsLst;
	}
	
	@SuppressWarnings("unchecked")
	public void findParentMenu(Long childMenuId, Map<Long, Map<String, Object>> ownsMap){
		
		Map<String, Object> menuMap = (Map<String, Object>)menuCacheManager.get(childMenuId);
		if(menuMap == null)return;
		
		//从当前自身MAP取, 如果存在, 则表明先前已遍历过, 无须再往上遍历
		if(ownsMap.get(childMenuId) != null){
			return;
		}
		
		ownsMap.put(childMenuId, menuMap);
		Long parentMenuId = ((BigDecimal)menuMap.get("PARENT_ID")).longValue();
		
		this.findParentMenu(parentMenuId, ownsMap);
	}

	public List<Map<String, Object>> getTabMenus() {
		return menuDao.getTabMenus();
	}

	public List<Map<String, Object>> getAllMenus() {
		return menuDao.getAllMenus();
	}
	

	public Map<Long, Map<String, Object>> getAllMenusForMap(){
		List<Map<String, Object>> lst = getAllMenus();
		
		Map<Long, Map<String, Object>> menuMaps = new HashMap<Long, Map<String, Object>>();
		
		for(Map<String, Object> map : lst){
			Long menuId = ((BigDecimal)map.get("MENU_ID")).longValue();
			menuMaps.put(menuId, map);
		}
		
		return menuMaps;
	}

	public Menu getTabMenuBySubMenu(Long menuId) {
		if(menuId == null)
			return null;
		return this.menuDao.getTabMenuBySubMenu(menuId);
	}

	public Menu getByMenuName(String menuName) {
		if(StringUtils.isBlank(menuName))
			return null;
		return this.menuDao.getByMenuName(menuName);
	}

	public int getNextNum(Long parentId) {
		return this.menuDao.getNextNum(parentId);
	}

	
	public List<Map<String, Object>> getMenusByUser(String czybh) {
		return	this.menuDao.getLeafMenusByUser(czybh);
	}

	
	@SuppressWarnings({ "unchecked" })
	public List<Menu> getDefinitionMenu(Long status) {
		StringBuffer hql = new StringBuffer(" from Menu m where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(status!=null){
			hql.append(" and m.status = ?");
			params.add(status);
		}
		return this.menuDao.find(hql.toString(), params.toArray());
	}

	
	public Menu getByCode(String code) {
		StringBuffer hql = new StringBuffer("from Menu m where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(code!=null){
			hql.append(" and m.code = ?");
			params.add(code);
		}
		@SuppressWarnings("unchecked")
		List<Menu> menuList = this.menuDao.find(hql.toString(), params.toArray());
		return menuList.get(0);
	}

	
	public List<Menu> getByCzybh(String czybh) {
		return menuDao.getByCzybh(czybh);
	}

	@Override
	public List<Menu> getBySysCode(String czylb) {
		return menuDao.getBySysCode(czylb);
	}

	@Override
	public Menu findByUrl(String id) {
		return menuDao.findByUrl(id);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Menu menu) {
		return menuDao.findPageBySql(page, menu);
	}

	@Override
	public List<Menu> getAllMenus_czybh() {
		return menuDao.getAllMenus_czybh();
	}

	@Override
	public List<String> getForbidMenusUrlByCzybh(String czybh) {
	    List<String> result = new ArrayList<String>();
	    
//		List<Menu> allMenus = BeanConvertUtil.mapToBeanList(menuDao.getAllMenus(), Menu.class);
//		List<Menu> menusByCzybh = menuDao.getByCzybh(czybh);
//		for (int i = 0; i < allMenus.size(); i++) {
//			for (int j = 0; j < menusByCzybh.size(); j++) {
//				if(StringUtils.isNotBlank(allMenus.get(i).getMenuUrl())){
//					if (allMenus.get(i).getMenuUrl().equals(menusByCzybh.get(j).getMenuUrl())) {
//						break;
//					}else if(j==menusByCzybh.size()-1){
//						result.add(allMenus.get(i).getMenuUrl());
//					}
//				}
//			}
//		}
		
		List<Menu> forbiddenMenus = menuDao.findForbiddenMenusByCzybh(czybh);
		for (Menu menu : forbiddenMenus) {
            result.add(menu.getMenuUrl());
        }
		
		return result;
	}

	@Override
	public Map<String, Object> getMenuMsgByUrl(String url) {
		return menuDao.getMenuMsgByUrl(url);
	}
	
}
