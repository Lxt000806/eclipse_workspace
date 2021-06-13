/**
 * 
 */
package com.abc.hrmis.domain;

/**
 * �û���
 * 
 * @author HY
 *
 */
public class User extends ValueObject {

	/** �û���� */
	private String userNo;
	/** �û����� */
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
	 * ��ȡ�û���Ϣ���������û�����
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
