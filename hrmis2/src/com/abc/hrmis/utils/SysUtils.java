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
 * ϵͳ������
 * @author HY
 *
 */
public class SysUtils {
	private static String loginedUserNo;

	/**
	 * ���¼�룬�����������
	 * @return
	 */
	public static String getEntry(){
		return getEntry(false);
	}
	
	/**
	 * ���¼��
	 * @param allowBlank �Ƿ����������
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
	 * �մ����
	 * @param str
	 * @return
	 */
	public static boolean isBlankStr(String str){
		return str==null || str.trim().length()==0;
	}
	
	/**
	 * ��ͣ
	 */
	public static void pause(){
		SysUtils.getEntry(true);
	}
	
	/**
	 * ����ʾ����ͣ
	 * @param msg ��ʾ��Ϣ
	 */
	public static void pause(String msg){
		System.out.println(msg);
		pause();
	}
	
	/**
	 * ����ui����
	 * @param ui
	 */
	public static void runUI(BaseUI ui) {
		ui.setup();
	}
	
	/**
	 * ����UI����
	 * @param type UI��������
	 */
	public static void runUI(UIType type) {
		UIFactory.getInstance().getUI(type).setup();
	}

	/**
	 * �����¼���û�����
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
