package com.house.framework.web.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session 监听器
 *
 */
public class MySessionListener implements HttpSessionListener  {
	
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		MySessionContext.AddSession(httpSessionEvent.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		MySessionContext.DelSession(session);
	}


}
