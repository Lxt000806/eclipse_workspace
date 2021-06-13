package com.house.framework.web.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sun.security.util.BigInt;

import com.alibaba.fastjson.JSON;
import com.house.framework.commons.cache.AuthorityCacheManager;
import com.house.framework.commons.cache.CacheUtils;
import com.house.framework.commons.cache.MenuCacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.DictConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.IpUtils;
import com.house.framework.commons.utils.RSAUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.UserUtil;
import com.house.framework.dao.MenuDao;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Menu;
import com.house.framework.service.AuthorityService;
import com.house.framework.service.MenuService;
import com.house.framework.service.RoleService;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.XtcsService;

/**
 * 用户登入验证Controller
 * 
 */
@Controller
public class LogonController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(LogonController.class);

	private static final String ID_SUFFIX = "_ID";
	private static final String URL_SUFFIX = "_URL";
	private static final String MENU_SUFFIX = "_MENU";

	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private AuthorityCacheManager authorityCacheManager;
	@Autowired
	private MenuCacheManager menuCacheManager;
	@Autowired
	private MenuService menuService;
	private List<Date> datelist;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private XtcsService xtcsService;

	//private String url;
	/**
	 * 用户登入
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public void logon(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("获取用户登录信息并进行判断");
		try {
			if (!this.checkVerify(request, response)) {
				return;
			}
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			String czylb = request.getParameter("czylb");

			String privateKey = SystemConfig.getProperty("privateKey", "", "rsaKey");
			password = new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(password), privateKey));
			
			if (StringUtils.isBlank(loginName)) {
				ServletUtils.outPrintWithToken(request, response, "请输入用户名");
				logger.debug("用户名为空，返回登入界面");
				return;
			}
			if (StringUtils.isBlank(password)) {
				ServletUtils.outPrintWithToken(request, response, "请输入密码");
				logger.debug("密码为空，返回登入界面");
				return;
			}
			if (StringUtils.isBlank(czylb)) {
				ServletUtils.outPrintWithToken(request, response, "请选择平台");
				logger.debug("平台为空，返回登入界面");
				return;
			}

			logger.debug("根据用户名={} 密码={} 获取用户对象", loginName, password);
			Czybm czybm = czybmService.getByCzybhAndMm(loginName.trim(), password);

			if (czybm == null) {
				ServletUtils.outPrintWithToken(request, response,
						"用户名或密码错误，请重试");
				logger.debug("用户名或密码错误,获取用户对象为空,验证失败");
				return;
			}
			
			if (czybm.getZfbz()==null || czybm.getZfbz().booleanValue()==true){
				logger.debug("用户 ID={} 的状态不可用", czybm.getCzybh());
				ServletUtils.outPrintWithToken(request, response, "您的帐号已作废，请联系管理员。");
				return;
			}
			
			if (!"0".equals(czybm.getCzylb()) && !czylb.equals(czybm.getCzylb())){
				logger.debug("用户 ID={} 没有这个平台的权限", czybm.getCzybh());
				ServletUtils.outPrintWithToken(request, response, "您的帐号没有这个平台的权限，请联系管理员。");
				return;
			}else{
				czybm.setCzylb(czylb);
			}
			if("2".equals(czylb)){
				Supplier supplier= czybmService.get(Supplier.class, czybm.getEmnum());
				if(supplier!=null){
					if("T".equalsIgnoreCase(supplier.getExpired())){
						logger.debug("用户 ID={} 对应的供应商状态已过期", czybm.getCzybh());
						ServletUtils.outPrintWithToken(request, response, "您的帐号已过期，请联系管理员。");
						return;	
					}
				}
			}else{
				Employee employee = czybmService.get(Employee.class, czybm.getEmnum());
				if(employee!=null){
					if("T".equalsIgnoreCase(employee.getExpired())||"SUS".equals(employee.getStatus())){
						logger.debug("用户 ID={} 对应的员工状态已过期", czybm.getCzybh());
						ServletUtils.outPrintWithToken(request, response, "您的帐号已过期，请联系管理员。");
						return;		
					}
				}
			}	
			if("0".equals(czybm.getIsOutUser()) && "1".equals(czylb)){
				
				String ip = IpUtils.getIpAddr(request);
				long ipV4Long = IpUtils.ipV4ToLong(ip);
				boolean isInnerIp = IpUtils.isInnerIp(ip);
				
		        //判断登录ip是否为白名单
		        boolean isWhiteIp = czybmService.checkIsWhiteIp(ipV4Long); 
				
		        //不是内网ip 并且不是 白名单ip
		        if(!isInnerIp && !isWhiteIp){
					ServletUtils.outPrintWithToken(request, response, "内网用户不允许使用外网IP[" + ip + "]登录");
					logger.error("内网用户，不允许使用外网ip登录。操作员编号:"+czybm.getCzybh()+",ip:"+ip);
					return;
				}
			}
			
			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);
			User user = identityService.createUserQuery().userId(czybm.getCzybh().trim()).singleResult();
			UserUtil.saveUserToSession(request, user);
			bind(czybm, request);
			clearVerify(request);
			// 写登陆日志
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("用户登入出现异常: " + e.getMessage(), e);
			ServletUtils.outPrintWithToken(request, response,
					"用户登入出现异常: " + e.getMessage());
			return;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("用户登入出现异常: " + e.getMessage(), e);
			ServletUtils.outPrintWithToken(request, response,
					"用户登入出现异常: " + e.getMessage());
			return;
		}
		ServletUtils.outPrintSuccess(request, response, "用户登入成功");
		return;
	}
	/**
	 * 用户登入
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/delphiLogin")
	public ModelAndView delphiLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("获取用户登录信息并进行判断");
		String url = "";
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			password = DesUtils.decode(password);
			url = request.getParameter("url");
			//url = "/admin/customer/goList";
			String czylb = "1";

			if (StringUtils.isBlank(loginName)) {
				logger.debug("用户名为空，返回登入界面");
			}
			if (StringUtils.isBlank(password)) {
				logger.debug("密码为空，返回登入界面");
			}
			if (StringUtils.isBlank(czylb)) {
				logger.debug("平台为空，返回登入界面");
			}

			logger.debug("根据用户名={} 密码={} 获取用户对象", loginName, password);
			Czybm czybm = czybmService.getByCzybhAndMm(loginName.trim(), password);

			if (czybm == null) {
				logger.debug("用户名或密码错误,获取用户对象为空,验证失败");
				return new ModelAndView("redirect:"+url);
			}
			
			if (czybm.getZfbz()==null || czybm.getZfbz().booleanValue()==true){
				logger.debug("用户 ID={} 的状态不可用", czybm.getCzybh());
			}
			
			if (!"0".equals(czybm.getCzylb()) && !czylb.equals(czybm.getCzylb())){
				logger.debug("用户 ID={} 没有这个平台的权限", czybm.getCzybh());
			}else{
				czybm.setCzylb(czylb);
			}
			Employee employee = czybmService.get(Employee.class, czybm.getEmnum());
			if(employee!=null){
				if("T".equalsIgnoreCase(employee.getExpired())||"SUS".equals(employee.getStatus())){
					logger.debug("用户 ID={} 对应的员工状态已过期", czybm.getCzybh());		
				}
			}
			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);
			
			bind(czybm, request);
			clearVerify(request);
			// 写登陆日志
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("用户登入出现异常: " + e.getMessage(), e);
		}
		return new ModelAndView("redirect:"+url);
	}
	
	/**
	 * Pos端用户登入
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/posLogin")
	public void posLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("获取用户登录信息并进行判断");
		String url = "";
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			password = DesUtils.decode(password);
	
			String czylb = "1";

			if (StringUtils.isBlank(loginName)) {
				logger.debug("用户名为空，返回登入界面");
				ServletUtils.outPrintFail(request, response, "用户名不能为空！");
				return;
			}
			if (StringUtils.isBlank(password)) {
				logger.debug("密码为空，返回登入界面");
				ServletUtils.outPrintFail(request, response, "密码不能为空！");
				return;
			}

			logger.debug("根据用户名={} 密码={} 获取用户对象", loginName, password);
			Czybm czybm = czybmService.getByCzybhAndMm(loginName.trim(), password);

			if (czybm == null) {
				logger.debug("用户名或密码错误,获取用户对象为空,验证失败");
				ServletUtils.outPrintFail(request, response, "用户名或密码错误！");
				return;
			}
			
			if (czybm.getZfbz()==null || czybm.getZfbz().booleanValue()==true){
				logger.debug("用户 ID={} 的状态不可用", czybm.getCzybh());
				ServletUtils.outPrintFail(request, response, "该账号已作废！");
				return;
			}
			
			czybm.setCzylb(czylb);
			
			Employee employee = czybmService.get(Employee.class, czybm.getEmnum());
			if(employee!=null){
				if("T".equalsIgnoreCase(employee.getExpired())||"SUS".equals(employee.getStatus())){
					logger.debug("用户 ID={} 对应的员工状态已过期", czybm.getCzybh());		
				}
			}
			//登录成功重新建立session
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);
			
			bind(czybm, request);
			
			ServletUtils.outPrintSuccess(request, response, request.getSession().getId(), czybm);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("用户登入出现异常: " + e.getMessage(), e);
			ServletUtils.outPrintFail(request, response, "用户登录异常，请联系系统管理员！");
		}
	}
	
	/**
	 * 切换平台
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/changePt")
	public void changePt(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("切换平台");
		String loginName = request.getParameter("loginName");
		
		Czybm czybm = czybmService.findByCzybhOrPhone(loginName);
		
		if (czybm!=null){
			ServletUtils.outPrintSuccess(request, response, czybm.getCzylb());
		}else{
			ServletUtils.outPrintFail(request, response, "切换平台失败");
		}
	}

	/**
	 * 检查验证码是否正确
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkVerify(HttpServletRequest request,
			HttpServletResponse response) {
		// 验证码
		String verifycode = request.getParameter("verifycode");
		if (StringUtils.isBlank(verifycode)) {
			ServletUtils.outPrintWithToken(request, response, "请输入验证码");
			return false;
		}

		Object code = request.getSession().getAttribute(
				CommonConstant.VERITY_CODE_KEY);

		if (code == null) {
			ServletUtils.outPrintWithToken(request, response, "登入超时，请重新登入");
			logger.debug("Session 中没有验证码信息 返回页面 重新获取验证码");
			return false;
		}

		if (!verifycode.equalsIgnoreCase(code.toString())) {
			ServletUtils.outPrintWithToken(request, response, "验证码错误");
			return false;
		}
		return true;
	}

	/**
	 * 清除验证码
	 * 
	 * @param request
	 */
	private void clearVerify(HttpServletRequest request) {
		logger.debug("清除Session中验证码");
		request.getSession().removeAttribute(CommonConstant.VERITY_CODE_KEY);
	}

	/**
	 * 绑定用户上下文数据到session中
	 * 
	 * @param user
	 * @param request
	 */
	private void bind(Czybm user, HttpServletRequest request) {
		if (null == user)
			throw new RuntimeException("待载入缓存的用户对象为空错误！");
		logger.debug("用户登入验证通过，开始绑定用户到Session中");

		UserContext uc = new UserContext();
		try {
			uc.setCzybh(user.getCzybh());
			uc.setZwxm(user.getZwxm());
			uc.setEmnum(user.getEmnum());
			uc.setCzylb(user.getCzylb());
			uc.setCustRight(user.getCustRight());
			uc.setCostRight(user.getCostRight());
			uc.setItemRight(user.getItemRight());
			uc.setProjectCostRight(user.getProjectCostRight());
			uc.setIp(request.getRemoteAddr());
			uc.setJslx(user.getJslx());
			// 三种角色是互斥的，如果有高优先级角色，则无需在关注其低优先级的角色
			uc.setSuperAdmin(false);
			uc.setOneAdmin(false);
			uc.setTwoAdmin(false);
			

			if (this.czybmService.isSuperAdmin(user.getCzybh())) {
				uc.setSuperAdmin(true);
			}
//			if (user.getCzybh().trim().equals("0") || user.getCzybh().trim().equals("1")){
//				uc.setSuperAdmin(true);
//			}
			// 绑定用户权限到用户上下文
			this.bindAuths(uc);
			UserContextHolder.set(uc);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("绑定用户上下文数据出现异常：", e);
			throw new RuntimeException(e);
		}

		logger.debug("绑定用户到Session中结束");
		HttpSession session = request.getSession(); 
		session.setAttribute(CommonConstant.USER_CONTEXT_KEY, uc);
		//Tomcat日志无法通过USER_CONTEXT_KEY获取操作员编号，所以需要在session中直接记录操作员编号
		session.setAttribute(CommonConstant.CZYBH, uc.getCzybh()); 

		// 未配置周期，退出
		if (datelist == null || datelist.size() == 0) {
			return;
		}
	}

	/**
	 * 设置用户权限信息，存在缓存中则直接从缓存获取，否则使用sql查询
	 * 
	 * @param uc
	 * @param cache
	 */
	private void bindAuths(UserContext uc) {
		// 从缓存中获取并绑定
		boolean flag = this.bindAuthsFromCache(uc);
		if (!flag) {
			// 从数据库中获取并绑定
			this.bindAuthsFromSql(uc);
		}
	}

	/**
	 * 使用SQL查询数据库，将获取权限信息，绑定到用户上下文中和缓存中
	 * 
	 * @param uc
	 * @param cache
	 */
	private void bindAuthsFromSql(UserContext uc) {
		List<Authority> authList = null;
		if (uc.isSuperAdmin()) {
			authList = this.authorityService.getAll();
		} else {
			authList = this.authorityService.getByCzybh(uc.getCzybh());
		}
		// 则先设置到缓存中
		this.authorityCacheManager.put(uc.getCzybh(), authList);
		// 从缓存中获取设置到用户上下文仲
		this.bindAuthsFromCache(uc);
	}

	/**
	 * 将权限设置到用户上下文中
	 * 
	 * @param uc
	 * @param cache
	 * @param key
	 * @return
	 */
	private boolean bindAuthsFromCache(UserContext uc) {
		List<String> forbidUrls = this.authorityCacheManager
				.getUrlStrsFromCache(uc.getCzybh() + LogonController.URL_SUFFIX);
		String authStr = this.authorityCacheManager.getAuthCodeStrsFormCache(uc.getCzybh() + LogonController.ID_SUFFIX);
		if (authStr == null) {
			return false;
		}
		
		List<String> list = this.menuService.getForbidMenusUrlByCzybh(uc.getCzybh());
		
		if(forbidUrls != null && list != null){
			forbidUrls.addAll(list);
		}
		uc.setForbidUrls(forbidUrls);
		uc.setAuthStr(authStr);
		logger.debug("从缓存中获取用户权限");
		return true;
	}

	/**
	 * 退出登入
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/admin/loginOut")
	public void loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserContext uc = (UserContext) request.getSession().getAttribute(
					CommonConstant.USER_CONTEXT_KEY);
			if (uc != null && StringUtils.isNotBlank(uc.getCzybh())) {

				Czybm user = this.czybmService.get(Czybm.class, uc.getCzybh());
				request.getSession().removeAttribute(
						CommonConstant.USER_CONTEXT_KEY);
//				this.userService.update(user);
				UserContextHolder.set(null);
				logger.debug("用户{} 退出登入", user.getCzybh());
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch (RuntimeException e) {
			logger.error("用户退出登入异常: " + e.getMessage(), e);
			ServletUtils.outPrintFail(request, response, "用户退出登入出现异常");
		}
	}

	@RequestMapping("/admin/main")
	@SuppressWarnings("unchecked")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		UserContext uc = this.getUserContext(request);
//		String loginFlag = request.getParameter("loginFlag");
//		if (uc==null || !"login".equals(loginFlag)){
		if (uc==null){
			return new ModelAndView("redirect:/login.jsp");
		}
//		if(czybmService.isPasswordExpired(uc.getCzybh())){	
//			return new ModelAndView("redirect:/updatePassWord.jsp");
//			
//		}
		String html = "";
		String js = "";
		Long firstTabId = 0L;

		// 从缓存中获取当前用户所有的菜单
		List<Menu> allMenuList = (List<Menu>) this.menuCacheManager.get(uc.getCzybh());

		// 缓存中没有用户菜单，则从数据库中查找，找到则同步到缓存
		if (allMenuList == null || allMenuList.size() < 1) {
			if (uc.isSuperAdmin()) {
				allMenuList = this.menuService.getAll();
			} else {
				// 获取用户对应的菜单
				//allMenuList = this.menuService.getByCzybh(uc.getCzybh());
				allMenuList = menuDao.findAllowedMenusByCzybh(uc.getCzybh());
			}
			if (allMenuList != null && allMenuList.size() > 0) {
				this.menuCacheManager.put(uc.getCzybh(), allMenuList);
			}
		}
		String czylb = uc.getCzylb();
		//根据登录的平台筛选菜单
		List<Menu> menuList = new ArrayList<Menu>();
		if (allMenuList!=null && allMenuList.size()>0){
			for (Menu menu:allMenuList){
				if (CommonConstant.PLATFORM_ALL.equals(menu.getSysCode()) || uc.getCzylb().equals(menu.getSysCode())){
					if ("2".equals(czylb)){//2-材料供应商平台
						if ("DICT_MENU_TYPE_TAB".equals(menu.getMenuType())){
							if (500==menu.getMenuId().longValue()){
								menuList.add(menu);
							}
						}else{
							menu.setParentId(500l);//500-材料供应商管理
							menuList.add(menu);
						}
					}else{
						menuList.add(menu);
					}
					
				}
			}
		}
		Collections.sort(menuList, new Comparator<Menu>() {
			/** 根据元注释order 排列顺序 */
			public int compare(Menu o1, Menu o2) {
				Long order1 = o1.getOrderNo();
				Long order2 = o2.getOrderNo();
				return order1.compareTo(order2);
			}
		});
		List<Menu> tabMenus = this.getTabMenuList(menuList);
		if ("2".equals(czylb)){//2-材料供应商平台
			tabMenus.clear();
			tabMenus.add(menuService.get(500l));
		}
		if (tabMenus != null && tabMenus.size() > 0) {
			html = this.createHtml(tabMenus);
			js = this.createJs(menuList, tabMenus);
			firstTabId = tabMenus.get(0).getMenuId();
		}
		
		String title = CacheUtils.getDictItemLabel("ptdm", uc.getCzylb());
		Czybm czybm = czybmService.get(Czybm.class, uc.getCzybh());
		int mmLength = 0;
		String str = DesUtils.decode(czybm.getMm());
		mmLength = str.length();
		
		Xtcs cmpnyCode = xtcsService.get(Xtcs.class, "CmpnyCode");
		String regionName = cmpnyCode != null ? cmpnyCode.getSm() : "";
		String platfromName = regionName + "有家装饰ERP平台";
		
		return new ModelAndView("main")
				.addObject("html", html)
				.addObject("title", title)
				.addObject("js", js)
				.addObject("userName", uc.getZwxm())
				.addObject("MENU_OPEN_TYPE_FULL",
						DictConstant.DICT_MENU_OPEN_FULL)
				.addObject("MENU_OPEN_TYPE_INNER",
						DictConstant.DICT_MENU_OPEN_INNER)
				.addObject("MENU_TYPE_TAB", DictConstant.DICT_MENU_TYPE_TAB) //将tab的类型传到前端，模块搜索用 add by zb on 20200313
				.addObject("MENU_TYPE_URL", DictConstant.DICT_MENU_TYPE_URL)
				.addObject("MENU_TYPE_FOLDER",
						DictConstant.DICT_MENU_TYPE_FOLDER)
				.addObject("firstTabId", firstTabId)
				.addObject("mmLength", mmLength)
				.addObject("isPasswordExpired", czybmService.isPasswordExpired(uc.getCzybh()))
				.addObject("fastMenus", roleService.getFastMenus(uc.getCzybh()))
				.addObject("platformName", platfromName);
	}

	/**
	 * 分离出所有的tab类型菜单，作为生成树的发起点
	 * 
	 * @param menuList
	 * @return
	 */
	private List<Menu> getTabMenuList(List<Menu> menuList) {
		if (menuList == null) {
			logger.warn("用户菜单为空");
			return null;
		}
		List<Menu> tabMenus = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if (menu != null
					&& DictConstant.DICT_MENU_TYPE_TAB.equals(menu
							.getMenuType())) {
				tabMenus.add(menu);
			}
		}
		return tabMenus;
	}

	/**
	 * 根据tab量生成对应的tab html
	 * 
	 * @param tabMenus
	 * @return
	 */
	private String createHtml(List<Menu> tabMenus) {
		StringBuilder strb = new StringBuilder();
		for (Menu tabMenu : tabMenus) {
			if (tabMenu != null) {
				strb.append("<div title='").append(tabMenu.getMenuName())
						.append("' class='l-scroll' id='div_")
						.append(tabMenu.getMenuId())
						//当为第一个选项卡时，就打开它 modify by zb on 20200112
						.append(tabMenu.getMenuId()==1?"' lselected='true":"")
						.append(tabMenu.getMenuId()==500?"' lselected='true":"")
						.append("'><ul id='tab_").append(tabMenu.getMenuId())
						.append("' class='ztree' style='overflow:auto;' />")
						.append("</div>\n");
			}
		}

		return strb.toString();
	}

	/**
	 * 根据tabMenu 分隔出所有tab对应的所有子节点
	 * 
	 * @param menuList
	 * @param tabMenu
	 * @return
	 */
	private List<Menu> divideByTabMenu(List<Menu> allMenuList, Long parentMenuId) {
		List<Menu> list = new ArrayList<Menu>();

		for (Menu menu : allMenuList) {
			if (menu.getParentId().longValue() == parentMenuId.longValue()) {
				list.add(menu);
				// 文件夹类型菜单，递归获取其子菜单
				if (DictConstant.DICT_MENU_TYPE_FOLDER.equals(menu
						.getMenuType())) {
					list.addAll(this.divideByTabMenu(allMenuList,
							menu.getMenuId()));
				}
			}
		}
		return list;
	}

	/**
	 * 根据tab菜单，生成对应数量的ztree js 关键：每颗tab树菜单下的节点，需由tab向下在 总菜单列表中递归选择出来
	 * 
	 * @param allMenuList
	 *            所有菜单列表
	 * @param tabMenus
	 *            tab菜单列表
	 * @return
	 */
	private String createJs(List<Menu> allMenuList, List<Menu> tabMenus) {
		StringBuilder strb = new StringBuilder();

		List<Menu> menuTree = null;
		for (Menu tabMenu : tabMenus) {
			if (tabMenu != null) {
				menuTree = this.divideByTabMenu(allMenuList,
						tabMenu.getMenuId());
				if (menuTree == null)
					continue;
				strb.append("$.fn.zTree.init($(\"#tab_")
						.append(tabMenu.getMenuId()).append("\"), setting, ")
						.append(JSON.toJSONString(menuTree)).append(");\n\n");
			}
		}
		//获取所有模块tree到搜索栏 add by zb on 20191204
		strb.append("$.fn.zTree.init($(\"#tab_search_inner\"), settingSearch, ")
			.append(JSON.toJSONString(allMenuList)).append("); \n\n");
		return strb.toString();
	}
	
	/**
	 * app用户登入
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/appLogin")
	public void appLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("获取用户登录信息并进行判断");
		try {
//			if (!this.checkVerify(request, response)) {
//				return;
//			}
			String loginName = request.getParameter("username");
			String password = request.getParameter("password");

			if (StringUtils.isBlank(loginName)) {
				ServletUtils.outPrintWithToken(request, response, "请输入用户名");
				logger.debug("用户名为空，返回登入界面");
				return;
			}
			if (StringUtils.isBlank(password)) {
				ServletUtils.outPrintWithToken(request, response, "请输入密码");
				logger.debug("密码为空，返回登入界面");
				return;
			}

			logger.debug("根据用户名={} 密码={} 获取用户对象", loginName, password);
			Czybm czybm = czybmService.getByCzybhAndMm(loginName.trim(), password);

			if (czybm == null) {
				ServletUtils.outPrintWithToken(request, response,
						"用户名或密码错误，请重试");
				logger.debug("用户名或密码错误,获取用户对象为空,验证失败");
				return;
			}
			
			if (czybm.getZfbz()==null || czybm.getZfbz().booleanValue()==true){
				logger.debug("用户 ID={} 的状态不可用", czybm.getCzybh());
				ServletUtils.outPrintWithToken(request, response, "您的帐号已作废，请联系管理员。");
				return;
			}
			if(StringUtils.isNotBlank(czybm.getCzylb())){
				if("2".equals(czybm.getCzylb())){
					Supplier supplier= czybmService.get(Supplier.class, czybm.getEmnum());
					if(supplier!=null){
						if("T".equalsIgnoreCase(supplier.getExpired())){
							logger.debug("用户 ID={} 对应的供应商状态已过期", czybm.getCzybh());
							ServletUtils.outPrintWithToken(request, response, "您的帐号已过期，请联系管理员。");
							return;	
						}
					}
				}else if("1".equals(czybm.getCzylb())){
					Employee employee = czybmService.get(Employee.class, czybm.getEmnum());
					if(employee!=null){
						if("T".equalsIgnoreCase(employee.getExpired())||"SUS".equals(employee.getStatus())){
							logger.debug("用户 ID={} 对应的员工状态已过期", czybm.getCzybh());
							ServletUtils.outPrintWithToken(request, response, "您的帐号已过期，请联系管理员。");
							return;		
						}
					}
				}			
			}
			HttpSession session = request.getSession(false);
			if (session!=null && !session.isNew()) {
			    session.invalidate();
			}
			request.getSession(true);

			bind(czybm, request);
			clearVerify(request);
			// 写登陆日志
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("用户登入出现异常: " + e.getMessage(), e);
			ServletUtils.outPrintWithToken(request, response,
					"用户登入出现异常: " + e.getMessage());
			return;
		}
		ServletUtils.outPrintSuccess(request, response, "用户登入成功");
		return;
	}
	
	//@RequestMapping("/.well-known/acme-challenge/*")
    public ResponseEntity<String> check(HttpServletRequest request, HttpServletResponse response){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
        String result="";
        try {
            String URI = request.getRequestURI().replace("/","\\");
            String path = "D:\\letsencrypt-win-simple.V1.9.1\\app.u-om.com\\";
            //文件路径自行替换一下就行,就是上图中生成验证文件的路径,因为URI中已经包含了/.well-known/acme-challenge/,所以这里不需要
            File file=new File(path+URI);
            InputStream is = new FileInputStream(file);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(("验证文件").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        }catch (Exception e){

        }
        return new ResponseEntity<String>(result, responseHeaders, HttpStatus.OK);
    }
	
	//@RequestMapping("/.well-known/acme-challenge/*")
	//@ResponseBody
    public String check2(HttpServletRequest request, HttpServletResponse response){
        String result="";
        try {
            String URI = request.getRequestURI().replace("/","\\");
            String path = "D:\\letsencrypt-win-simple.V1.9.1\\app.u-om.com\\";
            //文件路径自行替换一下就行,就是上图中生成验证文件的路径,因为URI中已经包含了/.well-known/acme-challenge/,所以这里不需要
            result = FileHelper.readTxtFile(path + URI);
        }catch (Exception e){

        }
        return result;
    }
    

}
