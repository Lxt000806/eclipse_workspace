/**
 * 
 */
package com.abc.hrmis.exception;

/**
 * HRMIS系统基础异常
 * @author HY
 *
 */
public class HRMISException extends RuntimeException {

	/**
	 * 
	 */
	public HRMISException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public HRMISException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public HRMISException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public HRMISException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public HRMISException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
