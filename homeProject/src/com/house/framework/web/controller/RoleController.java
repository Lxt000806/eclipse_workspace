package com.house.framework.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.enums.AreaCodeEnum;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Menu;
import com.house.framework.entity.Role;
import com.house.framework.service.AuthorityService;
import com.house.framework.service.MenuService;
import com.house.framework.service.RoleService;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.DepartmentBean;
import com.house.home.entity.basic.Czybm;
import com.house.home.service.basic.CzybmService;

/**
 * 角色管理 Controller
 *
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private CzybmService czybmService;
	@Resource(name = "roleCacheManager")
	private ICacheManager roleCacheManager;
	@Resource(name = "authorityCacheManager")
	private ICacheManager authorityCacheManager;
	@Resource(name = "menuCacheManager")
	private ICacheManager menuCacheManager;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Role role) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
//		role.setSysCode(getUserContext(request).getCzylb());
		this.roleService.findPage(page, role);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFastMenuJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFastMenuJqGrid(HttpServletRequest request,
			HttpServletResponse response,Long roleId) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		this.roleService.goFastMenuJqGrid(page, roleId);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 跳转到角色列表页面
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
		
		return new ModelAndView("system/role/role_list")
			.addObject("superAdmin",CommonConstant.SUPER_ADMIN);
	}
	
	/**
	 * 跳转到新增角色页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增角色页面");
		
		UserContext uc = this.getUserContext(request);
		
		return new ModelAndView("system/role/role_save")
					.addObject("isAdmin", uc.isSuperAdmin() || uc.isOneAdmin())
					.addObject("CityAreaCodeJSON", JSON.toJSONString(DictCacheUtil.getItemsByDictCode(AreaCodeEnum.DICT_CODE.getValue())))
					.addObject("fuJianCode", AreaCodeEnum.FUJIAN.getValue());
	}
	
	/**
	 * 跳转到修改角色页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,Long id){
		logger.debug("跳转到修改角色页面");
		Role role = this.roleService.get(id);
		String cityName = getCityNames(role);
		
		return new ModelAndView("system/role/role_update")
					.addObject("cityName", cityName)
					.addObject("role", role);
	}
	
	@RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, String id){
    	Role role= czybmService.get(Role.class, Long.parseLong(id));
    	List<Map<String,Object>> list = roleService.getRoleList(id);
    	String nodes =null;
    	try {
    		nodes = new ObjectMapper().writeValueAsString(list);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("system/role/role_copyAuth")
    			.addObject("role", role)
    			.addObject("nodes", nodes);
                   
    }
	
	/**
	 * 跳转到查看角色页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response,Long id,String ptDescr){
		logger.debug("跳转到查看角色页面");
		Role role = this.roleService.get(id);
		
		//获取角色分配的用户
		List<Czybm> userList = this.czybmService.getByRoleId(id);
		String userNames = IdUtil.joinEntityList(userList, "loginName", " , ");

		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(id);
		List<Authority> authList = this.authorityService.getByRoleIds(roleIds);
		String authNames = IdUtil.joinEntityList(authList, "authName", " , ");
		String cityName = getCityNames(role);
		role.setPtDescr(ptDescr);
		return new ModelAndView("system/role/role_detail")
					.addObject("role", role)
					.addObject("cityName", cityName)
					.addObject("userNames", userNames)
					.addObject("authNames", authNames);
	}
	
	
	/**
	 * 跳转分配角色权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/goAuthority")
	public ModelAndView goAuthority(HttpServletRequest request, HttpServletResponse response, Long id){
		logger.debug("");
		UserContext uc = this.getUserContext(request);
		Role role = roleService.get(id);
		List<Menu> menuList = menuService.getBySysCode(role.getSysCode());
//		List<Menu> menuList = this.menuService.getAll();
		List<Authority> authList = null;
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//虚拟权限根节点
		nodes.add(newVirtualRootNode());
		
		authList = this.authorityService.getAll();
//		//超级管理员可以分配所有的权限
//		if(uc.isSuperAdmin()){
//			authList = this.authorityService.getAll();
//		//其他用户只能分配自己拥有的权限
//		}else{
//			authList = this.authorityService.getByCzybh(uc.getCzybh());
//		}
		//获取已分配给角色的权限
		List<Long> selIds = this.roleService.getAuthIdsByRoleId(id);
		
		if (menuList!=null && menuList.size()>0){
			List<Long> list = new ArrayList<Long>();
			for (Menu menu : menuList){
				list.add(menu.getMenuId());
			}
			//树菜单节点JSON格式
			nodes.addAll(this.treeNodeMapper(menuList,null,list));
			//树权限节点JSON格式
			nodes.addAll(this.treeNodeMapper(authList,selIds,list));
		}
		
		String json = "[]";
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		
		return new ModelAndView("system/role/role_auth_tree")
					.addObject("nodes", json)
					.addObject("roleId", id)
					.addObject("operatorType", uc.getJslx());
	}
	
	/**
	 * 跳转快捷菜单页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goFastMenu")
	public ModelAndView goFastMenu(HttpServletRequest request, HttpServletResponse response, Long id){
		logger.debug("跳转快捷菜单页面");
		List<Map<String,Object>> menuList =roleService.getMenuByRoId(id);
		String json = "[]";
		if(null != menuList && menuList.size()>0)
			json = JSON.toJSONString(menuList);
		return new ModelAndView("system/role/role_fastMenu")
		.addObject("roleId", id).addObject("nodes", json)
		.addObject("czybh", getUserContext(request).getCzybh());
	}
	
	/**
	 * 添加角色
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Role role, BindingResult bindingResult){
		logger.debug("添加角色开始");
		springValidator.validate(role, bindingResult);
		
		if(!validate(role.getRoleCode(), null)){
			bindingResult.rejectValue("roleCode", "","角色编码 ["+role.getRoleCode()+"] 已存在");
		}
		
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
		UserContext uc = this.getUserContext(request);
		role.setCreaterId(uc.getCzybh());
		
		String cityCodes = request.getParameter("cityCodes");
		if(StringUtils.isBlank(cityCodes)){
			role.setCityCode(uc.getCityCode());
			this.roleService.save(role);
			roleCacheManager.refresh();
			logger.debug("添加角色结束，USRID={} 创建一条本地市角色 ROLEID={}", new Object[]{uc.getCzybh(),role.getRoleId()});
		}else{
			role.setCityCode(cityCodes);
			this.roleService.save(role);
			roleCacheManager.refresh();
			logger.debug("添加角色结束，USRID={} 创建一条多地市角色 ROLEID={}", new Object[]{uc.getCzybh(),cityCodes});
		}
		
		ServletUtils.outPrintSuccess(request, response);
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Role role, BindingResult bindingResult){
		logger.debug("修改角色开始");
		springValidator.validate(role, bindingResult);
		if(!validate(role.getRoleCode(), request.getParameter("oldRoleCode"))){
			bindingResult.rejectValue("roleCode", "","角色编码 ["+role.getRoleCode()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
		this.roleService.update(role);
		roleCacheManager.refresh();
		logger.debug("修改角色 ID={} 结束", role.getRoleId());
		ServletUtils.outPrintSuccess(request, response);
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response){
		logger.debug("删除角色开始");
		String roleIds = request.getParameter("deleteIds");
		if(StringUtils.isBlank(roleIds)){
			ServletUtils.outPrintFail(request, response, "角色ID不能为空,删除失败");
			return ;
		}
		if(roleService.isUsedByCzy(roleIds).size()>0){
			ServletUtils.outPrintFail(request, response, "存在已分配给相关操作员的角色,删除失败");
			return ;
		}
		if(roleService.isUsedByAuth(roleIds).size()>0){
			ServletUtils.outPrintFail(request, response, "存在已分配权限的角色,删除失败");
			return ;
		}
		List<Long> roleIdList = IdUtil.splitIds(roleIds);
		
		this.roleService.delete(roleIdList);
		roleCacheManager.refresh();
		logger.debug("删除角色 IDS={} 完成",roleIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 设置角色权限
	 * @param request
	 * @param response
	 * @param roleId 
	 */
	@RequestMapping("/doSaveRoleAuth")
	public void doSaveRoleAuth(HttpServletRequest request, HttpServletResponse response, Long id){
		logger.debug("设置角色权限开始");
		if(null == id){
			ServletUtils.outPrintFail(request, response, "角色ID不能为空,设置失败");
			return ;
		}
		String addIds = request.getParameter("addIds");
		String delIds = request.getParameter("delIds");
		
		List<Long> addList = IdUtil.splitIds(addIds);
		List<Long> delList = IdUtil.splitIds(delIds);
		
		this.roleService.setRoleAuths(id, addList, delList);
		authorityCacheManager.refresh();
		menuCacheManager.refresh();
		logger.debug("设置角色 ID={} 权限 新增权限 AUTHIDS={} 删除权限 AUTHIDS={} 结束",new Object[]{id, addList, delList});
		ServletUtils.outPrintSuccess(request, response,"设置角色权限成功");
	}
	
	/**
	 * 验证角色编码不能重复
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doValidate")
	public void doValidate(HttpServletRequest request, HttpServletResponse response) {
		String roleCode = request.getParameter("roleCode");
		if(!validate(roleCode, null)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		ServletUtils.outPrintSuccess(request, response,"验证通过");
		return ;
	}

	
	private boolean validate(String roleCode, String oldRoleCode){
		if(StringUtils.isBlank(roleCode))
			return false;
		if(StringUtils.isNotBlank(oldRoleCode)){
			if(oldRoleCode.trim().equals(roleCode))
				return true;
		}
		return this.roleService.getByRoleCode(roleCode.trim()) == null;
	}
	
	/**
	 * 复制权限保存操作
	 * @param request
	 * @param response
	 * @param no
	 * @param message
	 * @param detailJson
	 */
	@RequestMapping("/doCopyRight")
	public void doCopyRight(HttpServletRequest request,HttpServletResponse response,Role role){
		try{
			UserContext uc = getUserContext(request);
			role.setOperatorType(uc.getJslx());
			Result result = roleService.doCopyRight(role);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "保存成功");
				authorityCacheManager.refresh();
				menuCacheManager.refresh();
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 虚拟权限根节点
	 * @return
	 */
	private Map<String, Object> newVirtualRootNode(){
		Map<String, Object> virtualRoot = new HashMap<String, Object>();
		virtualRoot.put("id", "0");
		virtualRoot.put("pId", "-1");
		virtualRoot.put("name", "权限分配");
		virtualRoot.put("isParent", true);
		virtualRoot.put("open", true);
		virtualRoot.put("nodeType", "menu");
		return virtualRoot;
	}
	
	@SuppressWarnings("unused")
	private List<String> getSystemDefaultRoleId(HttpServletRequest request){
		UserContext uc = this.getUserContext(request);
		//获取 超级管理员，一级管理员，二级管理员
		List<Czybm> superAdmins = this.czybmService.getByRoleCode(CommonConstant.SUPER_ADMIN);
		List<Czybm> proAdmins = this.czybmService.getByRoleCode(CommonConstant.ONE_ADMIN);
		List<Czybm> cityAdmins = this.czybmService.getByRoleCode(CommonConstant.TWO_ADMIN);
		
		List<String> forbidIdList = new ArrayList<String>();
		if(superAdmins != null && superAdmins.size() > 0){
			for(Czybm user : superAdmins){
				forbidIdList.add(user.getCzybh());
			}
		}
		if(proAdmins != null && proAdmins.size() > 0){
			for(Czybm user : proAdmins){
				forbidIdList.add(user.getCzybh());
			}
		}
		if(cityAdmins != null && cityAdmins.size() > 0){
			for(Czybm user : cityAdmins){
				if(!user.getCzybh().equals(uc.getCzybh()))
					forbidIdList.add(user.getCzybh());
			}
		}
		
		return forbidIdList;
	}
	
	private String getCityNames(Role role){
		StringBuffer sb = new StringBuffer();
		if (role!=null && StringUtils.isNotBlank(role.getCityCode())){
			String[] citys = role.getCityCode().split(",");
			int i = 0;
			for (String str : citys){
				sb.append(DictCacheUtil.getItemLabel(AreaCodeEnum.DICT_CODE.getValue(), str));
				if (i != citys.length-1){
					sb.append(",");
				}
				i++;
			}
		}
		return sb.toString();
		
	}
	
	/**
	 * 跳转分配操作员APP权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goAppAuthority")
	public ModelAndView goAppAuthority(HttpServletRequest request, HttpServletResponse response, Long id){
		logger.debug("");
		Role role = roleService.get(id);
		List<Map<String, Object>> allMkList = czybmService.getQX_ALLMK(2);
		List<Map<String, Object>> hasMkList = roleService.getQX_RoleQX(id, 2);
		Set<String> set=new LinkedHashSet<String>();
		Set<String> set1=new LinkedHashSet<String>();
		List<DepartmentBean> list = new ArrayList<DepartmentBean>();
		UserContext uc = getUserContext(request);
		for(Map<String, Object> map : allMkList){
			set.add(map.get("XTDM")+","+map.get("XTMC"));
		}
		for(Map<String, Object> map : allMkList){
			set1.add(map.get("MKDM")+","+map.get("MKMC")+","+map.get("MK_ISAdminAssign"));
		}
		for (Map<String, Object> map : allMkList) {
			DepartmentBean bean = new DepartmentBean();
            if (map.get("GNMC")!=null) {
            	bean.setId((String)map.get("GNMC"));
    			bean.setName((String)map.get("GNMC"));
    			bean.setPid((String)map.get("MKDM"));
    			if(!"ADMIN".equals(uc.getJslx()) && "1".equals((String)map.get("GN_ISAdminAssign"))){
    				bean.setChkDisabled(true);
    			}
    			list.add(bean);
    			bean = null;
            }
        } 
		for(String str:set){		
			DepartmentBean bean = new DepartmentBean();
            	bean.setId(str.substring(0, str.indexOf(",")));
    			bean.setName(str.substring(str.indexOf(",")+1));
    			bean.setPid("0");
    			list.add(bean);
    			bean = null;   
		}
		for(String str1:set1){		
			DepartmentBean bean = new DepartmentBean();
            	bean.setId(str1.substring(0, str1.indexOf(",")));
    			//bean.setName(str1.substring(str1.indexOf(",")+1));
            	bean.setName(str1.substring(str1.indexOf(",")+1, str1.lastIndexOf(",")));
    			bean.setPid("I");
    			if(!"ADMIN".equals(uc.getJslx()) && "1".equals(str1.substring(str1.lastIndexOf(",")+1))){
    				bean.setChkDisabled(true);
    			}
    			list.add(bean);
    			bean = null;   
		}
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//树菜单节点JSON格式
		nodes.addAll(this.treeNodeMapper_czyqx(list, hasMkList));
		String json = "[]";
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		
		return new ModelAndView("system/role/role_appAuth")
			.addObject("nodes", json)
			.addObject("role", role);
	}
	
	public List<Map<String, Object>> treeNodeMapper_czyqx(List<DepartmentBean> list,List<Map<String, Object>> selIds){
		List<Map<String, Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(list != null && list.size()>0){
			rsList = new ArrayList<Map<String,Object>>(list.size());
			for(DepartmentBean bean : list){
				map = this.beanNodeMapper_czyqx(bean,selIds);
				if(map != null){
					rsList.add(map);
				}
			}
		}
		return rsList;
	}
	private Map<String,Object> beanNodeMapper_czyqx(DepartmentBean depart,List<Map<String, Object>> selIds){
		if(depart == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		map = new HashMap<String,Object>();
		map.put("id", depart.getId());
		map.put("name", depart.getName());
		map.put("pId", (depart.getPid() == null) ? "0" : depart.getPid());
		map.put("isParent", false);
		map.put("open", false);
		map.put("nodeType", "department");
		map.put("chkDisabled", depart.isChkDisabled());
		for (Map<String, Object> maps : selIds) {
			if(maps.get("GNMC")!=null){
				if(((String)maps.get("GNMC")).equals(depart.getId())&&((String)maps.get("MKDM")).equals(depart.getPid())){
					map.put("checked", true);
					break;
				}
			}else{
				if(((String)maps.get("MKDM")).equals(depart.getId())){
					map.put("checked", true);
					break;
				}	
			}
        } 
		return map;
	}
	
	/**
	 * app权限保存操作
	 * @param request
	 * @param response
	 * @param detailJson
	 */
	@RequestMapping("/doAppRight")
	public void doAppRight(HttpServletRequest request,HttpServletResponse response,Role role){
		try{

			Result result = roleService.doAppRight(role);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
			e.printStackTrace();
		}
	}	
	
	/**
	 * 快捷菜单保存操作
	 * @param request
	 * @param response
	 * @param detailJson
	 */
	@RequestMapping("/doSaveFastMenu")
	public void doSaveFastMenu(HttpServletRequest request,HttpServletResponse response,Role role){
		try{

			Result result = roleService.doSaveFastMenu(role);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
			e.printStackTrace();
		}
	}	
	
}
