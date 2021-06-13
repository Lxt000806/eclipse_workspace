package com.house.framework.web.login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.house.framework.commons.conf.CommonConstant;

/**
 * 用户登入过滤器
 *
 */
public class LogonFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(LogonFilter.class);
	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String uri=request.getRequestURI();
		System.out.println(uri);
		if(!(uri.startsWith(request.getContextPath()+"/resources-") 
				|| uri.equals(request.getContextPath()+"/") 
				|| uri.equals(request.getContextPath()) 
				|| uri.startsWith(request.getContextPath()+"/login")
				|| uri.startsWith(request.getContextPath()+"/formToken")
				)){
			UserContext uc =(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			if(null == uc || StringUtils.isBlank(uc.getCzybh())){
				logger.debug("过滤器发现用户未登入或用户Session超时");
				HttpServletResponse response = (HttpServletResponse)res;
				String url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
				if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					String info="{\"rs\":false,\"msg\":\"用户未登入\"}";
					out.print(info);
					out.close();
					out.flush();
					return ;
				}else{
					response.setHeader("Location", url);
					response.sendRedirect(url);
					return ;
				}
			}
			try {
				UserContextHolder.set(uc);
			} catch (RuntimeException e) {
				logger.error("绑定Session中用户信息到ThreadLocal 出现异常: "+e.getMessage(), e);
			}
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
