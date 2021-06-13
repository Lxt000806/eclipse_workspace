package com.house.framework.commons.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.entity.Menu;
import com.house.framework.service.MenuService;
import com.house.home.entity.basic.Czybm;
import com.house.home.service.basic.CzybmService;

/**
 *功能说明: 菜单缓存管理器，根据用户的权限获取用户所有的菜单，放入缓存
 */
@Component
public class MenuCacheManager extends SimpleCacheManager{
	protected static final Logger logger = LoggerFactory.getLogger(MenuCacheManager.class);
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private CzybmService czybmService;

	
	public String getCacheKey() {
		return CommonConstant.CACHE_MENUS_KEY;
	}

	@SuppressWarnings("unchecked")
	public void initCacheData() {
		logger.info("###### 初始化 系统菜单 数据开始 ######");
		
		//获取所有的菜单，用于复用，减小内存占用
		List<Menu> allMenus = this.menuService.getAll();
		if(allMenus == null || allMenus.size() < 1){
			logger.warn("系统不存在任何菜单");
			return ;
		}
//		Map<Long,Menu> mapAllMenus = new HashMap<Long,Menu>();
//		for (Menu menu : allMenus){
//			mapAllMenus.put(menu.getMenuId(), menu);
//		}
//		//普通管理员
//		List<Menu> list = menuService.getAllMenus_czybh();
//		if (list!=null && list.size()>0){
//			for (Menu menu:list){
//				Map<Long,Menu> retMap = new HashMap<Long,Menu>();
//				getParentMenu(menu,mapAllMenus,retMap);
//				if (this.get(menu.getCzybh())!=null){
//					List<Menu> list2 = (List<Menu>) this.get(menu.getCzybh());
//					for (Menu m : list2){
//						retMap.put(m.getMenuId(), m);
//					}
//					list2 = null;
//					this.put(menu.getCzybh(),new ArrayList<Menu>(retMap.values()));
//				}else{
//					this.put(menu.getCzybh(),new ArrayList<Menu>(retMap.values()));
//				}
//				retMap = null;
//			}
//		}
//		//获取超级管理员列表
//		List<Czybm> superAdminList = this.czybmService.getSuperAdminList();
//		if (superAdminList!=null && superAdminList.size()>0){
//			for (Czybm czybm : superAdminList){
//				this.put(czybm.getCzybh(),allMenus);
//			}
//		}
		logger.info("###### 初始化 系统菜单 数据结束 ######");
	}
	
	/**
	 * 递归获取父菜单和本身
	 * @param menu
	 * @param allMap
	 * @param retMap
	 */
	private void getParentMenu(Menu menu,Map<Long,Menu> allMap,Map<Long,Menu> retMap){
		if (menu!=null){
			retMap.put(menu.getMenuId(), menu);
			Menu menuP = allMap.get(menu.getParentId());
			getParentMenu(menuP,allMap,retMap);
		}
	}
}
