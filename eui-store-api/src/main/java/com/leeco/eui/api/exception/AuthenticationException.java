package com.leeco.eui.api.exception;
public class AuthenticationException extends RuntimeException {
	 
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public AuthenticationException(String msg) {
		super(msg);
	}
}