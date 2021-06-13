/**
 * 
 */
package com.abc.hrmis.ui;

import com.abc.hrmis.dao.UserDao;
import com.abc.hrmis.dao.UserDaoTxtImpl;
import com.abc.hrmis.domain.User;
import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.SysUtils;

/**
 * @author HY
 *
 */
public class UserUpdateUI implements BaseUI {
	
	UserDao userDao = new UserDaoTxtImpl();	
	
	@Override
	public void setup() {
		 
        User newuser = new User();
		
        newuser.setUserNo(this.getUserNo());	
        newuser.setUserPwd(this.getUserPwd());
        
        System.out.print("Are you sure update? (y)es or (n)o,");
		if(SysUtils.getEntry().equalsIgnoreCase("y")) {
			userDao.updateUser(newuser);
			System.out.printf("修改成功!请重新登录系统!\n\n");
		}
		
	}
	
	/**
	 * 用户编号录入
	 * @return
	 */
	public String getUserNo() {
		
		while(true) {
			try {
				System.out.print("Enter UserNo:");
				String entry = SysUtils.getEntry();
				
				if(entry.matches("[a-zA-Z0-9_]{1,}")) {
					return entry;
				}else System.out.println("invalid entered!(只允许输入字母、数字、下划线)");
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No UserNo entered – try again…");
			}
			
		}
	}

	/**
	 * 用户密码录入
	 * @return
	 */
	public String getUserPwd() {
		
		while(true) {
			try {
				System.out.print("Enter UserPwd:");
				String entry = SysUtils.getEntry();
				
				System.out.print("Repeat the UserPwd:");
				String entry2 = SysUtils.getEntry();
				
				if(entry.matches("[a-zA-Z0-9]{1,}")){
					if(entry.equals(entry2))
						return entry;
					else System.out.println("密码不一致");
				}else System.out.println("invalid entered!(只允许输入字母、数字)");
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No UserPwd entered – try again…");
			}
			
		}
	}
}
