package com.house.framework.web.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨站点脚本xss问题
 * @describe 安全信息审核类
 */
public class XSSSecurityFilter implements Filter{
	private String regex;
	/**
	 * 初始化操作
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		regex = filterConfig.getInitParameter("regex");
	}

	/**
	 * 安全审核
	 * 读取配置信息
	 */
	@SuppressWarnings("unused")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest)request).getRequestURI();
		Matcher matcher = Pattern.compile(regex).matcher(uri);
		if(matcher.find()){
			chain.doFilter(request, response);
		}else{
			// 判断是否使用HTTP
	        checkRequestResponse(request, response);
	        // 转型
	        HttpServletRequest httpRequest = (HttpServletRequest) request;
	        HttpServletResponse httpResponse = (HttpServletResponse)response;
	        //设置httponly跨站脚本问题
	        //httpResponse.setHeader("Set-Cookie", "cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;HTTPOnly");

	        // http信息封装类
			XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(httpRequest);
			xssRequest.validateXssFilter();  
	        chain.doFilter(xssRequest, response);
		}
	}


	/**
	 * 销毁操作
	 */
	public void destroy() {
		
	}

	/**
     * 判断Request ,Response 类型
     * @param request
     *            ServletRequest
     * @param response
     *            ServletResponse
     * @throws ServletException 
     */
    private void checkRequestResponse(ServletRequest request,
            ServletResponse response) throws ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");

        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
    }
}

