/**
 * 
 */
package com.abc.hrmis.ui;

import java.util.List;

import com.abc.hrmis.dao.UserDao;
import com.abc.hrmis.dao.UserDaoTxtImpl;
import com.abc.hrmis.domain.User;
import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.MD5Utils;
import com.abc.hrmis.utils.SysUtils;

/**
 * 用户登录界面
 * @author HY
 *
 */
public class UserLoginUI implements BaseUI {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
	@Override
	public void setup() {
		
		while(true) {
			try {
				System.out.print("Enter userNo:");
				String userNo = SysUtils.getEntry();
				
				System.out.print("Enter userPwd:");
				String userPwd = SysUtils.getEntry();
				
				if(vertifyAccount(userNo, userPwd)) {
					SysUtils.saveUserNo(userNo);
					SysUtils.runUI(UIType.MAIN_MENU);					
					break;
				}else {
					SysUtils.pause("\nPress Enter to continue...");
					continue;
				}
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No entered – try again…");
			}
			
		}
		
		
				
	}
	
	/**
	 * 登录验证
	 */
	private static boolean vertifyAccount(String userNo,String userPwd) {
		
		UserDao userDao = new UserDaoTxtImpl();
		User user = userDao.getUserByNo(userNo);
		
		if(user!=null) {
			if(user.getUserPwd().equals(MD5Utils.encrypt(userPwd))) {
				System.out.printf("login success!\n\n");
				return true;
				
			}else System.out.println("userPwd error!");
			
		}else System.out.println("userNo error!");
			
		return false;
	}

}
