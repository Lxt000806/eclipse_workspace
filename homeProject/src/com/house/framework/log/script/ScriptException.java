package com.house.framework.log.script;

public class ScriptException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ScriptException() {
	}

	public ScriptException(String message) {
		super(message);

	}

	public ScriptException(Throwable cause) {
		super(cause);

	}

	public ScriptException(String message, Throwable cause) {
		super(message, cause);

	}

}
