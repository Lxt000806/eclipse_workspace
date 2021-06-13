/**
 * 
 */
package com.abc.hrmis.domain;

/**
 * 用户类
 * 
 * @author HY
 *
 */
public class User extends ValueObject {

	/** 用户编号 */
	private String userNo;
	/** 用户密码 */
	private String userPwd;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	/**
	 * 读取用户信息串，生成用户对象
	 * @param userInfoStr
	 * @return
	 */
	public static User parseUserByStr(String userInfoStr) {
		String[] sections = userInfoStr.split("\\:");
		User user = new User();
		user.setUserNo(sections[0]); 
		user.setUserPwd(sections[1]);
		
		return user;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", this.userNo,this.userPwd);
	}
	
	

}
