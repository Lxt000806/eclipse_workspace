package com.house.framework.web.login;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.house.framework.commons.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Lists;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.exceptions.ForbidVisitException;
import com.house.framework.web.servlet.MySessionContext;
import com.house.home.entity.basic.Table;
import com.house.home.service.basic.TableService;

/**
 * url访问过滤
 *
 */
public class ForbidVisitInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(ForbidVisitInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri=request.getRequestURI().substring(request.getContextPath().length());
		if(uri.endsWith("/")){
			uri=uri.substring(0, uri.length()-2);
		}
		if(!(uri.startsWith("/resources-") 
				|| uri.startsWith("/formToken")
				|| uri.equals("") 
				|| uri.startsWith("/login")
				|| uri.startsWith("/client")
				|| uri.startsWith("/admin/czybh/goFindPwd")
				|| uri.startsWith("/admin/czybh/resetPwd")
				|| uri.startsWith("/admin/czybh/sendMail")
				|| uri.startsWith("/admin/fileUpload") // 20200611 add by xzp 文件上传接口先不控制登录，安全问题后续改进
				|| uri.endsWith(".asp") //为了避开安全检测
				|| uri.endsWith(".php") //为了避开安全检测
				|| uri.startsWith("/admin/custContract/eSignCallBack")
				)){
			
			HttpSession session = request.getSession();
			
			/** swfupload附件上传session处理 */
			if(uri.startsWith("/swfupload/swfupload_upload")){
				String jsessionid = (String)request.getParameter("jsessionid");
				session = MySessionContext.getSession(jsessionid);
			}
			
			UserContext uc =(UserContext) session.getAttribute(CommonConstant.USER_CONTEXT_KEY);

			// APP将会直接访问页面
			if(uc == null){
				uc =(UserContext) session.getAttribute(CommonConstant.USER_APP_KEY);
				if(uc==null){
					//多的权限没有判断少的是否有
					uc =(UserContext) session.getAttribute(CommonConstant.CLIENT_APP_KEY);
				}
			}
			
			if(null == uc || uc.getCzybh() == null){
				String scheme = request.getHeader("x-forwarded-proto");
				if (StringUtils.isBlank(scheme)) {
					scheme = request.getScheme();
				}
				String loginUrl=scheme+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/login.jsp";

				if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					String info="{\"rs\":false,\"msg\":\"用户未登入\"}";
					out.print(info);
					out.close();
					out.flush();
					return false;
				}else{
					response.setHeader("Location", loginUrl);
					response.sendRedirect(loginUrl);
					return false;
				}
			}else{
				//防止uri '/  '后面为空格情况
				if(uri.indexOf("%20") != -1){
					uri = uri.replaceAll("%20", "");
				}
				List<String> forbidUrls = uc.getForbidUrls();
				if(!uc.isSuperAdmin() && forbidUrls !=null && forbidUrls.size() > 0){
					if(forbidUrls.indexOf(uri.split("/admin")[1].split(";")[0].split("\\?")[0]) != -1 
							|| forbidUrls.indexOf(uri.split(";")[0].split("\\?")[0]) != -1){ //uri 后面可能有';'或'?' 带参数  
						
						logger.error("禁止访问! 操作员编号: " + uc.getCzybh().trim() + ", uri: " + uri + "\n"
								+ "\n该操作员所有禁止访问的uri: \n" + forbidUrls);
						throw new ForbidVisitException("禁止访问");
					}
				}
			}
			try {
				UserContextHolder.set(uc);
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.error("绑定Session中用户信息到ThreadLocal 出现异常: "+e.getMessage(), e);
			}
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//页面路径
		String uri=request.getRequestURI().substring(request.getContextPath().length());
		//配置需要增强设置列功能的页面路径
		List<String> uriList = Lists.newArrayList("/admin/customerXx/goList");
		
		if(uriList.contains(uri)){
			//用户上下文
			UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			//从spring容器中取tableService
			TableService tableService = (TableService)SpringContextHolder.getBean(TableService.class);
			//初始化参数
			String isShow = "";
			String model = "";
			String colModel = "[";
			Table table = new Table();
			table.setModuleUrl(uri);
			table.setCzybh(uc.getCzybh());
			table.setIsMain("1");
			table.setTableCode("");
			//固定列数
			Integer frozenNum = 0;
			Map<String, Object> tableCzyMapperMap = tableService.getTableCzyMapper(table); 
			if (tableCzyMapperMap != null) {
				frozenNum = Integer.parseInt(tableCzyMapperMap.get("FrozenNum").toString());
			}
			//集合字段
			List<Map<String, Object>> columnList = tableService.getColModel(table);
			//拼接固定列、是否显示
			int j = 0;
			for (int i = 0; i < columnList.size(); i++) {
				Map<String, Object> column = columnList.get(i);
				isShow = column.get("IsShow").toString();
				model = column.get("Model").toString();
				if("0".equals(isShow.trim())){
					model = model.replace("{", "{hidden:true,");
					j++;
				}
				if (i < frozenNum+j) {
					model = model.replace("{", "{frozen:true,");
				}
				colModel += model.trim();
			}
			colModel+=']';
			System.out.println("接口地址："+uri+"，操作员编码："+uc.getCzybh()+"，拼接结果："+colModel);
			//视图增加参数
			modelAndView.addObject("colModel", colModel)
			.addObject("moduleUrl", uri)
			.addObject("tableCode", table.getTableCode());
			
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}


