package com.house.framework.web.login;

public class UserContextHolder {
	private static ThreadLocal<UserContext> holder = new ThreadLocal<UserContext>();
	
	private UserContextHolder(){}
	
	public static void set(UserContext userContext) {
		if(userContext != null)
			holder.set(userContext);
	}
	
	public static UserContext getUserContext(){
		return holder.get();
	}
}
