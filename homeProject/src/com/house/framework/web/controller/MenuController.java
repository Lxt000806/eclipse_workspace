package com.house.framework.web.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Exceptions;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Menu;
import com.house.framework.service.MenuService;
import com.house.framework.web.login.UserContext;

/**
 * 菜单管理Controller
 *
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	@Resource(name = "menuCacheManager")
	private ICacheManager menuCacheManager;
	
	/**
	 * 跳转到菜单框架页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goMain")
	public ModelAndView goMain(HttpServletRequest request, HttpServletResponse response, Long initId){
		logger.debug("跳转到菜单框架页面");
		if(initId == null || initId.longValue() == 0){
			List<Menu> rootMenus = this.menuService.getSubMenu(0L);
			if(rootMenus != null && rootMenus.size() > 0){
				initId = rootMenus.get(0).getMenuId();
			}
		}
		
		return new ModelAndView("system/menu/menu_main").addObject("initId", initId);
	}
	
	/**
	 * 跳转到菜单树页面，进行菜单维护
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goTree")
	public ModelAndView goTree(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到菜单树页面");
		List<Menu> list = this.menuService.getAll();
//		List<Menu> list = this.menuService.getBySysCode(this.getUserContext(request).getCzylb());
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);
		
		return new ModelAndView("system/menu/menu_tree")
					.addObject("nodes", json)
					.addObject("rootMenuId", request.getParameter("rootMenuId"));
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, Long parentId) {
		logger.debug("跳转到添加页面");
		String pName = "";
		String pMenuType = "";
		if(parentId != null){
			try {
				Menu menu = this.menuService.get(parentId);
				if(menu != null){
					pName = menu.getMenuName();
					pMenuType = menu.getMenuType();
					if(DictConstant.DICT_MENU_TYPE_URL.equals(pMenuType)){
						logger.error("父菜单 id={} 类型不能是URL类型菜单",parentId);
						throw new RuntimeException("父菜单类型不能是URL类型菜单");
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error("菜单parentId=["+parentId+"] 类型转换出错", e);
				throw Exceptions.unchecked(e);
			}
		}
		return new ModelAndView("system/menu/menu_save")
					.addObject("parentId", parentId)
					.addObject("pName", pName)
					.addObject("pMenuType", pMenuType)
					.addObject("openTypeCode",DictConstant.ABSTRACT_DICT_MENU_OPEN)
					.addObject("menuTypeCode", DictConstant.ABSTRACT_DICT_MENU_TYPE)
					.addObject("tabMenuType", DictConstant.DICT_MENU_TYPE_TAB)
					.addObject("folderMenuType", DictConstant.DICT_MENU_TYPE_FOLDER)
					.addObject("urlMenuType", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("nextNum", this.menuService.getNextNum(parentId));
	}
	
	/**
	 * 跳转到修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Long menuId) {
		logger.debug("跳转到菜单修改页面");
		if(menuId == null){
			throw new RuntimeException("菜单ID不能为空");
		}
		Menu menu = this.menuService.get(menuId);
		if(menu == null){
			logger.error("查找不到 ID={} 的菜单对象", menuId);
			throw new RuntimeException("菜单不存在");
		}
		Long pId = 0L;
		String pName = "";
		String pMenuType = "";
		if(menu.getParentId() != null && menu.getParentId().longValue() != 0){
			Menu pMenu = this.menuService.get(menu.getParentId());
			pName = pMenu.getMenuName();
			pMenuType = pMenu.getMenuType();
			pId = pMenu.getMenuId();
		}
		
		return new ModelAndView("system/menu/menu_update")
					.addObject("parentId", pId)
					.addObject("pName", pName)
					.addObject("pMenuType", pMenuType)
					.addObject("openTypeCode",DictConstant.ABSTRACT_DICT_MENU_OPEN)
					.addObject("menuTypeCode", DictConstant.ABSTRACT_DICT_MENU_TYPE)
					.addObject("tabMenuType", DictConstant.DICT_MENU_TYPE_TAB)
					.addObject("folderMenuType", DictConstant.DICT_MENU_TYPE_FOLDER)
					.addObject("urlMenuType", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("menu", menu);
	}	
	
	/**
	 * 跳转到菜单查看页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, Long menuId){
		logger.debug("跳转到菜单维护页面");
		
		ModelAndView mav = new ModelAndView("system/menu/menu_detail");
		
		Menu menu = null;
		if(menuId != null && (menu = this.menuService.get(menuId)) != null){
			mav.addObject("menu", menu);
			if(menu.getParentId() != null && menu.getParentId().longValue() != 0){
				Menu pMenu = this.menuService.get(menu.getParentId());
				if(pMenu != null){
					mav.addObject("parentName", pMenu.getMenuName());
				}
			}
			String openType = DictCacheUtil.getItemLabel(DictConstant.ABSTRACT_DICT_MENU_OPEN, menu.getOpenType());
			mav.addObject("openTypeName", openType == null ? "" : openType);
			String menuType = DictCacheUtil.getItemLabel(DictConstant.ABSTRACT_DICT_MENU_TYPE, menu.getMenuType());
			mav.addObject("menuTypeName", menuType == null ? "" : menuType);
			String sysCode = DictCacheUtil.getItemLabel("ptdm", menu.getSysCode());
			mav.addObject("sysCodeName", sysCode == null ? "" : sysCode);
		}
		
		mav.addObject("urlMenuType", DictConstant.DICT_MENU_TYPE_URL);
		return mav;
	}
	/**
	 * 根据菜单路径查询菜单详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getMenu")
	@ResponseBody
	public JSONObject getMenu(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Menu menu = menuService.findByUrl(id);
		if(menu == null){
			return this.out("系统中不存在code="+id+"的菜单信息", false);
		}
		return this.out(menu, true);
	}
	/**
	 * 菜单Code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("system/menu/menu_code");
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Menu menu) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		menuService.findPageBySql(page, menu);
		return new WebPage<Map<String,Object>>(page);
	}
	
//--------------------------------------------------------------------------------------------
	
	/**
	 * 添加菜单
	 * @param request
	 * @param response
	 * @param menu
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Menu menu, BindingResult bindingResult){
		logger.debug("添加菜单开始");
		//测试是否能获取页面参数
//		String status = request.getParameter("status");
//		String code = request.getParameter("code");
		springValidator.validate(menu, bindingResult);
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
//		menu.setSysCode(getUserContext(request).getCzylb());
		this.menuService.save(menu);
		menuCacheManager.refresh();
		logger.debug("添加菜单 ID={} 结束",menu.getMenuId());
		ServletUtils.outPrintSuccess(request, response, "保存成功", menu);
	}
	
	/**
	 * 修改菜单
	 * @param request
	 * @param response
	 * @param menu
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Menu menu, BindingResult bindingResult){
		logger.debug("修改菜单开始");
		springValidator.validate(menu, bindingResult);
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		//订制菜单的状态跟编码不变
//		Menu befmenu = this.menuService.get(menu.getMenuId());
		this.menuService.update(menu);
		menuCacheManager.refresh();
		logger.debug("修改菜单 ID={} 结束",menu.getMenuId());
		ServletUtils.outPrintSuccess(request, response);
	}
	
	/**
	 * 删除菜单
	 * @param request
	 * @param response
	 * @param menuId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Long menuId){
		logger.debug("删除菜单开始");
		if(null == menuId){
			logger.debug("删除菜单失败,菜单ID不能为空");
			ServletUtils.outPrintFail(request, response, "删除菜单失败,菜单ID不能为空");
			return ;
		}
		List<Menu> subList = this.menuService.getSubMenu(menuId);
		if(null !=subList && subList.size()>0){
			logger.debug("删除菜单失败,存在 ["+subList.size()+"] 个子菜单，请先删除子菜单");
			ServletUtils.outPrintFail(request, response, "删除菜单失败,存在 ["+subList.size()+"] 个子菜单，请先删除子菜单");
			return ;
		}
		this.menuService.delete(menuId);
		menuCacheManager.refresh();
		logger.debug("删除菜单 ID={} 结束",menuId);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 获取菜单具体信息，结果以JSON格式返回
	 * @param request
	 * @param response
	 * @param menuId
	 */
	@RequestMapping("/getMenuInfo")
	public void getMenuInfo(HttpServletRequest request, HttpServletResponse response, Long menuId){
		logger.debug("获取菜单具体信息");
		if(menuId == null){
			ServletUtils.outPrintFail(request, response, "菜单Id为空");
		}
		Menu menu = this.menuService.get(menuId);
		if(menu == null){
			ServletUtils.outPrintFail(request, response, "部门对象为空");
			return ;
		}
		StringBuilder strb = new StringBuilder("{\"rs\":true,\"datas\":{");
		String menuType = DictCacheUtil.getItemLabel(DictConstant.ABSTRACT_DICT_MENU_TYPE, menu.getMenuType());
		strb.append("\"menuTypeName\":\"").append(menuType == null ? "" : menuType).append("\",");
		String openType = DictCacheUtil.getItemLabel(DictConstant.ABSTRACT_DICT_MENU_OPEN, menu.getOpenType());
		strb.append("\"openTypeName\":\"").append(openType == null ? "" : openType).append("\",");
		String sysCode = DictCacheUtil.getItemLabel("ptdm", menu.getSysCode());
		strb.append("\"sysCodeName\":\"").append(sysCode == null ? "" : sysCode).append("\",");
		if(menu.getParentId() != null && menu.getParentId() != 0){
			Menu parentMenu = this.menuService.get(menu.getParentId());
			if(parentMenu != null){
				strb.append("\"parentName\":\"").append(parentMenu.getMenuName()).append("\",");
			}
		}
		strb.append(JSON.toJSONString(menu).substring(1)).append("}");
		ServletUtils.outPrintMsg(request, response, strb.toString());
	}
	
	/**
	 * 获取菜单具体信息，结果以JSON格式返回
	 * @param request
	 * @param response
	 * @param menuId
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/treeMenus")
	public void getTreeMenus(HttpServletRequest request, HttpServletResponse response, Long menuId){
		logger.debug("获取用户tab下的菜单开始");
		UserContext userContext = this.getUserContext(request);
		
		if(menuId == null){
			ServletUtils.outPrintFail(request, response, "菜单Id为空");
		}
		
		List<Map> lst =  menuService.getMenusByUser(userContext.getCzybh(), menuId);
		logger.debug("获取用户 ID={} tab下的菜单结束",userContext.getCzybh());
		ServletUtils.outPrintSuccess(request, response, lst);
	}
	
	/**
	 * 验证
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doValidate")
	public void doValidate(HttpServletRequest request, HttpServletResponse response){
		String menuName = request.getParameter("menuName");
		
		if(!validate(menuName, null)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		ServletUtils.outPrintSuccess(request, response, "可用");
		return ;
	}
	
	//---------------------------------------------------------------------------------------
	private boolean validate(String menuName, String oldMenuName){
		if(StringUtils.isBlank(menuName))
			return false;
		if(StringUtils.isNotBlank(oldMenuName)){
			if(oldMenuName.trim().equals(menuName))
				return true;
		}
		return this.menuService.getByMenuName(menuName.trim()) == null;
	}
}
