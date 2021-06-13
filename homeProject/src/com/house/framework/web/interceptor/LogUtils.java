package com.house.framework.web.interceptor;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.service.MenuService;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.basic.Logo;
import com.house.home.entity.basic.SysLog;
import com.house.home.service.basic.LogoService;
import com.house.home.service.basic.SysLogService;

/**
 * 
* @ClassName: LogUtils 
* @Description: 字典工具类
* @author liuxiaobin
* @date 2015年8月24日 下午2:38:13 
*
*/
public class LogUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(LogUtils.class);
	
	public static final String RESOURCE_PATH_CACHE = "resourcePathCache";
	
	public static SysLogService sysLogService = SpringContextHolder.getBean(SysLogService.class);
	
	public static LogoService logoService = SpringContextHolder.getBean(LogoService.class);
	
	public static MenuService menuService = SpringContextHolder.getBean(MenuService.class);
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title){
		saveLog(request, null, null, title);
	}
	
	/**
	 * 保存增删改日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		UserContext userContext = UserContextHolder.getUserContext();
		if (userContext != null){
			String uri = request.getRequestURI();
			// 20190226 mark by xzp 原先只保存增删改操作(/do开头)的日志，改为：增删改操作的日志记录到数据库，其他请求的日志记录到硬盘
			/*
			if ((uri.lastIndexOf("/")==uri.lastIndexOf("/do")
					&& uri.lastIndexOf("/")!=uri.lastIndexOf("/doExcel"))
					|| uri.lastIndexOf("/")==uri.lastIndexOf("/goViewPhone")){
				
			}else{
				return;
			}
			*/
			Map<String, String[]> map = request.getParameterMap();
//			if (map==null || map.size()==0){
//				return;
//			}
			SysLog syslog = new SysLog();
			syslog.setTitle(title);
			syslog.setType(ex == null ? SysLog.TYPE_ACCESS : SysLog.TYPE_EXCEPTION);
			syslog.setAppType(SysLog.TYPE_BS);
			syslog.setRemoteAddr(StringUtils.getRemoteAddr(request));
			syslog.setUserAgent(StringUtils.substring(request.getHeader("user-agent"),0,300));
			syslog.setRequestUrl(StringUtils.substring(uri, 0, 100));
			syslog.setParams(map);
			syslog.setMethod(request.getMethod());
			syslog.setOperId(userContext.getCzybh());
			syslog.setOperDate(new Date());
			
			// 20190226 add by xzp 增加判断条件，增删改操作(/do开头)的日志保存到数据库
			if ((uri.lastIndexOf("/")==uri.lastIndexOf("/do")
					&& uri.lastIndexOf("/")!=uri.lastIndexOf("/doExcel"))
					|| uri.lastIndexOf("/")==uri.lastIndexOf("/goViewPhone")){
				// 异步保存日志
				sysLogService.saveLog(syslog, handler, ex);
			}
			
			// 20191127 add by xzp 访问主页以及各模块时须记录到模块日志表tLOGO
			if (uri.lastIndexOf("/")==uri.lastIndexOf("/goList")
					|| uri.lastIndexOf("/")==uri.lastIndexOf("/main")){
				Logo logo = new Logo();
				if (uri.lastIndexOf("/")==uri.lastIndexOf("/main")) {
					logo.setMkdm("9901");
					logo.setMenuID(0);
				} else if (uri.lastIndexOf("/")==uri.lastIndexOf("/goList")) {
					String servletPath = request.getServletPath();
					String pathInfo = request.getPathInfo();
					String url = (servletPath==null ? "" : servletPath) + (pathInfo==null ? "" : pathInfo);
					Map<String, Object> menuMap = menuService.getMenuMsgByUrl(url);
					if (menuMap != null) {
						logo.setMkdm((String) menuMap.get("MKDM"));
						logo.setMenuID((Integer) menuMap.get("MENUID"));
					} else {
						logo.setMkdm("");
						logger.error("URI：" + uri +  "， 没有找到匹配的模块！");
					}
				}
				logo.setIp(StringUtils.getRemoteAddr(request));
				logo.setDlrq(DateUtil.format(DateUtil.getNow(), "yyyyMMdd"));
				logo.setDlsj(DateUtil.format(DateUtil.getNow(), "HHmmss"));
				logo.setTcrq(DateUtil.format(DateUtil.getNow(), "yyyyMMdd"));
				logo.setTcsj(DateUtil.format(DateUtil.getNow(), "HHmmss"));
				logo.setDlcgbz(1);
				logo.setCzybh(userContext.getCzybh());
				logo.setMac("0");
				
				// 异步保存日志
				logoService.saveLog(logo, handler, ex);
			} 
			
			logger.info("{title="+syslog.getTitle()+";type="+syslog.getType()+";remoteAddr="+syslog.getRemoteAddr()+
					";userAgent="+syslog.getUserAgent()+";requestUrl="+syslog.getRequestUrl()+";param="+syslog.getAllParams()+
					";method="+syslog.getMethod()+";operId="+syslog.getOperId()+";operDate="+syslog.getOperDate()+"}");
		}
	}


}
