package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.DepartmentCacheManager;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.IpUtils;
import com.house.framework.commons.utils.RSAUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Menu;
import com.house.framework.service.AuthorityService;
import com.house.framework.service.MenuService;
import com.house.framework.service.RoleService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.bean.basic.DepartmentBean;
import com.house.home.client.util.MD5EncryptionMgr;
import com.house.home.entity.basic.CzyDept;
import com.house.home.entity.basic.CzyMtRegion;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.insales.WareHouseOperater;
import com.house.home.service.basic.CzyDeptService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.insales.WareHouseOperaterService;
import com.house.home.service.insales.WareHouseService;

@Controller
@RequestMapping("/admin/czybm")
public class CzybmController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzybmController.class);

	@Autowired
	private CzybmService czybmService;
	@Autowired
	private DepartmentCacheManager departmentManager;
	@Autowired
	private CzyDeptService czyDeptService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmployeeService employeeService;
	@Resource(name = "authorityCacheManager")
	private ICacheManager authorityCacheManager;
	@Resource(name = "menuCacheManager")
	private ICacheManager menuCacheManager;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private WareHouseOperaterService wareHouseOperaterService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private AuthorityService authorityService;
	
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param czybm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Czybm czybm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
//		czybm.setCzylb(getUserContext(request).getCzylb());
		czybmService.findPageBySql(page, czybm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * Czybm列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Czybm czybm) throws Exception {
			czybm.setCzylb("1");
		return new ModelAndView("admin/basic/czybm/czybm_list_jqGrid");
	}
	
	/**
	 * 跳转分配操作员权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goCzybmAuthority")
	public ModelAndView goCzybmAuthority(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("");
		Czybm czybm = czybmService.get(Czybm.class, id);
		UserContext uc = this.getUserContext(request);
		List<Menu> menuList = menuService.getBySysCode(czybm.getCzylb());
		List<Authority> authList = null;
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//虚拟权限根节点
		nodes.add(newVirtualRootNode());
		
		authList = this.authorityService.getAll();
		
		//获取已分配给角色的权限
//		List<Long> selIds = this.roleService.getAuthIdsByCzybh(id);
//		List<Long> selIds = this.czybmService.getAuthIdsByCzybh(id);
		List<Long> selIds = czybmService.getPersonalAuthIdsByCzybh(id);
		
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
		return new ModelAndView("admin/basic/czybm/czybm_auth_tree")
					.addObject("nodes", json)
					.addObject("czybh", id)
					.addObject("operatorType", uc.getJslx());
	}
	
	/**
	 * 设置操作员权限
	 * @param request
	 * @param response
	 * @param roleId 
	 */
	@RequestMapping("/doSaveCzybmAuth")
	public void doSaveCzybmAuth(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("设置操作员权限开始");
		if(null == id){
			ServletUtils.outPrintFail(request, response, "操作员编号不能为空,设置失败");
			return ;
		}
		String addIds = request.getParameter("addIds");
		String delIds = request.getParameter("delIds");
		
		List<Long> addList = IdUtil.splitIds(addIds);
		List<Long> delList = IdUtil.splitIds(delIds);
		

//		this.czybmService.setCzybmAuths(id, addList, delList);
		/*if (result.isSuccess()){
			ServletUtils.outPrint(request, response, true, result.getInfo(), null, true);
		}else{
			ServletUtils.outPrintFail(request, response, result.getInfo());
		}*/
		//this.czybmService.setCzybmAuths(id, addList, delList);
		Result result = this.czybmService.setCzybmAuths(id, addList, delList);
		if (result.isSuccess()){
			ServletUtils.outPrint(request, response, true, result.getInfo(), null, true);
			authorityCacheManager.refresh();
			menuCacheManager.refresh();
			//refreshOhterServerCache(request);
		}else{
			ServletUtils.outPrintFail(request, response, result.getInfo());
		}
		
	/*	logger.debug("设置操作员 czybh={} 权限 新增权限 AUTHIDS={} 删除权限 AUTHIDS={} 结束",new Object[]{id, addList, delList});
		ServletUtils.outPrintSuccess(request, response,"设置操作员权限成功");*/
	}
	
	/**
	 * 根据操作员编号查询操作员详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCzybm")
	@ResponseBody
	public JSONObject getCzybm(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Czybm czybm = czybmService.get(Czybm.class, id);
		if(czybm == null){
			return this.out("系统中不存在code="+id+"的操作员信息", false);
		}
		return this.out(czybm, true);
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
			HttpServletResponse response,Czybm czybm) throws Exception {

		return new ModelAndView("admin/basic/czybm/czybm_code").addObject("czybm",czybm);
	}
	
	/**
	 * 跳转到新增Czybm页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增Czybm页面");
		Czybm czybm = null;
		if (StringUtils.isNotBlank(id)){//复制
			czybm = czybmService.get(Czybm.class, id);
			Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
			czybm.setEmnumDescr(employee==null?"":employee.getNameChi());
			czybm.setMm(DesUtils.decode(czybm.getMm()));
			czybm.setUserRole(roleService.getRolesByCzybh(czybm.getCzybh()));
		}else{
			czybm = new Czybm();
			czybm.setCzylb("1");
		}
		String json = getJsonTree(id);
		String jsonDept = getSelectedJsonTree(id);
		String jsonCkAll = getJsonTreeCk(id);
		String jsonCkSelect = getSelectedJsonTreeCk(id);
		UserContext uc = this.getUserContext(request);
		return new ModelAndView("admin/basic/czybm/czybm_save")
			.addObject("czybm", czybm)
			.addObject("nodes", json)
			.addObject("nodesDept", jsonDept)
			.addObject("nodesCkAll", jsonCkAll)
			.addObject("nodesCkSelect", jsonCkSelect)
		    .addObject("operatorType", uc.getJslx());
		
	}
	/**
	 * 跳转到修改Czybm页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Czybm页面");
		Czybm czybm = null;
		UserContext uc = this.getUserContext(request);
		if (StringUtils.isNotBlank(id)){
			czybm = czybmService.get(Czybm.class, id);
		}else{
			czybm = czybmService.get(Czybm.class, uc.getCzybh());
		}
		Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
		czybm.setEmnumDescr(employee==null?"":employee.getNameChi());
		czybm.setMm(DesUtils.decode(czybm.getMm()==null?null:czybm.getMm().trim()));
		czybm.setUserRole(roleService.getRolesByCzybh(czybm.getCzybh()));
		String json = getJsonTree(id);
		String jsonDept = getSelectedJsonTree(id);
		String jsonCkAll = getJsonTreeCk(id);
		String jsonCkSelect = getSelectedJsonTreeCk(id);
		
		return new ModelAndView("admin/basic/czybm/czybm_update")
			.addObject("czybm", czybm)
			.addObject("nodes", json)
			.addObject("nodesDept", jsonDept)
			.addObject("nodesCkAll", jsonCkAll)
			.addObject("nodesCkSelect", jsonCkSelect)
			.addObject("operatorType", uc.getJslx());
	}
	/**
	 * 跳转到复制Czybm页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到复制Czybm页面");
		Czybm czybm = null;
		UserContext uc = this.getUserContext(request);
		if (StringUtils.isNotBlank(id)){
			czybm = czybmService.get(Czybm.class, id);
		}else{
			czybm = czybmService.get(Czybm.class, uc.getCzybh());
		}
		Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
		czybm.setEmnumDescr(employee==null?"":employee.getNameChi());
		czybm.setMm(DesUtils.decode(czybm.getMm()==null?null:czybm.getMm().trim()));
		czybm.setUserRole(roleService.getRolesByCzybh(czybm.getCzybh()));
		String json = getJsonTree(id);
		String jsonDept = getSelectedJsonTree(id);
		String jsonCkAll = getJsonTreeCk(id);
		String jsonCkSelect = getSelectedJsonTreeCk(id);
		
		return new ModelAndView("admin/basic/czybm/czybm_copy")
			.addObject("czybm", czybm)
			.addObject("nodes", json)
			.addObject("nodesDept", jsonDept)
			.addObject("nodesCkAll", jsonCkAll)
			.addObject("nodesCkSelect", jsonCkSelect)
			.addObject("operatorType", uc.getJslx());
	}
	
	/**
	 * 跳转到分配角色页面
	 * @return
	 */
	@RequestMapping("/goAssign")
	public ModelAndView goAssign(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到分配角色页面");
		Czybm czybm = null;
		if (StringUtils.isNotBlank(id)){
			czybm = czybmService.get(Czybm.class, id);
		}else{
			UserContext uc = this.getUserContext(request);
			czybm = czybmService.get(Czybm.class, uc.getCzybh());
		}
		czybm.setUserRole(roleService.getRolesByCzybh(czybm.getCzybh()));
		
		return new ModelAndView("admin/basic/czybm/czybm_assign")
			.addObject("czybm", czybm);
	}
	
	/**
	 * 跳转到查看Czybm页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Czybm页面");
		Czybm czybm = null;
		if (StringUtils.isNotBlank(id)){
			czybm = czybmService.get(Czybm.class, id);
		}else{
			UserContext uc = this.getUserContext(request);
			czybm = czybmService.get(Czybm.class, uc.getCzybh());
		}
		Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
		czybm.setEmnumDescr(employee==null?"":employee.getNameChi());
		czybm.setMm(DesUtils.decode(czybm.getMm()==null?null:czybm.getMm().trim()));
		czybm.setUserRole(roleService.getRolesByCzybh(czybm.getCzybh()));
		String json = getJsonTree(id);
		String jsonDept = getSelectedJsonTree(id);
		String jsonCkAll = getJsonTreeCk(id);
		String jsonCkSelect = getSelectedJsonTreeCk(id);
		
		return new ModelAndView("admin/basic/czybm/czybm_detail")
			.addObject("czybm", czybm)
			.addObject("nodes", json)
			.addObject("nodesDept", jsonDept)
			.addObject("nodesCkAll", jsonCkAll)
			.addObject("nodesCkSelect", jsonCkSelect);
	}
	/**
	 * 跳转到修改密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goChangePassword")
	public ModelAndView goChangePassword(HttpServletRequest request, HttpServletResponse response, int mmLength) {
		logger.debug("跳转到修改密码页面");

		return new ModelAndView("admin/basic/czybm/czybm_password")
			.addObject("mmLength", mmLength)
			.addObject("isPasswordExpired", czybmService.isPasswordExpired(UserContextHolder.getUserContext().getCzybh()));
	}
	/**
	 * 添加Czybm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Czybm czybm){
		logger.debug("添加Czybm开始");
		try{
			Czybm xt = this.czybmService.get(Czybm.class, czybm.getCzybh());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "操作员编号重复！");
				return;
			}
			if(czybmService.isHasEMNum(czybm) && !"2".equals(czybm.getCzylb())){
				ServletUtils.outPrintFail(request, response, "该员工已有账号！");
				return;
			}
			czybm.setMm(DesUtils.encode(czybm.getMm()));
//			czybm.setCzylb(getUserContext(request).getCzylb());
			
			String addIds = request.getParameter("addIds");
			String addIdsCk = request.getParameter("addIdsCk");
			String xml = getXmlData(request,czybm,addIds);
			String xmlCk = getXmlDataCk(request,czybm,addIdsCk);
			                             
			Result result = czybmService.updateForProc(czybm, xml, xmlCk);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, e.getMessage());
		}
	}
	
	/**
	 * 修改Czybm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Czybm czybm){
		logger.debug("修改Czybm开始");
		try{
			czybm.setMm(DesUtils.encode(czybm.getMm()));
			
			String addIds = request.getParameter("addIds");
			String addIdsCk = request.getParameter("addIdsCk");
			String xml = getXmlData(request,czybm,addIds);
			String xmlCk = getXmlDataCk(request,czybm,addIdsCk);
			
			Result result = czybmService.updateForProc(czybm, xml, xmlCk);
			if ("1".equals(result.getCode())){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改操作员失败");
		}
	}
	
	/**
	 * 分配角色
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doAssign")
	public void doAssign(HttpServletRequest request, HttpServletResponse response, Czybm czybm){
		logger.debug("分配角色开始");
		try{
			czybmService.assignRole(czybm);
			authorityCacheManager.refresh();
			menuCacheManager.refresh();
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "分配角色失败");
		}
	}
	
	/**
	 * 删除Czybm
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Czybm开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "操作员编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Czybm czybm = czybmService.get(Czybm.class, deleteId);
				if(czybm == null)
					continue;
				czybm.setZfbz(true);
				czybmService.update(czybm);
			}
		}
		logger.debug("删除Czybm IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doChangePassword")
	public void doChangePassword(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("修改密码开始");
		UserContext uc = this.getUserContext(request);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		
		// 加密类型默认 '1'DES加密用于delphi修改密码
		String desType = request.getParameter("desType");
		if("1".equals(desType)){
			oldPassword = new String(DesUtils.decode(oldPassword));
			newPassword1 = new String(DesUtils.decode(newPassword1));
		    newPassword2 = new String(DesUtils.decode(newPassword2)); 
		}else{
			String privateKey = SystemConfig.getProperty("privateKey", "", "rsaKey");
			oldPassword = new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(oldPassword), privateKey));
			newPassword1 = new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(newPassword1), privateKey));
			newPassword2 = new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(newPassword2), privateKey));
		}
		boolean flag = false;
		StringBuilder strb = new StringBuilder();

		Czybm czybm = czybmService.get(Czybm.class, uc.getCzybh());
		if (StringUtils.isBlank(oldPassword) || !DesUtils.encode(oldPassword).equals(czybm.getMm().trim())) {
			strb.append("原密码错误，请重新输入<br/>");
			flag = true;
		}

		if (StringUtils.isBlank(newPassword1)) {
			strb.append("新密码不能为空<br/>");
			flag = true;
		} else {
			if (!newPassword1.equals(newPassword2) || newPassword1.trim().length() < 1) {
				strb.append("新密码输入错误<br/>");
				flag = true;
			}
			if (this.hasChinese(newPassword1 + newPassword2)) {
				strb.append("密码不能包含汉字<br/>");
				flag = true;
			}
		}
		if (flag) {
			ServletUtils.outPrintFail(request, response, strb.toString());
			return;
		}

		czybm.setMm(DesUtils.encode(newPassword1.trim()));
		czybm.setModifyMMDate(new Date());
		czybmService.update(czybm);
		//request.getSession().removeAttribute(CommonConstant.USER_CONTEXT_KEY);
		logger.debug("修改密码结束 操作员编号={} 原密码={} 新密码={}", new Object[] { czybm.getCzybh(), oldPassword, newPassword1 });
		ServletUtils.outPrintSuccess(request, response, "修改密码成功");
	}

	/**
	 *Czybm导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Czybm czybm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		czybmService.findPageBySql(page, czybm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"操作员管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转分配部门权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goAuthority")
	public ModelAndView goAuthority(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("进入分配部门权限页面");
		String json = getJsonTree("00004");
		
		return new ModelAndView("admin/basic/czybm/czybm_auth_tree")
					.addObject("nodes", json);
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
		virtualRoot.put("nodeType", "department");
		return virtualRoot;
	}
	
	public List<Map<String, Object>> treeNodeMapper(List<DepartmentBean> list,List<String> selIds){
		List<Map<String, Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(list != null && list.size()>0){
			rsList = new ArrayList<Map<String,Object>>(list.size());
			for(DepartmentBean bean : list){
				map = this.beanNodeMapper(bean,selIds);
				if(map != null){
					rsList.add(map);
				}
			}
		}
		return rsList;
	}

	private Map<String,Object> beanNodeMapper(DepartmentBean depart,List<String> selIds){
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
		
		if(selIds != null){
			for(String id : selIds){
				if(id.equals(depart.getId())){
					map.put("checked", true);
					break;
				}
			}
		}
		return map;
	}
	
	/**获取所有部门树
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getJsonTree(String czybh){
		String json = "[]";
		List<DepartmentBean> list = new ArrayList<DepartmentBean>();
		List<Department1> department1List = (List<Department1>) departmentManager.get("department1");
		for (Department1 dp : department1List){
			DepartmentBean bean = new DepartmentBean();
			bean.setId("department1_"+dp.getCode());
			bean.setName(dp.getDesc2());
			bean.setPid("0");
			list.add(bean);
			bean = null;
		}
		List<Department2> department2List = (List<Department2>) departmentManager.get("department2");
		for (Department2 dp : department2List){
			String str = dp.getDepartment1();
			if (StringUtils.isNotBlank(str)){
				Department1 dp1 = (Department1) departmentManager.get("department1_"+str);
				if (dp1!=null && !"T".equals(dp1.getExpired())){
					DepartmentBean bean = new DepartmentBean();
					bean.setId("department2_"+dp.getCode());
					bean.setName(dp.getDesc2());
					bean.setPid("department1_"+dp.getDepartment1());
					list.add(bean);
					bean = null;
				}
			}
		}
		List<Department3> department3List = (List<Department3>) departmentManager.get("department3");
		for (Department3 dp : department3List){
			String str = dp.getDepartment2();
			if (StringUtils.isNotBlank(str)){
				Department2 dp2 = (Department2) departmentManager.get("department2_"+str);
				if (dp2!=null && !"T".equals(dp2.getExpired())){
					Department1 dp1 = (Department1) departmentManager.get("department1_"+dp2.getDepartment1());
					if (dp1!=null){
						DepartmentBean bean = new DepartmentBean();
						bean.setId("department3_"+dp.getCode());
						bean.setName(dp.getDesc2());
						bean.setPid("department2_"+dp.getDepartment2());
						list.add(bean);
						bean = null;
					}
				}
			}
		}
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//虚拟权限根节点
//		nodes.add(newVirtualRootNode());
		
		//获取已分配给操作员的部门
		List<String> selIds = new ArrayList<String>();
		List<CzyDept> listDept = czyDeptService.findByCzybh(czybh);
		if (listDept!=null && listDept.size()>0){
			for (CzyDept dept:listDept){
				if (StringUtils.isNotBlank(dept.getDepartment3())){
					selIds.add("department3_"+dept.getDepartment3());
				}else if (StringUtils.isNotBlank(dept.getDepartment2())){
					selIds.add("department2_"+dept.getDepartment2());
					List<Department3> dp3List = (List<Department3>) departmentManager.get("department2_"+dept.getDepartment2()+"_list");
					if (dp3List!=null && dp3List.size()>0){
						for (Department3 dp:dp3List){
							selIds.add("department3_"+dp.getCode());
						}
					}
				}else{
					selIds.add("department1_"+dept.getDepartment1());
					List<Department2> dp2List = (List<Department2>) departmentManager.get("department1_"+dept.getDepartment1()+"_list");
					if (dp2List!=null && dp2List.size()>0){
						for (Department2 dp2:dp2List){
							selIds.add("department2_"+dp2.getCode());
							List<Department3> dp3List = (List<Department3>) departmentManager.get("department2_"+dp2.getCode()+"_list");
							if (dp3List!=null && dp3List.size()>0){
								for (Department3 dp3:dp3List){
									selIds.add("department3_"+dp3.getCode());
								}
							}
						}
					}
				}
			}
		}
		
		//树菜单节点JSON格式
		nodes.addAll(this.treeNodeMapper(list, selIds));
		
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		
		return json;
	}
	/**获取操作员已有的部门树
	 * @return
	 */
	private String getSelectedJsonTree(String czybh){
		String json = "[]";
		List<DepartmentBean> list = new ArrayList<DepartmentBean>();
		List<CzyDept> listDept = czyDeptService.findByCzybh(czybh);
		if (listDept!=null && listDept.size()>0){
			for (CzyDept dept:listDept){
				DepartmentBean bean = new DepartmentBean();
				if (StringUtils.isNotBlank(dept.getDepartment3())){
					Department3 dp3 = (Department3) departmentManager.get("department3_"+dept.getDepartment3());
					if (dp3!=null){
						bean.setId("department3_"+dept.getDepartment3());
						Department2 dp2 = (Department2) departmentManager.get("department2_"+dp3.getDepartment2());
						Department1 dp1 = null;
						if (dp2!=null){
							dp1 = (Department1) departmentManager.get("department1_"+dp2.getDepartment1());
						}
						bean.setName((dp1==null?"":dp1.getDesc2())+"-"+(dp2==null?"":dp2.getDesc2())+"-"+dp3.getDesc2());
						//bean.setName(dp3.getDesc2());
						bean.setPid("department2_"+dept.getDepartment2());
					}
				}else if (StringUtils.isNotBlank(dept.getDepartment2())){
					Department2 dp2 = (Department2) departmentManager.get("department2_"+dept.getDepartment2());
					if (dp2!=null){
						bean.setId("department2_"+dept.getDepartment2());
						Department1 dp1 = (Department1) departmentManager.get("department1_"+dp2.getDepartment1());
						bean.setName(dp1==null?"":dp1.getDesc2()+"-"+dp2.getDesc2());
						//bean.setName(dp2.getDesc2());
						bean.setPid("department1_"+dept.getDepartment1());
					}
				}else{
					Department1 dp1 = (Department1) departmentManager.get("department1_"+dept.getDepartment1());
					if (dp1!=null){
						bean.setId("department1_"+dept.getDepartment1());
						bean.setName(dp1.getDesc2());
						bean.setPid("0");
					}
				}
				list.add(bean);
				bean = null;
			}
		}
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//树菜单节点JSON格式
		nodes.addAll(this.treeNodeMapper(list, null));
		
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		
		return json;
	}
	/**获取所有仓库树
	 * @return
	 */
	private String getJsonTreeCk(String czybh){
		String json = "[]";
		List<DepartmentBean> list = new ArrayList<DepartmentBean>();
		List<WareHouse> wareHouseList = wareHouseService.findByNoExpired();
		for (WareHouse dp : wareHouseList){
			DepartmentBean bean = new DepartmentBean();
			bean.setId(dp.getCode());
			bean.setName(dp.getDesc1());
			bean.setPid("-1");
			list.add(bean);
			bean = null;
		}
		
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//获取已分配给操作员的仓库
		List<String> selIds = new ArrayList<String>();
		List<Map<String,Object>> listWareHouse = wareHouseOperaterService.findByCzybh(czybh);
		if (listWareHouse!=null && listWareHouse.size()>0){
			for (Map<String,Object> wareHouse:listWareHouse){
				selIds.add(String.valueOf(wareHouse.get("whcode")));
			}
		}
		
		//树菜单节点JSON格式
		nodes.addAll(this.treeNodeMapper(list, selIds));
		
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		
		return json;
	}
	/**获取操作员已有的仓库
	 * @return
	 */
	private String getSelectedJsonTreeCk(String czybh){
		String json = "[]";
		List<DepartmentBean> list = new ArrayList<DepartmentBean>();
		List<Map<String,Object>> listWareHouse = wareHouseOperaterService.findByCzybh(czybh);
		if (listWareHouse!=null && listWareHouse.size()>0){
			for (Map<String,Object> wareHouse:listWareHouse){
				DepartmentBean bean = new DepartmentBean();
				bean.setId(String.valueOf(wareHouse.get("whcode")));
				bean.setName(String.valueOf(wareHouse.get("whcodedescr")));
				bean.setPid("0");
				list.add(bean);
				bean = null;
			}
		}
		
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		//树菜单节点JSON格式
		nodes.addAll(this.treeNodeMapper(list, null));
		
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		
		return json;
	}
	
	private boolean hasChinese(String str) {
		if (StringUtils.isBlank(str))
			return false;
		for (int i = 0; i < str.length(); i++) {
			String regEx = "[\u4e00-\u9fa5]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str.charAt(i) + "");
			if (m.find())
				return true;
		}
		return false;
	}
	
	/**获取xml字符串
	 * @param request
	 * @param czybm
	 * @param addIds
	 * @return
	 */
	private String getXmlData(HttpServletRequest request, Czybm czybm, String addIds){
		List<CzyDept> list = getCzyDeptList(request,czybm,addIds);
		String json = JSONArray.toJSONString(list);
		String xml = XmlConverUtil.jsonToXmlNoHead(json);
		return xml;
	}
	
	/**获取xml字符串
	 * @param request
	 * @param czybm
	 * @param addIds
	 * @return
	 */
	private String getXmlDataCk(HttpServletRequest request, Czybm czybm, String addIds){
		List<WareHouseOperater> list = getWareHouseOperaterList(request,czybm,addIds);
		String json = JSONArray.toJSONString(list);
		String xml = XmlConverUtil.jsonToXmlNoHead(json);
		return xml;
	}
	
	private List<WareHouseOperater> getWareHouseOperaterList(
			HttpServletRequest request, Czybm czybm, String addIds) {
		UserContext uc = getUserContext(request);
		List<String> addList = IdUtil.splitStringIds(addIds);
		List<WareHouseOperater> list = new ArrayList<WareHouseOperater>();
		if (addList!=null && addList.size()>0){
			for (String s:addList){
				WareHouseOperater wareHouseOperater = new WareHouseOperater();
				wareHouseOperater.setCzybh(czybm.getCzybh());
				wareHouseOperater.setWhcode(s);
				wareHouseOperater.setLastUpdate(new Date());
				wareHouseOperater.setLastUpdatedBy(uc.getCzybh());
				wareHouseOperater.setActionLog("ADD");
				wareHouseOperater.setExpired("F");
				list.add(wareHouseOperater);
				wareHouseOperater = null;
			}
		}
		return list;
	}

	private List<CzyDept> getCzyDeptList(HttpServletRequest request, Czybm czybm, String addIds){
		UserContext uc = getUserContext(request);
		List<String> addList = IdUtil.splitStringIds(addIds);
		List<CzyDept> list = new ArrayList<CzyDept>();
		if (addList!=null && addList.size()>0){
			for (String s:addList){
				String[] arr = s.split("_");
				if (arr!=null && arr.length==2){
					CzyDept czyDept = new CzyDept();
					if ("department1".equals(arr[0])){
						czyDept.setCzybh(czybm.getCzybh());
						czyDept.setDepartment1(arr[1]);
						czyDept.setLastUpdate(new Date());
						czyDept.setLastUpdatedBy(uc.getCzybh());
						czyDept.setActionLog("ADD");
						czyDept.setExpired("F");
					}else if ("department2".equals(arr[0])){
						czyDept.setCzybh(czybm.getCzybh());
						Department2 dp2 = (Department2) departmentManager.get("department2_"+arr[1]);
						if (dp2!=null){
							czyDept.setDepartment1(dp2.getDepartment1());
						}
						czyDept.setDepartment2(arr[1]);
						czyDept.setLastUpdate(new Date());
						czyDept.setLastUpdatedBy(uc.getCzybh());
						czyDept.setActionLog("ADD");
						czyDept.setExpired("F");
					}else if ("department3".equals(arr[0])){
						czyDept.setCzybh(czybm.getCzybh());
						Department3 dp3 = (Department3) departmentManager.get("department3_"+arr[1]);
						if (dp3!=null){
							czyDept.setDepartment2(dp3.getDepartment2());
							Department2 dp2 = (Department2) departmentManager.get("department2_"+dp3.getDepartment2());
							if (dp2!=null){
								czyDept.setDepartment1(dp2.getDepartment1());
							}
						}
						czyDept.setDepartment3(arr[1]);
						czyDept.setLastUpdate(new Date());
						czyDept.setLastUpdatedBy(uc.getCzybh());
						czyDept.setActionLog("ADD");
						czyDept.setExpired("F");
					}
					list.add(czyDept);
					czyDept = null;
				}
			}
		}
		return list;
	}
	
	/**
	 * 操作员专盘
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCzybmBuilder")
	public ModelAndView goCzybmBuilder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/czySpcBuilder/czySpcBuilder_list");
	}
	/**
	 * 跳转分配操作员APP权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goAppAuthority")
	public ModelAndView goCzybmAPPAuthority(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("");
		Czybm czybm = czybmService.get(Czybm.class, id);
		List<Map<String, Object>> allMkList = czybmService.getQX_ALLMK(2);
		List<Map<String, Object>> hasMkList = czybmService.getQX_CZYQX(id, 2, "0");
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
		
		return new ModelAndView("admin/basic/czybm/czybm_appAuth_tree")
			.addObject("nodes", json)
			.addObject("czybm", czybm);
	}
	
	/**
     * 跳转到复制权限
     */
    @RequestMapping("/goCopyAuthority")
    public ModelAndView goCopyAuthority(HttpServletRequest request, HttpServletResponse response, String id){
    	Czybm czybm = czybmService.get(Czybm.class, id);
    	List<Map<String,Object>> list = czybmService.getCZYList(id);
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
        return new ModelAndView("admin/basic/czybm/czybm_copyAuth")
    			.addObject("czybm", czybm)
    			.addObject("nodes", nodes);
                   
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
	public void doCopyRight(HttpServletRequest request,HttpServletResponse response,Czybm czybm){
		try{
			UserContext uc = getUserContext(request);
			czybm.setLastUpdatedBy(uc.getCzybh());   
			Result result = czybmService.doCopyRight(czybm);
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
	 * app权限保存操作
	 * @param request
	 * @param response
	 * @param detailJson
	 */
	@RequestMapping("/doAppRight")
	public void doAppRight(HttpServletRequest request,HttpServletResponse response,Czybm czybm){
		try{
			UserContext uc = getUserContext(request);
			czybm.setLastUpdatedBy(uc.getCzybh());
			Result result = czybmService.doAppRight(czybm);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "点击失败");
			e.printStackTrace();
		}
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
	 * 跳转到麦田区域设置
	 * @return
	 */
	@RequestMapping("/goMtRegion")
	public ModelAndView goCheckApp(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到麦田区域设置页面");
			Czybm czybm = czybmService.get(Czybm.class, id);
		return new ModelAndView("admin/basic/czybm/czybm_mtRegion")
			.addObject("czybm", czybm);
	}
	/**
	 * 查询明细麦田区域表格数据
	 * @param request
	 * @param response
	 * @param czybm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridMtRegion")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridPuFeeDetail(HttpServletRequest request,	
			HttpServletResponse response, Czybm czybm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czybmService.findPageBySql_mtRegion(page, czybm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增麦田明细页面
	 * @return
	 */
	@RequestMapping("/goCzyMtRegionAdd")
	public ModelAndView goAddCheckApp(HttpServletRequest request, HttpServletResponse response,CzyMtRegion czyMtRegion){
		
		return new ModelAndView("admin/basic/czybm/czybm_mtRegion_add")
				.addObject("czyMtRegion", czyMtRegion);
	}
	
	/**
	 * 获取操作员权限明细
	 * @param czybm
	 * @return
	 */
	@RequestMapping("/goAuthDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAuthDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, Czybm czybm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czybmService.findPageBySqlAuthDetail(page, czybm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 获取操作员平台权限
	 * @param czybm
	 * @param ptbh
	 * @return
	 */
	@RequestMapping("/goPlatformAuthJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPlatformAuthJqGrid(HttpServletRequest request,
			HttpServletResponse response, String ptbh, String czybh) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		czybmService.findPageBySqlPlatformAuth(page, ptbh, czybh);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 根据材料编号查算法
	 * @param request
	 * @param response
	 * @param czybhs
	 * @return
	 */
	@RequestMapping("/getCzyByIds")
	@ResponseBody
	public List<Map<String, Object>> getCzyByIds(HttpServletRequest request,HttpServletResponse response, String czybhs){
		List<Map<String, Object>>list=czybmService.getCzyByIds(czybhs);
		return list;
	}

	/**
	 * 角色联动多选
	 * @param request
	 * @param response
	 * @param code
	 */
	@RequestMapping("/changeCzylb")
	public void changeCzylb(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载角色");
		String virtualRootLabel = "请选择";
		String virtualRootId = "_VIRTUAL_RO0T_ID_";
		String sqlValueKey = "ROLE_ID";
		String sqlLableKey = "ROLE_NAME";
		String retStr = "[]";
		if (StringUtils.isBlank(code)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("strSelect", retStr);
			try {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		String sql = " select a.ROLE_ID,a.ROLE_NAME from TSYS_ROLE a where a.SYS_CODE= " + code +" order by a.ROLE_ID ";
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", virtualRootId);
		item.put("pId", "");
		item.put("name", virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map : list){
				item = new HashMap<String, Object>();
				item.put("id", map.get(sqlValueKey));
				item.put("pId", virtualRootId);
				item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey));
				rsList.add(item);
			}
		}
		retStr = JSON.toJSONString(rsList);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", retStr);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/refreshCache")
	public void refreshCache(HttpServletRequest request, String sig){
		logger.debug("刷新缓存开始");
		try{
			String date = DesUtils.decode(sig);
			String nowDateString = DateUtil.DateToString(new Date(), "yyyyMMdd");
			
			if(!nowDateString.equals(date)){
				return ;
			}
			
			authorityCacheManager.refresh();
			menuCacheManager.refresh();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void refreshOhterServerCache(HttpServletRequest request){
			String url = "";
		try {
			
			String sig =DesUtils.encode(DateUtil.DateToString(new Date(), "yyyyMMdd"));
			url = SystemConfig.getProperty("serverUrl", "", "refreshCache");
			if(StringUtils.isNotBlank(url)){
				url+="?&sig=" + sig;
			} else {
				return;
			}
			// 创建HttpClient
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(url);
	        
	        // 超时设置，单位：毫秒
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(60000)
					.setConnectionRequestTimeout(10000)  
			        .setSocketTimeout(60000).build();  
			httpPost.setConfig(requestConfig); 
			HttpResponse response = httpClient.execute(httpPost);
			
		} catch (Exception e){
            logger.error("刷新缓存失败：{}", url, e.getMessage());
            return ;
        }
	}
}
