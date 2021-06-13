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
 * �û�ע�����
 * @author HY
 *
 */
public class UserRegisterUI implements BaseUI {

	UserDao userDao = new UserDaoTxtImpl();
	
	@Override
	public void setup() {		
		User user = new User();
		
		user.setUserNo(this.getUserNo());	
		user.setUserPwd(this.getUserPwd());
		
		userDao.AddUser(user);
		System.out.printf("Record Saved\n\n");

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
				User user = userDao.getUserByNo(entry);
				
				if(user!=null){					
					SysUtils.pause("The UserNo aleady exists");				
					continue;			
				}else if(entry.matches("[a-zA-Z0-9_]{1,}")) {
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
