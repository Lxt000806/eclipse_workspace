package com.house.framework.exceptions;

public class BaseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BaseException(String message){
		super(message);
	}
	
	public BaseException(String message,Throwable cause){
		super(message, cause);
	}

}
