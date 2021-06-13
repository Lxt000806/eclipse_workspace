package com.house.framework.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.BuilderCacheManager;
import com.house.framework.commons.cache.BuilderGroupCacheManager;
import com.house.framework.commons.cache.CustTypeCacheManager;
import com.house.framework.commons.cache.DepartmentCacheManager;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.cache.MessageCacheManager;
import com.house.framework.commons.cache.PositionCacheManager;
import com.house.framework.commons.cache.RoleCacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;

@Controller
@RequestMapping("/admin/cache")
public class RefreshCacheController extends BaseController {
	
	@Resource(name = "menuCacheManager")
	private ICacheManager menuCacheManager;
	
	@Resource(name = "dictCacheManager")
	private ICacheManager dictCacheManager;
	
	@Resource(name = "authorityCacheManager")
	private ICacheManager authorityCacheManager;
	
	@Resource(name = "xtdmCacheManager")
	private ICacheManager xtdmCacheManager;
	
	@Resource(name = "builderCacheManager")
	private BuilderCacheManager builderCacheManager;
	
	@Resource(name = "builderGroupCacheManager")
	private BuilderGroupCacheManager builderGroupCacheManager;
	
	@Resource(name = "departmentCacheManager")
	private DepartmentCacheManager departmentCacheManager;
	
	@Resource(name = "messageCacheManager")
	private MessageCacheManager messageCacheManager;
	
	@Resource(name = "positionCacheManager")
	private PositionCacheManager positionCacheManager;
	
	@Resource(name = "roleCacheManager")
	private RoleCacheManager roleCacheManager;
	@Resource(name = "custTypeCacheManager")
	private CustTypeCacheManager custTypeCacheManager;
	
	@RequestMapping("/goRefresh")
	public ModelAndView goRefresh(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/cache/refresh_cache");
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", "DICT_CACHE");
		map.put("descr", "字典缓存");
		list.add(map);
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("code", "AUTHORITY_CACHE");
		map1.put("descr", "权限缓存");
		list.add(map1);
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("code", "MENU_CACHE");
		map2.put("descr", "菜单缓存");
		list.add(map2);
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("code", "XTDM_CACHE");
		map3.put("descr", "系统代码缓存");
		list.add(map3);
		
		Map<String,Object> map4 = new HashMap<String,Object>();
		map4.put("code", "OTHER_CACHE");
		map4.put("descr", "其他缓存");
		list.add(map4);
		
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doRefreshAll")
	public void refreshAll(HttpServletRequest request, HttpServletResponse response, String itemKeys){
		if(itemKeys.indexOf(CommonConstant.CACHE_DICT_KEY) > -1){
			this.dictCacheManager.refresh();
		}
		
		if(itemKeys.indexOf(CommonConstant.CACHE_MENUS_KEY) > -1){
			this.menuCacheManager.refresh();
		}
		
		if(itemKeys.indexOf(CommonConstant.CACHE_AUTHORITY_KEY) > -1){
			this.authorityCacheManager.refresh();
		}
		
		if(itemKeys.indexOf(CommonConstant.CACHE_XTDM_KEY) > -1){
			this.xtdmCacheManager.refresh();
		}
		
		if(itemKeys.indexOf(CommonConstant.CACHE_OTHER_KEY) > -1){
			this.builderCacheManager.refresh();
			this.builderGroupCacheManager.refresh();
			this.departmentCacheManager.refresh();
			this.messageCacheManager.refresh();
			this.positionCacheManager.refresh();
			this.roleCacheManager.refresh();
			this.custTypeCacheManager.refresh();
		}
		
		ServletUtils.outPrintSuccess(request, response, "刷新缓存成功");
	}
}
