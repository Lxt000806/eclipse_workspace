/**
 * 
 */
package com.abc.hrmis.utils;

import java.text.SimpleDateFormat;

/**
 * 系统常量
 * @author HY
 *
 */
public class Constants {

	//磁盘存储路径
	public static final String DATE_FILE_PATH = "d:/records.txt";
	
	//用户文件存储路径
	public static final String USER_FILE_PATH = "d:/users.txt";
	
	//系统专用日期格式化
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
}
