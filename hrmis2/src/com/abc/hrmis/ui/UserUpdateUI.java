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
			System.out.printf("�޸ĳɹ�!�����µ�¼ϵͳ!\n\n");
		}
		
	}
	
	/**
	 * �û����¼��
	 * @return
	 */
	public String getUserNo() {
		
		while(true) {
			try {
				System.out.print("Enter UserNo:");
				String entry = SysUtils.getEntry();
				
				if(entry.matches("[a-zA-Z0-9_]{1,}")) {
					return entry;
				}else System.out.println("invalid entered!(ֻ����������ĸ�����֡��»���)");
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No UserNo entered �C try again��");
			}
			
		}
	}

	/**
	 * �û�����¼��
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
					else System.out.println("���벻һ��");
				}else System.out.println("invalid entered!(ֻ����������ĸ������)");
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No UserPwd entered �C try again��");
			}
			
		}
	}
}
