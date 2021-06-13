/**
 * 
 */
package com.abc.hrmis.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.abc.hrmis.domain.User;
import com.abc.hrmis.utils.Constants;
import com.abc.hrmis.utils.MD5Utils;
import com.abc.hrmis.utils.SysUtils;

/**
 * @author HY
 *
 */
public class UserDaoTxtImpl implements UserDao {

	@Override
	public List<User> loadUsers() {
		BufferedReader reader = null;
		String userInfoStr = null;
		List<User> userList = new ArrayList<>();
		
		try {
			reader = new BufferedReader( new InputStreamReader(new FileInputStream(Constants.USER_FILE_PATH)));
			
			while((userInfoStr=reader.readLine())!=null) {
				userList.add(User.parseUserByStr(userInfoStr));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userList;
	}

	@Override
	public User getUserByNo(String userNo) {
		List<User> userList = this.loadUsers();
		for(User user:userList) {
			if(user.getUserNo().equals(MD5Utils.encrypt(userNo))) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void AddUser(User user) {
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(Constants.USER_FILE_PATH,true));
			//Ω¯––MD5º”√‹¥Ê¥¢
			user.setUserNo(MD5Utils.encrypt(user.getUserNo()));
			user.setUserPwd(MD5Utils.encrypt(user.getUserPwd()));
			writer.println(user);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writer.flush();
			writer.close();
		}
		
	}
	
	@Override
	public void updateUser(User newUser) {
		List<User> userList = this.loadUsers();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(Constants.USER_FILE_PATH));
			for(User user:userList) {
				if(user.getUserNo().equals(MD5Utils.encrypt(SysUtils.getLoginedUserNo()))) {
					user.setUserNo(MD5Utils.encrypt(newUser.getUserNo()));
					user.setUserPwd(MD5Utils.encrypt(newUser.getUserPwd()));
				}
				writer.println(user);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writer.flush();
			writer.close();
		}
		
	}

	

}
