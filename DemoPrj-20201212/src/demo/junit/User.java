/**
 * 
 */
package demo.junit;

/**
 * @author HY
 *
 */
public class User {
	/** 用户邮箱 */
	private String userEmail;
	/** 用户名称 */
	private String userName;
	/** 用户电话 */
	private String userPhone;

	public String getUserEmail() {
		return userEmail;
	}

	public boolean setUserEmail(String userEmail) {
		if(userEmail.matches("[^@\\.]*@[A-Za-z0-9\\\\-]*\\.[A-Za-z0-9\\-]*")) {
			this.userEmail = userEmail;
			return true;
		}
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public boolean setUserName(String userName) {
		if(userName.matches("[a-zA-Z ]{1,}")) {
			this.userName = userName;
			return true;
		}
		
		return false;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public boolean setUserPhone(String userPhone) {
		if(userPhone.matches("1[3578][0-9]{9}")) {
			this.userPhone = userPhone;
			return true;
		}
		return false;
	}

}
