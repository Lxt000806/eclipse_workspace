package com.house.framework.web.servlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;

public class MySessionContext {
    private static Map<String, HttpSession> mymap = new ConcurrentHashMap<String, HttpSession>();

    public static void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static HttpSession getSession(String jsessionid) {
        if (jsessionid == null) 
        	return null;
        return (HttpSession) mymap.get(jsessionid);
    }

}
