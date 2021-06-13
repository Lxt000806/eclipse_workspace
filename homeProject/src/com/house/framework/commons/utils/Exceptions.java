package com.house.framework.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @ClassName: Exceptions 
 * @Description: TODO(关于异常的工具类.) 
 *
 */
public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}
	
	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(String message, Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(message, e);
		}
	}
	
	/**
	 * 产生UncheckedException实例
	 */
	public static RuntimeException unchecked(String message) {
		return new RuntimeException(message);
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
