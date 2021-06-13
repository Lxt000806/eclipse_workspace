/**
 * 
 */
package edu.mju.stuwork.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author joeyang ong
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	    
		//获取访问地址
	    String uri = request.getRequestURI();
	    
//	    System.out.println(uri);
	    if(uri.startsWith("/stuinfo3/students") ){
	    	Object user = request.getSession().getAttribute("loginedUser");
	    	if(user==null)
	    	{
	    		System.out.println("login fail!");
	    		response.sendRedirect("/stuinfo3/login");
	    		return false;
	    	}
	    }
		
		return true;
	} 
	
	
}
