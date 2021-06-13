/**
 * 
 */
package com.mju.hrmis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mju.hrmis.exception.BlankEntryException;

/**
 * 系统工具类
 * @author HY
 *
 */
public class SysUtils {
	
	public static String getEntry() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String entry = null;
		try {
			entry = reader.readLine();
			if(isBlankStr(entry))
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
	public static boolean isBlankStr(String str) {
		return str==null || str.trim().length()==0;
	}
}
