package com.house.framework.exceptions;

public class ForbidVisitException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 禁止访问
	 * @param message the message
	 * @param cause the cause
	 */
	public ForbidVisitException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * 禁止访问
	 * @param message the message
	 */
	public ForbidVisitException(String message) {
		super(message);
	}
}
