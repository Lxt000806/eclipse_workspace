package com.mju.hrmis.exception;

public class HRMISException extends RuntimeException {
	
	public HRMISException() {
		
	}

	public HRMISException(String message) {
		super(message);
		
	}
	
	public HRMISException(Throwable cause) {
		super(cause);
		
	}
	
	public HRMISException(String message,Throwable cause) {
		super(message,cause);
		
	}
	
	public HRMISException(String message,Throwable cause,boolean enableSuppression,boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}
}
