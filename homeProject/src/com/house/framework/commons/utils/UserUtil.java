package com.house.framework.commons.utils;

import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.identity.User;

/**
 * 用户工具类
 * 
 * @author HenryYan
 */
public class UserUtil {

	public static final String USER = "actUser";

	/**
	 * 设置用户到session
	 * 
	 * @param session
	 * @param user
	 */
	public static void saveUserToSession(HttpServletRequest request, User user) {
		request.getSession().setAttribute(USER, user);
	}

	/**
	 * 从Session获取当前用户信息
	 * 
	 * @param session
	 * @return
	 */
	public static User getUserFromSession(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(USER);
		return attribute == null ? null : (User) attribute;
	}

}
