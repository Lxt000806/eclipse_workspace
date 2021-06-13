/**
 * 
 */
package com.abc.hrmis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.ui.BaseUI;
import com.abc.hrmis.ui.UIFactory;
import com.abc.hrmis.ui.UIType;

/**
 * 系统工具类
 * @author HY
 *
 */
public class SysUtils {
	private static String loginedUserNo;

	/**
	 * 获得录入，不允许空输入
	 * @return
	 */
	public static String getEntry(){
		return getEntry(false);
	}
	
	/**
	 * 获得录入
	 * @param allowBlank 是否允许空输入
	 * @return
	 */
	public static String getEntry(boolean allowBlank){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String entry = null;
		
		try {
			entry = reader.readLine();
			if(isBlankStr(entry) && !allowBlank)
				throw new BlankEntryException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entry;
	}
	
	/**
	 * 空串检测
	 * @param str
	 * @return
	 */
	public static boolean isBlankStr(String str){
		return str==null || str.trim().length()==0;
	}
	
	/**
	 * 暂停
	 */
	public static void pause(){
		SysUtils.getEntry(true);
	}
	
	/**
	 * 带提示的暂停
	 * @param msg 提示消息
	 */
	public static void pause(String msg){
		System.out.println(msg);
		pause();
	}
	
	/**
	 * 启动ui界面
	 * @param ui
	 */
	public static void runUI(BaseUI ui) {
		ui.setup();
	}
	
	/**
	 * 启动UI界面
	 * @param type UI类型名称
	 */
	public static void runUI(UIType type) {
		UIFactory.getInstance().getUI(type).setup();
	}

	/**
	 * 保存登录的用户名称
	 * @param userNo
	 * @return
	 */
	public static void saveUserNo(String userNo) {
		loginedUserNo = userNo;
	}
	
	public static String getLoginedUserNo() {
		return loginedUserNo;
	}
}
