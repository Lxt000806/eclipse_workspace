package com.house.framework.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Menu;
import com.house.framework.entity.Resources;
import com.house.framework.service.AuthorityService;
import com.house.framework.service.MenuService;

/**
 * 权限管理Controller
 */
@Controller
@RequestMapping("/admin/authority")
public class AuthorityController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);
	
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private MenuService menuService;
	@Resource(name = "authorityCacheManager")
	private ICacheManager authorityCacheManager;
	
	/**
	 * 跳转到权限主框架页面
	 * @param request
	 * @param response
	 * @param authority
	 * @return
	 */
	@RequestMapping("/goMain")
	public ModelAndView goMain(HttpServletRequest request, HttpServletResponse response, Authority authority) {
		logger.debug("跳转到权限主框架页面");
		
		return new ModelAndView("system/authority/authority_main");
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
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Authority authority) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		authorityService.findPage(page, authority);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 跳转到权限列表页面
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response, 
			Authority authority){
		logger.debug("跳转到权限列表页面");
		
		return new ModelAndView("system/authority/authority_list")
					.addObject("MENU_TYPE_URL", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("authority", authority);
	}
	
	/**
	 * 跳转到权限树页面
	 * @param request
	 * @param response
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/goTree")
	public ModelAndView goUserDepartTree(HttpServletRequest request, HttpServletResponse response, Long rootId){
		logger.debug("跳转到权限树页面");
		List<Menu> list = this.menuService.getAll();
		String json = "[]";
		if(null != list && list.size()>0)
			json = JSON.toJSONString(list);

		return new ModelAndView("system/authority/authority_tree").addObject("nodes", json);
	}
	
	/**
	 * 跳转到新增权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增权限页面");
		String defaultMenuIdStr = request.getParameter("defaultMenuId");
		Long defaultMenuId = 0L;
		if(StringUtils.isNotBlank(defaultMenuIdStr)){
			defaultMenuId = Long.valueOf(defaultMenuIdStr.trim());
		}
		List<Menu> list = this.menuService.getAll();
		return new ModelAndView("system/authority/authority_save")
					.addObject("menus", JSON.toJSONString(list))
					.addObject("defaultMenuId", defaultMenuId)
					.addObject("URL_MENU",DictConstant.DICT_MENU_TYPE_URL);
	}
	
	/**
	 * 跳转到更新权限页面
	 * @param request
	 * @param response
	 * @param authorityId
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Long authorityId){
		logger.debug("跳转到更新权限页面");
		Authority authority = this.authorityService.get(authorityId);
		List<Menu> list = this.menuService.getAll();
		
		return new ModelAndView("system/authority/authority_update")
					.addObject("authority", authority)
					.addObject("menus", JSON.toJSONString(list))
					.addObject("URL_MENU", DictConstant.DICT_MENU_TYPE_URL)
					.addObject("size", authority.getResourcesList().size());
	}
	
	/**
	 * 跳转到查看权限页面
	 * @param request
	 * @param response
	 * @param authorityId
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, Long authorityId){
		logger.debug("跳转到查看权限页面");
		Authority authority = this.authorityService.get(authorityId);
		String menuName = "";
		Menu menu = this.menuService.get(authority.getMenuId());
		if(menu != null)
			menuName = menu.getMenuName();
		
		return new ModelAndView("system/authority/authority_detail")
						.addObject("authority", authority)
						.addObject("menuName", menuName);
	}
	
//--------------------------------------------------------------------------------------------
	
	/**
	 * 添加权限
	 * @param request
	 * @param response
	 * @param authority
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Authority authority, BindingResult bindingResult){
		logger.debug("添加权限开始");
		springValidator.validate(authority, bindingResult);
		if(authority.getAuthCode().indexOf(",") != -1){
			bindingResult.rejectValue("authCode", "","权限编码不能包含逗号");
		}
		if(!this.validate(authority.getAuthCode(), null)){
			bindingResult.rejectValue("authCode", "","权限编码 ["+authority.getAuthCode()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
		String[] urlStrs = request.getParameterValues("urlStr");
		String[] urlRemarks = request.getParameterValues("urlRemark");
		List<Resources> resourcesList = null;
		
		if(urlStrs == null || urlStrs.length < 1){
			this.authorityService.save(authority);
		}else{
			resourcesList = new ArrayList<Resources>(urlStrs.length);
			Resources resources = null;
			for(int i=0; i<urlStrs.length; i++){
				if(StringUtils.isNotBlank(urlStrs[i])){
					resources = new Resources();
					resources.setGenTime(new Date());
					resources.setUrlStr(urlStrs[i]);
					if(urlRemarks != null && urlRemarks.length > 0)
						resources.setRemark(urlRemarks[i]);
					resourcesList.add(resources);
				}
			}
		}
		this.authorityService.save(authority, resourcesList);
		authorityCacheManager.refresh();
		logger.debug("添加权限 ID={} 成功",authority.getAuthorityId());
		ServletUtils.outPrintSuccess(request, response);
	}
	
	/**
	 * 修改权限
	 * @param request
	 * @param response
	 * @param authority
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Authority authority, BindingResult bindingResult){
		logger.debug("修改权限开始");
		springValidator.validate(authority, bindingResult);
		if(authority.getAuthCode().indexOf(",") != -1){
			bindingResult.rejectValue("authCode", "", "权限编码不能包含逗号");
		}
		if(!this.validate(authority.getAuthCode(), request.getParameter("oldAuthCode"))){
			bindingResult.rejectValue("authCode", "", "权限编码 ["+authority.getAuthCode()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
		String[] urlStrs = request.getParameterValues("urlStr");
		String[] urlRemarks = request.getParameterValues("urlRemark");
		List<Resources> resourcesList = null;
		
		if(urlStrs != null && urlStrs.length > 0){
			resourcesList = new ArrayList<Resources>(urlStrs.length);
			Resources resources = null;
			for(int i=0; i<urlStrs.length; i++){
				if(StringUtils.isNotBlank(urlStrs[i])){
					resources = new Resources();
					resources.setGenTime(new Date());
					resources.setUrlStr(urlStrs[i]);
					if(urlRemarks != null && urlRemarks.length > 0)
						resources.setRemark(urlRemarks[i]);
					resourcesList.add(resources);
				}
			}
		}
		
		this.authorityService.update(authority,resourcesList);
		authorityCacheManager.refresh();
		logger.debug("修改权限 ID={} 成功", authority.getAuthorityId());
		ServletUtils.outPrintSuccess(request, response, "修改权限成功");
	}
	
	/**
	 * 批量删除权限
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response){
		logger.debug("批量删除权限开始");
		String authIds = request.getParameter("deleteIds");
		if(StringUtils.isBlank(authIds)){
			ServletUtils.outPrintFail(request, response, "权限ID不能为空,删除失败");
			return ;
		}
		List<Long> authIdList = IdUtil.splitIds(authIds);
		
		this.authorityService.delete(authIdList);
		authorityCacheManager.refresh();
		logger.debug("批量删除权限 IDS={} 结束",authIdList);
		ServletUtils.outPrintSuccess(request, response, "删除[ "+authIdList.size()+" ] 条权限成功");
	}
	
	/**
	 * 验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/doValidate")
	public void doValidate(HttpServletRequest request, HttpServletResponse response){
		
		String authCode = request.getParameter("authCode");
		if(!this.validate(authCode, null)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		ServletUtils.outPrintSuccess(request, response,"验证通过");
		return ;
	}
	
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Authority authority){
		List<Map<String, Object>> dataList = null;
		dataList = this.authorityService.findAll(authority);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, dataList, UUID.randomUUID().toString(), 
				columnList, titleList, sumList);
	}

	//------------------------------------------------------------------------------------
	
	private boolean validate(String authCode, String oldAuthCode){
		if(StringUtils.isBlank(authCode))
			return false;
		if(StringUtils.isNotBlank(oldAuthCode)){
			if(oldAuthCode.trim().equals(authCode))
				return true;
		}
		Authority authority = this.authorityService.getByAuthCode(authCode.trim());
		return authority == null;
	}
}
